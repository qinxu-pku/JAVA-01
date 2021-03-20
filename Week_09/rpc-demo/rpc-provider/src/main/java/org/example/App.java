package org.example;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.example.provider.ServiceProvider;
import org.example.register.zk.ZkServiceRegistry;
import org.example.remoting.transport.netty.http.server.HttpNettyServer;

public class App {
    public static void main(String[] args) throws InterruptedException {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").namespace("rpcfx").retryPolicy(retryPolicy).build();
        client.start();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.addService(new TestDemoServiceImpl(), DemoService.class.getName());
        new ZkServiceRegistry().registerService(client, DemoService.class.getName());
        new HttpNettyServer(serviceProvider).run();
    }
}
