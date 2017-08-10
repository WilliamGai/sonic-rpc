package org.sonic.rpc.consumer;

import javax.annotation.Resource;

import org.sonic.rpc.core.LogCore;
import org.sonic.tcp.rpc.api.People;
import org.sonic.tcp.rpc.api.SpeakInterface;
import org.springframework.stereotype.Component;

@Component("peopleController")
public class PeopleController {
	@Resource
	private SpeakInterface speakInterface;

	public String getSpeak(Integer age, Integer sex) {
		LogCore.BASE.info("conroller speak------------------------");
		People people = new People();
		people.setAge(age);
		people.setSex(sex);
		return speakInterface.speak(people);
	}
}
