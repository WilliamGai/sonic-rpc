package org.sonic.tcp.rpc.provider;

import org.sonic.tcp.rpc.api.People;
import org.sonic.tcp.rpc.api.SpeakInterface;
import org.springframework.stereotype.Component;

@Component("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface {
    public String speak(People people) {
	if (people.getAge() > 18) {
	    if (people.getSex() == 0) {
		return "female" + people.getAge() + "岁";
	    } else {
		return "male" + people.getAge() + "岁";
	    }
	} else {
	    return "小朋友";
	}
    }
}
