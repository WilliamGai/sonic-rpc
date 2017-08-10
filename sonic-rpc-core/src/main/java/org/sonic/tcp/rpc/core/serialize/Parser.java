package org.sonic.tcp.rpc.core.serialize;

import org.sonic.tcp.rpc.core.exception.RpcException;

public interface Parser {
    /**
     * @param param 请求参数
     * @return
     */
    Request requestParse(String param) throws RpcException;

    public <T> T rsponseParse(String result);
}
