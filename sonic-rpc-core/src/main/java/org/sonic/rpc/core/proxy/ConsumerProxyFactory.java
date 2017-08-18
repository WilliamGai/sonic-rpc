package org.sonic.rpc.core.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.invoke.ConsumerConfig;
import org.sonic.rpc.core.invoke.HttpInvoker;
import org.sonic.rpc.core.invoke.Invoker;
import org.sonic.rpc.core.serialize.Formater;
import org.sonic.rpc.core.serialize.Parser;
import org.sonic.rpc.core.serialize.Request;
import org.sonic.rpc.core.serialize.JsonFormater;
import org.sonic.rpc.core.serialize.JSONParser;

/**
 * 消费者,核心是代理
 * 
 * @author 继承 java.lang.reflect.InvocationHandler
 */
public class ConsumerProxyFactory implements InvocationHandler {
	private ConsumerConfig consumerConfig;// spring注入 consumerConfig

	private Parser parser = JSONParser.parser;

	private Formater formater = JsonFormater.formater;

	private Invoker invoker = HttpInvoker.invoker;

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
		String reqStr = formater.requestFormat(req);
		String resb = invoker.request(reqStr, consumerConfig.getUrl(clazz));//调用远程接口
		return parser.rsponseParse(resb);
	}

	public ConsumerConfig getConsumerConfig() {
		return consumerConfig;
	}

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}
}
