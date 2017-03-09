package org.xiem.com.hibernate.object;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;

@SuppressWarnings("serial") //
public abstract class AbstractVersionedWithIdGen implements Versioned {

	static final String IDGEN_TABLE_SUFFIX = "_idgen";// 该表对应的ID外键表的后缀

    protected abstract void setIdAsBigInt(final BigInteger bi);

    public abstract int getId();

	@SuppressWarnings("rawtypes")
    public void setIdFromGenerator(final Session session) {//suggested to be called within a transaction.
        
		ClassMetadata data = session.getSessionFactory().getClassMetadata(this.getClass());

		System.out.println("获取对应类的元数据: " + data.getEntityName());
		System.out.println("获取对应类的元数据: " + data.getIdentifierPropertyName());
		System.out.println("获取对应类的元数据: " + data.getVersionProperty());
		System.out.println("获取对应类的元数据: " + data);

		/***********************************************************************
		 * 获取对应类的元数据: org.xiem.com.hibernate.object.VersionedAdvertisement
		 * 
		 * 获取对应类的元数据: versionId
		 * 
		 * 获取对应类的元数据: -66
		 * 
		 * 获取对应类的元数据: SingleTableEntityPersister(org.xiem.com.hibernate.object.
		 * VersionedAdvertisement)
		 **************************************************************************/

		assert (data instanceof SingleTableEntityPersister);
        
		SingleTableEntityPersister tableData = (SingleTableEntityPersister) data;
		
		System.out.println("获取对应类的元数据: " + tableData.getTableName());
		System.out.println("获取对应类的元数据: " + tableData.getTableSpan());
		System.out.println("获取对应类的元数据: " + tableData.getVersionProperty());
		System.out.println("获取对应类的元数据: " + tableData);

		StringBuilder querySb = new StringBuilder();
        
		querySb.append("insert into ").append(tableData.getTableName()).append(IDGEN_TABLE_SUFFIX)
				.append("() values()");// 对应的ID生成表

		int a = session.createSQLQuery(querySb.toString()).executeUpdate();// 返回的值是操作影响的行数
        
		System.out.println("操作影响的函数: " + a);

		SQLQuery query = session.createSQLQuery("SELECT LAST_INSERT_ID() limit 1");//

		List list = query.list();// 各个列对象
        
		Object result = list.get(0);
		
		assert (result instanceof BigInteger);
        
		setIdAsBigInt((BigInteger) result);
    }

	public long getVersionId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setVersionId(long revisionId) {
		// TODO Auto-generated method stub

	}

	public int getRevisionUserId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setRevisionUserId(int id) {
		// TODO Auto-generated method stub

	}

	public Date getRevisionTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRevisionTime(Date time) {
		// TODO Auto-generated method stub

	}

	public String getRevisionComment() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRevisionComment(String comment) {
		// TODO Auto-generated method stub

	}

	public boolean getIsDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIsDeleted(boolean deleted) {
		// TODO Auto-generated method stub

	}

	public boolean getIsLatest() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIsLatest(boolean isLatest) {
		// TODO Auto-generated method stub

	}

	public boolean getIsEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setIsEnabled(boolean isEnabled) {
		// TODO Auto-generated method stub

	}

}
