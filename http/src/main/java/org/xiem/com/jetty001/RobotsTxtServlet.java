package org.xiem.com.jetty001;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

public class RobotsTxtServlet extends DefaultServlet {
	private static final long serialVersionUID = 218837504233384594L;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		final PrintWriter output = resp.getWriter();
		output.println("User-agent: *");
		output.println("Disallow: /");
	}
}
