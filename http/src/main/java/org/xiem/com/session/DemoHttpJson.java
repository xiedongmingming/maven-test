package org.xiem.com.session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class DemoHttpJson extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DemoHttpJson() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletInputStream servletInputStream = request.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int i = 0;
		while ((i = servletInputStream.read(b, 0, 1024)) > 0) {
			out.write(b, 0, i);
		}

		byte[] req = out.toByteArray();

		String str = new String(req, "UTF-8");
		JSONObject reqJson = JSONObject.fromObject(str);
		JSONObject json = new JSONObject();
		json.put("VER", "1.0");
		if (reqJson.get("username").equals("demo")) {
			json.put("msg", "true");
		} else {
			json.put("msg", "false");
		}

		PrintWriter pw = response.getWriter();
		pw.write(json.toString());
	}

}