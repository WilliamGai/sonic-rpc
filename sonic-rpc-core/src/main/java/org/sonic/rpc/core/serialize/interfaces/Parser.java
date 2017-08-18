package org.sonic.rpc.core.serialize.interfaces;

import org.sonic.rpc.core.exception.RpcException;
import org.sonic.rpc.core.serialize.Request;
import org.sonic.rpc.core.serialize.Result;

public interface Parser {
	/** @param param 请求参数 */
	Request requestParse(String param) throws RpcException;

	public Result rsponseParse(String result);
}
