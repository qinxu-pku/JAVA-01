package io.kimmking.cache.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/redis/lock-random")
    public String lockExpireRandom() {
        int id = 1;
        String key = "user:" + id;
        String rand = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean lock = ops.setIfAbsent(key, rand, 5, TimeUnit.SECONDS);
        if (lock != null && lock) {
            log.info("lockExpireRandom:locked");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                releaseLock(key, rand);
                log.info("lockExpireRandom:unlocked");
            }
        }
        return "success";
    }


    private void releaseLock(String key, String value) {
        String s = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        RedisScript<Long> script = RedisScript.of(s, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(key), value);
        System.out.println("releaseLock result:" + result);
    }


    /**
     * 分布式锁实现秒杀扣库存
     * @return
     */
    @RequestMapping("/redis/decr-stock")
    public String decrStock() {
        int productId = 1;
        String key = "product:" + productId;
        String rand = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        Boolean lock = ops.setIfAbsent(key, rand, 15, TimeUnit.SECONDS);
        if (lock != null && lock) {
            log.info("decrStock:locked");
            try {
                //update t_product set stock = stock - 1 where product_id = #{productId} and stock > 0
                //if (return 1) { create order .. } 秒杀成功
                //else return 卖完了
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                releaseLock(key, rand);
                log.info("decrStock:unlocked");
            }
        }
        return "success";
    }
}