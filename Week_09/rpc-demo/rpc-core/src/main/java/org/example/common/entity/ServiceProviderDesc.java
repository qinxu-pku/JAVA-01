package org.example.common.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ServiceProviderDesc {

    private String host;

    private Integer port;

    private String serviceClass;
}
