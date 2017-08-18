package org.sonic.rpc.core.zookeeper.functions;

import org.I0Itec.zkclient.ZkClient;

/**
 * 返回值为空
 * @author bao
 */
public interface ZkCall {
	void doInZk(ZkClient zk) throws Exception;
}
