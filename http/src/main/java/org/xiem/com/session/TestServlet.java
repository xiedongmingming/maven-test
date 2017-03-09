package org.xiem.com.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 5387094265938322482L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("jqm:" + request.getParameter("jqm"));
		System.out.println("sqm:" + request.getParameter("sqm"));

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("jqm:" + request.getParameter("jqm"));
		out.println("sqm:" + request.getParameter("sqm"));
		out.flush();
		out.close();
	}

}