package org.sonic.rpc.core.serialize;

public interface Formater {
	/**
	 * @param clazz 请求的接口
	 * @param method 请求的方法
	 * @param param 请求的参数
	 */
	String requestFormat(Request request);

	/** @param param 响应的结果 */
	String responseFormat(Object param);
}
