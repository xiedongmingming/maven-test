package org.xiem.com.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
public class ZipFileReceiveServlet extends HttpServlet {

	private static final long serialVersionUID = 2843907109614315830L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("请求参数: 注意是头不是参数");
		System.out.println(URLDecoder.decode(request.getHeader("dsp_token")));
		System.out.println(URLDecoder.decode(request.getHeader("ad_id")));
		System.out.println(URLDecoder.decode(request.getHeader("name")));
		System.out.println(URLDecoder.decode(request.getHeader("industry")));
		System.out.println(URLDecoder.decode(request.getHeader("file_name")));
		System.out.println(URLDecoder.decode(request.getHeader("op")));

		FileOutputStream out = new FileOutputStream(URLDecoder.decode(request.getHeader("file_name")));

		ServletInputStream in = request.getInputStream();

		byte[] bytes = new byte[1024];

		int n = 0;

		while ((n = in.read(bytes)) > 0) {// 注意这里是大于0(不能写成不等于0)
			out.write(bytes, 0, n);
		}

		out.flush();

		out.close();

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		response.getWriter().println("OK");
	}
}
