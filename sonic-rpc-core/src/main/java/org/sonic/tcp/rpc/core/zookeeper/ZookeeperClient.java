package org.sonic.tcp.rpc.core.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.zookeeper.Watcher;
import org.sonic.tcp.rpc.core.LogCore;
import org.sonic.tcp.rpc.core.Util;
import org.sonic.tcp.rpc.core.exception.RpcException;
import org.sonic.tcp.rpc.core.exception.RpcExceptionCodeEnum;
import org.sonic.tcp.rpc.core.zookeeper.functions.ZkCall;
import org.sonic.tcp.rpc.core.zookeeper.functions.ZkCallBack;

public class ZookeeperClient {
    private volatile Watcher.Event.KeeperState state = Watcher.Event.KeeperState.SyncConnected;

    private ZkClient zkClient;

    public ZookeeperClient(String url) {
	LogCore.BASE.info("zookeeper url={}", url);

	zkClient = new ZkClient(url);
	zkClient.setZkSerializer(new ZStringSerializer());// 自定义序列化
	// Callable<V>
    }

    public void execute(ZkCall call) {
	try {
	    call.doInZk(zkClient);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public <T> T executeResult(ZkCallBack<T> call) {
	try {
	    return call.doInZk(zkClient);
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    // 创建持久化目录
    public void createPersistent(String path) {
	execute(zk -> zk.createPersistent(path, true));
    }

    // 创建临时目录
    public void createEphemeral(String path, String data) {
	execute(zk -> zk.createEphemeralSequential(path, data));
    }

    // 删除目录
    public void deleteEphemeral(String path) {
	execute(zk -> zk.delete(path));
    }

    // 获取子目录
    public List<String> getChildren(String path) throws RpcException {
	try {
	    List<String> pathList = zkClient.getChildren(path);
	    if (Util.isEmpty(pathList)) {
		throw new RpcException(RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
	    }
	    return pathList;
	} catch (ZkNoNodeException e) {
	    throw new RpcException(e.getMessage(), e, RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
	}
    }

    // 获取节点中的值,因为我们默认是用的String序列化
    public String getData(String path) {
	return executeResult(zk -> zk.readData(path));
    }

    public void delete(String path) {
	zkClient.delete(path);
    }

    public void setWatcher(String path, IZkChildListener watcher) {
	zkClient.subscribeChildChanges(path, watcher);
    }

    public boolean isConnected() {
	return state == Watcher.Event.KeeperState.SyncConnected;
    }

    public void doClose() {
	zkClient.close();
    }
}
