package org.sonic.rpc.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.sonic.rpc.core.invoke.ProviderConfig;
import org.sonic.rpc.core.proxy.ProviderProxyFactory;
import org.sonic.tcp.rpc.api.SpeakInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @PropertySource("classpath:jedis.properties")
public class AppConfig {
	@Autowired
	SpeakInterface speak;
	
	@Bean
	ProviderConfig getProviderConfig(
			@Value("${provider.target}") String target,
			@Value("${provider.port}") int port) {
		return new ProviderConfig(target, port);
	}
	
	@Bean
	@Autowired
	ProviderProxyFactory getProviderProxyFactory(ProviderConfig providerConfig, SpeakInterface speak){
		Map<Class<?>, Object> providers = new ConcurrentHashMap<Class<?>, Object>();
		providers.put(SpeakInterface.class, speak);
		return new ProviderProxyFactory(providers, providerConfig);
	}
}