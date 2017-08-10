package org.sonic.tcp.rpc.consumer;

import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.sonic.tcp.rpc.core.LogCore;
import org.sonic.tcp.rpc.core.StepWatchUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <bean id="consumerConfig" class="com.sonic.http.rpc.invoke.ConsumerConfig"> <!-- <property name="url" value="http://127.0.0.1:8888/invoke" /> -->
 * <property name="url" value="zookeeper://123.56.13.70:2181,123.56.13.70:2182,123.56.13.70:2183" /> </bean>
 * 
 * <bean id="speakInterfaceInvoker" class="com.sonic.http.rpc.proxy.ConsumerProxyFactory"> <property name="consumerConfig" ref="consumerConfig" />
 * <property name="clazz" value="com.sonic.http.rpc.api.SpeakInterface" /> </bean>
 * <bean id="speakInterface" factory-bean="speakInterfaceInvoker" factory-method="create" />
 */
public class ConsumerApp {
	private static final int _COUNT = 1;
	private static final SecureRandom RAND = new SecureRandom();

	public static void main(String[] args) throws Exception {

		LogCore.BASE.info("消费者者上线了");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
		context.start();
		PeopleController peopleController = context.getBean(PeopleController.class);

		final ExecutorService exec = Executors.newFixedThreadPool(50);

		AtomicInteger count = new AtomicInteger(0);
		final CountDownLatch countDownLatch = new CountDownLatch(_COUNT);

		StepWatchUtil sw = new StepWatchUtil();
		sw.reset();
		while (count.getAndIncrement() < _COUNT) {
			final int _reqId = count.get();
			exec.submit(() -> {
				LogCore.BASE.info("reqId={},rpc={}", _reqId,
						peopleController.getSpeak(RAND.nextInt(100), RAND.nextInt(2)));
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		LogCore.BASE.info("req num={}, used time={}", _COUNT, sw.interval());
		exec.shutdown();
		context.close();
	}
}
