package org.example.proxy;

import org.example.exception.RpcException;
import org.example.provider.ServiceProvider;
import org.example.remoting.dto.RpcRequest;
import org.example.remoting.dto.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ServerInvoker {

    private ServiceProvider serviceProvider;

    public ServerInvoker(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public RpcResponse invok(RpcRequest request) {

        RpcResponse response = new RpcResponse();
        String serviceClass = request.getServiceClass();

        Object service = serviceProvider.getService(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethodName());
            Object result = method.invoke(service, request.getParameters());
            response.setResult(result);
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException e) {
            response.setException(new RpcException(RpcException.BIZ_EXCEPTION, "服务端异常", e));
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }
}
