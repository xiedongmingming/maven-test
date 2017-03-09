package org.xiem.com.guice;

import com.google.inject.Provider;

public class WwwServiceProvider implements Provider<Service> {

	@Override
	public Service get() {
		return new WwwService();
	}

	// 上面的PROVIDER的意思很简单每次新建一个新的WWWSERVICE对象出来
}
