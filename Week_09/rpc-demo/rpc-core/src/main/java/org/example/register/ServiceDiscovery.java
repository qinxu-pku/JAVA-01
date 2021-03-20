package org.example.register;

import org.apache.curator.framework.CuratorFramework;
import org.example.common.entity.ServiceProviderDesc;

public interface ServiceDiscovery {
    ServiceProviderDesc discoveryService(CuratorFramework client, String rpcServiceName);
}
