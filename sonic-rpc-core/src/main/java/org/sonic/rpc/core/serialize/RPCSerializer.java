package org.sonic.rpc.core.serialize;

import org.sonic.rpc.core.exception.RpcException;
import org.sonic.rpc.core.serialize.interfaces.Formater;
import org.sonic.rpc.core.serialize.interfaces.Parser;
import org.sonic.rpc.core.utils.JSONUtil;

public class RPCSerializer implements Formater, Parser {
	
	public static final RPCSerializer INSTANCE = new RPCSerializer();
	
	@Override
	public String requestFormat(Request request) {
		return JSONUtil.serialize(request);
	}

	@Override
	public String responseFormat(Result response) {
		return JSONUtil.serialize(response);
	}

	@Override
	public Request requestParse(String param) throws RpcException {
		return JSONUtil.deserialize(param);
	}

	@Override
	public Result rsponseParse(String result) {
		return JSONUtil.deserialize(result);
	}
}
