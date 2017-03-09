package org.xiem.com.guice;

import com.google.inject.ProvidedBy;

@ProvidedBy(WwwServiceProvider.class)
public interface Service {// @ImplementedBy(ServiceImpl.class)
	void execute();
}
