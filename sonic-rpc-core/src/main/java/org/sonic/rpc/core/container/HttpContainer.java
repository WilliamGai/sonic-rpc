package org.sonic.rpc.core.container;

import java.util.function.Function;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.netty.NettyHttpServer;

/**
 * ProviderProxyFactory使用
 * 
 * @author bao
 * @date 2017年8月1日 下午10:46:49
 */
public class HttpContainer extends Container {

	public Function<String, String> httpRequestCallBack;
	public int port;

	public HttpContainer(int port, Function<String, String> httpCall) {
		Container.container = this;
		this.port = port;
		this.httpRequestCallBack = httpCall;
	}

	public void start() {
		try {
			NettyHttpServer server = new NettyHttpServer();
			server.start(port, httpRequestCallBack);
			LogCore.BASE.info("netty start");
		} catch (Throwable e) {
			LogCore.BASE.error("netty 异常", e);
		}
	}
}
