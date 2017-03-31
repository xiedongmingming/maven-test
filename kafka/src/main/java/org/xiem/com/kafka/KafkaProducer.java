package org.xiem.com.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.Partitioner;
import kafka.producer.ProducerConfig;

public class KafkaProducer extends Thread {

	private final Producer<Integer, String> producer;
	private final String topic;

	public KafkaProducer(String topic) {

		// 配置属性:
		// 为了使得我们能够连上KAFKA集群、如何序列化消息以及其它的属性
		// 我需要在程序里面进行相关的配置
		Properties props = new Properties();

		// serializer.class:
		// 这个参数指定了如何序列化发送的消息这个参数的默认值是: kafka.serializer.DefaultEncoder
		// 它将ARRAY[BYTE]转换成ARRAY[BYTE]也就是说什么都没做
		// 如果你发送的消息不是ARRAY[BYTE]的那么我们需要指定
		// 当然我们可以自定义消息系列化类只需要继承指定类即可: kafka.serializer.Encoder
		// 实现其中的TOBYTES方法即可(详情可以参见<<用SPARK往KAFKA里面写对象设计与实现>>)
		props.put("serializer.class", "kafka.serializer.StringEncoder");

		// key.serializer.class:
		// 参数指定了如何序列化消息的KEY默认值同SERIALIZER.CLASS也是可以自定义的
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");

		// partitioner.class:
		// 参数指定了如何根据消息计算其分区号
		// 必须是继承类: kafka.producer.Partitioner
		// 默认值是类: kafka.producer.DefaultPartitioner
		// 它是根据KEY的哈希值模上总分区数的
		// 如果没有指定KEY那么PRODUCER将为这条消息随机选择一个分区
		// props.put("partitioner.class", "com.iteblog.kafka.IteblogPartitioner"); // 自定义实现
		// props.put("partitioner.class", MyPartition.class.getName());

		// request.required.acks
		// 参数指定了PRODUCER发送消息之后是否需要来自BROKER的ACKNOWLEDGEMENT
		// 默认值为0也就是不需要ACKNOWLEDGEMENT
		// 这种情况下PRODUCER只管发送消息可能会导致消息丢失但是因为不需要确认所以发送速度也是很快的
		// 0:是不获取反馈(消息有可能传输失败)
		// 1、是获取消息传递给LEADER后反馈(其他副本有可能接受消息失败)
		// -1、是所有IN-SYNC REPLICAS接受到消息时的反馈
		props.put("request.required.acks", "1");

		// metadata.broker.list:
		// 参数指定了KAFKABROKER的地址(这个参数使得PRODUCER可以找到每个TOPIC的LEADER)我们不需要将KAFKABROKER所有的地址都列在这里而只需要列出其中的两个
		// 之所以列出两个是因为预防其中一个连不上有了这两个KAFKABROKER我们的PRODUCER足以找到TOPIC的META_DATA
		props.put("metadata.broker.list", "192.168.186.135:9093");
		// props.put("zookeeper.connect", "vm1:2181");// ZK集群


		// 创建PRODUCER
		producer = new Producer<Integer, String>(new ProducerConfig(props));

		this.topic = topic;
	}

	@Override
	public void run() {

		int messageNo = 1;

		while (true) {

			String messageStr = new String("message " + messageNo);

			System.out.println("send: " + messageStr);

			// 如果这个TOPIC不存在则PRODUCER可以创建
			// auto.create.topics.enable: true (默认就是TRUE)
			producer.send(new KeyedMessage<Integer, String>(topic, messageStr));// 发送消息的函数

			// 将消息封装成: KeyedMessage
			// 指定其中的TOPIC、KEY以及VALUE即可
			
			// MESSAGE可以带KEY根据KEY来将消息分配到指定区如果没有KEY则随机分配到某个区
			// KeyedMessage<Integer, String> keyedMessage = new KeyedMessage<Integer, String>("test", 1, message);

			messageNo++;

			try {
				sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// producer.close();
	}
}

class MyPartition implements Partitioner {// 自定义分区类

	@Override
	public int partition(Object key, int numPartitions) {
		return key.hashCode() % numPartitions;
	}

}