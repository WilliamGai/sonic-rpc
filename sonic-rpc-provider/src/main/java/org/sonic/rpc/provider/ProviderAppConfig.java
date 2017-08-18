package org.sonic.rpc.provider;

import java.util.Map;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.annotation.SService;
import org.sonic.rpc.core.invoke.ProviderConfig;
import org.sonic.rpc.core.proxy.ProviderProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/***
 * 服务提供者的配置
 * @PropertySource("classpath:jedis.properties")
 * @author bao
 * @date 2017年8月10日 下午4:15:40
 */
@Configuration
public class ProviderAppConfig {
	
	@Bean
	ProviderConfig getProviderConfig(
			@Value("${provider.target}") String target,
			@Value("${provider.port}") int port) {
		LogCore.BASE.info("target=={}",target,port);
		return new ProviderConfig(target, port).start();
	}
	
	@Bean
	@Autowired
	ProviderProxyFactory getProviderProxyFactory(ProviderConfig providerConfig, ApplicationContext ct){
		ProviderProxyFactory pf = new ProviderProxyFactory(providerConfig);
		Map<String,Object> map = ct.getBeansWithAnnotation(SService.class);
		map.values().forEach(pf::register);
		LogCore.BASE.info("要发布的SService服务列表 {}",map);
		return pf;
	}
}