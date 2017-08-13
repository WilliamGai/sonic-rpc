package org.sonic.rpc.core.invoke;

import org.sonic.rpc.core.exception.RpcException;

public interface Invoker {
    /** 调用请求 */
    String request(String request, String url) throws RpcException;

    /** 请求应答 */
    String response(String response) throws RpcException;
}
