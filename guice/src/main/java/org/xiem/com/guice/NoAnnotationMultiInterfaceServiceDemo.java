package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class NoAnnotationMultiInterfaceServiceDemo {// GOOGLE帮我们提供了一个NAMES的模板来生成注解

	@Inject
	@Named("Www")
	private static Service wwwService;
	@Inject
	@Named("Home")
	private static Service homeService;

	public static void main(String[] args) {

		Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {

				binder.bind(Service.class).annotatedWith(Names.named("Www")).to(WwwService.class);
				binder.bind(Service.class).annotatedWith(Names.named("Home")).to(HomeService.class);

				binder.requestStaticInjection(NoAnnotationMultiInterfaceServiceDemo.class);
			}
		});

		NoAnnotationMultiInterfaceServiceDemo.homeService.execute();
		NoAnnotationMultiInterfaceServiceDemo.wwwService.execute();

		// 上面的例子中我们使用NAMED来标注我们的服务应该使用什么样的注解,当然前提是我们已经将相应的服务与注解关联起来了.
	}

}
