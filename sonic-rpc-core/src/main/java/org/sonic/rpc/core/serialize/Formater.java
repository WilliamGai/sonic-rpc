package org.sonic.rpc.core.serialize;

public interface Formater {
    /**
     * @param clazz 请求的接口
     * @param method 请求的方法
     * @param param 请求的参数
     * @return
     */
    String requestFormat(Class<?> clazz, String method, Object param);

    /**
     * @param param 响应的结果
     * @return
     */
    String responseFormat(Object param);
}
