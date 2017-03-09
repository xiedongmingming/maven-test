package org.xiem.com.kafka.yc;

public class MainTest {
	//
	// private final Producer<String, byte[]> producer;
	//
	// public final boolean logToKafka;// ȫ�ֿ��Ƹü�¼��
	//
	// public static void main(String[] args) {
	//
	// }
	//
	// // ����KAFKA��Ҫ�ĸ�����:
	//
	// // "metadata.broker.list":"kafka1.t1.shadc.yosemitecloud.com:9092"
	// // "producer.type":"async"
	// // "batch.num.messages":"2000"
	// // "key.serializer.class":"kafka.serializer.StringEncoder"
	//
	// public MainTest() {
	//
	// ProducerConfig config = new ProducerConfig(null);
	// producer = new Producer<String, byte[]>(config);
	// }
	//
	// public void add(final AbstractEvent event) throws IOException {//
	// ��Ӵ���¼���¼���KAFAKA��
	//
	// if (!event.loggit()) {// ��ʾ���¼�����Ҫ��¼--���¼��������
	// return;
	// }
	//
	// if (logToKafka && producer != null) {// �����˼�¼����
	//
	// final byte[] msg = event.toSerializedByteArray();// ���¼����л�Ϊ�ֽ�����
	//
	// // ���������ֱ�Ϊ:
	// // �¼������ع�
	// // �¼���־����ID��ʱ��
	// // �¼���Ϣ
	// KeyedMessage<String, byte[]> message = new
	// KeyedMessage<>(event.getTopic(), event.getKey(), msg);
	//
	// producer.send(message);
	// }
	// }
	//
	// public void close() {// �رռ�¼��
	// try {
	// if (producer != null) {
	// producer.close();
	// }
	// } catch (Throwable e) {
	// LOG.error("error closing kafka producer", e);
	// }
	// }
}
