package org.xiem.com.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;

//属性注入(FIELD INJECT)

public class FieldInjectDemo {

	// 基本属性注入--前提是该接口已经被关联到了实现类上
	@Inject
	private Service servcie;

	public Service getServcie() {
		return servcie;
	}

	public static void main(String[] args) {

		// ******************************************************************************
		FieldInjectDemo demo = Guice.createInjector().getInstance(FieldInjectDemo.class);
		demo.getServcie().execute();

		// ******************************************************************************
		// 自己构造FIELDINJECTDEMO对象而不通过GUICE
		// 由于FIELDINJECTDEMO不属于GUICE容器(暂且称为容器吧)托管,这样SERVICE服务没有机会被注入到FIELDINJECTDEMO类中
		FieldInjectDemo fd = new FieldInjectDemo();
		fd.getServcie().execute();
	}

}
