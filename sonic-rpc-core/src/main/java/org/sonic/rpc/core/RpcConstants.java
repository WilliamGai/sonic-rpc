package org.sonic.rpc.core;

public interface RpcConstants {
    static final String ZK_RPC_PATH_TAG = "s_rpc";
    static final String ZK_RPC_PATH = "/" + ZK_RPC_PATH_TAG;
    
    //空请求的返回
    static final String EMPTY_RETURN = "rpc request is empty !";
}
