package org.xiem.com.http;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xiem.com.http.impl.HttpHandler;
import org.xiem.com.http.utils.XmlUtils;

public class Context {

	private static Map<String, HttpHandler> contextMap = new HashMap<String, HttpHandler>();// ӳ��

	public static String contextPath = "";

	public static void load() {

		try {

			System.out.println("��Դ��·��Ϊ:" + Context.class.getResource("/resources"));// ����ĸ�·����ʾCLASSESĿ¼

			Document doc = XmlUtils.load(Context.class.getResource("/").getPath() + "context.xml");

			Element root = doc.getDocumentElement();// ��ʾ��Ԫ��
			
			contextPath = XmlUtils.getAttribute(root, "context");//��ȡָ��Ԫ�ص�ָ������

			System.out.println("����·��Ϊ:" + contextPath);

			Element[] handlers = XmlUtils.getChildrenByName(root, "handler");

			for(Element ele : handlers){

				String handle_class = XmlUtils.getChildText(ele, "handler-class");

				String url_pattern = XmlUtils.getChildText(ele, "url-pattern");
				
				Class<?> cls = Class.forName(handle_class);

				Object newInstance = cls.newInstance();//����ʵ��

				if (newInstance instanceof HttpHandler) {
					contextMap.put(contextPath + url_pattern, (HttpHandler) newInstance);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HttpHandler getHandler(String key){
		return contextMap.get(key);
	}
}