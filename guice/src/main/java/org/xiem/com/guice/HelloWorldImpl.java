package org.xiem.com.guice;

import com.google.inject.Singleton;

@Singleton
public class HelloWorldImpl implements HelloWorld {
	public String sayHello() {
		return "Hello world!";
	}
}
