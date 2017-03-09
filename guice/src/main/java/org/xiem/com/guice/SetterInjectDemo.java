package org.xiem.com.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class SetterInjectDemo {

	private Service service;

	@Inject
	public void setService(Service service) {// 会在创建实例时注入
		System.out.println("正在注入中");
		this.service = service;
	}

	public SetterInjectDemo() {
		super();
		System.out.println("构造时的值: " + getService());

	}

	public Service getService() {
		return service;
	}

	public static void main(String[] args) {
		SetterInjectDemo sid = Guice.createInjector().getInstance(SetterInjectDemo.class);
		sid.getService().execute();
	}


}
