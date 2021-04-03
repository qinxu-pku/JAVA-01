package io.kimmking.cache.demo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;


public class CacheDemo {
    public static Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1024)
            .expireAfterAccess(5, TimeUnit.SECONDS).weakValues()
            .build();

    public static void main(String[] args) {
        cache.put("word", "Hello Guava Cache");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("word"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cache.getIfPresent("word"));
    }
}
