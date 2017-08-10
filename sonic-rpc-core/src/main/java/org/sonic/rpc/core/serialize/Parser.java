package org.sonic.rpc.core.serialize;

import org.sonic.rpc.core.exception.RpcException;

public interface Parser {
    /**
     * @param param 请求参数
     * @return
     */
    Request requestParse(String param) throws RpcException;

    public <T> T rsponseParse(String result);
}
