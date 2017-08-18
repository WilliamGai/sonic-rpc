package org.sonic.srpc.excample.consumer;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.annotation.SReference;
import org.sonic.srpc.excample.api.PlateService;
import org.springframework.stereotype.Component;

@Component("peopleController")
public class PlateController {
	@SReference
//	@Autowired
	private PlateService service;

	public String applyPlate(Integer age, Integer sex) {
		LogCore.BASE.info("conroller speak------------------------"+service.getClass());
		return service.applyOnePalte("william gai","宁夏");
	}
}
