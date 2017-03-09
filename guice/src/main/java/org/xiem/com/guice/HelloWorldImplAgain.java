package org.xiem.com.guice;

import com.google.inject.Singleton;

@Singleton
public class HelloWorldImplAgain implements HelloWorld {

	@Override
	public String sayHello() {
		return "Hello world again!";
	}

}
