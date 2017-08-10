package org.sonic.tcp.rpc.core.proxy;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.handler.AbstractHandler;
import org.sonic.tcp.rpc.core.LogCore;
import org.sonic.tcp.rpc.core.Util;
import org.sonic.tcp.rpc.core.container.Container;
import org.sonic.tcp.rpc.core.container.HttpContainer;
import org.sonic.tcp.rpc.core.exception.RpcException;
import org.sonic.tcp.rpc.core.exception.RpcExceptionCodeEnum;
import org.sonic.tcp.rpc.core.invoke.HttpInvoker;
import org.sonic.tcp.rpc.core.invoke.Invoker;
import org.sonic.tcp.rpc.core.invoke.ProviderConfig;
import org.sonic.tcp.rpc.core.serialize.Formater;
import org.sonic.tcp.rpc.core.serialize.Parser;
import org.sonic.tcp.rpc.core.serialize.RPCFormater;
import org.sonic.tcp.rpc.core.serialize.RPCParser;
import org.sonic.tcp.rpc.core.serialize.Request;

/***
 * 服务提供者核心是反射 继承 org.mortbay.jetty.handler.AbstractHandler Spring<br>
 * 将"com.sonic.http.rpc.api.SpeakInterface"<br>
 * 和"com.sonic.http.rpc.invoke.ProviderConfig"注入providers
 */

public class ProviderProxyFactory extends AbstractHandler {
    private ProviderConfig providerConfig;
    public Map<Class<?>, Object> providers = new ConcurrentHashMap<>();// spring注入

    private Parser parser = RPCParser.parser;

    private Formater formater = RPCFormater.formater;

    private Invoker invoker = HttpInvoker.invoker;

    public ProviderProxyFactory(Map<Class<?>, Object> providers, ProviderConfig providerConfig) {
	this.providerConfig = providerConfig;
	if (Container.container == null) {
	    new HttpContainer(this, this.providerConfig).start();
	}
	if(Util.isEmpty(providers)){
		return;
	}
	this.providers = providers;
	providers.forEach(this::register);
    }

    public void register(Class<?> clazz, Object object) {
	providers.put(clazz, object);
	if (providerConfig != null) {
	    providerConfig.register(clazz);
	}
	LogCore.BASE.info("{} 已经发布,conf={}", clazz.getSimpleName(), providerConfig);
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
	    throws IOException, ServletException {
	String reqStr = request.getParameter("data");
	LogCore.BASE.info("get the param value={}", reqStr);
	try {
	    // 将请求参数解析
	    Request rpcRequest = parser.requestParse(reqStr);
	    // 反射请求
	    // Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanByClass(rpcRequest.getClazz()));
	    Object result = rpcRequest.invoke(getBeanByClass(rpcRequest.getClazz()));
	    // 相应请求
	    invoker.response(formater.responseFormat(result), response.getOutputStream());
	} catch (Exception e) {
	    LogCore.RPC.error("providerProxyFactory handle", e);
	}
    }

    public Object getBeanByClass(Class<?> clazz) throws RpcException {
	Object bean = providers.get(clazz);
	if (bean != null) {
	    return bean;
	}
	throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(), clazz);
    }

    public ProviderConfig getProviderConfig() {
        return providerConfig;
    }

    public void setProviderConfig(ProviderConfig providerConfig) {
        this.providerConfig = providerConfig;
    }
}
