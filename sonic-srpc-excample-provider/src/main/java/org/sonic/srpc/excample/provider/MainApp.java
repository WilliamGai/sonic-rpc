package org.sonic.srpc.excample.provider;

import java.util.Arrays;

import org.sonic.rpc.core.LogCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 服务提供者测试用例
 */
@SpringBootApplication(scanBasePackages={"org.sonic.rpc.provider","org.sonic.srpc.excample"})
public class MainApp implements CommandLineRunner {

	@Autowired
	ApplicationContext ct;

	public static void main(String[] args) {
		LogCore.BASE.info("provider app start 开始");
		ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);
		LogCore.BASE.info("测试配置文件调用{}", Arrays.toString(context.getEnvironment().getActiveProfiles()));
	}

	@Override
	public void run(String... arg0) throws Exception {
		LogCore.BASE.info("context autowired = {}", ct.getClass());// AnnotationConfigApplicationContext
	}
}
