package org.xiem.com.server;

public class JettySpec {

	// ************************************************************************************
	// 下面是通过配置文件配置的6个参数
	public final int maxThreads;// 线程池的最大线程数
	public final int minThreads;// 线程池的最小线程数

	public final int acceptors;// 1
	public final int selectors;// 8
	public final int idleTimeout;// 5000
	public final int port;// 8080

	// ************************************************************************************
	// 下面是写死在构造函数中的
	public final boolean jettyStatsEnabled;// 使能JETTY统计相关的功能--false

	public final int maxCookieLength;// 128
	public final int maxHeaderLength;// 128+16384(表示HTTP头部的长度限制)
	public final int acceptQueueSize;// 500
	public final int controlAcceptors;// 1
	public final int controlSelectors;// 2
	public final int controlIdleTimeout;// 30000
	public final int controlPort;// 8082
	// ************************************************************************************

	public JettySpec() {

		// ************************************************************************************
		maxThreads = 21;// 配置文件为21
		minThreads = maxThreads;
		acceptors = 1;
		selectors = 8;
		idleTimeout = 5000;
		port = 8080;
		// ************************************************************************************8

		controlAcceptors = 1;
		controlSelectors = 2;

		controlIdleTimeout = 30000;
		controlPort = 8082;

		maxCookieLength = 128;
		maxHeaderLength = maxCookieLength + 16384;
		acceptQueueSize = 500;

		jettyStatsEnabled = false;

		// ************************************************************************************
	}
}
