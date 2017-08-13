package org.sonic.rpc.core.invoke;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.RpcUtil;
import org.sonic.rpc.core.zookeeper.ZookeeperClient;

public class ProviderConfig {
	private String target;
	private Integer port;// 如果target是zookeeper,则port不起作用了
	private ZookeeperClient client;

	public ProviderConfig(String target, Integer port) {
		this.target = target;
		this.port = port;
		if (target.toLowerCase().startsWith("zookeeper://")) {
			client = new ZookeeperClient(target.toLowerCase().replaceFirst("zookeeper://", ""));
		}
	}

	public void register(Class<?> clazz) {
		if (client == null) {
			return;
		}
		String path = RpcUtil.getZkRootPath(clazz);
		String childrenPath = path + "/node";
		client.createPersistent(path);
		client.createEphemeral(childrenPath, getNodeInfo());
	}

	public String getNodeInfo() {
		try {
			String info = "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + getPort();
			LogCore.BASE.info("info={}", info);
			return info;
		} catch (UnknownHostException e) {
			LogCore.RPC.error("getNodeInfo", e);
			return null;
		}

	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
