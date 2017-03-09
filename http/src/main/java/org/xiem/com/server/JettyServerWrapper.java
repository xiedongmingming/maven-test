package org.xiem.com.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.HashSet;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.io.ManagedSelector;
import org.eclipse.jetty.io.SelectChannelEndPoint;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ConnectorStatistics;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

@SuppressWarnings("deprecation")
public class JettyServerWrapper {

	public final Server server;

	private final JettySpec spec;// JETTY的配置参数

	private HttpConnectionFactoryCarryingBidRequestPaths connectionFactory;

	private ServletContextHandler servletContextRoot;// 根路径

	public JettyServerWrapper() {// 构造器

		this.spec = new JettySpec();

		final QueuedThreadPool threadPool = new QueuedThreadPool(spec.maxThreads);// 创建线程池(21)

		threadPool.setMinThreads(spec.minThreads);
		threadPool.setName("jetty thread pool");

		server = new Server(threadPool);
	}

	private static Listener getLifeCycleListener() {
		return new Listener() {// 匿名类
			@Override
			public void lifeCycleStopping(LifeCycle lifeCycle) {
				System.out.println("listener stopping..........");
			}

			@Override
			public void lifeCycleStopped(LifeCycle lifeCycle) {
				System.out.println("listener stopped..........");
			}

			@Override
			public void lifeCycleStarting(LifeCycle lifeCycle) {
				System.out.println("listener starting..........");
			}

			@Override
			public void lifeCycleStarted(LifeCycle lifeCycle) {
				System.out.println("listener started..........");
			}

			@Override
			public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {
				System.out.println("listener failure..........");
			}
		};
	}

	public void addConnectors() {// 第一步:添加连接器

		// 添加了两个连接器:
		// 1、常规连接器(带有监听器)
		// 2、控制连接器

		Listener regularConnListener = getLifeCycleListener();

		final ServerConnector regularConnector = createRegularConnector(regularConnListener, spec.maxHeaderLength,
				spec.acceptors, spec.selectors, spec.port, spec.acceptQueueSize, spec.idleTimeout);
		final ServerConnector controlConnector = createControlConnector(spec.maxHeaderLength, spec.controlAcceptors,
				spec.controlSelectors, spec.controlPort, spec.acceptQueueSize, spec.controlIdleTimeout);

		server.setConnectors(new Connector[] { regularConnector, controlConnector });
	}

	private ServerConnector createRegularConnector(final Listener lifeCycleListener, final int maxHeaderLength,
			final int numAcceptors, final int numSelectors, final int port, final int acceptQueueSize,
			final int idleTimeout) {// 该连接器有监听器--常规连接器

		HttpConfiguration httpConfig = new HttpConfiguration();// 配置HTTP

		httpConfig.setSendServerVersion(false);

		httpConfig.addCustomizer(new HttpConfiguration.Customizer() {

			@Override
			public void customize(Connector connector, HttpConfiguration config, Request request) {// 注意:请求为JETTYSERVER中的请求

				System.out.println("定制中的连接器类型: " + connector.getClass().getName());

				String xForwardedProtoVal = request.getHeader(HttpHeader.X_FORWARDED_PROTO.asString());// 表示请求中携带了域:X-Forwarded-Proto:
																										// https

				if (xForwardedProtoVal != null && xForwardedProtoVal.equals("https")) {// 只支持HTTPS协议--该域的值
					request.setSecure(true);
				} else {
					request.setSecure(false);
				}

			}
		});

		httpConfig.setRequestHeaderSize(maxHeaderLength);
		httpConfig.setResponseHeaderSize(maxHeaderLength);

		connectionFactory = new HttpConnectionFactoryCarryingBidRequestPaths(httpConfig);// 连接工厂(自定义类主要用于统计连接的建立和断开)

		// 服务连接器需要的参数:
		// @Name("server") Server server -- 服务器实例
		// @Name("executor") Executor executor -- 默认为空
		// @Name("scheduler") Scheduler scheduler -- 默认为空
		// @Name("bufferPool") ByteBufferPool bufferPool -- 默认为空
		// @Name("acceptors") int acceptors -- 默认为-1
		// @Name("selectors") int selectors -- 默认为-1
		// @Name("factories") ConnectionFactory... factories --
		// 默认为(HttpConnectionFactory)
		final ServerConnector regularConnect = new ServerConnector(server, null, null, null, numAcceptors, numSelectors,
				connectionFactory) {
			@Override
			protected SelectChannelEndPoint newEndPoint(SocketChannel channel, ManagedSelector selectSet,
					SelectionKey key) throws IOException {// 为建立的连接生成一个端点
				return new YciSelectChannelEndPoint(channel, selectSet, key, getScheduler(), getIdleTimeout());// 这里面的两个函数调用的都是父类中的函数
			}
			// 用于统计端点被选中的时间
		};

		regularConnect.setPort(port);
		regularConnect.setReuseAddress(true);// 重用地址
		regularConnect.setSoLingerTime(0);
		regularConnect.addLifeCycleListener(lifeCycleListener);
		regularConnect.setAcceptQueueSize(acceptQueueSize);// 缓存的大小
		regularConnect.setIdleTimeout(idleTimeout);

		return regularConnect;
	}

