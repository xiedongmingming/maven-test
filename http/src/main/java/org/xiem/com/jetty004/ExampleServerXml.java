package org.xiem.com.jetty004;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

/**
 * : https://raw.githubusercontent.com/eclipse/jetty.project/master/examples/
 * embedded/src/main/resources/exampleserver.xml
 */
public class ExampleServerXml {// ���������ļ�������JETTY����

	public static void main(String[] args) throws Exception {

		// find jetty xml (in classpath) that configures and starts server.
        Resource serverXml = Resource.newSystemResource("exampleserver.xml");

		XmlConfiguration.main(new String[] { serverXml.getFile().getAbsolutePath() });
    }
}
