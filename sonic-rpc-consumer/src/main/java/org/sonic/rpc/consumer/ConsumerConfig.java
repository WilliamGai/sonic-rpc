package org.sonic.rpc.consumer;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.proxy.ConsumerProxyFactory;
import org.sonic.rpc.core.proxy.handler.ConsumerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//class org.sonic.rpc.consumer.ConsumerAppConfig$$EnhancerBySpringCGLIB$$3b554800
@Configuration
public class ConsumerConfig {

	@Bean
	ConsumerHandler getConsumerHandler(@Value("${consumer.url}") String url) {
		LogCore.BASE.info("ConsumerHandler init, url is {}", url);
		return new ConsumerHandler(url).start();
	}

	@Bean
	@Autowired
	ConsumerProxyFactory getConsumerProxyFactory(ConsumerHandler consumerHandler) {
		ConsumerProxyFactory cf = new ConsumerProxyFactory();
		cf.setConsumerConfig(consumerHandler);
		return cf;
	}

//	旧的方式,将代理bean注册到Spring
//	@Autowired
//	@Bean
//	PlateService getPlateService(ConsumerProxyFactory consumerProxyFactory) {
//		LogCore.BASE.info("发布Consumer start   {}", consumerProxyFactory);
//		Class<?> clazz = PlateService.class;
////		AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
////		autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(SReference.class);
//		return (PlateService) consumerProxyFactory.create(clazz);
//	}

//	旧的方式,使自定义注解(替换Autowired)SReference被Spring识别。否则SReference需要内部添加Component
//	@Bean
//	public AutowiredAnnotationBeanPostProcessor getAutowiredAnnotationBeanPostProcessor() {
////		AutowiredAnnotationBeanPostProcessor
//		AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//		autowiredAnnotationBeanPostProcessor.setAutowiredAnnotationType(SReference.class);
//		return autowiredAnnotationBeanPostProcessor;
//	}
}