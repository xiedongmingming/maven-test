package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class StaticFieldInjectDemo {

	// 非常棒!上面我们并没有使用GUICE获取一个STATICFIELDINJECTDEMO实例(废话),
	// 实际上STATIC字段(属性)是类相关的,因此我们需要请求静态注入服务.但是一个好处是在
	// 外面看起来我们的服务没有GUICE绑定,甚至CLIENT不知道(或者不关心)服务的注入过程.

	@Inject
	private static Service service;

	public static void main(String[] args) {
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.requestStaticInjection(StaticFieldInjectDemo.class);// 在此过程执行注入(注意此处的绑定函数)
			}
		});
		StaticFieldInjectDemo.service.execute();
	}
}
