package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class HelleWorldTest {

	public static void main(String[] args) {
		TestSayHello();
	}

	public static void TestSayHello() {
		Injector inj = Guice.createInjector(new Module() {// 必须首先创建一个INJECTOR--通过MODULE
			public void configure(Binder binder) {

				// *************************************************************
				// 这时创建的实例各不相同
				// binder.bind(HelloWorld.class).to(HelloWorldImpl.class);

				// *************************************************************
				// GUICE目前看起来不允许多个实例绑定到同一个接口上了(加上下面的行将在编译时运行时报错)
				// binder.bind(HelloWorld.class).to(HelloWorldImplAgain.class);

				// *************************************************************
				// 可以绑定一个实现类到实现类么?非常不幸不可以自己绑定到自己
				// 通过查看BIND的语法发现只能绑定一个类的子类到其本身.改造下,改用子类替代.
				// binder.bind(HelloWorldImpl.class).to(HelloWorldImpl.class);

				// *************************************************************
				// 支持子类绑定,这样即使我们将一个实现类发布出去了(尽管不推荐这么做),我们在后期仍然有办法替换实现类.
				// 使用BIND有一个好处,由于JAVA5以上的泛型在编译器就确定了,所以可以帮我们检测出绑定错误的问题,而
				// 这个在配置文件中是无法检测出来的.这样看起来MODULE像是一个MAP,根据一个KEY获取其VALUE,非常简单的逻辑.
				binder.bind(HelloWorldImpl.class).to(HelloWorldSubImpl.class);

				// *************************************************************
				// 可以绑定到我们自己构造出来的实例--是同一个实例
				// binder.bind(HelloWorld.class).toInstance(new
				// HelloWorldImpl());

				// *************************************************************
				// 绑定一个单例
				// 可以看到现在获取的实例已经是单例的,不再每次请求生成一个新的实例.事实上GUICE提供两种SCOPE
				// com.guice.inject.Scopes.SINGLETON和com.guice.inject.Scopes.NO_SCOPE
				// 所谓没有SCOPE即是每次生成一个新的实例.
				// 对于自动注入就非常简单了,只需要在实现类加一个SINGLETON注解即可.
				binder.bind(HelloWorld.class).to(HelloWorldImplAgain.class).in(Scopes.SINGLETON);

			}

		});

		HelloWorld hw1 = inj.getInstance(HelloWorld.class);// 不是单例的每次都会返回一个新的实例
		HelloWorld hw2 = inj.getInstance(HelloWorld.class);

		System.out.println(hw1.sayHello());
		System.out.println(hw2.sayHello());

		System.out.println(System.identityHashCode(hw1));
		System.out.println(System.identityHashCode(hw2));

		System.out.println(hw1 == hw2);

		// 确实只是返回了一个正常的实例并没有做过多的转换和代理
		// org.xiem.com.HelloWorldImpl
		// org.xiem.com.HelloWorldImpl
		System.out.println(hw1.getClass().getName());
		System.out.println(hw2.getClass().getName());

		HelloWorldImpl hw = inj.getInstance(HelloWorldImpl.class);
		System.out.println(hw.sayHello());

		HelloWorld hw3 = inj.getInstance(HelloWorld.class);
		HelloWorld hw4 = inj.getInstance(HelloWorld.class);
		System.out.println(hw3.hashCode() + "->" + hw4.hashCode());
	}
}
