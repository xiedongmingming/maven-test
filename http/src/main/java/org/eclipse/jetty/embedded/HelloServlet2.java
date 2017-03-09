package org.eclipse.jetty.embedded;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HelloServlet2 extends HttpServlet {
    final String greeting;

	public HelloServlet2() {
        this("Hello");
    }

	public HelloServlet2(String greeting) {
        this.greeting = greeting;
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		@SuppressWarnings("resource")
		FileInputStream input = new FileInputStream("D:/优闪开发工具/笔记/优闪学习/BEACON/other.html");

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
