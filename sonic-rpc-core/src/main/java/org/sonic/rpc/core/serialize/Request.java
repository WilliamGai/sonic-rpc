package org.sonic.rpc.core.serialize;

import java.io.Serializable;

/** 服务提供者使用反射 */
public class Request implements Serializable {
	private static final long serialVersionUID = -3076866403581737243L;

	private Class<?> clazz;
	private String methodName;
//	private Class<?>[] parameterTypes;fastJson不支持Class<?>[]反序列化
	private String [] parameterTypeNames;
	private Object[] arguments;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParameterTypeNames() {
		return parameterTypeNames;
	}

	public void setParameterTypeNames(String[] parameterTypeNames) {
		this.parameterTypeNames = parameterTypeNames;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
}
