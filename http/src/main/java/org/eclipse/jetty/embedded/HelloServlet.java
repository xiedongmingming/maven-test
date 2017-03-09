package org.eclipse.jetty.embedded;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {
    final String greeting;

	public HelloServlet() {
        this("Hello");
    }

	public HelloServlet(String greeting) {
        this.greeting = greeting;
    }

	@SuppressWarnings("resource")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {



		response.setContentType("text/html");

		FileInputStream input = null;
		if (request.getPathInfo().contains("other")) {
			input = new FileInputStream("D:/优闪开发工具/笔记/优闪学习/BEACON/other.html");
		} else if (request.getPathInfo().contains("as")) {
			System.out.println("退出请求中的参考: " + request.getPathInfo());
			System.out.println("请求中的参考: " + request.getHeader("Referer"));
			return;
		} else if (request.getPathInfo() != null) {
			input = new FileInputStream("D:/优闪开发工具/笔记/优闪学习/BEACON" + request.getPathInfo());
		}

		System.out.println("请求中的参考: " + request.getPathInfo());
		System.out.println("请求中的参考: " + request.getHeader("Referer"));

		byte[] bytes = new byte[1024];

		int n = 0;

		while ((n = input.read(bytes)) > 0) {
			response.getOutputStream().write(bytes, 0, n);
		}

		response.setStatus(HttpServletResponse.SC_OK);

		//
		// final String queryString = request.getQueryString();
		//
		// if (queryString != null && queryString.indexOf("&tanx_ext=win") > 0)
		// {
		// response.setStatus(HttpServletResponse.SC_OK);
		// return;
		// }
		//
		// System.out.println("");
		//
		// response.setContentType("text/html");
		// response.setStatus(HttpServletResponse.SC_OK);
		// response.getWriter().println("<h1>" + greeting + " from
		// HelloServlet</h1>");
    }
}
