package org.sonic.rpc.core.invoke;

import org.sonic.rpc.core.HttpUtil;
import org.sonic.rpc.core.exception.RpcException;

/***
 * 为consumer提供http服务,请求远程的服务提供者
 * 
 * @author bao
 * @date 2017年8月1日 下午11:21:07
 */
public class HttpInvoker implements Invoker {

	public static final Invoker invoker = new HttpInvoker();

	public String request(String request, String url) throws RpcException {
		return HttpUtil.sendPost(url, request);
	}

	public String response(String response) throws RpcException {
		// outputStream.write(response.getBytes("UTF-8"));
		// outputStream.flush();
		return response;
	}

}
