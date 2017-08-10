package org.sonic.rpc.provider;

import org.sonic.rpc.core.LogCore;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 加载Spring
 * 
<bean class="com.sonic.http.rpc.proxy.ProviderProxyFactory">
		<constructor-arg name="providers">
			<map key-type="java.lang.Class" value-type="java.lang.Object">
				<entry key="com.sonic.http.rpc.api.SpeakInterface" value-ref="speakInterface" />
			</map>
		</constructor-arg>
		<constructor-arg name="providerConfig">
			<bean id="providerConfig" class="com.sonic.http.rpc.invoke.ProviderConfig">
				<!-- <property name="target" value="/invoke" /> -->
				<constructor-arg name="port" value="8888" />
				<constructor-arg name="target" value="zookeeper://123.56.13.70:2181,123.56.13.70:2182,123.56.13.70:2183" />
			</bean>
		</constructor-arg>
	</bean>
 */
public class ProviderApp {
    public static void main(String[] args) throws Exception {
	LogCore.BASE.info("服务提供者上线了");
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
	context.start();
//	ProviderConfig obj = context.getBean(ProviderConfig.class);
//	LogCore.BASE.info("obj is {}", obj.getTarget());

	LogCore.BASE.info("ClassPathXmlApplicationContext.appName is {}", context.getApplicationName());
	context.close();
    }
}
