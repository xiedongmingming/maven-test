// package org.xiem.com.session;
//
// import java.io.BufferedOutputStream;
// import java.io.BufferedReader;
// import java.io.DataOutputStream;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.UnsupportedEncodingException;
// import java.net.SocketException;
// import java.net.UnknownHostException;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
//
// import org.apache.http.HttpEntity;
// import org.apache.http.NameValuePair;
// import org.apache.http.client.ClientProtocolException;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpGet;
// import org.apache.http.client.utils.URLEncodedUtils;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.message.BasicNameValuePair;
// import org.apache.http.protocol.HttpContext;
// import org.apache.http.util.EntityUtils;
// import org.junit.Test;
//
//// import com.wp.nevel.base.exception.ParserException;
//// import com.wp.nevel.base.service.impl.LogServiceHelp;
//
// public class HttpClientUtils {//
// http://www.myexception.cn/program/1508138.html
//
// // 其他参数资料:
// // http://blog.csdn.net/chaijunkun/article/details/40145685
// //
// http://www.programcreek.com/java-api-examples/index.php?api=org.apache.http.impl.client.HttpClientBuilder
//
// // HttpClient4.3 使用心得(一) 简单使用
// // HttpClient4.3 创设SSL协议的HttpClient对象
// // Httpclient4.3实例。 每个版本接口变更都巨大
// //
// // 1.新增简单的url请求内容返回, 比较时髦的链调用
// //
// // try {
// // Content returnContent = Request.Get("http://www.qq.com")
// // .execute().returnContent();
// // System.out.println(returnContent.toString());
// // } catch (ClientProtocolException e) {
// // // TODO Auto-generated catch block
// // e.printStackTrace();
// // } catch (IOException e) {
// // // TODO Auto-generated catch block
// // e.printStackTrace();
// // }
// //
// // 2. 一套demo方法， 含模拟登录，获取cookie，并把cookie带入后面的请求中
// //
// // CookieStore cookieStore = new BasicCookieStore();
// //
// // HttpClientContext context = HttpClientContext.create();
// // context.setCookieStore(cookieStore);
// //
// // RequestConfig globalConfig =
// //
// RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
// //
// // CloseableHttpClient httpclient = HttpClients.custom().
// // setDefaultRequestConfig(globalConfig)
// // .setDefaultCookieStore(cookieStore).build();
// // // 登录
// // HttpPost loginPost = new
// // HttpPost("https://www.xxx.com/account/login.php");
// // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
// // nvps.add(new BasicNameValuePair("email", email));
// // nvps.add(new BasicNameValuePair("password", pwd));
// // loginPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
// // CloseableHttpResponse loginResp = httpclient.execute(loginPost,context);
// // try {
// //// System.out.println(loginResp.getStatusLine());
// // HttpEntity entity1 = loginResp.getEntity();
// // EntityUtils.consume(entity1);
// // } finally {
// // loginResp.close();
// // }
// //
// // for (int i = START_PAGE; i < END_PAGE; i++) {
// // Thread.sleep(SLEEP_TIME);
// // //获取交易
// // HttpGet get = new HttpGet(
// //
// "https://www.xxx.com/trade/index.php?a=history&t=0&amt_begin=0&amt_end=0&date_begin=&date_end=&pn="+i);
// // HttpClientContext context1 = HttpClientContext.create();
// // context1.setCookieStore(cookieStore);
// // HttpResponse dealResp = httpclient.execute(get, context1);
// // try {
// // HttpEntity dealEntity = dealResp.getEntity();
// // String body = EntityUtils.toString(dealEntity);
// // prasepage(body);
// // EntityUtils.consume(dealEntity);
// // } finally {
// // loginResp.close();
// // }
// // }
// // HttpClient4.3课程 第二章 连接管理
// // 2.1.持久连接
// //
// 两个主机建立连接的过程是很复杂的一个过程，涉及到多个数据包的交换，并且也很耗时间。Http连接需要的三次握手开销很大，这一开销对于比较小的http消息来说更大。但是如果我们直接使用已经建立好的http连接，这样花费就比较小，吞吐率更大。
// //
// HTTP/1.1默认就支持Http连接复用。兼容HTTP/1.0的终端也可以通过声明来保持连接，实现连接复用。HTTP代理也可以在一定时间内保持连接不释放，方便后续向这个主机发送http请求。这种保持连接不释放的情况实际上是建立的持久连接。HttpClient也支持持久连接。
// // 2.2.HTTP连接路由
// // HttpClient既可以直接、又可以通过多个中转路由（hops）和目标服务器建立连接。HttpClient把路由分为三种plain（明文
// // ），tunneled（隧道）和layered（分层）。隧道连接中使用的多个中间代理被称作代理链。
// //
// 客户端直接连接到目标主机或者只通过了一个中间代理，这种就是Plain路由。客户端通过第一个代理建立连接，通过代理链tunnelling，这种情况就是Tunneled路由。不通过中间代理的路由不可能时tunneled路由。客户端在一个已经存在的连接上进行协议分层，这样建立起来的路由就是layered路由。协议只能在隧道—>目标主机，或者直接连接（没有代理），这两种链路上进行分层。
// // 2.2.1.路由计算
// //
// RouteInfo接口包含了数据包发送到目标主机过程中，经过的路由信息。HttpRoute类继承了RouteInfo接口，是RouteInfo的具体实现，这个类是不允许修改的。HttpTracker类也实现了RouteInfo接口，它是可变的，HttpClient会在内部使用这个类来探测到目标主机的剩余路由。HttpRouteDirector是个辅助类，可以帮助计算数据包的下一步路由信息。这个类也是在HttpClient内部使用的。
// //
// HttpRoutePlanner接口可以用来表示基于http上下文情况下，客户端到服务器的路由计算策略。HttpClient有两个HttpRoutePlanner的实现类。SystemDefaultRoutePlanner这个类基于java.net.ProxySelector，它默认使用jvm的代理配置信息，这个配置信息一般来自系统配置或者浏览器配置。DefaultProxyRoutePlanner这个类既不使用java本身的配置，也不使用系统或者浏览器的配置。它通常通过默认代理来计算路由信息。
// // 2.2.2. 安全的HTTP连接
// //
// 为了防止通过Http消息传递的信息不被未授权的第三方获取、截获，Http可以使用SSL/TLS协议来保证http传输安全，这个协议是当前使用最广的。当然也可以使用其他的加密技术。但是通常情况下，Http信息会在加密的SSL/TLS连接上进行传输。
// // 2.3. HTTP连接管理器
// // 2.3.1. 管理连接和连接管理器
// //
// Http连接是复杂，有状态的，线程不安全的对象，所以它必须被妥善管理。一个Http连接在同一时间只能被一个线程访问。HttpClient使用一个叫做Http连接管理器的特殊实体类来管理Http连接，这个实体类要实现HttpClientConnectionManager接口。Http连接管理器在新建http连接时，作为工厂类;管理持久http连接的生命周期;同步持久连接（确保线程安全，即一个http连接同一时间只能被一个线程访问）。Http连接管理器和ManagedHttpClientConnection的实例类一起发挥作用，ManagedHttpClientConnection实体类可以看做http连接的一个代理服务器，管理着I/O操作。如果一个Http连接被释放或者被它的消费者明确表示要关闭，那么底层的连接就会和它的代理进行分离，并且该连接会被交还给连接管理器。这是，即使服务消费者仍然持有代理的引用，它也不能再执行I/O操作，或者更改Http连接的状态。
// // 下面的代码展示了如何从连接管理器中取得一个http连接：
// // HttpClientContext context = HttpClientContext.create();
// // HttpClientConnectionManager connMrg = new
// // BasicHttpClientConnectionManager();
// // HttpRoute route = new HttpRoute(new HttpHost("www.yeetrack.com", 80));
// // // 获取新的连接. 这里可能耗费很多时间
// // ConnectionRequest connRequest = connMrg.requestConnection(route, null);
// // // 10秒超时
// // HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
// // try {
// // // 如果创建连接失败
// // if (!conn.isOpen()) {
// // // establish connection based on its route info
// // connMrg.connect(conn, route, 1000, context);
// // // and mark it as route complete
// // connMrg.routeComplete(conn, route, context);
// // }
// // // 进行自己的操作.
// // } finally {
// // connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
// // }
// //
// 如果要终止连接，可以调用ConnectionRequest的cancel()方法。这个方法会解锁被ConnectionRequest类get()方法阻塞的线程。
// // 2.3.2.简单连接管理器
// //
// BasicHttpClientConnectionManager是个简单的连接管理器，它一次只能管理一个连接。尽管这个类是线程安全的，它在同一时间也只能被一个线程使用。BasicHttpClientConnectionManager会尽量重用旧的连接来发送后续的请求，并且使用相同的路由。如果后续请求的路由和旧连接中的路由不匹配，BasicHttpClientConnectionManager就会关闭当前连接，使用请求中的路由重新建立连接。如果当前的连接正在被占用，会抛出java.lang.IllegalStateException异常。
// // 2.3.3.连接池管理器
// //
// 相对BasicHttpClientConnectionManager来说，PoolingHttpClientConnectionManager是个更复杂的类，它管理着连接池，可以同时为很多线程提供http连接请求。Connections
// // are pooled on a per route
// // basis.当请求一个新的连接时，如果连接池有有可用的持久连接，连接管理器就会使用其中的一个，而不是再创建一个新的连接。
// //
// PoolingHttpClientConnectionManager维护的连接数在每个路由基础和总数上都有限制。默认，每个路由基础上的连接不超过2个，总连接数不能超过20。在实际应用中，这个限制可能会太小了，尤其是当服务器也使用Http协议时。
// // 下面的例子演示了如果调整连接池的参数：
// // PoolingHttpClientConnectionManager cm = new
// // PoolingHttpClientConnectionManager();
// // // 将最大连接数增加到200
// // cm.setMaxTotal(200);
// // // 将每个路由基础的连接增加到20
// // cm.setDefaultMaxPerRoute(20);
// // //将目标主机的最大连接数增加到50
// // HttpHost localhost = new HttpHost("www.yeetrack.com", 80);
// // cm.setMaxPerRoute(new HttpRoute(localhost), 50);
// //
// // CloseableHttpClient httpClient = HttpClients.custom()
// // .setConnectionManager(cm)
// // .build();
// // 2.3.4.关闭连接管理器
// // 当一个HttpClient的实例不在使用，或者已经脱离它的作用范围，我们需要关掉它的连接管理器，来关闭掉所有的连接，释放掉这些连接占用的系统资源。
// // CloseableHttpClient httpClient = <...>
// // httpClient.close();
// // 2.4.多线程请求执行
// // 当使用了请求连接池管理器（比如PoolingClientConnectionManager）后，HttpClient就可以同时执行多个线程的请求了。
// //
// PoolingClientConnectionManager会根据它的配置来分配请求连接。如果连接池中的所有连接都被占用了，那么后续的请求就会被阻塞，直到有连接被释放回连接池中。为了防止永远阻塞的情况发生，我们可以把http.conn-manager.timeout的值设置成一个整数。如果在超时时间内，没有可用连接，就会抛出ConnectionPoolTimeoutException异常。
// // PoolingHttpClientConnectionManager cm = new
// // PoolingHttpClientConnectionManager();
// // CloseableHttpClient httpClient = HttpClients.custom()
// // .setConnectionManager(cm)
// // .build();
// //
// // // URL列表数组
// // String[] urisToGet = {
// // "http://www.domain1.com/",
// // "http://www.domain2.com/",
// // "http://www.domain3.com/",
// // "http://www.domain4.com/"
// // };
// //
// // // 为每个url创建一个线程，GetThread是自定义的类
// // GetThread[] threads = new GetThread[urisToGet.length];
// // for (int i = 0; i < threads.length; i++) {
// // HttpGet httpget = new HttpGet(urisToGet[i]);
// // threads[i] = new GetThread(httpClient, httpget);
// // }
// //
// // // 启动线程
// // for (int j = 0; j < threads.length; j++) {
// // threads[j].start();
// // }
// //
// // // join the threads
// // for (int j = 0; j < threads.length; j++) {
// // threads[j].join();
// // }
// // 即使HttpClient的实例是线程安全的，可以被多个线程共享访问，但是仍旧推荐每个线程都要有自己专用实例的HttpContext。
// // 下面是GetThread类的定义：
// // static class GetThread extends Thread {
// //
// // private final CloseableHttpClient httpClient;
// // private final HttpContext context;
// // private final HttpGet httpget;
// //
// // public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
// // this.httpClient = httpClient;
// // this.context = HttpClientContext.create();
// // this.httpget = httpget;
// // }
// //
// // @Override
// // public void run() {
// // try {
// // CloseableHttpResponse response = httpClient.execute(
// // httpget, context);
// // try {
// // HttpEntity entity = response.getEntity();
// // } finally {
// // response.close();
// // }
// // } catch (ClientProtocolException ex) {
// // // Handle protocol errors
// // } catch (IOException ex) {
// // // Handle I/O errors
// // }
// // }
// //
// // }
// // 2.5. 连接回收策略
// //
// 经典阻塞I/O模型的一个主要缺点就是只有当组侧I/O时，socket才能对I/O事件做出反应。当连接被管理器收回后，这个连接仍然存活，但是却无法监控socket的状态，也无法对I/O事件做出反馈。如果连接被服务器端关闭了，客户端监测不到连接的状态变化（也就无法根据连接状态的变化，关闭本地的socket）。
// //
// HttpClient为了缓解这一问题造成的影响，会在使用某个连接前，监测这个连接是否已经过时，如果服务器端关闭了连接，那么连接就会失效。这种过时检查并不是100%有效，并且会给每个请求增加10到30毫秒额外开销。唯一一个可行的，且does
// // not involve a one thread per socket model for idle
// //
// connections的解决办法，是建立一个监控线程，来专门回收由于长时间不活动而被判定为失效的连接。这个监控线程可以周期性的调用ClientConnectionManager类的closeExpiredConnections()方法来关闭过期的连接，回收连接池中被关闭的连接。它也可以选择性的调用ClientConnectionManager类的closeIdleConnections()方法来关闭一段时间内不活动的连接。
// // public static class IdleConnectionMonitorThread extends Thread {
// //
// // private final HttpClientConnectionManager connMgr;
// // private volatile boolean shutdown;
// //
// // public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
// // super();
// // this.connMgr = connMgr;
// // }
// //
// // @Override
// // public void run() {
// // try {
// // while (!shutdown) {
// // synchronized (this) {
// // wait(5000);
// // // 关闭失效的连接
// // connMgr.closeExpiredConnections();
// // // 可选的, 关闭30秒内不活动的连接
// // connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
// // }
// // }
// // } catch (InterruptedException ex) {
// // // terminate
// // }
// // }
// //
// // public void shutdown() {
// // shutdown = true;
// // synchronized (this) {
// // notifyAll();
// // }
// // }
// //
// // }
// // 2.6. 连接存活策略
// //
// Http规范没有规定一个持久连接应该保持存活多久。有些Http服务器使用非标准的Keep-Alive头消息和客户端进行交互，服务器端会保持数秒时间内保持连接。HttpClient也会利用这个头消息。如果服务器返回的响应中没有包含Keep-Alive头消息，HttpClient会认为这个连接可以永远保持。然而，很多服务器都会在不通知客户端的情况下，关闭一定时间内不活动的连接，来节省服务器资源。在某些情况下默认的策略显得太乐观，我们可能需要自定义连接存活策略。
// // ConnectionKeepAliveStrategy myStrategy = new
// // ConnectionKeepAliveStrategy() {
// //
// // public long getKeepAliveDuration(HttpResponse response, HttpContext
// // context) {
// // // Honor 'keep-alive' header
// // HeaderElementIterator it = new BasicHeaderElementIterator(
// // response.headerIterator(HTTP.CONN_KEEP_ALIVE));
// // while (it.hasNext()) {
// // HeaderElement he = it.nextElement();
// // String param = he.getName();
// // String value = he.getValue();
// // if (value != null && param.equalsIgnoreCase("timeout")) {
// // try {
// // return Long.parseLong(value) * 1000;
// // } catch(NumberFormatException ignore) {
// // }
// // }
// // }
// // HttpHost target = (HttpHost) context.getAttribute(
// // HttpClientContext.HTTP_TARGET_HOST);
// // if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
// // // Keep alive for 5 seconds only
// // return 5 * 1000;
// // } else {
// // // otherwise keep alive for 30 seconds
// // return 30 * 1000;
// // }
// // }
// //
// // };
// // CloseableHttpClient client = HttpClients.custom()
// // .setKeepAliveStrategy(myStrategy)
// // .build();
// // 2.7.socket连接工厂
// //
// Http连接使用java.net.Socket类来传输数据。这依赖于ConnectionSocketFactory接口来创建、初始化和连接socket。这样也就允许HttpClient的用户在代码运行时，指定socket初始化的代码。PlainConnectionSocketFactory是默认的创建、初始化明文socket（不加密）的工厂类。
// // 创建socket和使用socket连接到目标主机这两个过程是分离的，所以我们可以在连接发生阻塞时，关闭socket连接。
// // HttpClientContext clientContext = HttpClientContext.create();
// // PlainConnectionSocketFactory sf =
// // PlainConnectionSocketFactory.getSocketFactory();
// // Socket socket = sf.createSocket(clientContext);
// // int timeout = 1000; //ms
// // HttpHost target = new HttpHost("www.yeetrack.com");
// // InetSocketAddress remoteAddress = new InetSocketAddress(
// // InetAddress.getByName("www.yeetrack.com", 80);
// // //connectSocket源码中，实际没有用到target参数
// // sf.connectSocket(timeout, socket, target, remoteAddress, null,
// // clientContext);
// // 2.7.1.安全SOCKET分层
// //
// LayeredConnectionSocketFactory是ConnectionSocketFactory的拓展接口。分层socket工厂类可以在明文socket的基础上创建socket连接。分层socket主要用于在代理服务器之间创建安全socket。HttpClient使用SSLSocketFactory这个类实现安全socket，SSLSocketFactory实现了SSL/TLS分层。请知晓，HttpClient没有自定义任何加密算法。它完全依赖于Java加密标准（JCE）和安全套接字（JSEE）拓展。
// // 2.7.2.集成连接管理器
// // 自定义的socket工厂类可以和指定的协议（Http、Https）联系起来，用来创建自定义的连接管理器。
// // ConnectionSocketFactory plainsf = <...>
// // LayeredConnectionSocketFactory sslsf = <...>
// // Registry<ConnectionSocketFactory> r =
// // RegistryBuilder.<ConnectionSocketFactory>create()
// // .register("http", plainsf)
// // .register("https", sslsf)
// // .build();
// //
// // HttpClientConnectionManager cm = new
// // PoolingHttpClientConnectionManager(r);
// // HttpClients.custom()
// // .setConnectionManager(cm)
// // .build();
// // 2.7.3.SSL/TLS定制
// //
// HttpClient使用SSLSocketFactory来创建ssl连接。SSLSocketFactory允许用户高度定制。它可以接受javax.net.ssl.SSLContext这个类的实例作为参数，来创建自定义的ssl连接。
// // HttpClientContext clientContext = HttpClientContext.create();
// // KeyStore myTrustStore = <...>
// // SSLContext sslContext = SSLContexts.custom()
// // .useTLS()
// // .loadTrustMaterial(myTrustStore)
// // .build();
// // SSLConnectionSocketFactory sslsf = new
// // SSLConnectionSocketFactory(sslContext);
// // 2.7.4.域名验证
// //
// 除了信任验证和在ssl/tls协议层上进行客户端认证，HttpClient一旦建立起连接，就可以选择性验证目标域名和存储在X.509证书中的域名是否一致。这种验证可以为服务器信任提供额外的保障。X509HostnameVerifier接口代表主机名验证的策略。在HttpClient中，X509HostnameVerifier有三个实现类。重要提示：主机名有效性验证不应该和ssl信任验证混为一谈。
// // StrictHostnameVerifier: 严格的主机名验证方法和java
// // 1.4,1.5,1.6验证方法相同。和IE6的方式也大致相同。这种验证方式符合RFC 2818通配符。The hostname must
// // match either the first CN, or any of the subject-alts. A wildcard can
// // occur in the CN, and in any of the subject-alts.
// // BrowserCompatHostnameVerifier: 这种验证主机名的方法，和Curl及firefox一致。The hostname
// // must match either the first CN, or any of the subject-alts. A wildcard
// // can occur in the CN, and in any of the
// //
// subject-alts.StrictHostnameVerifier和BrowserCompatHostnameVerifier方式唯一不同的地方就是，带有通配符的域名（比如*.yeetrack.com),BrowserCompatHostnameVerifier方式在匹配时会匹配所有的的子域名，包括
// // a.b.yeetrack.com .
// // AllowAllHostnameVerifier:
// //
// 这种方式不对主机名进行验证，验证功能被关闭，是个空操作，所以它不会抛出javax.net.ssl.SSLException异常。HttpClient默认使用BrowserCompatHostnameVerifier的验证方式。如果需要，我们可以手动执行验证方式。
// // SSLContext sslContext = SSLContexts.createSystemDefault();
// // SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
// // sslContext,
// // SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
// // 2.8.HttpClient代理服务器配置
// // 尽管，HttpClient支持复杂的路由方案和代理链，它同样也支持直接连接或者只通过一跳的连接。
// // 使用代理服务器最简单的方式就是，指定一个默认的proxy参数。
// // HttpHost proxy = new HttpHost("someproxy", 8080);
// // DefaultProxyRoutePlanner routePlanner = new
// // DefaultProxyRoutePlanner(proxy);
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setRoutePlanner(routePlanner)
// // .build();
// // 我们也可以让HttpClient去使用jre的代理服务器。
// // SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(
// // ProxySelector.getDefault());
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setRoutePlanner(routePlanner)
// // .build();
// // 又或者，我们也可以手动配置RoutePlanner，这样就可以完全控制Http路由的过程。
// // HttpRoutePlanner routePlanner = new HttpRoutePlanner() {
// //
// // public HttpRoute determineRoute(
// // HttpHost target,
// // HttpRequest request,
// // HttpContext context) throws HttpException {
// // return new HttpRoute(target, null, new HttpHost("someproxy", 8080),
// // "https".equalsIgnoreCase(target.getSchemeName()));
// // }
// //
// // };
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setRoutePlanner(routePlanner)
// // .build();
// // }
// // }
// // HttpClient4.3教程 第三章 Http状态管理
//
// //
// 最初，Http被设计成一个无状态的，面向请求/响应的协议，所以它不能在逻辑相关的http请求/响应中保持状态会话。由于越来越多的系统使用http协议，其中包括http从来没有想支持的系统，比如电子商务系统。因此，http支持状态管理就很必要了。
// //
// 当时的web客户端和服务器软件领先者，网景(netscape)公司，最先在他们的产品中支持http状态管理，并且制定了一些专有规范。后来，网景通过发规范草案，规范了这一机制。这些努力促成
// // RFC standard
// //
// track制定了标准的规范。但是，现在多数的应用的状态管理机制都在使用网景公司的规范，而网景的规范和官方规定是不兼容的。因此所有的浏览器开发这都被迫兼容这两种协议，从而导致协议的不统一。
// // 3.1.Http cookies
// // 所谓的Http
// // cookie就是一个token或者很短的报文信息，http代理和服务器可以通过cookie来维持会话状态。网景的工程师把它们称作“magic
// // cookie”。
// //
// HttpClient使用Cookie接口来代表cookie。简单说来，cookie就是一个键值对。一般，cookie也会包含版本号、域名、路径和cookie有效期。
// //
// SetCookie接口可以代表服务器发给http代理的一个set-cookie响应头，在浏览器中，这个set-cookie响应头可以写入cookie，以便保持会话状态。SetCookie2接口对SetCookie接口进行了拓展，添加了Set-Cookie2方法。
// //
// ClientCookie接口继承了Cookie接口，并进行了功能拓展，比如它可以取出服务器发送过来的原始cookie的值。生成头消息是很重要的，因为只有当cookie被指定为Set-Cookie或者Set-Cookie2时，它才需要包括一些特定的属性。
// // 3.1.1COOKIES版本
// // 兼容网景的规范，但是不兼容官方规范的cookie，是版本0. 兼容官方规范的版本，将会是版本1。版本1中的Cookie可能和版本0工作机制有差异。
// // 下面的代码，创建了网景版本的Cookie：
// // BasicClientCookie netscapeCookie = new BasicClientCookie("name",
// // "value");
// // netscapeCookie.setVersion(0);
// // netscapeCookie.setDomain(".yeetrack.com");
// // netscapeCookie.setPath("/");
// // 下面的代码，创建标准版本的Cookie。注意，标准版本的Cookie必须保留服务器发送过来的Cookie所有属性。
// // BasicClientCookie stdCookie = new BasicClientCookie("name", "value");
// // stdCookie.setVersion(1);
// // stdCookie.setDomain(".yeetrack.com");
// // stdCookie.setPath("/");
// // stdCookie.setSecure(true);
// // // Set attributes EXACTLY as sent by the server
// // stdCookie.setAttribute(ClientCookie.VERSION_ATTR, "1");
// // stdCookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".yeetrack.com");
// // 下面的代码，创建了Set-Cookie2兼容cookie。
// // BasicClientCookie2 stdCookie = new BasicClientCookie2("name", "value");
// // stdCookie.setVersion(1);
// // stdCookie.setDomain(".yeetrack.com");
// // stdCookie.setPorts(new int[] {80,8080});
// // stdCookie.setPath("/");
// // stdCookie.setSecure(true);
// // // Set attributes EXACTLY as sent by the server
// // stdCookie.setAttribute(ClientCookie.VERSION_ATTR, "1");
// // stdCookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".yeetrack.com");
// // stdCookie.setAttribute(ClientCookie.PORT_ATTR, "80,8080");
// // 3.2. Cookie规范
// // CookieSpec接口代表了Cookie管理规范。Cookie管理规范规定了：
// // 解析Set-Cookie和Set-Cookie2(可选）头消息的规则
// // 验证Cookie的规则
// // 将指定的主机名、端口和路径格式化成Cookie头消息
// // HttpClient有下面几种CookieSpec规范：
// // Netscape draft: 这种符合网景公司指定的规范。但是尽量不要使用，除非一定要保证兼容很旧的代码。
// // Standard: RFC 2965 HTTP状态管理规范
// // Browser compatibility: 这种方式，尽量模仿常用的浏览器，如IE和firefox
// // Best match: ‘Meta’ cookie specification that picks up a cookie policy
// // based on the format of cookies sent with the HTTP
// // response.它基本上将上面的几种规范积聚到一个类中。
// // ++ Ignore cookies: 忽略所有Cookie
// // 强烈推荐使用Best Match匹配规则，让HttpClient根据运行时环境自己选择合适的规范。
// // 3.3.选择Cookie策略
// // 我们可以在创建Http client的时候指定Cookie测试，如果需要，也可以在执行http请求的时候，进行覆盖指定。
// // RequestConfig globalConfig = RequestConfig.custom()
// // .setCookieSpec(CookieSpecs.BEST_MATCH)
// // .build();
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setDefaultRequestConfig(globalConfig)
// // .build();
// // RequestConfig localConfig = RequestConfig.copy(globalConfig)
// // .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
// // .build();
// // HttpGet httpGet = new HttpGet("http://www.yeetrack.com");
// // httpGet.setConfig(localConfig);
// // 3.4.自定义Cookie策略
// //
// 如果我们要自定义Cookie测试，就要自己实现CookieSpec接口，然后创建一个CookieSpecProvider接口来新建、初始化自定义CookieSpec接口，最后把CookieSpecProvider注册到HttpClient中。一旦我们注册了自定义策略，就可以像其他标准策略一样使用了。
// // CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
// //
// // public CookieSpec create(HttpContext context) {
// //
// // return new BrowserCompatSpec() {
// // @Override
// // public void validate(Cookie cookie, CookieOrigin origin)
// // throws MalformedCookieException {
// // // Oh, I am easy
// // }
// // };
// // }
// //
// // };
// // Registry<CookieSpecProvider> r =
// // RegistryBuilder.<CookieSpecProvider>create()
// // .register(CookieSpecs.BEST_MATCH,
// // new BestMatchSpecFactory())
// // .register(CookieSpecs.BROWSER_COMPATIBILITY,
// // new BrowserCompatSpecFactory())
// // .register("easy", easySpecProvider)
// // .build();
// //
// // RequestConfig requestConfig = RequestConfig.custom()
// // .setCookieSpec("easy")
// // .build();
// //
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setDefaultCookieSpecRegistry(r)
// // .setDefaultRequestConfig(requestConfig)
// // .build();
// // 3.5.Cookie持久化
// // HttpClient可以使用任何存储方式的cookie store，只要这个cookie
// //
// store实现了CookieStore接口。默认的CookieStore通过java.util.ArrayList简单实现了BasicCookieStore。存在在BasicCookieStore中的Cookie，当载体对象被当做垃圾回收掉后，就会丢失。如果必要，用户可以自己实现更为复杂的方式。
// // // 创建CookieStore实例
// // CookieStore cookieStore = new BasicCookieStore();
// // // 新建一个Cookie
// // BasicClientCookie cookie = new BasicClientCookie("name", "value");
// // cookie.setVersion(0);
// // cookie.setDomain(".mycompany.com");
// // cookie.setPath("/");
// // cookieStore.addCoo
// // //将CookieStore设置到httpClient中
// // CloseableHttpClient httpclient = HttpClients.custom()
// // .setDefaultCookieStore(cookieStore)
// // .build();
// // 3.6.HTTP状态管理和执行上下文
// // 在Http请求执行过程中，HttpClient会自动向执行上下文中添加下面的状态管理对象：
// // Lookup对象 代表实际的cookie规范registry。在当前上下文中的这个值优先于默认值。
// // CookieSpec对象 代表实际的Cookie规范。
// // CookieOrigin对象 代表实际的origin server的详细信息。
// // CookieStore对象 表示Cookie store。这个属性集中的值会取代默认值。
// //
// 本地的HttpContext对象可以用来在Http请求执行前，自定义Http状态管理上下文;或者测试http请求执行完毕后上下文的状态。我们也可以在不同的线程中使用不同的执行上下文。我们在http请求层指定的cookie规范集和cookie
// // store会覆盖在http Client层级的默认值。
// // CloseableHttpClient httpclient = <...>
// //
// // Lookup<CookieSpecProvider> cookieSpecReg = <...>
// // CookieStore cookieStore = <...>
// //
// // HttpClientContext context = HttpClientContext.create();
// // context.setCookieSpecRegistry(cookieSpecReg);
// // context.setCookieStore(cookieStore);
// // HttpGet httpget = new HttpGet("http://somehost/");
// // CloseableHttpResponse response1 = httpclient.execute(httpget, context);
// // <...>
// // // Cookie origin details
// // CookieOrigin cookieOrigin = context.getCookieOrigin();
// // // Cookie spec used
// // CookieSpec cookieSpec = context.getCookieSpec();
//
// // HttpClient4.3教程 第四章 HTTP认证
//
// // HttpClient既支持HTTP标准规范定义的认证模式，又支持一些广泛使用的非标准认证模式，比如NTLM和SPNEGO。
// // 4.1.用户凭证
// //
// 任何用户认证的过程，都需要一系列的凭证来确定用户的身份。最简单的用户凭证可以是用户名和密码这种形式。UsernamePasswordCredentials这个类可以用来表示这种情况，这种凭据包含明文的用户名和密码。
// // 这个类对于HTTP标准规范中定义的认证模式来说已经足够了。
// // UsernamePasswordCredentials creds = new
// // UsernamePasswordCredentials("user", "pwd");
// // System.out.println(creds.getUserPrincipal().getName());
// // System.out.println(creds.getPassword());
// // 上述代码会在控制台输出：
// // user
// // pwd
// //
// NTCredentials是微软的windows系统使用的一种凭据，包含username、password，还包括一系列其他的属性，比如用户所在的域名。在Microsoft
// // Windows的网络环境中，同一个用户可以属于不同的域，所以他也就有不同的凭据。
// // NTCredentials creds = new NTCredentials("user", "pwd", "workstation",
// // "domain");
// // System.out.println(creds.getUserPrincipal().getName());
// // System.out.println(creds.getPassword());
// // 上述代码输出：
// // DOMAIN/user
// // pwd
// // 4.2. 认证方案
// // AutoScheme接口表示一个抽象的面向挑战/响应的认证方案。一个认证方案要支持下面的功能：
// // 客户端请求服务器受保护的资源，服务器会发送过来一个chanllenge(挑战），认证方案（Authentication
// // scheme）需要解析、处理这个挑战
// // 为processed challenge提供一些属性值：认证方案的类型，和此方案需要的一些参数，这种方案适用的范围
// // 使用给定的授权信息生成授权字符串;生成http请求，用来响应服务器发送来过的授权challenge
// // 请注意：一个认证方案可能是有状态的，因为它可能涉及到一系列的挑战/响应。
// // HttpClient实现了下面几种AutoScheme:
// // Basic:
// //
// Basic认证方案是在RFC2617号文档中定义的。这种授权方案用明文来传输凭证信息，所以它是不安全的。虽然Basic认证方案本身是不安全的，但是它一旦和TLS/SSL加密技术结合起来使用，就完全足够了。
// // Digest:
// //
// Digest（摘要）认证方案是在RFC2617号文档中定义的。Digest认证方案比Basic方案安全多了，对于那些受不了Basic+TLS/SSL传输开销的系统，digest方案是个不错的选择。
// // NTLM: NTLM认证方案是个专有的认证方案，由微软开发，并且针对windows平台做了优化。NTLM被认为比Digest更安全。
// // SPNEGO: SPNEGO(Simple and Protected GSSAPI Negotiation
// //
// Mechanism)是GSSAPI的一个“伪机制”，它用来协商真正的认证机制。SPNEGO最明显的用途是在微软的HTTP协商认证机制拓展上。可协商的子机制包括NTLM、Kerberos。目前，HttpCLient只支持Kerberos机制。（原文：The
// // negotiable sub-mechanisms include NTLM and Kerberos supported by Active
// // Directory. At present HttpClient only supports the Kerberos
// // sub-mechanism.）
// // 4.3. 凭证 provider
// //
// 凭证providers旨在维护一套用户的凭证，当需要某种特定的凭证时，providers就应该能产生这种凭证。认证的具体内容包括主机名、端口号、realm
// //
// name和认证方案名。当使用凭据provider的时候，我们可以很模糊的指定主机名、端口号、realm和认证方案，不用写的很精确。因为，凭据provider会根据我们指定的内容，筛选出一个最匹配的方案。
// //
// 只要我们自定义的凭据provider实现了CredentialsProvider这个接口，就可以在HttpClient中使用。默认的凭据provider叫做BasicCredentialsProvider，它使用java.util.HashMap对CredentialsProvider进行了简单的实现。
// // CredentialsProvider credsProvider = new BasicCredentialsProvider();
// // credsProvider.setCredentials(
// // new AuthScope("somehost", AuthScope.ANY_PORT),
// // new UsernamePasswordCredentials("u1", "p1"));
// // credsProvider.setCredentials(
// // new AuthScope("somehost", 8080),
// // new UsernamePasswordCredentials("u2", "p2"));
// // credsProvider.setCredentials(
// // new AuthScope("otherhost", 8080, AuthScope.ANY_REALM, "ntlm"),
// // new UsernamePasswordCredentials("u3", "p3"));
// //
// // System.out.println(credsProvider.getCredentials(
// // new AuthScope("somehost", 80, "realm", "basic")));
// // System.out.println(credsProvider.getCredentials(
// // new AuthScope("somehost", 8080, "realm", "basic")));
// // System.out.println(credsProvider.getCredentials(
// // new AuthScope("otherhost", 8080, "realm", "basic")));
// // System.out.println(credsProvider.getCredentials(
// // new AuthScope("otherhost", 8080, null, "ntlm")));
// // 上面代码输出：
// // [principal: u1]
// // [principal: u2]
// // null
// // [principal: u3]
// // 4.4.HTTP授权和执行上下文
// //
// HttpClient依赖AuthState类去跟踪认证过程中的状态的详细信息。在Http请求过程中，HttpClient创建两个AuthState实例：一个用于目标服务器认证，一个用于代理服务器认证。如果服务器或者代理服务器需要用户的授权信息，AuthScope、AutoScheme和认证信息就会被填充到两个AuthScope实例中。通过对AutoState的检测，我们可以确定请求的授权类型，确定是否有匹配的AuthScheme，确定凭据provider根据指定的授权类型是否成功生成了用户的授权信息。
// // 在Http请求执行过程中，HttpClient会向执行上下文中添加下面的授权对象：
// // Lookup对象，表示使用的认证方案。这个对象的值可以在本地上下文中进行设置，来覆盖默认值。
// // CredentialsProvider对象，表示认证方案provider，这个对象的值可以在本地上下文中进行设置，来覆盖默认值。
// // AuthState对象，表示目标服务器的认证状态，这个对象的值可以在本地上下文中进行设置，来覆盖默认值。
// // AuthState对象，表示代理服务器的认证状态，这个对象的值可以在本地上下文中进行设置，来覆盖默认值。
// // AuthCache对象，表示认证数据的缓存，这个对象的值可以在本地上下文中进行设置，来覆盖默认值。
// //
// 我们可以在请求执行前，自定义本地HttpContext对象来设置需要的http认证上下文;也可以在请求执行后，再检测HttpContext的状态，来查看授权是否成功。
// // CloseableHttpClient httpclient = <...>
// //
// // CredentialsProvider credsProvider = <...>
// // Lookup<AuthSchemeProvider> authRegistry = <...>
// // AuthCache authCache = <...>
// //
// // HttpClientContext context = HttpClientContext.create();
// // context.setCredentialsProvider(credsProvider);
// // context.setAuthSchemeRegistry(authRegistry);
// // context.setAuthCache(authCache);
// // HttpGet httpget = new HttpGet("http://www.yeetrack.com/");
// // CloseableHttpResponse response1 = httpclient.execute(httpget, context);
// // <...>
// //
// // AuthState proxyAuthState = context.getProxyAuthState();
// // System.out.println("Proxy auth state: " + proxyAuthState.getState());
// // System.out.println("Proxy auth scheme: " +
// // proxyAuthState.getAuthScheme());
// // System.out.println("Proxy auth credentials: " +
// // proxyAuthState.getCredentials());
// // AuthState targetAuthState = context.getTargetAuthState();
// // System.out.println("Target auth state: " + targetAuthState.getState());
// // System.out.println("Target auth scheme: " +
// // targetAuthState.getAuthScheme());
// // System.out.println("Target auth credentials: " +
// // targetAuthState.getCredentials());
// // 4.5. 缓存认证数据
// //
// 从版本4.1开始，HttpClient就会自动缓存验证通过的认证信息。但是为了使用这个缓存的认证信息，我们必须在同一个上下文中执行逻辑相关的请求。一旦超出该上下文的作用范围，缓存的认证信息就会失效。
// // 4.6. 抢先认证
// //
// HttpClient默认不支持抢先认证，因为一旦抢先认证被误用或者错用，会导致一系列的安全问题，比如会把用户的认证信息以明文的方式发送给未授权的第三方服务器。因此，需要用户自己根据自己应用的具体环境来评估抢先认证带来的好处和带来的风险。
// //
// 即使如此，HttpClient还是允许我们通过配置来启用抢先认证，方法是提前填充认证信息缓存到上下文中，这样，以这个上下文执行的方法，就会使用抢先认证。
// // CloseableHttpClient httpclient = <...>
// //
// // HttpHost targetHost = new HttpHost("localhost", 80, "http");
// // CredentialsProvider credsProvider = new BasicCredentialsProvider();
// // credsProvider.setCredentials(
// // new AuthScope(targetHost.getHostName(), targetHost.getPort()),
// // new UsernamePasswordCredentials("username", "password"));
// //
// // // 创建 AuthCache 对象
// // AuthCache authCache = new BasicAuthCache();
// // //创建 BasicScheme，并把它添加到 auth cache中
// // BasicScheme basicAuth = new BasicScheme();
// // authCache.put(targetHost, basicAuth);
// //
// // // 把AutoCache添加到上下文中
// // HttpClientContext context = HttpClientContext.create();
// // context.setCredentialsProvider(credsProvider);
// //
// // HttpGet httpget = new HttpGet("/");
// // for (int i = 0; i < 3; i++) {
// // CloseableHttpResponse response = httpclient.execute(
// // targetHost, httpget, context);
// // try {
// // HttpEntity entity = response.getEntity();
// //
// // } finally {
// // response.close();
// // }
// // }
// // 4.7. NTLM认证
// //
// 从版本4.1开始，HttpClient就全面支持NTLMv1、NTLMv2和NTLM2认证。当人我们可以仍旧使用外部的NTLM引擎（比如Samba开发的JCIFS库）作为与Windows互操作性程序的一部分。
// // 4.7.1. NTLM连接持久性
// //
// 相比Basic和Digest认证，NTLM认证要明显需要更多的计算开销，性能影响也比较大。这也可能是微软把NTLM协议设计成有状态连接的主要原因之一。也就是说，NTLM连接一旦建立，用户的身份就会在其整个生命周期和它相关联。NTLM连接的状态性使得连接持久性更加复杂，The
// // stateful nature of NTLM connections makes connection persistence more
// // complex, as for the obvious reason persistent NTLM connections may not be
// // re-used by users with a different user identity.
// //
// HttpClient中标准的连接管理器就可以管理有状态的连接。但是，同一会话中逻辑相关的请求，必须使用相同的执行上下文，这样才能使用用户的身份信息。否则，HttpClient就会结束旧的连接，为了获取被NTLM协议保护的资源，而为每个HTTP请求，创建一个新的Http连接。更新关于Http状态连接的信息，点击此处。
// //
// 由于NTLM连接是有状态的，一般推荐使用比较轻量级的方法来处罚NTLM认证（如GET、Head方法），然后使用这个已经建立的连接在执行相对重量级的方法，尤其是需要附件请求实体的请求（如POST、PUT请求）。
// // CloseableHttpClient httpclient = <...>
// //
// // CredentialsProvider credsProvider = new BasicCredentialsProvider();
// // credsProvider.setCredentials(AuthScope.ANY,
// // new NTCredentials("user", "pwd", "myworkstation", "microsoft.com"));
// //
// // HttpHost target = new HttpHost("www.microsoft.com", 80, "http");
// //
// // //使用相同的上下文来执行逻辑相关的请求
// // HttpClientContext context = HttpClientContext.create();
// // context.setCredentialsProvider(credsProvider);
// //
// // //使用轻量级的请求来触发NTLM认证
// // HttpGet httpget = new HttpGet("/ntlm-protected/info");
// // CloseableHttpResponse response1 = httpclient.execute(target, httpget,
// // context);
// // try {
// // HttpEntity entity1 = response1.getEntity();
// // } finally {
// // response1.close();
// // }
// //
// // //使用相同的上下文，执行重量级的方法
// // HttpPost httppost = new HttpPost("/ntlm-protected/form");
// // httppost.setEntity(new StringEntity("lots and lots of data"));
// // CloseableHttpResponse response2 = httpclient.execute(target, httppost,
// // context);
// // try {
// // HttpEntity entity2 = response2.getEntity();
// // } finally {
// // response2.close();
// // }
// // 4.8. SPNEGO/Kerberos认证
// // SPNEGO(Simple and Protected GSSAPI Megotiation
// // Mechanism），当双方均不知道对方能使用/提供什么协议的情况下，可以使用SP认证协议。这种协议在Kerberos认证方案中经常使用。It
// // can wrap other mechanisms, however the current version in HttpClient is
// // designed solely with Kerberos in mind.
// // 4.8.1. 在HTTPCIENT中使用SPNEGO
// // SPNEGO认证方案兼容Sun java
// // 1.5及以上版本。但是强烈推荐jdk1.6以上。Sun的JRE提供的类就已经几乎完全可以处理Kerberos和SPNEGO
// // token。这就意味着，需要设置很多的GSS类。SpnegoScheme是个很简单的类，可以用它来handle marshalling the
// // tokens and 读写正确的头消息。
// //
// 最好的开始方法就是从示例程序中找到KerberosHttpClient.java这个文件，尝试让它运行起来。运行过程有可能会出现很多问题，但是如果人品比较高可能会顺利一点。这个文件会提供一些输出，来帮我们调试。
// // 在Windows系统中，应该默认使用用户的登陆凭据;当然我们也可以使用kinit来覆盖这个凭据，比如$JAVA_HOME\bin\kinit
// //
// testuser@AD.EXAMPLE.NET，这在我们测试和调试的时候就显得很有用了。如果想用回Windows默认的登陆凭据，删除kinit创建的缓存文件即可。
// // 确保在krb5.conf文件中列出domain_realms。这能解决很多不必要的问题。
// // 4.8.2. 使用GSS/JAVA KERBEROS
// // 下面的这份文档是针对Windows系统的，但是很多信息同样适合Unix。
// // org.ietf.jgss这个类有很多的配置参数，这些参数大部分都在krb5.conf/krb5.ini文件中配置。更多的信息，参考此处。
// // login.conf文件
// // 下面是一个基本的login.conf文件，使用于Windows平台的IIS和JBoss Negotiation模块。
// // 系统配置文件java.security.auth.login.config可以指定login.conf文件的路径。
// // login.conf的内容可能会是下面的样子：
// // com.sun.security.jgss.login {
// // com.sun.security.auth.module.Krb5LoginModule required client=TRUE
// // useTicketCache=true;
// // };
// //
// // com.sun.security.jgss.initiate {
// // com.sun.security.auth.module.Krb5LoginModule required client=TRUE
// // useTicketCache=true;
// // };
// //
// // com.sun.security.jgss.accept {
// // com.sun.security.auth.module.Krb5LoginModule required client=TRUE
// // useTicketCache=true;
// // };
// // 4.8.4. KRB5.CONF / KRB5.INI 文件
// //
// 如果没有手动指定，系统会使用默认配置。如果要手动指定，可以在java.security.krb5.conf中设置系统变量，指定krb5.conf的路径。krb5.conf的内容可能是下面的样子：
// // [libdefaults]
// // default_realm = AD.EXAMPLE.NET
// // udp_preference_limit = 1
// // [realms]
// // AD.EXAMPLE.NET = {
// // kdc = KDC.AD.EXAMPLE.NET
// // }
// // [domain_realms]
// // .ad.example.net=AD.EXAMPLE.NET
// // ad.example.net=AD.EXAMPLE.NET
// // 4.8.5. WINDOWS详细的配置
// //
// 为了允许Windows使用当前用户的tickets，javax.security.auth.useSubjectCredsOnly这个系统变量应该设置成false，并且需要在Windows注册表中添加allowtgtsessionkey这个项，而且要allow
// // session keys to be sent in the Kerberos Ticket-Granting Ticket.
// // Windows Server 2003和Windows 2000 SP4,配置如下：
// //
// HKEY_LOCAL_MACHINE\System\CurrentControlSet\Control\Lsa\Kerberos\Parameters
// // Value Name: allowtgtsessionkey
// // Value Type: REG_DWORD
// // Value: 0x01
// // Windows XP SP2 配置如下：
// // HKEY_LOCAL_MACHINE\System\CurrentControlSet\Control\Lsa\Kerberos\
// // Value Name: allowtgtsessionkey
// // Value Type: REG_DWORD
// // Value: 0x01
// // HttpClient4.3 教程 第五章 快速API
// // 5.1.Easy to use facade API
// //
// HttpClient从4.2开始支持快速api。快速api仅仅实现了HttpClient的基本功能，它只要用于一些不需要灵活性的简单场景。例如，快速api不需要用户处理连接管理和资源释放。
// // 下面是几个使用快速api的例子：
// // // 执行一个get方法，设置超时时间，并且将结果变成字符串
// // Request.Get("http://www.yeetrack.com/")
// // .connectTimeout(1000)
// // .socketTimeout(1000)
// // .execute().returnContent().asString();
// //
// // // 使用HTTP/1.1,通过'expect-continue' handshake来执行post方法
// // // 内容包含一个字符串，并且将结果转化成byte数组
// // Request.Post("http://www.yeetrack.com/do-stuff")
// // .useExpectContinue()
// // .version(HttpVersion.HTTP_1_1)
// // .bodyString("Important stuff", ContentType.DEFAULT_TEXT)
// // .execute().returnContent().asBytes();
// //
// // // 通过代理服务器来执行一个带有特殊header的post请求，post请求中带有form表单，并且将返回结果写入文件
// // Request.Post("http://www.yeetrack.com/some-form")
// // .addHeader("X-Custom-header", "stuff")
// // .viaProxy(new HttpHost("myproxy", 8080))
// // .bodyForm(Form.form().add("username", "vip").add("password",
// // "secret").build())
// // .execute().saveContent(new File("result.dump"));
// // 如果需要在指定的安全上下文中执行某些请求，我们也可以直接使用Exector，这时候用户的认证信息就会被缓存起来，以便后续的请求使用。
// // Executor executor = Executor.newInstance()
// // .auth(new HttpHost("somehost"), "username", "password")
// // .auth(new HttpHost("myproxy", 8080), "username", "password")
// // .authPreemptive(new HttpHost("myproxy", 8080));
// //
// // executor.execute(Request.Get("http://somehost/"))
// // .returnContent().asString();
// //
// // executor.execute(Request.Post("http://somehost/do-stuff")
// // .useExpectContinue()
// // .bodyString("Important stuff", ContentType.DEFAULT_TEXT))
// // .returnContent().asString();
// // 5.1.1.响应处理
// //
// 一般情况下，HttpClient的快速api不用用户处理连接管理和资源释放。但是，这样的话，就必须在内存中缓存这些响应消息。为了避免这一情况，建议使用使用ResponseHandler来处理Http响应。
// // Document result = Request.Get("http://somehost/content")
// // .execute().handleResponse(new ResponseHandler<Document>() {
// //
// // public Document handleResponse(final HttpResponse response) throws
// // IOException {
// // StatusLine statusLine = response.getStatusLine();
// // HttpEntity entity = response.getEntity();
// // if (statusLine.getStatusCode() >= 300) {
// // throw new HttpResponseException(
// // statusLine.getStatusCode(),
// // statusLine.getReasonPhrase());
// // }
// // if (entity == null) {
// // throw new ClientProtocolException("Response contains no content");
// // }
// // DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
// // try {
// // DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
// // ContentType contentType = ContentType.getOrDefault(entity);
// // if (!contentType.equals(ContentType.APPLICATION_XML)) {
// // throw new ClientProtocolException("Unexpected content type:" +
// // contentType);
// // }
// // String charset = contentType.getCharset();
// // if (charset == null) {
// // charset = HTTP.DEFAULT_CONTENT_CHARSET;
// // }
// // return docBuilder.parse(entity.getContent(), charset);
// // } catch (ParserConfigurationException ex) {
// // throw new IllegalStateException(ex);
// // } catch (SAXException ex) {
// // throw new ClientProtocolException("Malformed XML document", ex);
// // }
// // }
// //
// // });
// public static Logger logger = Logger.getLogger(LogServiceHelp.class);
//
// private static HttpClient httpclient;
//
// static {
// httpclient = HttpClients.createDefault();
// }
//
// @Test
// public void test() {
// String url =
// "http://www.shuchongw.com/files/article/html/23/23114/index.html";
// doGetHtmlContent2byte(url);
// }
//
// /**
// * 根据简单url获取网页数据,转换成byte [] 存储
// */
// // public static byte[] doGetHtmlContent2byte(String url) {
// // CloseableHttpResponse response = null;
// // byte[] resultByte = {};
// // try {
// // HttpGet get = new HttpGet(url);
// // System.out.println(url);
// // RequestConfig requestConfig =
// // RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
// // .build();
// // get.setConfig(requestConfig);
// // try {
// // response = (CloseableHttpResponse)
// // HttpClientUtils.httpclient.execute(get);
// // } catch (UnknownHostException e) {
// // e.printStackTrace();
// // logger.info("链接主网失败");
// // }
// // int statusCode = response.getStatusLine().getStatusCode();
// // System.out.println(statusCode);
// // if (statusCode == 200) {
// // HttpEntity entity = response.getEntity();
// // resultByte = EntityUtils.toByteArray(entity);
// // }
// // } catch (ClientProtocolException e) {
// // e.printStackTrace();
// // } catch (SocketException e) {
// // e.printStackTrace();
// // } catch (IOException e) {
// // e.printStackTrace();
// // } finally {
// // try {
// // if (response != null) {
// // response.close();
// // }
// // } catch (IOException e) {
// // e.printStackTrace();
// // }
// // }
// // return resultByte;
// // }
//
// /**
// * 根据复杂url获取网页数据,转换成byte [] 存储
// *
// * @throws ParserException
// * @throws IOException
// * @throws UnknownHostException
// * @throws ClientProtocolException
// * @throws SocketException
// */
// public static byte[] doGetHtmlByParams2Byte(Map<String, String> params,
// String paramsEncoding, String url) {
// if (params != null) {
// List<NameValuePair> formparams = new ArrayList<NameValuePair>();
// for (Entry<String, String> entry : params.entrySet()) {
// formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
// }
// if (!formparams.isEmpty()) {
// String paramsStr = URLEncodedUtils.format(formparams,
// paramsEncoding != null ? paramsEncoding : "utf-8");
// url = url + "?" + paramsStr;
// }
// }
// return doGetHtmlContent2byte(url);
// }
//
// /**
// * 根据复杂url获取网页数据,转换成String 存储
// *
// * @throws ParserException
// * @throws IOException
// * @throws UnknownHostException
// * @throws ClientProtocolException
// * @throws SocketException
// */
// public static String doGetHtmlByParams2Text(Map<String, String> params,
// String paramsEncoding, String url,
// String htmlEncoding) throws ClientProtocolException, UnknownHostException,
// SocketException {
// try {
// return getHtmlContentByText(doGetHtmlByParams2Byte(params, paramsEncoding,
// url),
// htmlEncoding != null ? htmlEncoding : "utf-8");
// } catch (Exception e) {
// e.printStackTrace();
// return "";
// }
// }
//
// /**
// * 根据简单url获取网页数据,转换成String 存储
// *
// * @throws ParserException
// * @throws IOException
// * @throws UnknownHostException
// * @throws ClientProtocolException
// * @throws SocketException
// */
// public static String doGetHtmlContentToString(String url, String encoding) {
// try {
// return getHtmlContentByText(doGetHtmlContent2byte(url), encoding);
// } catch (Exception e) {
// e.printStackTrace();
// return "";
// }
// }
//
// /**
// * 根据简单url获取网页图片数据, 保存路径[saveImagePath]
// *
// * @throws ParserException
// * @throws IOException
// * @throws UnknownHostException
// * @throws ClientProtocolException
// * @throws SocketException
// */
// public static void getHtml2Image(String url, String saveImagPath) {
// try {
// downloadData(doGetHtmlContent2byte(url), saveImagPath);
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
//
// public static String getHtmlContentByText(byte[] htmlBytes, String encoding)
// {
// try {
// return new String(htmlBytes, encoding != null ? encoding : "utf-8");
// } catch (UnsupportedEncodingException e) {
// e.printStackTrace();
// return "";
// }
// }
//
// /**
// * 执行下载io
// *
// * @param byte
// * [] data 网页字节流,filename 存储地址和文件名 return
// */
// public static void downloadData(byte[] data, String filename) {
// try {
// DataOutputStream writer = new DataOutputStream(new FileOutputStream(new
// File(filename)));
// BufferedOutputStream out = new BufferedOutputStream(writer);
// out.write(data);
// out.flush();
// out.close();
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } catch (IOException e) {
// e.printStackTrace();
// }
// }
//
// public static String readFile(String filename) throws IOException {
// String xmlContent = null;
// File file = new File(filename);
// BufferedReader reader = null;
// try {
// if (!file.exists()) {
// new RuntimeException("文件不存在");
// return xmlContent;
// }
// StringBuilder buider = new StringBuilder();
// String readata = "";
// reader = new BufferedReader(new FileReader(file));
// while (true) {
// readata = reader.readLine();
// if (readata == null) {
// break;
// }
// buider.append(readata).append("\n");
// }
// xmlContent = buider.toString();
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// } finally {
// if (reader != null)
// reader.close();
// }
// return xmlContent;
// }
//
// public static byte[] doGetByteByHttpclient2Url(HttpContext httpContext,
// CloseableHttpClient client, String url) {
// byte[] resultBytes = null;
// try {
// HttpGet get = new HttpGet(url);
// CloseableHttpResponse response = client.execute(get, httpContext);
// int status = response.getStatusLine().getStatusCode();
// System.out.println("链接状态=" + status);
// if (status != 200)
// return resultBytes;
// HttpEntity entity = response.getEntity();
// resultBytes = EntityUtils.toByteArray(entity);
// } catch (ClientProtocolException e) {
// throw new RuntimeException("失败连接地址" + url, e);
// } catch (IOException e) {
// throw new RuntimeException("失败连接地址" + url, e);
// }
// if (resultBytes == null) {
// try {
// Thread.sleep(2000);
// } catch (InterruptedException e) {
// e.printStackTrace();
// }
// }
// return resultBytes;
// }
//
// SSLContext sslContext=null;
//
// try
// {
// sslContext = new SSLContextBuilder().loadTrustMaterial(null, new
// TrustStrategy() {
//
// @Override
// public boolean isTrusted(X509Certificate[] chain, String authType) throws
// CertificateException {
//
// // 信任所有
// return true;
// }
//
// }).build();
//
// }catch(
// KeyManagementException e)
// {
// e.printStackTrace();
// }catch(
// NoSuchAlgorithmException e)
// {
// e.printStackTrace();
// }catch(
// KeyStoreException e)
// {
// e.printStackTrace();
// }
//
// SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
// sslContext);return
// HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookies).build();
//
// }
// }
