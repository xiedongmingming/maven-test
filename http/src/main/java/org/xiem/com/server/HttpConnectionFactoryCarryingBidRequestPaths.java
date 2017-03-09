package org.xiem.com.server;

import java.util.Set;

import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;

public class HttpConnectionFactoryCarryingBidRequestPaths extends HttpConnectionFactory {

	// private MetricRegistry metrics = Metrics.getInstance();

	@SuppressWarnings("unused")
	private Set<String> bidRequestServletPaths = null;// ????

	// private final Timer stats = metrics.timer("connections");// 统计连接断开建立的频率

	public HttpConnectionFactoryCarryingBidRequestPaths(HttpConfiguration httpConfig) {// 需要配置参数
		super(httpConfig);
	}

	@Override
	public org.eclipse.jetty.io.Connection newConnection(Connector connector, EndPoint endPoint) {// 生成一个新的连接

		// // 调用父类的函数生成连接
		// Connection c = configure(new HttpConnection(getHttpConfiguration(),
		// connector, endPoint), connector, endPoint);
		//
		// c.addListener(new Connection.Listener() {// 为连接添加监听器
		//
		// // private Timer.Context context;
		//
		// @Override
		// public void onOpened(Connection connection) {
		// // this.context = stats.time();
		// }
		//
		// @Override
		// public void onClosed(Connection connection) {
		// // context.stop();
		// }
		//
		// });
		//
		// return c;
		return null;
	}

	public void setBidRequestServletPaths(Set<String> bidRequestServletPaths) {
		this.bidRequestServletPaths = bidRequestServletPaths;
	}
}
