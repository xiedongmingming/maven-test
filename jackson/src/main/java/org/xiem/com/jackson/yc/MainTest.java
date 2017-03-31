package org.xiem.com.jackson.yc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class MainTest {// JAVA生成JSON字符串的方法

	public static void main(String[] args) throws JSONException {

		Test000();
		Test001();
		Test002();
		Test003();
	}

	public static void Test000() {

		JSONObject json = new JSONObject();

		try {

			json.put("min_duration", "http://jinjunmei.bnmat.cn/static/uirest1_creative_14547_content.jpeg?20170123163029000000");
			json.put("max_duration", 20);

			json.put("person", new Person("zhangsan", 30000, "shanghai").getJSON());

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(json.toString());
	}

	public static void Test001() throws JSONException {// 将MAP对象添加一次元素(包括字符串对、数组)转换成JSON对象一次

		String json = "{'name':'reiz'}";

		JSONObject jsonObj = new JSONObject(json);

		String name = jsonObj.getString("name");

		System.out.println(jsonObj);

		jsonObj.put("initial", name.substring(0, 1).toUpperCase());

		String[] likes = new String[] { "JavaScript", "Skiing", "Apple Pie" };

		jsonObj.put("likes", likes);

		System.out.println(jsonObj);

		Map<String, String> ingredients = new HashMap<String, String>();

		ingredients.put("apples", "3kg");
		ingredients.put("sugar", "1kg");
		ingredients.put("pastry", "2.4kg");
		ingredients.put("bestEaten", "outdoors");

		jsonObj.put("ingredients", ingredients);

		System.out.println(jsonObj);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void Test002() throws JSONException {// LIST转换成JSON的三种参数形式

		boolean[] boolArray = new boolean[] { true, false, true };

		net.sf.json.JSONArray jsonArray1 = net.sf.json.JSONArray.fromObject(boolArray);
		System.out.println(jsonArray1);

		List list = new ArrayList();
		list.add("first");
		list.add("second");
		net.sf.json.JSONArray jsonArray2 = net.sf.json.JSONArray.fromObject(list);
		System.out.println(jsonArray2);

		net.sf.json.JSONArray jsonArray3 = net.sf.json.JSONArray.fromObject("['json','is','easy']");
		System.out.println(jsonArray3);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void Test003() throws JSONException {// JSON转换成LIST和MAP

		String listStr = "[\"apple\",\"orange\"]";

		Collection<String> strlist = net.sf.json.JSONArray.toCollection(net.sf.json.JSONArray.fromObject(listStr));

		for (String str : strlist) {
			System.out.println(str);
		}

		String mapStr = "{\"age\":30,\"name\":\"Michael\",\"baby\":[\"Lucy\",\"Lily\"]}";

		Map<String, Object> map = (Map) net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(mapStr), Map.class);

		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	public static class Person {

		public String name;
		public int id;
		public String address;

		public Person(String name, int id, String address) {
			this.name = name;
			this.id = id;
			this.address = address;
		}

		public JSONObject getJSON() throws JSONException {

			JSONObject json = new JSONObject();

			json.put("name", this.name);
			json.put("id", this.id);
			json.put("address", this.address);

			return json;

		}
	}

}
