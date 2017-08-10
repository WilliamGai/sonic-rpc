package org.sonic.rpc.core.zookeeper;

import org.sonic.rpc.core.exception.SerializeException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String data) throws SerializeException {
	return (T) JSON.parse(data);//切记不可以使用ParseObject(obj),parseObject(s,clz)的使用必须传入Class
    }

    public static String serialize(Object serializable) throws SerializeException {
	return JSON.toJSONString(serializable, SerializerFeature.WriteClassName);
    }
}