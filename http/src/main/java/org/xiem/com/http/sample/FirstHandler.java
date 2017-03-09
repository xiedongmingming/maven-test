package org.xiem.com.http.sample;

import org.xiem.com.http.core.Request;
import org.xiem.com.http.core.Response;
import org.xiem.com.http.impl.HttpHandler;

public class FirstHandler extends HttpHandler {
	
	@Override     
	public void doGet(Request request, Response response) {          

		System.out.println("doGet");                   
		System.out.println(request.getParamter("aaa"));          
		System.out.println(request.getParamter("bbb"));                    

		response.write("helloWorld.....");      
		
	}              
	
	@Override     
	public void doPost(Request request, Response response) {          

		System.out.println("doPost");          

		System.out.println(request.getRequestBody());                    

		response.write("helloWorld.....");     
	}          
}