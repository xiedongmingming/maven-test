package org.xiem.com.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class ConstructorInjectDemo {// 构造函数注入--通过GUICE获取实例时会自动注入(传递参数)

	private Service service;

	private HelloWorld helloWorld;

	// @Inject
	// public ConstructorInjectDemo(Service service) {
	//
	// // **************************************************************
	// // 我们在构造函数上添加@INJECT来达到自动注入的目的.构造函数注入的好处是可以保证只有一个地方
	// // 来完成属性注入,这样可以确保在构造函数中完成一些初始化工作(尽管不推荐这么做).当然构造函数注
	// // 入的缺点是类的实例化与参数绑定了,限制了实例化类的方式.
	// // **************************************************************
	//
	// this.service = service;
	// }

	@Inject
	public ConstructorInjectDemo(Service service, HelloWorld helloWorld) {// 构造函数中可以自动注入多个参数
		System.out.println("++++++++++++++++++++++++++++++++++");
		// 非常完美的支持了多参数构造函数注入.当然了没有必要写多个@INJECT,而且写了的话不能通过编译.
		this.service = service;
		this.helloWorld = helloWorld;
	}

	public Service getService() {
		return service;
	}
	public HelloWorld getHelloWorld() {
		return helloWorld;
	}

	public static void main(String[] args) {

		// ConstructorInjectDemo cid =
		// Guice.createInjector().getInstance(ConstructorInjectDemo.class);
		// cid.getService().execute();

		ConstructorInjectDemo cid = Guice.createInjector().getInstance(ConstructorInjectDemo.class);
		cid.getService().execute();
		System.out.println(cid.getHelloWorld().sayHello());
	}
}
