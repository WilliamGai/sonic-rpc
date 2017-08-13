package org.sonic.rpc.provider;

import java.util.Map;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.invoke.ProviderConfig;
import org.sonic.rpc.core.proxy.ProviderProxyFactory;
import org.sonic.rpc.provider.annotation.SService;
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
public class AppConfig {
	
	@Bean
	ProviderConfig getProviderConfig(
			@Value("${provider.target}") String target,
			@Value("${provider.port}") int port) {
		LogCore.BASE.info("target=={}",target,port);

		return new ProviderConfig(target, port);
	}
	
	@Bean
	@Autowired
	ProviderProxyFactory getProviderProxyFactory(ProviderConfig providerConfig, ApplicationContext ct){
		Map<String,Object> map = ct.getBeansWithAnnotation(SService.class);
		ProviderProxyFactory pf = new ProviderProxyFactory(providerConfig);
		map.values().forEach(pf::register);
		LogCore.BASE.info("ct.get======={}",map);
		return pf;
	}
}