package org.sonic.rpc.core.serialize;

import org.sonic.rpc.core.exception.RpcException;

public class JSONParser implements Parser {

	public static final Parser parser = new JSONParser();

	@Override
	public Request requestParse(String param) throws RpcException {
		return JSONUtil.deserialize(param);
	}

	@Override
	public <T> T rsponseParse(String result) {
		return JSONUtil.deserialize(result);
	}
}
