package org.sonic.rpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.proxy.handler.ConsumerHandler;
import org.sonic.rpc.core.serialize.RPCSerializer;
import org.sonic.rpc.core.serialize.Request;
import org.sonic.rpc.core.serialize.Result;
import org.sonic.rpc.core.utils.HttpUtil;

/**
 * 消费者,核心是代理
 * 
 * @author 继承 java.lang.reflect.InvocationHandler
 */
public class ConsumerProxyFactory implements InvocationHandler {
	private ConsumerHandler consumerHandler;// spring注入 consumerHandler

	/* create()创建工厂bean speakInterface Class<?> interfaceClass = Class.forName(clazz); */
	public Object create(Class<?> interfaceClass) {
		return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass }, this);
	}

	/**
	 * 实现InvocationHandler的接口<br>
	 * TODO: 需要增加失败重试机制
	 * 遇到的问题,Spring容器可能访问被代理类的的实例的toString()方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class<?> clazz = proxy.getClass().getInterfaces()[0];
		LogCore.BASE.info("proxy invoke interfaceClass ={},method={},args={}",clazz, method, Arrays.toString(args));
		Class<?>[] parameterTypes = method.getParameterTypes();
		Request req = new Request();
		req.setClazz(clazz);
		req.setMethodName(method.getName());
		String[] parameterTypeNames = Arrays.stream(parameterTypes).map(Class::getName).toArray(String[]::new);
		req.setParameterTypeNames(parameterTypeNames);
		req.setArguments(args);
		String reqStr = RPCSerializer.INSTANCE.requestFormat(req);
		String resb =  HttpUtil.sendPost(consumerHandler.getUrl(clazz), reqStr);//调用远程接口
		Result result = RPCSerializer.INSTANCE.rsponseParse(resb);
		return result.data;
	}

	public void setConsumerConfig(ConsumerHandler consumerHandler) {
		this.consumerHandler = consumerHandler;
	}
}
