package org.sonic.rpc.core.proxy.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.sonic.rpc.core.AtomicPositiveInteger;
import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.exception.RpcException;
import org.sonic.rpc.core.utils.RpcUtil;
import org.sonic.rpc.core.utils.Util;
import org.sonic.rpc.core.zookeeper.ZookeeperClient;

public class ConsumerHandler {
	public String url;
	private ZookeeperClient client;
	/** 调用的接口，调用接口的次数 */
	private final ConcurrentHashMap<Class<?>, AtomicPositiveInteger> INVOKE_COUNT_MAP = new ConcurrentHashMap<>();


	public ConsumerHandler(String url) {
		this.url = url;
		LogCore.BASE.info("consumerconf invoke zkclient url={}", url);
	}

	public ConsumerHandler start() {
		this.client = new ZookeeperClient(url);
		return this;
	}

	public String getUrl(Class<?> clazz) throws RpcException {
		List<String> urlList = getRpcUrls(clazz);
		return getCurrentUrl(clazz, urlList);
	}

	private List<String> getRpcUrls(Class<?> clazz) throws RpcException {
		String rootPath = RpcUtil.getZkRootPath(clazz);
		List<String> childrenList = client.getChildren(rootPath);
		if (Util.isEmpty(childrenList)) {
			return new ArrayList<String>(0);
		}
		return childrenList.stream().filter(Util::notEmpty).map(ph -> client.getData(rootPath + "/" + ph))
		            .filter(Util::notEmpty).collect(Collectors.toList());

	}

	private String getCurrentUrl(Class<?> clazz, List<String> urlList) throws RpcException {
		final int _count = INVOKE_COUNT_MAP.computeIfAbsent(clazz, k -> new AtomicPositiveInteger())
		            .getAndIncrement();
		return urlList.get(_count % urlList.size());
	}

}
