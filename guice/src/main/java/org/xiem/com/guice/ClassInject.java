package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;

public class ClassInject {// 类依赖注入

	// 所谓的绑定就是将一个接口绑定到具体的类中,这样客户端不用关心具体的实现,而只需要获取相应的接口完成其服务即可

	public static void GuiceTest001() {// GUICE提供了一个方式(PROVIDER<T>)允许自己提供构造对象的方式
		Injector inj = Guice.createInjector(new Module() {
			public void configure(Binder binder) {
				binder.bind(HelloWorld.class).toProvider(new Provider<HelloWorld>() {
					public HelloWorld get() {
						return new HelloWorldImpl();
					}
				});


			}
		});

		HelloWorld hw = inj.getInstance(HelloWorld.class);
		System.out.println(hw.sayHello());

	}

	public static void GuiceTest002() {// 提供自动寻找实现类

		// 实现类可以不经过绑定就获取么?比如我想获取HELLOWORLDIMPL的实例而不通过MODULE绑定么?可以,实际上GUICE能够自动寻找实现类.

		Injector inj = Guice.createInjector();
		HelloWorld hw = inj.getInstance(HelloWorldImpl.class);
		System.out.println(hw.sayHello());
	}

	public static void GuiceTest003() {// 可以使用注解方式完成注入

		// GUICE提供了注解的方式完成关联.我们需要在接口上指明此接口被哪个实现类关联了

		// 事实上对于一个已经被注解的接口我们仍然可以使用MODULE来关联,这样获取的实例将是MODULE关联的实
		// 例,而不是@IMPLEMENTEDBY注解关联的实例.这样仍然遵循一个原则,手动优于自动.
		Injector inj = Guice.createInjector();
		HelloWorld hw = inj.getInstance(HelloWorld.class);
		System.out.println(hw.sayHello());
	}

	public static void main(String[] args) {
		GuiceTest003();
	}
}
