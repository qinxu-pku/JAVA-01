package org.example.register;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;

public interface ServiceRegistry {

    @SneakyThrows
    void registerService(CuratorFramework client, String service);
}
