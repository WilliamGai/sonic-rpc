package org.sonic.rpc.consumer;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.invoke.ConsumerConfig;
import org.sonic.rpc.core.proxy.ConsumerProxyFactory;
import org.sonic.tcp.rpc.api.PlateService;
import org.sonic.tcp.rpc.api.SpeakInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerAppConfig {

	@Bean
	ConsumerConfig getConsumerConfig(@Value("${consumer.url}") String url) {
		ConsumerConfig consumerCf = new ConsumerConfig();
		LogCore.BASE.info("consumer.url=============={}", url);
		consumerCf.setUrl(url);
		return consumerCf;
	}

	@Bean
	@Autowired
	ConsumerProxyFactory getConsumerProxyFactory(ConsumerConfig consumerConfig) {
		ConsumerProxyFactory cf = new ConsumerProxyFactory();
		cf.setConsumerConfig(consumerConfig);
		return cf;
	}

	@Autowired
	@Bean
	PlateService getPlateService(ConsumerProxyFactory consumerProxyFactory) {
		LogCore.BASE.info("发布Consumer start   {}", consumerProxyFactory);
		return (PlateService) consumerProxyFactory.create(PlateService.class);
	}
}