package org.xiem.com.kafka;

public class MainTest {

	public static void main(String[] args) throws InterruptedException {

		KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
		producerThread.start();

		Thread.sleep(1000);

		KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
		consumerThread.start();
	}
}
