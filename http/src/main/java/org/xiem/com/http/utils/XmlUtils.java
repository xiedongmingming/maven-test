package org.xiem.com.http.utils;

import java.io.CharArrayReader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlUtils {
	
	public static final String BR = System.getProperty("line.separator");

	public static Document load(String xmlfile) throws Exception {

		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);//
		factory.setValidating(false);
		factory.setCoalescing(false);
		
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(xmlfile);
	}

	public static Document load(File xmlfile) throws Exception {
		
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setValidating(false);
		factory.setCoalescing(false);
		
		DocumentBuilder builder = factory.newDocumentBuilder();

		return builder.parse(xmlfile);
	}

	public static String getFileName(String filePath) {

		Pattern p = Pattern.compile("[^\\" + File.separator + "]+.xml");
		
		Matcher m = p.matcher(filePath);
		
		if (m.find()) {
			return m.group().substring(0, m.group().length() - 4);
		}
		
		return "";
	}

	public static boolean checkValidity(String filePath) {
		
		String[] array = filePath.split(".");
		
		if (array[array.length - 1].equals("xml")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isXml(String file) {
		
		if (file.toLowerCase().endsWith("xml")) {
			return true;
		} else {
			return false;
		}
	}

	public static Document loadStringWithoutTitle(String domContent) throws Exception {
		domContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + BR + domContent;
		return XmlUtils.loadString(domContent);
	}

	public static Document loadString(String domContent) throws Exception {
		
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setValidating(false);
		factory.setCoalescing(false);
		
		DocumentBuilder builder = factory.newDocumentBuilder();

		char[] chars = new char[domContent.length()];
		domContent.getChars(0, domContent.length(), chars, 0);
		InputSource is = new InputSource(new CharArrayReader(chars));
		return (builder.parse(is));
	}

	public static String getTextByFullName(Document doc, String fullname) {
		String path[] = StringUtils.toStringArray(fullname, ".");
		Element e = doc.getDocumentElement();
		for (int i = 1; i < path.length; i++) {
			e = getChildByName(e, path[i]);
		}
		return getText(e);
	}

	public static String getTextByFullName(Element parent, String fullname) {
		String path[] = StringUtils.toStringArray(fullname, ".");
		Element e = parent;
		for (int i = 0; i < path.length; i++) {
			e = getChildByName(e, path[i]);
		}
		return getText(e);
	}

	public static String getChildText(Element parent, String name) {
		
		Element e = getChildByName(parent, name);
		
		if (e == null) {
			return "";
		}
		
		return getText(e);
	}

	public static Element[] getChildrenByName(Element e, String name) {
		
		NodeList nl = e.getChildNodes();
		
		int max = nl.getLength();
		
		LinkedList<Node> list = new LinkedList<Node>();
		for (int i = 0; i < max; i++) {
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(name)) {
				list.add(n);
			}
		}
		
		return list.toArray(new Element[list.size()]);
	}

	public static Element getChildByName(Element e, String name) {
		
		//���ڵ� �ӽڵ�����
		
		Element[] list = getChildrenByName(e, name);
		if (list.length == 0) {
			return null;
		}
		if (list.length > 1) {
			throw new IllegalStateException("Too many (" + list.length + ") '" + name + "' elements found!");
		}
		return list[0];
	}

	public static String getText(Element e) {
		NodeList nl = e.getChildNodes();
		int max = nl.getLength();
		for (int i = 0; i < max; i++) {
			Node n = nl.item(i);
			if (n.getNodeType() == Node.TEXT_NODE) {
				return n.getNodeValue();
			}
		}
		return "";
	}

	public static String getAttribute(Element e, String name) {
		return e.getAttribute(name);
	}

	public static int getIntValue(Element e) {
		return Integer.valueOf(getText(e));
	}

	public static byte getByteValue(Element e) {
		return Byte.valueOf(getText(e));
	}

	public static Map<String, Object> getProperties(Element root) {
		Map<String, Object> map = new HashMap<String, Object>();
		Element[] list = getChildrenByName(root, "property");
		for (int i = 0; i < list.length; i++) {
			String name = list[i].getAttribute("name");
			String type = list[i].getAttribute("type");
			String valueString = getText(list[i]);
			try {
				Class<?> cls = Class.forName(type);
				Constructor<?> con = cls.getConstructor(new Class<?>[] { String.class

				});
				Object value = con.newInstance(new Object[] { valueString

				});
				map.put(name, value);
			} catch (Exception e) {
				System.err.println("Unable to parse property '" + name + "'='" + valueString + "': " + e.toString());
			}
		}
		return map;
	}

	public static void save(String xmlfile, Document doc) throws Exception {

		DOMSource doms = new DOMSource(doc);

		File f = new File(xmlfile);
		File dir = f.getParentFile();
		dir.mkdirs();

		StreamResult sr = new StreamResult(f);

		try {

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();

			Properties properties = t.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "UTF-8");
			properties.setProperty(OutputKeys.INDENT, "yes");

			t.setOutputProperties(properties);

			t.transform(doms, sr);

		} catch (TransformerConfigurationException tce) {
			tce.printStackTrace();
		} catch (TransformerException te) {
			te.printStackTrace();
		}

	}

	public static Document blankDocument(String rootElementName) throws Exception {
		javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(false);
		factory.setValidating(false);
		factory.setCoalescing(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		Element root = doc.createElement(rootElementName);
		doc.appendChild(root);
		return doc;
	}

	public static Element createChild(Document doc, Element root, String name) {
		Element elem = doc.createElement(name);
		root.appendChild(elem);
		return elem;
	}

	public static void createChildText(Document doc, Element elem, String name, String value) {
		Element child = doc.createElement(name);
		child.appendChild(doc.createTextNode(value == null ? "" : value));
		elem.appendChild(child);
	}

	public static void createChildTextWithComment(Document doc, Element elem, String name, String value,
			String comment) {
		Element child = doc.createElement(name);
		child.appendChild(doc.createTextNode(value == null ? "" : value));
		Comment c = doc.createComment(comment);
		elem.appendChild(c);
		elem.appendChild(child);

	}

	public static void createComment(Document doc, String comment) {
		Comment c = doc.createComment(comment);
		doc.getDocumentElement().appendChild(c);
	}

	public static void createOptionalChildText(Document doc, Element elem, String name, String value) {
		if (value == null || value.length() == 0) {
			return;
		}
		Element child = doc.createElement(name);
		child.appendChild(doc.createTextNode(value));
		elem.appendChild(child);
	}

	public static void applyProperties(Object o, Element root) {
		Map<String, Object> map = getProperties(root);
		Iterator<String> it = map.keySet().iterator();
		Field[] fields = o.getClass().getFields();
		Method[] methods = o.getClass().getMethods();
		while (it.hasNext()) {
			String name = it.next();
			Object value = map.get(name);
			try {
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getName().equalsIgnoreCase(name)
							&& isTypeMatch(fields[i].getType(), value.getClass())) {
						fields[i].set(o, value);
						System.err.println("Set field " + fields

						[i].getName() + "=" + value);
						break;
					}
				}
				for (int i = 0; i < methods.length; i++) {
					if (methods[i].getName().equalsIgnoreCase("set" + name) && methods[i].getParameterTypes

					().length == 1 && isTypeMatch(methods

					[i].getParameterTypes()[0], value.getClass())) {
						methods[i].invoke(o, new Object[] { value

						});
						System.err.println("Set method " + methods

						[i].getName() + "=" + value);
						break;
					}
				}
			} catch (Exception e) {
				System.err.println("Unable to apply property '" + name + "': " + e.toString());
			}
		}
	}

	private static boolean isTypeMatch(Class<?> one, Class<?> two) {
		if (one.equals(two)) {
			return true;
		}
		if (one.isPrimitive()) {
			if (one.getName().equals("int") && two.getName().equals("java.lang.Integer")) {
				return true;
			}
			if (one.getName().equals("long") && two.getName().equals("java.lang.Long")) {
				return true;
			}
			if (one.getName().equals("float") && two.getName().equals("java.lang.Float")) {
				return true;
			}
			if (one.getName().equals("double") && two.getName().equals("java.lang.Double")) {
				return true;
			}
			if (one.getName().equals("char") && two.getName().equals("java.lang.Character")) {
				return true;
			}
			if (one.getName().equals("byte") && two.getName().equals("java.lang.Byte")) {
				return true;
			}
			if (one.getName().equals("short") && two.getName().equals("java.lang.Short")) {
				return true;
			}
			if (one.getName().equals("boolean") && two.getName().equals("java.lang.Boolean")) {
				return true;
			}
		}
		return false;
	}
}