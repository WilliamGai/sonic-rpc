package org.sonic.rpc.core.container;

import java.util.function.Function;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.invoke.ProviderConfig;
import org.sonic.rpc.core.netty.NettyHttpServer;

/**
 * ProviderProxyFactory使用
 * @author bao
 * @date 2017年8月1日 下午10:46:49
 */
public class HttpContainer extends Container {

	private ProviderConfig providerConfig;
	public Function<String, String> httpCall;

	public HttpContainer(ProviderConfig providerConfig, Function<String, String> httpCall) {
		this.providerConfig = providerConfig;
		if (this.providerConfig == null) {
			this.providerConfig = new ProviderConfig("/invoke", 8080);
		}
		Container.container = this;
		this.httpCall = httpCall;
	}

	public void start() {
		try {
			NettyHttpServer server = new NettyHttpServer();
			server.start(providerConfig.getPort(), httpCall);
			LogCore.BASE.info("netty start");
		} catch (Throwable e) {
			LogCore.BASE.error("容器异常", e);
		}
	}
}
