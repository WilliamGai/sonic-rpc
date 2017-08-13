package org.sonic.rpc.core.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.RpcConstants;
import org.sonic.rpc.core.Util;
import org.sonic.rpc.core.container.Container;
import org.sonic.rpc.core.container.HttpContainer;
import org.sonic.rpc.core.exception.RpcException;
import org.sonic.rpc.core.exception.RpcExceptionCodeEnum;
import org.sonic.rpc.core.invoke.HttpInvoker;
import org.sonic.rpc.core.invoke.Invoker;
import org.sonic.rpc.core.invoke.ProviderConfig;
import org.sonic.rpc.core.serialize.Formater;
import org.sonic.rpc.core.serialize.Parser;
import org.sonic.rpc.core.serialize.RPCFormater;
import org.sonic.rpc.core.serialize.RPCParser;
import org.sonic.rpc.core.serialize.Request;

/**
 * 服务提供者核心是反射 继承 org.mortbay.jetty.handler.AbstractHandler
 */

public class ProviderProxyFactory{
	private ProviderConfig providerConfig;

	public Map<Class<?>, Object> providers = new ConcurrentHashMap<>();

	private Parser parser = RPCParser.parser;

	private Formater formater = RPCFormater.formater;

	private Invoker invoker = HttpInvoker.invoker;

	public ProviderProxyFactory(ProviderConfig providerConfig) {
		this.providerConfig = providerConfig;
		if (Container.container == null) {
			new Thread(()->{
				new HttpContainer(this.providerConfig, this::handleHttpContent).start();

			}).start();
		}
	}

	public void register(Object obj) {
		Class<?> interFaceClazz = obj.getClass().getInterfaces()[0];
		providers.put(interFaceClazz, obj);
		if (providerConfig != null) {
			providerConfig.register(interFaceClazz);
		}
		LogCore.BASE.info("{} 已经发布,conf={}", interFaceClazz.getSimpleName(), providerConfig);
	}
	/***
	 * 主要RPC 逻辑接收请求信息,解析后调用相关方法并返回<br>
	 * 需要将异常返回给调用者
	 * @param reqStr
	 * @return
	 */
	public String handleHttpContent(String reqStr){
		LogCore.BASE.info("get the reqStr is {}", reqStr);
		try {
			if(Util.isEmpty(reqStr)){
				return RpcConstants.EMPTY_RETURN;
			}
			// 将请求参数解析
			Request rpcRequest = parser.requestParse(reqStr);
			// 反射请求
			// Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanByClass(rpcRequest.getClazz()));
			Object result = rpcRequest.invoke(getBeanByClass(rpcRequest.getClazz()));
			// 响应请求
			return invoker.response(formater.responseFormat(result));
		} catch (Exception e) {
			LogCore.RPC.error("providerProxyFactory handle", e);
			return e.getMessage();//TODO
		}
	}

	public Object getBeanByClass(Class<?> clazz) throws RpcException {
		Object bean = providers.get(clazz);
		if (bean != null) {
			return bean;
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(), clazz);
	}
}
