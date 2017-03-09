package org.xiem.com.hibernate;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;

@SuppressWarnings("serial")
public abstract class AbstractVersionedWithIdGen implements Versioned {

	static final String IDGEN_TABLE_SUFFIX = "_idgen";

	protected abstract void setIdAsBigInt(final BigInteger bi);// 抽象函数

	public abstract int getId();// 抽象函数

	@SuppressWarnings({ "rawtypes", "unused" })
	public void setIdFromGenerator(final Session session) {// 假设在一个事务中调用

		ClassMetadata data = session.getSessionFactory().getClassMetadata(this.getClass());// 获取之类的元数据信息

		assert (data instanceof SingleTableEntityPersister);

		SingleTableEntityPersister tableData = (SingleTableEntityPersister) data;

		StringBuilder querySb = new StringBuilder();

		querySb.append("insert into ").append(tableData.getTableName())// 获取表的名称
				.append(IDGEN_TABLE_SUFFIX).append("() values()");

		int a = session.createSQLQuery(querySb.toString()).executeUpdate();// 在IDGEN表中执行插入语句

		SQLQuery query = session.createSQLQuery("SELECT LAST_INSERT_ID() limit 1");// 获取上一步中插入语句生成的ID

		List list = query.list();

		Object result = list.get(0);

		assert (result instanceof BigInteger);

		setIdAsBigInt((BigInteger) result);// 设置ID
	}

}
