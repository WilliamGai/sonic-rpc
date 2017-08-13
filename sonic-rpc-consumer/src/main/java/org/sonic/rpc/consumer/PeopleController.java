package org.sonic.rpc.consumer;

import javax.annotation.Resource;

import org.sonic.rpc.core.LogCore;
import org.sonic.tcp.rpc.api.People;
import org.sonic.tcp.rpc.api.PlateService;
import org.springframework.stereotype.Component;

@Component("peopleController")
public class PeopleController {
	@Resource
	private PlateService service;

	public String getSpeak(Integer age, Integer sex) {
		LogCore.BASE.info("conroller speak------------------------"+service.getClass());
		People people = new People();
		people.setAge(age);
		people.setSex(sex);
		return service.applyOnePalte("wiiliam");
	}
}
