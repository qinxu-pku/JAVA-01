package org.example.proxy;

import org.apache.curator.framework.CuratorFramework;
import org.example.common.entity.ServiceProviderDesc;
import org.example.register.ServiceDiscovery;
import org.example.register.zk.ZkServiceDiscovery;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    private ServiceDiscovery serviceDiscovery;

    public RpcClientProxy() {
        serviceDiscovery = new ZkServiceDiscovery();
    }

    public  <T> T createFromRegistry(CuratorFramework client, Class<T> serviceClass) {
        ServiceProviderDesc serviceProviderDesc = serviceDiscovery.discoveryService(client, serviceClass.getName());
        return create(serviceClass, "http://" + serviceProviderDesc.getHost() + ":" + serviceProviderDesc.getPort());
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        return (T) Proxy.newProxyInstance(RpcClientProxy.class.getClassLoader(), new Class[]{serviceClass}, new HttpInvocationHandler(serviceClass, url));

    }
}
