package org.sonic.rpc.provider;

import java.util.Arrays;

import org.sonic.rpc.core.LogCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class MainApp implements CommandLineRunner{
//	@Autowired 
//	MainApp app;
	
//	@Autowired
//	ProviderProxyFactory p;
	
	
//	@Autowired
//	List<SpeakInterface> list;
//	@Autowired
//	Map<String,SpeakInterface> map;
//	
	@Autowired
	ApplicationContext ct;
//	
	public static void main(String[] args) {
		LogCore.BASE.info("{}开始");

		ConfigurableApplicationContext context = SpringApplication.run(MainApp.class, args);
		LogCore.BASE.info("测试配置文件调用{}", Arrays.toString(context.getEnvironment().getActiveProfiles()));
//		LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment()));
//		Map<String,Object> map = context.getBeansWithAnnotation(SService.class);
//		LogCore.BASE.info("{}",Util.prettyJsonStr(context.getEnvironment().getProperty("a")));
//		LogCore.BASE.info("map by annoation = {}",Util.prettyJsonStr(map));
//		LogCore.BASE.info("PlateService by getBean = {}",context.getBean(PlateService.class));
//		LogCore.BASE.info("PlateServiceImpl by getBean = {}",context.getBean(PlateServiceImpl.class));
//		LogCore.BASE.info("context.getClass() = {}",context.getClass());
	}
	@Override
	public void run(String... arg0) throws Exception {
//		LogCore.BASE.info("运行参数为{}", Arrays.toString(arg0));
//		LogCore.BASE.info("{}",Util.prettyJsonStr(p.getProviderConfig()));
//		LogCore.BASE.info("providers ={}",Util.prettyJsonStr(p.providers));
//		LogCore.BASE.info("app ={}",app);
//		LogCore.BASE.info("list ={}",Util.prettyJsonStr(list));
//		LogCore.BASE.info("map ={}",Util.prettyJsonStr(map));
		LogCore.BASE.info("context autowired = {}",ct.getClass());//AnnotationConfigApplicationContext

//		Class c = void.clasass;

	}
}

