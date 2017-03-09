// package com.yosemitecloud.rts.main.server.jetty;
//
// import java.util.Set;
//
// import org.eclipse.jetty.io.Connection;
// import org.eclipse.jetty.io.EndPoint;
// import org.eclipse.jetty.server.Connector;
// import org.eclipse.jetty.server.HttpConfiguration;
// import org.eclipse.jetty.server.HttpConnectionFactory;
//
// import com.codahale.metrics.MetricRegistry;
// import com.codahale.metrics.Timer;
// import com.yosemitecloud.rts.main.utils.Metrics;
//
//
// public class HttpConnectionFactoryCarryingBidRequestPaths extends HttpConnectionFactory {
//
// 		private MetricRegistry metrics = Metrics.getInstance();
//
// 		private Set<String> bidRequestServletPaths = null;
//
// 		private final Timer stats = metrics.timer("connections");
//
// 		public HttpConnectionFactoryCarryingBidRequestPaths(HttpConfiguration httpConfig) {
// 			super(httpConfig);
// 		}
//
// 		@Override
// 		public org.eclipse.jetty.io.Connection newConnection(Connector connector, EndPoint endPoint) {
// 			Connection c = configure(new YciHttpConnection(getHttpConfiguration(), connector, endPoint, bidRequestServletPaths), connector, endPoint);
// 			c.addListener(new Connection.Listener() {
// 				private Timer.Context context;
//
// 				@Override
// 				public void onOpened(Connection connection) {
// 					this.context = stats.time();
// 				}
//
// 				@Override
// 				public void onClosed(Connection connection) {
// 					context.stop();
// 				}
// 			});
// 			return c;
// 		}
//
// 		public void setBidRequestServletPaths(Set<String> bidRequestServletPaths) {
// 			this.bidRequestServletPaths = bidRequestServletPaths;
// 		}
// }
