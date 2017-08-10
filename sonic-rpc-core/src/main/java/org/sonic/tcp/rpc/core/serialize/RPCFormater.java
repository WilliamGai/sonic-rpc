package org.sonic.tcp.rpc.core.serialize;

import org.sonic.tcp.rpc.core.zookeeper.JSONUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class RPCFormater implements Formater
{
    public static final Formater formater = new RPCFormater();

    public String requestFormat(Class<?> clazz, String method, Object param)
    {
        Request request = new Request();
        request.setParam(param);
        request.setClazz(clazz);
        request.setMethod(method);
        return JSONUtil.serialize(request);
    }

    public String responseFormat(Object param)
    {
        return JSON.toJSONString(param, SerializerFeature.WriteClassName);
    }

}
