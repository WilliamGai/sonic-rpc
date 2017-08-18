package org.sonic.srpc.excample.consumer;
//package org.sonic.rpc.consumer.excample;
//
//import java.security.SecureRandom;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import org.sonic.rpc.core.LogCore;
//import org.sonic.rpc.core.StepWatchUtil;
//import org.sonic.rpc.core.Util;
//import org.sonic.rpc.core.invoke.ConsumerConfig;
//import org.sonic.rpc.core.proxy.ConsumerProxyFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
///**
// * Hello world! class org.sonic.rpc.consumer.ConsumerMainApp$$EnhancerBySpringCGLIB$$21742a45
// */
//@SpringBootApplication
//public class ConsumerMainApp implements CommandLineRunner {
//	@Autowired
//	ConsumerMainApp consumerMainApp;
//	@Autowired
//	ConsumerConfig consumerCf;
//	@Autowired
//	ConsumerProxyFactory consumerProxyFactor;
//	@Autowired
//	PlateController plateController;
//
//	@Autowired
//	ConsumerAppConfig consumerAppConf;
//	// @Autowired
//	// AutowiredAnnotationBeanPostProcessor p;
//	// @Resource
//	// private SpeakInterface speakInterface;
//
//	private static final int _COUNT = 10;
//	private static final SecureRandom RAND = new SecureRandom();
//
//	@Value("${consumer.url}")
//	String url;
//
//	public static void main(String[] args) {
//		LogCore.BASE.info("启动参数为{}", args);
//
//		ConfigurableApplicationContext context = SpringApplication.run(ConsumerMainApp.class, args);
//		// LogCore.BASE.info("测试配置文件调用{}", context.getEnvironment().getActiveProfiles());
//		//// LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment()));
//		// LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment().getProperty("a")));
//
//	}
//
//	@Override
//	public void run(String... arg0) throws Exception {
//		LogCore.BASE.info("运行参数为{}", arg0);
//		LogCore.BASE.info("{}", Util.prettyJsonStr(consumerProxyFactor));
//		LogCore.BASE.info("consumerCf={}", Util.prettyJsonStr(consumerCf));
//		LogCore.BASE.info("app ={}", consumerMainApp);
//		LogCore.BASE.info("url ={}", url);
//		LogCore.BASE.info("peopleController= {}", plateController);
//		Object obj = plateController.applyPlate(RAND.nextInt(100), RAND.nextInt(2));
//		LogCore.BASE.info("obj={}", obj);
//		LogCore.BASE.info("consumerAppConf={}", consumerAppConf);
//		// LogCore.BASE.info("p={}", p);
//		// LogCore.BASE.info("look p={}", ReflectUtil.getFields(p));
//		// LogCore.BASE.info("speakInterface ={}",speakInterface.getClass());
//		final ExecutorService exec = Executors.newFixedThreadPool(50);
//
//		AtomicInteger count = new AtomicInteger(0);
//		final CountDownLatch countDownLatch = new CountDownLatch(_COUNT);
//
//		StepWatchUtil sw = new StepWatchUtil();
//		sw.reset();
//		while (count.getAndIncrement() < _COUNT) {
//			final int _reqId = count.get();
//			exec.submit(() -> {
//				LogCore.BASE.info("reqId={},rpc={}", _reqId,
//				            plateController.applyPlate(RAND.nextInt(100), RAND.nextInt(2)));
//				countDownLatch.countDown();
//			});
//		}
//		countDownLatch.await();
//		LogCore.BASE.info("req num={}, used time={}", _COUNT, sw.interval());
//		exec.shutdown();
//	}
//}
