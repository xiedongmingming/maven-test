package org.xiem.com.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Locale;

public class ExecuteSql {

	private String driver;
	private String url;
	private String user;
	private String pass;

	public void initParam() throws Exception {

		driver = "com.mysql.jdbc.Driver";

		url = "jdbc:mysql://xtradb1.t1.shadc.yosemitecloud.com/yc_core";

		user = "yc_core";
		pass = "yc_core";
	}

	public void executeSql(String sql) throws Exception {

		Class.forName(driver);

		try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement()) {

			boolean hasResultSet = stmt.execute(sql);

			if (hasResultSet) {// 是否有查询结果

				try (ResultSet rs = stmt.getResultSet()) {// 结果集(一行一行的遍历)

					ResultSetMetaData rsmd = rs.getMetaData();// 结果集的元数据(就是第一行的列名称)

					int columnCount = rsmd.getColumnCount();// 获取列数
					
					System.out.println("结果集元数据: " + columnCount);

					for (int column = 0; column < columnCount; column++) {

						// System.out.println("结果集元数据: " +
						// rsmd.getColumnDisplaySize(column));
						// System.out.println("结果集元数据: " +
						// rsmd.getColumnLabel(column));
						// System.out.println("结果集元数据: " +
						// rsmd.getColumnType(column));
						System.out.println("结果集元数据: " + rsmd.getColumnName(column));
						// System.out.println("结果集元数据: " +
						// rsmd.getColumnTypeName(column));
//						System.out.println("结果集元数据: " + rsmd.getPrecision(column));
//						System.out.println("结果集元数据: " + rsmd.getCatalogName(column));
//						System.out.println("结果集元数据: " + rsmd.getTableName(column));
//						System.out.println("结果集元数据: " + rsmd.getScale(column));
//						System.out.println("结果集元数据: " + rsmd.getSchemaName(column));
						// System.out.println("结果集元数据: " +
						// rsmd.getColumnClassName(column));


					}

					while (rs.next()) {

						for (int i = 0; i < columnCount; i++) {

							System.out.print(rs.getString(i + 1) + "\t");
						}

						System.out.print("\n");
					}
				}
			} else {
				System.out.println("操作影响的行数: " + stmt.getUpdateCount());
			}
		}
	}

	public static void main(String[] args) throws Exception {

		ExecuteSql es = new ExecuteSql();

		es.initParam();
		es.executeSql("select * from yc_core.versioned_org where is_latest=1 and id=33");

		Locale locale = Locale.getDefault();

		System.out.println("------->" + locale.getLanguage());
		System.out.println("------->" + locale.getCountry());

		// es.executeSql("drop table if exists my_test");
		// System.out.println("------执锟叫斤拷锟斤拷锟紻DL锟斤拷锟�-----");
		// es.executeSql("create table my_test (test_id int auto_increment
		// primary key, test_name varchar(255))");
		// System.out.println("------执锟叫诧拷锟斤拷锟斤拷锟捷碉拷DML锟斤拷锟�-----");
		// es.executeSql("insert into my_test(test_name) select student_name
		// from student_table");
		// System.out.println("------执锟叫诧拷询锟斤拷锟捷的诧拷询锟斤拷锟�-----");
		// es.executeSql("select * from my_test");
	}
}
