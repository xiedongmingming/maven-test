package org.xiem.com.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaConsumer2 {// 使用JAVA实现KAFKA的消费者

	private static final String TOPIC = "test";

	private static final int THREAD_AMOUNT = 1;

	public static void main(String[] args) {

		Properties props = new Properties();

		props.put("zookeeper.connect", "vm1:2181");
		props.put("group.id", "group1");
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

		topicCountMap.put(TOPIC, THREAD_AMOUNT);// 每个TOPIC使用多少个KAFKASTREAM读取(多个CONSUMER)
		// topicCountMap.put(TOPIC2, 1);// 可以读取多个TOPIC

		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));

		Map<String, List<KafkaStream<byte[], byte[]>>> msgStreams = consumer.createMessageStreams(topicCountMap);

		List<KafkaStream<byte[], byte[]>> msgStreamList = msgStreams.get(TOPIC);

		ExecutorService executor = Executors.newFixedThreadPool(THREAD_AMOUNT);// 调度线程

		for (int i = 0; i < msgStreamList.size(); i++) {

			KafkaStream<byte[], byte[]> kafkaStream = msgStreamList.get(i);

			executor.submit(new HanldMessageThread(kafkaStream, i));

		}

		// 关闭CONSUMER

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (consumer != null) {
			consumer.shutdown();
		}
		if (executor != null) {
			executor.shutdown();
		}

		try {
			if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
				System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
			}
		} catch (InterruptedException e) {
			System.out.println("Interrupted during shutdown, exiting uncleanly");
		}
	}

}

class HanldMessageThread implements Runnable {// 具体处理消息的线程

	private KafkaStream<byte[], byte[]> kafkaStream = null;
	private int num = 0;

	public HanldMessageThread(KafkaStream<byte[], byte[]> kafkaStream, int num) {
		super();
		this.kafkaStream = kafkaStream;
		this.num = num;
	}

	@Override
	public void run() {
		ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
		while (iterator.hasNext()) {
			String message = new String(iterator.next().message());
			System.out.println("Thread no: " + num + ", message: " + message);
		}
	}

}

//
// props.put("auto.commit.interval.ms", "1000");
// 表示的是：consumer间隔多长时间在zookeeper上更新一次offset
//
// 说明：
//
// 为什么使用High Level Consumer？
//
// 有些场景下，从Kafka中读取消息的逻辑不处理消息的offset，仅仅是获取消息数据。High Level Consumer就提供了这种功能。
//
// 首先要知道的是，High Level
// Consumer在ZooKeeper上保存最新的offset（从指定的分区中读取）。这个offset基于consumer group名存储。
//
// Consumer group名在Kafka集群上是全局性的，在启动新的consumer
// group的时候要小心集群上没有关闭的consumer。当一个consumer线程启动了，Kafka会将它加入到相同的topic下的相同consumer
// group里，并且触发重新分配。在重新分配时，Kafka将partition分配给consumer，有可能会移动一个partition给另一个consumer。如果老的、新的处理逻辑同时存在，有可能一些消息传递到了老的consumer上。
//
// 设计High Level Consumer
//
// 使用High
// LevelConsumer首先要知道的是，它应该是多线程的。消费者线程的数量跟tipic的partition数量有关，它们之间有一些特定的规则：
//
// 如果线程数量大于主题的分区数量，一些线程将得不到任何消息
//
// 如果分区数大于线程数，一些线程将得到多个分区的消息
//
// 如果一个线程处理多个分区的消息，它接收到消息的顺序是不能保证的。比如，先从分区10获取了5条消息，从分区11获取了6条消息，然后从分区10获取了5条，紧接着又从分区10获取了5条，虽然分区11还有消息。
//
// 添加更多了同consumer group的consumer将触发Kafka重新分配，某个分区本来分配给a线程的，从新分配后，有可能分配给了b线程。
//
// 关闭消费组和错误处理
//
// Kafka不会再每次读取消息后马上更新zookeeper上的offset，而是等待一段时间。由于这种延迟，有可能消费者读取了一条消息，但没有更新offset。所以，当客户端关闭或崩溃后，从新启动时有些消息重复读取了。另外，broker宕机或其他原因导致更换了partition的leader，也会导致消息重复读取。
//
// 为了避免这种问题，你应该提供一个平滑的关闭方式，而不是使用kill -9
//
// 上面的java代码中提供一种关闭的方式：
//
// 1
// 2
// 3
// 4
// 5
// 6
// 7
// 8
// 9
// 10
// 11
// 12
// 13
// if (consumer != null) {
// consumer.shutdown();
// }
// if (executor != null) {
// executor.shutdown();
// }
// try {
// if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
// System.out.println("Timed out waiting for consumer threads to shut down,
// exiting uncleanly");
// }
// } catch (InterruptedException e) {
// System.out.println("Interrupted during shutdown, exiting uncleanly");
// }
// 在shutdown之后，等待了5秒钟，给consumer线程时间来处理完kafka stream里保留的消息。
//
// 参考资料：https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example