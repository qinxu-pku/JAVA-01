package org.example.register.zk;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.example.common.entity.ServiceProviderDesc;
import org.example.register.ServiceRegistry;

import java.net.InetAddress;

public class ZkServiceRegistry implements ServiceRegistry {


    @SneakyThrows
    @Override
    public void registerService(CuratorFramework client, String serviceName) {

        ServiceProviderDesc userServiceSesc = ServiceProviderDesc.builder()
                .host(InetAddress.getLocalHost().getHostAddress())
                .port(8081).serviceClass(serviceName).build();

        if (null == client.checkExists().forPath("/" + serviceName)) {
            client.create().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName, "service".getBytes());
        }
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/" + serviceName + "/" + userServiceSesc.getHost() + "_" + userServiceSesc.getPort(), "provider".getBytes());
    }
}
