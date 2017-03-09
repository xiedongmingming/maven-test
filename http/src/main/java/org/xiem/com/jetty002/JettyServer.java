package org.xiem.com.jetty002;

//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.eclipse.jetty.continuation.Continuation;
//import org.eclipse.jetty.continuation.ContinuationSupport;
//import org.eclipse.jetty.server.Request;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.server.handler.AbstractHandler;
//import org.eclipse.jetty.server.nio.SelectChannelConnector;
//import org.eclipse.jetty.util.thread.QueuedThreadPool;
//import org.xiem.com.jetty002.annotation.HBean;

public class JettyServer {

	// private final ExecutorService es = Executors.newCachedThreadPool();
	//
	// public void start(String host, int port) throws Exception {
	//
	// Server server = new Server();//
	//
	// // TCP
	// SelectChannelConnector appBeanConn = new SelectChannelConnector();
	// appBeanConn.setUseDirectBuffers(false);
	// appBeanConn.setHost(host);
	// appBeanConn.setPort(port);
	// appBeanConn.setThreadPool(new QueuedThreadPool(20));
	// appBeanConn.setName("appBeans");
	//
	// server.addConnector(appBeanConn);
	//
	// //
	// server.setHandler(new HttpAppBeanHandler());
	//
	// server.start();
	// server.join();
	// }
	//
	// private class HttpAppBeanHandler extends AbstractHandler {
	//
	// private static final String CALLTYPE = "calltype";
	//
	// Map<String, Class<HBean>> map;
	//
	// public HttpAppBeanHandler() throws Exception {
	// map = AnnotationBeanLoader.loadHandlerBean();
	// }
	//
	// public void invokeBean(String id, HttpServletRequest request,
	// HttpServletResponse response)
	// throws InstantiationException, IllegalAccessException {
	// HBean bean = map.get(id).newInstance();//
	// bean.init(request, response);//
	// bean.response(request, response);//
	// }
	//
	// @Override
	// public void handle(String str, final Request baseRequest, final
	// HttpServletRequest request,
	// final HttpServletResponse response) throws IOException, ServletException
	// {
	//
	// // String method = request.getMethod();
	// final String callType = (String) request.getAttribute(CALLTYPE);
	//
	// final Continuation continuation =
	// ContinuationSupport.getContinuation(request);
	// if (continuation.isExpired()) {
	// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "http
	// bean invocation timeout");
	// Future<?> f = (Future<?>) request.getAttribute("KEY_INVOCATION_FUTURN");
	// if (f != null)
	// f.cancel(true);
	// } else {
	// continuation.setTimeout(1000 * 3);
	// continuation.suspend(response);
	//
	// Future<?> f = es.submit(new Runnable() {
	// @Override
	// public void run() {
	// try {
	// invokeBean(callType, request, response);
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// continuation.complete();
	// }// run
	// });// future
	// }
	//
	// }
	//
	// }

}
