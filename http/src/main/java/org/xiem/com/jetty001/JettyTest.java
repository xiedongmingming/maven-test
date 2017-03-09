package org.xiem.com.jetty001;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import sun.misc.Signal;
import sun.misc.SignalHandler;

@SuppressWarnings("restriction")
public class JettyTest implements SignalHandler {

	public Server server = null;// JETTY������

	@SuppressWarnings("unused")
	private ServletContextHandler servletContextRoot;// ���������

	@SuppressWarnings("unused")
	private HttpConnectionFactory connectionFactory;// �������

	public JettyTest() {
		// final QueuedThreadPool threadPool = new QueuedThreadPool(21, 21);//
		// �����������������̳߳�
		// threadPool.setName("Jetty Queued Thread Pool");
		// server = new Server(threadPool);
	}

	@Override
	public void handle(Signal arg0) {
		// TODO Auto-generated method stub

	}

	// public void addHandlersAndFilters() throws MalformedURLException {
	//
	// final ContextHandlerCollection contexts = new ContextHandlerCollection();
	//
	// server.setHandler(contexts);
	//
	// servletContextRoot = new ServletContextHandler(contexts, "/",
	// ServletContextHandler.NO_SECURITY);
	//
	// // handlers
	// // this.register(new RobotsTxtServlet(), "robots.txt");
	//
	// }
	//
	// private void register(final Servlet servlet, final String... paths) {
	// if (servletContextRoot == null) {
	// throw new IllegalStateException("servletContextRoot not initiated");
	// }
	// for (String path : paths) {
	// servletContextRoot.addServlet(new ServletHolder(servlet), "/" + path);
	// }
	// }
	//
	// private ServerConnector createRegularConnector(final Listener
	// lifeCycleListener, final int maxHeaderLength,
	// final int numAcceptors, final int numSelectors, final int port, final int
	// acceptQueueSize,
	// final int idleTimeout) {
	//
	// /************************************************************************************************
	// * This class is a holder of HTTP configuration for use by the
	// * HttpChannel class. Typically a HTTPConfiguration instance is
	// * instantiated and passed to a ConnectionFactory that can create HTTP
	// * channels (e.g. HTTP, AJP or FCGI). The configuration held by this
	// * class is not for the wire protocol, but for the interpretation and
	// * handling of HTTP requests that could be transported by a variety of
	// * protocols.
	// **********************************************************************************************/
	// HttpConfiguration httpConfig = new HttpConfiguration();// HTTP������Ϣ
	//
	// httpConfig.setSendServerVersion(true);
	// httpConfig.addCustomizer(new HttpConfiguration.Customizer() {//
	// �����Զ�HTTP����
	// @Override
	// public void customize(Connector connector, HttpConfiguration config,
	// Request request) {
	// String xForwardedProtoVal =
	// request.getHeader(HttpHeader.X_FORWARDED_PROTO.asString());
	// if (xForwardedProtoVal != null && xForwardedProtoVal.equals("https")) {
	// request.setSecure(true);// ��ȫ�������
	// } else {
	// request.setSecure(false);
	// }
	// }
	// });
	//
	// httpConfig.setRequestHeaderSize(maxHeaderLength);
	// httpConfig.setResponseHeaderSize(maxHeaderLength);
	//
	// connectionFactory = new HttpConnectionFactory(httpConfig);
	//
	// final ServerConnector regularConnect = new ServerConnector(server, null,
	// null, null, numAcceptors, numSelectors,
	// connectionFactory) {
	// @Override
	// protected SelectChannelEndPoint newEndPoint(SocketChannel channel,
	// ManagedSelector selectSet,
	// SelectionKey key) throws IOException {
	// return new SelectChannelEndPoint(channel, selectSet, key, getScheduler(),
	// getIdleTimeout());
	// }
	// };
	//
	// regularConnect.setPort(port);
	// regularConnect.setReuseAddress(true);
	// regularConnect.setSoLingerTime(0);
	// regularConnect.addLifeCycleListener(lifeCycleListener);// ??????
	// regularConnect.setAcceptQueueSize(acceptQueueSize);
	// regularConnect.setIdleTimeout(idleTimeout);
	//
	// return regularConnect;
	// }
	//
	// private ServerConnector createControlConnector(int maxHeaderLength, int
	// numAcceptors, int numSelectors, int port,
	// int acceptQueueSize, int idleTimeout) {
	//
	// final QueuedThreadPool threadPoolControl = new QueuedThreadPool();
	// threadPoolControl.setMaxThreads(20);
	// threadPoolControl.setMinThreads(20);
	//
	// threadPoolControl.setName("Control Jetty Thread Pool");
	//
	// HttpConfiguration controlHttpConfig = new HttpConfiguration();
	// controlHttpConfig.setSendServerVersion(false);
	// controlHttpConfig.setRequestHeaderSize(maxHeaderLength);
	// controlHttpConfig.setResponseHeaderSize(maxHeaderLength);
	//
	// final ServerConnector controlConnector = new ServerConnector(server,
	// threadPoolControl, null, null,
	// numAcceptors, numSelectors, new
	// HttpConnectionFactory(controlHttpConfig));
	//
	// controlConnector.setPort(port);
	// controlConnector.setReuseAddress(true);
	// controlConnector.setSoLingerTime(0);
	// controlConnector.setAcceptQueueSize(acceptQueueSize);
	// controlConnector.setIdleTimeout(idleTimeout); // in ms
	//
	// return controlConnector;
	// }
	//
	// private static Listener getLifeCycleListener() {
	// return new Listener() {
	// @Override
	// public void lifeCycleStopping(LifeCycle lifeCycle) {
	// System.out.println("lifeCycleStopping...........................");
	// }
	//
	// @Override
	// public void lifeCycleStopped(LifeCycle lifeCycle) {
	// System.out.println("lifeCycleStopped...........................");
	// }
	//
	// @Override
	// public void lifeCycleStarting(LifeCycle lifeCycle) {
	// System.out.println("lifeCycleStarting...........................");
	// }
	//
	// @Override
	// public void lifeCycleStarted(LifeCycle lifeCycle) {
	// System.out.println("lifeCycleStarted...........................");
	// }
	//
	// @Override
	// public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {
	// System.out.println("lifeCycleFailure...........................");
	// }
	// };
	// }
	//
	// public void addConnectors() {// ��������� -- ���ڼ�������Ľӿ�
	//
	// Listener regularConnListener = getLifeCycleListener();
	//
	// final ServerConnector regularConnector =
	// createRegularConnector(regularConnListener, 128 + 16384, 1, 8, 8080,
	// 500, 5000);
	// // final ServerConnector controlConnector = createControlConnector(128 +
	// // 16384, 1, 2, 8082, 500, 30000);
	//
	// server.setConnectors(
	// new Connector[] { regularConnector/* , controlConnector */ });
	// }
	//
	// private void signalCallback(Signal sn) {
	// System.out.println(sn.getName() + "is recevied.");
	// System.exit(0);
	// }
	//
	// public static void main(String[] args) throws Exception {
	//
	// JettyTest jetty = new JettyTest();
	//
	// Signal.handle(new Signal("TERM"), jetty);
	// // Signal.handle(new Signal("USR1"), jetty);
	// // Signal.handle(new Signal("USR2"), jetty);
	//
	// jetty.addConnectors();
	// jetty.addHandlersAndFilters();
	// jetty.server.start();
	//
	// /***********************************************************
	// * ����1�� ��Linux��֧�ֵ��źţ������ź�kill -l����鿴���� SEGV, ILL, FPE, BUS,
	// * SYS, CPU, FSZ, ABRT, INT, TERM, HUP, USR1, USR2, QUIT, BREAK, TRAP,
	// * PIPE ��Windows��֧�ֵ��źţ� SEGV, ILL, FPE, ABRT, INT, TERM, BREAK
	// * ����2�� �����п��ܻ��׳��쳣�� java.lang.IllegalArgumentException: Signal
	// * already used by VM: USR1
	// * ������ΪĳЩ�źſ����Ѿ���JVMռ�ã����Կ����������źŴ���
	// **********************************************************/
	// }
	//
	// @Override
	// public void handle(Signal signalName) {
	// signalCallback(signalName);
	// }
}
