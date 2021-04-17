package io.kimmking.kmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class Kmq {

    public Kmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.messages = new KmqMessage[capacity];
    }

    private String topic;

    private int capacity;

    private int offset;

    private KmqMessage[] messages;

    public boolean send(KmqMessage message) {
        messages[offset] = message;
        offset++;
        return true;
    }

    public KmqMessage poll(int offset) {
        return messages[offset];
    }

}