	private ServerConnector createControlConnector(int maxHeaderLength, int numAcceptors, int numSelectors, int port,
			int acceptQueueSize, int idleTimeout) {

		final QueuedThreadPool threadPoolControl = new QueuedThreadPool();//
		threadPoolControl.setMaxThreads(20);
		threadPoolControl.setMinThreads(20);
		threadPoolControl.setName("control jetty thread pool");

		HttpConfiguration controlHttpConfig = new HttpConfiguration();

		controlHttpConfig.setSendServerVersion(false);
		controlHttpConfig.setRequestHeaderSize(maxHeaderLength);
		controlHttpConfig.setResponseHeaderSize(maxHeaderLength);

		// 服务连接器需要的参数:
		// @Name("server") Server server -- 服务器实例
		// @Name("executor") Executor executor -- 默认为空
		// @Name("scheduler") Scheduler scheduler -- 默认为空
		// @Name("bufferPool") ByteBufferPool bufferPool -- 默认为空
		// @Name("acceptors") int acceptors -- 默认为-1
		// @Name("selectors") int selectors -- 默认为-1
		// @Name("factories") ConnectionFactory... factories --
		// 默认为(HttpConnectionFactory)

		final ServerConnector controlConnector = new ServerConnector(server, threadPoolControl, null, null,
				numAcceptors, numSelectors, new HttpConnectionFactory(controlHttpConfig));

		controlConnector.setPort(port);
		controlConnector.setReuseAddress(true);
		controlConnector.setSoLingerTime(0);
		controlConnector.setAcceptQueueSize(acceptQueueSize);
		controlConnector.setIdleTimeout(idleTimeout);

		return controlConnector;
	}

	private boolean createAndRegisterBidRequestHandlers() {// 各个平台对应一个HANDLER--并外加调试和模拟器

		HashSet<String> bidRequestServletPathSet = new HashSet<String>();

		// this.register(servlet, servletPath);

		// bidRequestServletPathSet.add(servletPath);

		if (connectionFactory != null) {
			connectionFactory.setBidRequestServletPaths(bidRequestServletPathSet);
		} else {

		}

		return true;
	}

	private boolean createAndRegisterAuditRequestHandlers() {// 暂时不用了

		// this.register(auditServlet, auditServletPath);

		return true;
	}

	public void addHandlersAndFilters() throws MalformedURLException {// 第二步:添加处理器和过滤器

		final ContextHandlerCollection contexts = new ContextHandlerCollection();// 处理器容器

		if (spec.jettyStatsEnabled) {// 添加统计相关的内容

			ConnectorStatistics.addToAllConnectors(server);// 表示将连接统计添加到服务中的所有连接器中:ConnectorStatistics

			final StatisticsHandler statsHandler = new StatisticsHandler();

			server.setHandler(statsHandler);

			statsHandler.setHandler(contexts);// 包装作用--ContextHandlerCollection

		} else {
			server.setHandler(contexts);// 绑定到服务中
		}

		// 绑定在一起????
		servletContextRoot = new ServletContextHandler(contexts, "/", ServletContextHandler.NO_SECURITY);// 也是一种处理器

		URL base = MainTest.class.getResource("MainServer.class");// ?????

		System.out.println("BASE URL: " + base.toString());

		base = new URL(base, "../..");// 返回路径上两层

		System.out.println("BASE URL: " + base.toString());

		// 测试结果:
		// BASE URL:
		// file:/home/admin2/xieming/rts/main/main/target/classes/com/yosemitecloud/rts/main/server/MainServer.class
		// BASE URL:
		// file:/home/admin2/xieming/rts/main/main/target/classes/com/yosemitecloud/rts/

		servletContextRoot.setBaseResource(Resource.newResource(base));// 设置JETTY资源的根路径:file:/home/admin2/xieming/rts/main/main/target/classes/com/yosemitecloud/rts/
		servletContextRoot.getServletContext().getContextHandler().setMaxFormContentSize(10000000);// 设置表单的最大提交内容

		// *******************************************
		// handlers -- 所有的处理器都在此处添加
		// *******************************************
		// this.register(new RobotsTxtServlet(), "robots.txt");
		// this.register(new CrossDomainServlet(), "crossdomain.xml");

		this.createAndRegisterBidRequestHandlers();// 竞价相关的处理器(每个平台对应一个)--也是一种SERVLET
		this.createAndRegisterAuditRequestHandlers();
		// this.register(new WinNotificationHandler(), "WinNotification");
		this.registerUserRequestHandlers();// 用户相关的请求--AC、AS等

		// if (config.getBoolean(ConfigurationKey.SUPPORT_USER_PROFILE_ACCESSOR,
		// false)) {// 默认没有设置
		// this.register(new UserProfileAccessor(), "userProfile");
		// }
		// if (config.getBoolean(ConfigurationKey.SUPPORT_BEACON_GENERATOR,
		// false)) {
		// this.register(new BeaconGeneratorServlet(contextSupplier),
		// "beaconGenerator");
		// }
		// if (config.getBoolean(ConfigurationKey.SUPPORT_BID_SIMULATOR, false))
		// {
		// this.register(new BidSimulatorServlet(contextSupplier),
		// "bidSimulator");
		// }
		//
		// FugetechHandler fuge = new FugetechHandler(serverId, contextSupplier,
		// eventRecorder);
		// this.register(fuge, "FUGE_ORDER");
		// this.register(fuge, "FUGE_ORD_SB");

		// filters
		// servletContextRoot.addFilter(new FilterHolder(new RobotsTxtFilter()),
		// "*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
		// DispatcherType.INCLUDE, DispatcherType.ERROR));
		// servletContextRoot.addFilter(new FilterHolder(new
		// CrossDomainFilter()),
		// "*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
		// DispatcherType.INCLUDE, DispatcherType.ERROR));

		// *******************************************
		// filters
		// *******************************************
		// servletContextRoot.addFilter(new FilterHolder(new RobotsTxtFilter()),
		// "*", EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
		// DispatcherType.INCLUDE, DispatcherType.ERROR));
	}
	// **************************************************************************************************************

	public void start() throws Exception {
		server.start();
	}

	private void registerUserRequestHandlers() {// 注册所有的用户请求处理器

	}
}
