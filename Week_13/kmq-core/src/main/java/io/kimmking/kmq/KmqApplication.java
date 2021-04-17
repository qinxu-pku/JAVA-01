package io.kimmking.kmq;

import com.alibaba.fastjson.JSON;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.demo.Order;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@SpringBootApplication
public class KmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KmqApplication.class, args);
        HttpPost post = new HttpPost("http://127.0.0.1:8080/broker/createTopic");
        // 创建topic
        CloseableHttpResponse response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            StringEntity paramEntity = new StringEntity("testTopic", "UTF-8");
            paramEntity.setContentType("application/json; charset=utf-8");
            post.setEntity(paramEntity);
            response = httpClient.execute(post);
            System.out.println("创建topic：" + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        // 消费者线程
        keepTesting((httpClient)->{
            int hashCode = Thread.currentThread().hashCode();
            // poll
            HttpPost pollPost = new HttpPost("http://127.0.0.1:8080/broker/poll");
            Map<String, Object> map = new HashMap<>();
            map.put("topic", "testTopic");
            map.put("hashcode", String.valueOf(hashCode));
            StringEntity paramEntity = new StringEntity(JSON.toJSONString(map), "UTF-8");
            paramEntity.setContentType("application/json; charset=utf-8");
            pollPost.setEntity(paramEntity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(pollPost)) {
                String responseStr = EntityUtils.toString(httpResponse.getEntity());
                KmqMessage message = JSON.parseObject(responseStr, KmqMessage.class);
                if (message != null) {
                    Integer offset = (Integer) message.getHeaders().get("offset");
                    System.out.println("收到消息：" + message.getBody());
                    // commitConsumerOffset
                    HttpPost offsetPost = new HttpPost("http://127.0.0.1:8080/broker/commitConsumerOffset");
                    map = new HashMap<>();
                    map.put("hashcode", String.valueOf(hashCode));
                    map.put("offset", offset + 1);
                    paramEntity = new StringEntity(JSON.toJSONString(map), "UTF-8");
                    paramEntity.setContentType("application/json; charset=utf-8");
                    offsetPost.setEntity(paramEntity);
                    try (CloseableHttpResponse httpResponse1 = httpClient.execute(offsetPost)) {
                        System.out.println("消费者提交offset：" + EntityUtils.toString(httpResponse1.getEntity()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // 生产者线程
        keepTesting((httpClient)->{
            HttpPost sendPost = new HttpPost("http://127.0.0.1:8080/broker/send");
            Map<String, Object> map = new HashMap<>();
            map.put("topic", "testTopic");
            map.put("message", JSON.toJSONString(new KmqMessage(null, new Order(100000L + new Random().nextLong(), System.currentTimeMillis(), "USD2CNY", 6.52d))));
            StringEntity paramEntity = new StringEntity(JSON.toJSONString(map), "UTF-8");
            paramEntity.setContentType("application/json; charset=utf-8");
            sendPost.setEntity(paramEntity);
            try (CloseableHttpResponse httpResponse = httpClient.execute(sendPost)) {
                System.out.println("生产者提交消息：" + EntityUtils.toString(httpResponse.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void keepTesting(Consumer<CloseableHttpClient> consumer) {
        new Thread(()->{
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                while (true) {
                    consumer.accept(httpClient);
                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
