package org.sonic.rpc.provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@ConditionalOnClass
public class Test {

	public static void main(String[] args) {
		System.out.println("hullo");
	}

}
