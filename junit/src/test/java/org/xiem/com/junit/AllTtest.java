package org.xiem.com.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AppTest1.class, AppTest2.class }) // 表明这个类是一个打包测试类
public class AllTtest {// 打包测试--JUNIT为我们提供了打包测试的功能(将所有需要运行的测试类集中起来一次性的运行完毕)

}
