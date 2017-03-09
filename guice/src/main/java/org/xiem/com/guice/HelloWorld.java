package org.xiem.com.guice;

import com.google.inject.ImplementedBy;

@ImplementedBy(HelloWorldImpl.class)
public interface HelloWorld {
	String sayHello();
}
