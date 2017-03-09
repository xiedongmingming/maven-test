package org.xiem.com.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;

public class FieldInjectDemo2 {

	@Inject
	private static Service servcie;// 注入静态属性

	public static Service getServcie() {
		return servcie;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// GUICE看起来还不支持静态字段注入
		FieldInjectDemo2 fd = Guice.createInjector().getInstance(FieldInjectDemo2.class);
		FieldInjectDemo2.getServcie().execute();
	}
}
