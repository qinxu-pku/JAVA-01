package org.example.provider;


import org.example.exception.RpcException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServiceProvider {

    private final Map<String, Object> serviceMap = new HashMap<>();

    public void addService(Object service, String serviceName) {
        serviceMap.put(serviceName, service);
    }

    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (Objects.isNull(service)) {
            throw new RpcException(RpcException.SERVICE_NOT_FIND, "找不到服务");
        }
        return service;
    }
}
