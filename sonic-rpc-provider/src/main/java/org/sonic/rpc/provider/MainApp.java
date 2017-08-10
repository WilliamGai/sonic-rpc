package org.sonic.rpc.provider;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.Util;
import org.sonic.rpc.core.proxy.ProviderProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class MainApp implements CommandLineRunner{
	@Autowired
	AopDemoService service;	
	@Autowired 
	MainApp app;
	
	@Autowired
	ProviderProxyFactory p;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);
		LogCore.BASE.info("测试配置文件调用{}", context.getEnvironment().getActiveProfiles());
//		LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment()));
		LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment().getProperty("a")));
	}
	@Override
	public void run(String... arg0) throws Exception {
		LogCore.BASE.info("运行参数为{}", arg0);
		LogCore.BASE.info("测试service调用{}",service.getMappings());
		LogCore.BASE.info("{}",Util.prettyJsonStr(p.getProviderConfig()));
		LogCore.BASE.info("providers ={}",Util.prettyJsonStr(p.providers));
		LogCore.BASE.info("app ={}",app);

	}
}

