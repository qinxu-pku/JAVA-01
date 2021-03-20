package org.example.remoting.dto;

import lombok.Data;

@Data
public class RpcResponse {

    private Object result;

    private Boolean status;

    private Exception exception;
}
