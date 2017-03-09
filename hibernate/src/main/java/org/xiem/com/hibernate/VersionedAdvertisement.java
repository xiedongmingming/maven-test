package org.xiem.com.hibernate;

import java.math.BigInteger;
import java.util.Date;

public class VersionedAdvertisement extends AbstractVersionedWithIdGen implements Versioned {

	private static final long serialVersionUID = 6160004233724661855L;

	@Override
	public long getVersionId() {
		return 0;
	}
	@Override
	public void setVersionId(long id) {

	}
	@Override
	public boolean getIsDeleted() {
		return false;
	}
	@Override
	public void setIsDeleted(boolean deleted) {

	}
	@Override
	public boolean getIsLatest() {
		return false;
	}
	@Override
	public void setIsLatest(boolean isLatest) {

	}
	@Override
	public boolean getIsEnabled() {
		return false;
	}
	@Override
	public void setIsEnabled(boolean isEnabled) {

	}
	@Override
	public int getRevisionUserId() {
		return 0;
	}
	@Override
	public void setRevisionUserId(int id) {

	}
	@Override
	public String getRevisionComment() {
		return null;
	}
	@Override
	public void setRevisionComment(String comment) {

	}
	@Override
	public Date getRevisionTime() {
		return null;
	}
	@Override
	public void setRevisionTime(Date time) {

	}

	// **********************************************************************************
	// 下面两个方法是抽象类中的方式重写
	@Override
	protected void setIdAsBigInt(BigInteger bi) {

	}
	@Override
	public int getId() {
		return 0;
	}
	// **********************************************************************************
}
