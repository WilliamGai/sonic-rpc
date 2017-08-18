package org.sonic.rpc.core.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.RpcConstants;
import org.sonic.rpc.core.container.Container;
import org.sonic.rpc.core.container.HttpContainer;
import org.sonic.rpc.core.exception.RpcException;
import org.sonic.rpc.core.exception.RpcExceptionCodeEnum;
import org.sonic.rpc.core.proxy.handler.ProviderHandler;
import org.sonic.rpc.core.serialize.RPCSerializer;
import org.sonic.rpc.core.serialize.Request;
import org.sonic.rpc.core.serialize.Result;
import org.sonic.rpc.core.utils.Util;

/**
 * 服务提供者核心是反射 继承 org.mortbay.jetty.handler.AbstractHandler
 */

public class ProviderProxyFactory {
	private ProviderHandler providerHandler;

	public Map<Class<?>, Object> providers = new ConcurrentHashMap<>();

	public ProviderProxyFactory(ProviderHandler providerHandler) {
		this.providerHandler = providerHandler;
		if (Container.container == null) {
			// netty需要另起线程,如果是jetty就不需要
			new Thread(() -> {
				new HttpContainer(this.providerHandler.getPort(), this::handleHttpContent).start();

			}).start();
		}
	}

	public void register(Object obj) {
		Class<?> interFaceClazz = obj.getClass().getInterfaces()[0];
		providers.put(interFaceClazz, obj);
		providerHandler.register(interFaceClazz);
		LogCore.BASE.info("{} 已经发布,conf={}", interFaceClazz.getSimpleName(), providerHandler);
	}

	/***
	 * 主要RPC 逻辑接收请求信息,解析后调用相关方法并返回<br>
	 * 需要将异常返回给调用者
	 * 
	 * @param reqStr
	 * @return
	 */
	public String handleHttpContent(String reqStr) {
		LogCore.BASE.info("get the reqStr is {}", reqStr);
		try {
			if (Util.isEmpty(reqStr)) {
				return RpcConstants.EMPTY_RETURN;
			}
			// 将请求参数解析
			Request req = RPCSerializer.INSTANCE.requestParse(reqStr);
			// 反射请求
			// Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanByClass(rpcRequest.getClazz()));
			Class<?> clazz = req.getClazz();
			String methodName = req.getMethodName();
			Object[] args = req.getArguments();
			String[] parameterTypeNames = req.getParameterTypeNames();
			Class<?>[] parameterTypes = Arrays.stream(parameterTypeNames).map(this::classForName)
			            .toArray(Class[]::new);
			Method method = clazz.getMethod(methodName, parameterTypes);
			Object bean = getBeanByClass(clazz);
			Object result = method.invoke(bean, args);
			Result rst = new Result().setData(result);
			return RPCSerializer.INSTANCE.responseFormat(rst);
		} catch (Exception e) {
			LogCore.RPC.error("providerProxyFactory handle error", e);
			return e.getMessage();// TODO
		}
	}

	public Object getBeanByClass(Class<?> clazz) throws RpcException {
		Object bean = providers.get(clazz);
		if (bean != null) {
			return bean;
		}
		throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(), clazz);
	}

	public Class<?> classForName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			LogCore.BASE.error("classForName err", className);
			return null;
		}
	}
}
