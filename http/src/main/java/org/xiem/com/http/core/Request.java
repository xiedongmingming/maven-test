package org.xiem.com.http.core;

import java.net.URI;

public interface Request {//

	public final static String GET = "GET";
	public final static String POST = "POST";

	public String getParamter(String param);

	public String getMethod();

	public URI getReuestURI();

	public void initRequestHeader();
	public void initRequestParam();
	public void initRequestBody();

	public String getRequestBody();
}
