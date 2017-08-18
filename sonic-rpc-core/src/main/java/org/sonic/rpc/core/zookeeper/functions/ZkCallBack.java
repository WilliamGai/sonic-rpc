package org.sonic.rpc.core.zookeeper.functions;

import org.I0Itec.zkclient.ZkClient;
/**
 * 有返回值
 * @author bao
 */
public interface ZkCallBack<T> {
    T doInZk(ZkClient zk) throws Exception;
}
