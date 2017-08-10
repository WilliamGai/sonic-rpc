package org.sonic.tcp.rpc.provider;

import org.sonic.tcp.rpc.core.LogCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AopDemoService {
	@Autowired
	ApplicationContext context;

	public Object getMappings() {
		LogCore.BASE.info("requestMappings");
		return context.getDisplayName();
	}

}
