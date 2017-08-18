package org.sonic.rpc.core.utils;

import org.sonic.rpc.core.RpcConstants;

public class RpcUtil {
    public static String getZkRootPath(Class<?> clazz) {
	return RpcConstants.ZK_RPC_PATH + "/" + clazz.getName().replaceAll("\\.", "/");
    }
}
