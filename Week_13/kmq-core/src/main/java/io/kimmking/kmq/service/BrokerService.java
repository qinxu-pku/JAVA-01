package io.kimmking.kmq.service;

import io.kimmking.kmq.core.Kmq;
import io.kimmking.kmq.core.KmqMessage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BrokerService {

    public static final int CAPACITY = 10000;

    private final Map<String, Kmq> kmqMap = new ConcurrentHashMap<>(64);

    private final Map<String, Integer> consumerOffsetMap = new ConcurrentHashMap<>();

    public Boolean createTopic(String name){
        kmqMap.putIfAbsent(name, new Kmq(name,CAPACITY));
        return true;
    }

    public Kmq findKmq(String topic) {
        return this.kmqMap.get(topic);
    }

    public Boolean send(String topic, KmqMessage message) {
        Kmq kmq = this.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        return kmq.send(message);
    }

    public Boolean commitConsumerOffset(String hashcode, Integer offset) {
        consumerOffsetMap.put(hashcode, offset);
        return true;
    }

    public KmqMessage poll(String topic, String hashcode) {
        int offset = consumerOffsetMap.get(hashcode) == null ? 0 : consumerOffsetMap.get(hashcode);
        Kmq kmq = kmqMap.get(topic);
        KmqMessage message = kmq.poll(offset);
        if (message != null) {
            if (message.getHeaders() == null) {
                message.setHeaders(new HashMap<>());
            }
            message.getHeaders().put("offset", offset);
        }
        return message;
    }

}
