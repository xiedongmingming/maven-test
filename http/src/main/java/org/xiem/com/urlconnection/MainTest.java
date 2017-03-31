package org.xiem.com.urlconnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainTest {

	public static void main(String[] args) {

		URL url = null;

		HttpURLConnection connection = null;

		try {

			url = new URL("xxx");

			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			out.write("xxxx".getBytes("utf-8"));
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

			StringBuffer buffer = new StringBuffer();
			String line = "";

			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();

			System.out.println(buffer.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
