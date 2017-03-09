package org.xiem.com.http.impl;

import org.xiem.com.http.core.Handler;
import org.xiem.com.http.core.Request;
import org.xiem.com.http.core.Response;

public abstract class HttpHandler implements Handler {

	public void service(Request request, Response response) {

		// request.initRequestHeader();//会清除请求头
		// request.initRequestParam();//会清除请求参数

		if (request.getMethod().equals(Request.GET)) {

			this.doGet(request, response);

		} else if (request.getMethod().equals(Request.POST)) {

			// request.initRequestBody();

			this.doPost(request, response);
		}
	}

	public abstract void doGet(Request request, Response response);

	public abstract void doPost(Request request, Response response);

}
