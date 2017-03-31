package org.xiem.com.kafka.yosemite;

import java.util.Date;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MainTest {

	private static Producer<String, byte[]> producer;


	public static void main(String[] args) throws InterruptedException {
		
		Properties props = new Properties();
		
		// 下面是优闪KAFKA配置:
		//	props.setProperty("metadata.broker.list", "kafka1.t1.shadc.yosemitecloud.com:9092");
		//	props.setProperty("producer.type", "async");
		//	props.setProperty("batch.num.messages", "2000");
		//	props.setProperty("key.serializer.class", "kafka.serializer.StringEncoder");

		// 下面是本地KAFKA配置:
		props.setProperty("metadata.broker.list", "192.168.186.135:9093");
		props.setProperty("key.serializer.class", "kafka.serializer.StringEncoder");

		ProducerConfig config = new ProducerConfig(props);
		
		producer = new Producer<String, byte[]>(config);
		
		if (producer != null) {

			int i = 0;

			while (true) {
			
				final byte[] msg = new String(new Date() + " ---> message: " + i++).getBytes();

				System.out.println("发送的消息为: " + new String(msg));

				KeyedMessage<String, byte[]> message = new KeyedMessage<>("testtopic", "key" + i, msg);

				producer.send(message);

				Thread.sleep(1000);
			}
		}
		
		try {
			if (producer != null) {
				producer.close();
			}
		} catch (Throwable e) {
		}
	}

}
