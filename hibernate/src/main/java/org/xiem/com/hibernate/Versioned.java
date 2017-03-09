package org.xiem.com.hibernate;

import java.io.Serializable;
import java.util.Date;

public interface Versioned extends Serializable {// 七个公共字段--AbstractVersionedWithIdGen

	// 实现的接口是一个标识接口(用于指示编译器对该接口实现类完成序列化和反序列化处理)

	long getVersionId();

	void setVersionId(final long id);

	boolean getIsDeleted();

	void setIsDeleted(final boolean deleted);

	boolean getIsLatest();

	void setIsLatest(final boolean isLatest);

	boolean getIsEnabled();

	void setIsEnabled(final boolean isEnabled);

	int getRevisionUserId();

	void setRevisionUserId(final int id);

	String getRevisionComment();

	void setRevisionComment(final String comment);

	Date getRevisionTime();

	void setRevisionTime(final Date time);
}