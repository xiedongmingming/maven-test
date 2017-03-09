package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class StaticMultiInterfaceServiceDemo {// 静态注入多个服务

	// 一个属性不可以绑定多个服务



	@Inject
	@Www
	private static Service wwwService;
	@Inject
	@Home
	private static Service homeService;

	public static void main(String[] args) {

		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service.class).annotatedWith(Www.class).to(WwwService.class);
				binder.bind(Service.class).annotatedWith(Home.class).to(HomeService.class);
				binder.requestStaticInjection(StaticMultiInterfaceServiceDemo.class);
			}
		});
		StaticMultiInterfaceServiceDemo.homeService.execute();
		StaticMultiInterfaceServiceDemo.wwwService.execute();
	}
}
