package org.sonic.srpc.excample.consumer;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.proxy.ConsumerProxyFactory;
import org.sonic.rpc.core.utils.StepWatchUtil;
import org.sonic.rpc.core.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world! class org.sonic.rpc.consumer.ConsumerMainApp$$EnhancerBySpringCGLIB$$21742a45
 */
@SpringBootApplication(scanBasePackages={"org.sonic.rpc.consumer", "org.sonic.srpc"})

public class ConsumerMainApp implements CommandLineRunner {
	@Autowired
	ConsumerMainApp consumerMainApp;
	@Autowired
	ConsumerProxyFactory consumerProxyFactor;
	@Autowired
	PlateController plateController;

	private static final int _COUNT = 10;
	private static final SecureRandom RAND = new SecureRandom();

	@Value("${consumer.url}")
	String url;

	public static void main(String[] args) {
		LogCore.BASE.info("启动参数为{}", Arrays.toString(args));
		SpringApplication.run(ConsumerMainApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		LogCore.BASE.info("运行参数为{}", Arrays.toString(arg0));
		LogCore.BASE.info("{}", Util.prettyJsonStr(consumerProxyFactor));
		LogCore.BASE.info("app ={}", consumerMainApp);
		LogCore.BASE.info("url ={}", url);
		LogCore.BASE.info("peopleController= {}", plateController);
		Object obj = plateController.applyPlate(RAND.nextInt(100), RAND.nextInt(2));
		LogCore.BASE.info("obj={}", obj);
		final ExecutorService exec = Executors.newFixedThreadPool(50);
		AtomicInteger count = new AtomicInteger(0);
		final CountDownLatch countDownLatch = new CountDownLatch(_COUNT);

		StepWatchUtil sw = new StepWatchUtil();
		sw.reset();
		while (count.getAndIncrement() < _COUNT) {
			final int _reqId = count.get();
			exec.submit(() -> {
				LogCore.BASE.info("reqId={},rpc={}", _reqId,
				            plateController.applyPlate(RAND.nextInt(100), RAND.nextInt(2)));
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		LogCore.BASE.info("req num={}, used time={}", _COUNT, sw.interval());
		exec.shutdown();
	}
}
