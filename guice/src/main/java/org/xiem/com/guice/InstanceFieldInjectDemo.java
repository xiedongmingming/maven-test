package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class InstanceFieldInjectDemo {// 注入实例变量的属性

	@Inject
	private Service service;

	public static void main(String[] args) {
		final InstanceFieldInjectDemo ifid = new InstanceFieldInjectDemo();
		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.requestInjection(ifid);
			}
		});
		ifid.service.execute();

		// ***********************************************************
		// 一种简便的方法来注入字段,实际上此方法也支持SETTER注入
		// InstanceFieldInjectDemo ifid = new InstanceFieldInjectDemo();
		// Guice.createInjector().injectMembers(ifid);
		// ifid.service.execute();
	}
}
