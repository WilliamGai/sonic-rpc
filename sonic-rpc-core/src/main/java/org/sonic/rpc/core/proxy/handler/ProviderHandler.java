package org.sonic.rpc.core.proxy.handler;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.utils.RpcUtil;
import org.sonic.rpc.core.zookeeper.ZookeeperClient;

public class ProviderHandler {
	private String target;
	private Integer port;
	private ZookeeperClient client;

	public ProviderHandler(String target, Integer port) {
		this.target = target;
		this.port = port;
	}

	public ProviderHandler start() {
		client = new ZookeeperClient(this.target);
		return this;
	}

	public void register(Class<?> clazz) {
		String path = RpcUtil.getZkRootPath(clazz);
		String childrenPath = path + "/node";
		client.createPersistent(path);
		client.createEphemeral(childrenPath, getNodeInfo());
	}

	public String getNodeInfo() {
		try {
			String info = "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + port;
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

	public Integer getPort() {
		return port;
	}
}
