package org.sonic.rpc.core;

public class RpcUtil {
    public static String getZkRootPath(Class<?> clazz) {
	return HttpRpcConstants.ZK_RPC_PATH + "/" + clazz.getName().replaceAll("\\.", "/");
    }
}
