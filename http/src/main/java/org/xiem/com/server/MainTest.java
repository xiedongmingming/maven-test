package org.xiem.com.server;

public class MainTest {

	private final JettyServerWrapper jettyWrapper;

	private volatile boolean isStarted = false;

	public MainTest() {
		jettyWrapper = new JettyServerWrapper();
	}

	public static void main(String[] args) {
		try {
			new MainTest().start();
		} catch (Exception e) {
			System.exit(1);
		}
	}

	public void start() throws Exception {// 启动JETTY服务器

		isStarted = true;

		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownCleanupHandler(this)));

		jettyWrapper.addConnectors();
		jettyWrapper.addHandlersAndFilters();
		jettyWrapper.start();

	}

	private static class ShutdownCleanupHandler implements Runnable {

		private final MainTest server;

		ShutdownCleanupHandler(MainTest server) {
			this.server = server;
		}

		@Override
		public void run() {
			if (server.isStarted) {
				try {

					System.out.println(">>>>>>>>>>>>>>>>>>执行退出虚拟机时的回调>>>>>>>>>>>>>>>>>>");

					server.jettyWrapper.server.stop();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					server.isStarted = false;
				}
			}
		}
	}
}
