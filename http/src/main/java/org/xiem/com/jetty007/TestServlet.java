package org.xiem.com.jetty007;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

public class TestServlet extends DefaultServlet {

	private static final long serialVersionUID = 2252713664341314228L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("请求URL为: " + request.getQueryString());

		FileOutputStream out = new FileOutputStream(new File(request.getParameter("file_name")));
		
		ServletInputStream in = request.getInputStream();
		
		byte[] bytes = new byte[1024];
		
		int n = 0;
		
		while ((n = in.read(bytes)) != 0) {
			out.write(bytes, 0, n);
		}

		out.flush();

		out.close();
	}
}
