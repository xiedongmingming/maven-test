package org.xiem.com.guice;

public class ServiceImpl implements Service {

	public ServiceImpl() {
		super();
		System.out.println(".............................");
	}

	@Override
	public void execute() {
		System.out.println("this is made by imxylz (www.imxylz.cn).");
	}

}
