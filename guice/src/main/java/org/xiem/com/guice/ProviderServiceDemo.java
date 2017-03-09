package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ProviderServiceDemo {

	// PROVIDER注入--首先我们需要构造一个PROVIDER<T>出来.

	@Inject
	private Service service;

	public static void main(String[] args) {
		Injector inj = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service.class).toProvider(WwwServiceProvider.class);
			}
		});
		ProviderServiceDemo psd = inj.getInstance(ProviderServiceDemo.class);// 表示将参数指定类纳入到GUICE的管理之中
		psd.service.execute();
	}

	// 很显然如果这东西和线程绑定就非常好了,比如我们可以使用THREADLOCAL来做线程的对象交换.
	// 当然如果想自动注入(不使用MODULE手动关联)服务的话,可以使用@PROVIDERBY注解.
	// 这样我们就不必使用MODULE将PROVIDER绑定到SERVICE上,获取服务就很简单了.
	// ProviderServiceDemo psd = Guice.createInjector().getInstance(ProviderServiceDemo.class);
	// psd.service.execute();

	// 除了上述两种方式我们还可以注入Provider,而不是注入服务,比如下面的例子例子中,属性不再是Service,而是一个Provider<Service>.
	// public class ProviderServiceDemo {
	//
	// @Inject
	// private Provider<Service> provider;
	//
	// public static void main(String[] args) {
	// ProviderServiceDemo psd = GUICE.createInjector(new Module() {
	// @Override
	// public void configure(Binder binder) {
	// binder.bind(Service.class).toProvider(WwwServiceProvider.class);
	// }
	// }).getInstance(ProviderServiceDemo.class);
	// psd.provider.get().execute();
	// }
	// }

	// 当然了,由于我们WwwServiceProvider每次都是构造一个新的服务出来,因此在类ProviderServiceDemo中的provider每次获取的服务也是不一样的.
}
