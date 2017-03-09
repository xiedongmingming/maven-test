package org.xiem.com.hbase;

public class HBaseClientProvider {

	// final HBaseClient client;
	//
	// public static void main(String[] args) {
	//
	// final Config config = new Config();
	//
	// config.overrideConfig("zookeeper.directory", "/shadc-t1/hbase");
	// config.overrideConfig("zookeeper.quorum",
	// "zookeeper1.t1.shadc.yosemitecloud.com:2181,zookeeper2.t1.shadc.yosemitecloud.com:2181,zookeeper3.t1.shadc.yosemitecloud.com:2181");
	// config.overrideConfig("zookeeper.port", "2181");
	// config.overrideConfig("hbaseasynchutil.hbaseclient.randompoolsize", "2");
	// config.overrideConfig("rts.hbase.async", "true");
	//
	// System.out.println(config.dumpConfiguration());
	// }
	//
	// public HBaseClientProvider(final Config config) {
	// this.client = new HBaseClient(config);
	// }
	//
	// public Deferred<ArrayList<KeyValue>> readLatest(final byte[]
	// familyNameBytes, final byte[] key,
	// final ColumnSet columnSet) {// 具体实现
	//
	// final GetRequest getRequest = createGetRequest(tableNameBytes,
	// familyNameBytes, key, columnSet, 1);// 构建一个请求(只有一个版本的数据)
	//
	// return hcp.get().get(getRequest);// 执行请求--第一个GET返回CLIENT
	// }
	// public static GetRequest createGetRequest(// 构建一个请求
	// final byte[] tableNameBytes, // HBASE表名
	// final byte[] familyNameBytes, // HBASE列族名
	// final byte[] key, // 行键名称
	// final ColumnSet columnSet, // 列集合
	// final int maxVersions) {// 多少个版本的数据
	//
	// GetRequest getRequest = new GetRequest(tableNameBytes, key);// 只需要表名和行健名称
	//
	// getRequest.family(familyNameBytes);// 读取某个列族
	// getRequest.maxVersions(maxVersions);// 读取的版本数
	//
	// if (columnSet != null && !columnSet.isAllColumns()) {// 不为空且不是该列族的全部列
	//
	// final int size = columnSet.getNumberOfColumns();
	//
	// if (size > 0) {
	//
	// byte[][] qualifiers = new byte[size][];// 列的修饰符
	//
	// int index = 0;
	//
	// Iterator<ColumnInfo> iterator = columnSet.getColumnSetIterator();
	//
	// while (iterator.hasNext()) {
	//
	// ColumnInfo column = iterator.next();
	//
	// qualifiers[index++] = column.getNameBytes();// 获取列名
	// }
	//
	// getRequest.qualifiers(qualifiers);// 全部要查询的列名称集合
	// }
	// }
	//
	// return getRequest;
	// }
}
