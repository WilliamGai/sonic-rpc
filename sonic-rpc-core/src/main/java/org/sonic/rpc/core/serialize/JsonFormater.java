package org.sonic.rpc.core.serialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonFormater implements Formater {
	public static final Formater formater = new JsonFormater();

	@Override
	public String requestFormat(Request request) {
		return JSONUtil.serialize(request);
	}

	@Override
	public String responseFormat(Object param) {
		return JSON.toJSONString(param, SerializerFeature.WriteClassName,
		            SerializerFeature.DisableCircularReferenceDetect);
	}

}
