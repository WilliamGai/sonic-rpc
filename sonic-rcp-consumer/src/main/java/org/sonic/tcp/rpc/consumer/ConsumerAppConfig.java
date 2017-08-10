package org.sonic.tcp.rpc.consumer;

import org.sonic.tcp.rpc.api.SpeakInterface;
import org.sonic.tcp.rpc.core.LogCore;
import org.sonic.tcp.rpc.core.invoke.ConsumerConfig;
import org.sonic.tcp.rpc.core.proxy.ConsumerProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @PropertySource("classpath:jedis.properties")
public class ConsumerAppConfig {
	

	@Bean
	ConsumerConfig getConsumerConfig(@Value("${consumer.url}") String url) {
		ConsumerConfig consumerCf =  new ConsumerConfig();
		LogCore.BASE.info("consumer.url=============={}", url);
		consumerCf.setUrl(url);
		return consumerCf;
	}

	@Bean
	@Autowired
	ConsumerProxyFactory getConsumerProxyFactory(ConsumerConfig consumerConfig, @Value("${consumer.clazz}") String clazz){
		ConsumerProxyFactory cf =  new ConsumerProxyFactory();
		cf.setClazz(clazz);
		cf.setConsumerConfig(consumerConfig);;
		return cf;
	}
	
	
	@Autowired
	@Bean
	SpeakInterface getSpeakInterface(ConsumerProxyFactory consumerProxyFactory){
		try {
			return (SpeakInterface) consumerProxyFactory.create();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}