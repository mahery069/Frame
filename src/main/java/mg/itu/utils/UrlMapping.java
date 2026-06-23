package mg.itu.utils;

import java.lang.reflect.Method;

public class UrlMapping {
    private String url;
    private Object controllerInstance;
    private Method method;

    public UrlMapping(String url, Object controllerInstance, Method method) {
        this.url = url;
        this.controllerInstance = controllerInstance;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Object getControllerInstance() {
        return controllerInstance;
    }

    public Method getMethod() {
        return method;
    }
}
