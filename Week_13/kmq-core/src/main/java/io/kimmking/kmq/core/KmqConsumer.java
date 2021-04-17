package io.kimmking.kmq.core;

import java.util.HashMap;

public class KmqConsumer<T> {

    private final KmqBroker broker;

    private Kmq kmq;

    private int offset;

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
    }

    public KmqMessage<T> poll() {
        KmqMessage message = kmq.poll(offset);
        if (message != null) {
            if (message.getHeaders() == null) {
                message.setHeaders(new HashMap<>());
            }
            message.getHeaders().put("offset", offset);
        }
        return message;
    }

    public void commitOffset(int offset) {
        this.offset = ++offset;
    }

}
