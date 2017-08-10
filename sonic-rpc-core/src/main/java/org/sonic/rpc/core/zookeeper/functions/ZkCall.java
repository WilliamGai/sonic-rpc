package org.sonic.rpc.core.zookeeper.functions;

import org.I0Itec.zkclient.ZkClient;

public interface ZkCall {
    void doInZk(ZkClient zk) throws Exception;
}