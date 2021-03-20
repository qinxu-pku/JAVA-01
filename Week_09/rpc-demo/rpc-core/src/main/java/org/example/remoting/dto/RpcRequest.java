package org.example.remoting.dto;

import lombok.Data;

@Data
public class RpcRequest {

    private String serviceClass;

    private String methodName;

    private Object[] parameters;

    private Class<?>[] paramTypes;

}
