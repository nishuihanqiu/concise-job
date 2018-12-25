package com.lls.api.concise.rmi;

import com.lls.api.concise.core.Result;
import com.lls.api.concise.job.Action;
import com.lls.api.concise.logging.Logger;
import com.lls.api.concise.logging.LoggerFactory;
import com.lls.api.concise.rmi.codec.Request;
import com.lls.api.concise.rmi.codec.Response;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/************************************
 * RemoteContext
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class RemoteContext {

    private static final Logger logger = LoggerFactory.getLogger(RemoteContext.class);

    private final ConcurrentMap<String, Object> services = new ConcurrentHashMap<>();
    private String accessToken;
    private long requestMaxIntervalTime;

    public RemoteContext(String accessToken) {
        this.accessToken = accessToken;
    }

    public void putService(Class<?> clazz, Object serviceBean) {
        services.put(clazz.getName(), serviceBean);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRequestMaxIntervalTime(long requestMaxIntervalTime) {
        this.requestMaxIntervalTime = requestMaxIntervalTime;
    }

    public long getRequestMaxIntervalTime() {
        return requestMaxIntervalTime;
    }

    public Response invoke(Request request, Object serviceBean) {
        if (serviceBean == null) {
            serviceBean = services.get(request.getClassName());
        }

        Response response = new Response();
        if (System.currentTimeMillis() - request.getCreatedAt() > this.requestMaxIntervalTime) {
            response.setResult(new Result(Action.FAILED, "request timeout."));
            return response;
        }

        if (accessToken != null && accessToken.trim().length() > 0
            && !accessToken.trim().equals(request.getAccessToken())) {
            response.setResult(new Result(Action.FAILED, "access token:" + request.getAccessToken() + " error."));
            return response;
        }

        try {
            Class<?> serviceClass = serviceBean.getClass();
            String methodName = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[] parameters = request.getParameters();

            FastClass serviceFastClass = FastClass.create(serviceClass);
            FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
            Object result = serviceFastMethod.invoke(serviceBean, parameters);
            response.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }

        return response;
    }
}
