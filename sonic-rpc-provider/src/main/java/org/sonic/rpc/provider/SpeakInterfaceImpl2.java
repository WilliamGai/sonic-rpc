package org.sonic.rpc.provider;

import org.sonic.tcp.rpc.api.People;
import org.sonic.tcp.rpc.api.SpeakInterface;
import org.springframework.stereotype.Component;

@Component("speakInterface2")
public class SpeakInterfaceImpl2 implements SpeakInterface {
	public String speak(People people) {
		if (people.getAge() > 18) {
			return "小朋友";
		}
		if (people.getSex() == 0) {
			return "female" + people.getAge() + "岁";
		}
		return "male" + people.getAge() + "岁";
	}
}
