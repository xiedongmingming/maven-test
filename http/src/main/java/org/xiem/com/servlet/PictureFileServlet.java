package org.xiem.com.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PictureFileServlet extends HttpServlet {

	private static final long serialVersionUID = 767723997294529703L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/x-shockwave-flash");
		response.setStatus(HttpServletResponse.SC_OK);

		FileInputStream fis = null;

		System.out.println("请求路径为: " + request.getPathInfo());// 这里显示的路径是相对于该SERVLET的路径

		if ("/xieming034.flv".equals(request.getPathInfo())) {
			fis = new FileInputStream("src/main/resources/xieming034.flv");
		} else if ("/xieming030.swf".equals(request.getPathInfo())) {
			fis = new FileInputStream("src/main/resources/xieming030.swf");
		}
		
		if (fis == null) {
			response.getWriter().println("请求的文件不存在!");
			return;
		}

		OutputStream os = response.getOutputStream();

		byte[] bbuf = new byte[1024];
		int hasread = 0;
		while ((hasread = fis.read(bbuf)) > 0) {
			os.write(bbuf, 0, hasread);
		}

		os.flush();

    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
