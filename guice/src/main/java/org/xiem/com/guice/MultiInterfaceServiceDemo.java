package org.xiem.com.guice;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;

public class MultiInterfaceServiceDemo {// 接口多实现

	// ECLIPSE提示怎么解决: Syntax error on tokens, delete these tokens
	// 有中文字符或者符号,包括空格.上次遇到一个问题,检查了一遍语法没错误,后来发现是拷贝代码的时候有一部分中文空格没删除,就出现这个问题了.一个个删除就OK了.

	// 如果一个接口有多个实现,这样通过@INJECT和MODULE都难以直接实现,但是这种现象确实是存在的,于是GUICE提供了其它注入方式来解决此问题.比如下面的自定义注解.
	// 上面的代码描述的是一个SERVICE服务,有WWWSERVICE和HOMESERVICE两个实现,
	// 同时有WWW和HOME两个注解(如果对注解各个参数不明白的需要单独去学习JAVA5注解).好了下面请出我们的主角.

	// 此类的结构是注入两个SERVICE服务,其中WWWSERVICE是注入@WWW注解关联的WWWSERVICE服务,而HOMESERVICE是注入@HOME注解关联的HOMESERVICE服务.

	@Inject
	@Www
	private Service wwwService;
	@Inject
	@Home
	private Service homeService;// 不同的实现
	
	public static void main(String[] args) {
		MultiInterfaceServiceDemo misd = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(Service.class).annotatedWith(Www.class).to(WwwService.class);
				binder.bind(Service.class).annotatedWith(Home.class).to(HomeService.class);
			}
		}).getInstance(MultiInterfaceServiceDemo.class);
		misd.homeService.execute();
		misd.wwwService.execute();
	}

}
