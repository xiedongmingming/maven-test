package org.xiem.com.jetty002.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author y
 */
public interface HBean {
	/**
	 *
	 * 
	 * @param request
	 * @param response
	 */
	void init(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	void response(HttpServletRequest request, HttpServletResponse response);
}