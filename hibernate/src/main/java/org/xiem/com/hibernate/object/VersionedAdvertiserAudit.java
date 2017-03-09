package org.xiem.com.hibernate.object;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "versionedassoc_advertiser_audit")
public class VersionedAdvertiserAudit extends AbstractVersionedWithIdGen {

    private static final long serialVersionUID = 5408945009936085619L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id")
    private long versionId;
    @Override
    public long getVersionId() {
        return versionId;
    }
    @Override
    public void setVersionId(final long revisionId) {
        this.versionId = revisionId;
    }

	
	
    @Column(name = "revision_user_id", nullable = false)
    private int revisionUserId;
    @Override
    public int getRevisionUserId() {
        return this.revisionUserId;
    }
    @Override
    public void setRevisionUserId(final int id) {
        this.revisionUserId = id;
    }

	
	
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "revision_time", nullable = false)
    private Date revisionTime;
    @Override
    public Date getRevisionTime() {
        return this.revisionTime;
    }
    @Override
    public void setRevisionTime(final Date time) {
        this.revisionTime = time;
    }

	
	
    @Column(name = "revision_comment")
    private String revisionComment;
    @Override
    public String getRevisionComment() {
        return this.revisionComment;
    }
    @Override
    public void setRevisionComment(final String comment) {
        this.revisionComment = comment;
    }

	
	
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Override
    public boolean getIsDeleted() {
        return this.isDeleted;
    }
    @Override
    public void setIsDeleted(final boolean deleted) {
        this.isDeleted = deleted;
    }

	
	
    @Column(name = "is_latest", nullable = false)
    private boolean isLatest;
    @Override
    public boolean getIsLatest() {
        return this.isLatest;
    }
    @Override
    public void setIsLatest(final boolean isLatest) {
        this.isLatest = isLatest;
    }
	
	
	
    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;
    @Override
    public boolean getIsEnabled() {
        return this.isEnabled;
    }
    @Override
    public void setIsEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

	
	
    @Column(name = "id", nullable = false)
	private int id;
    @Override
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    protected void setIdAsBigInt(BigInteger bi) {
        this.id = bi.intValue();
    }

	
	
    @Column(name = "advertiser_id", nullable = false)
    private int advertiserId;
    public int getAdvertiserId() {
        return advertiserId;
    }
    public void setAdvertiserId(final int advertiserId) {
        this.advertiserId = advertiserId;
    }

	
	
    @Column(name = "exchange_id", nullable = false)
    private int exchangeId;
    public int getExchangeId() {
        return exchangeId;
    }
    public void setExchangeId(final int exchangeId) {
        this.exchangeId = exchangeId;
    }

	
	
    @Column(name = "audit_status", nullable = false)
    private short auditStatusCode;
    public short getAuditStatusCode() {
        return auditStatusCode;
    }
    public void setAuditStatusCode(short code) {
        auditStatusCode = code;
    }
    public AuditStatus getAuditStatus() {
        return AuditStatus.getStatusFromCode(auditStatusCode);
    }
    public void setAuditStatus(final AuditStatus auditStatus) {
        setAuditStatusCode(auditStatus.code);
    }

	public VersionedAdvertiserAudit() {

	}

	@Override
	public String toString() {
		return "VersionedAdvertiserAudit [versionId=" + versionId + ", revisionUserId=" + revisionUserId
				+ ", revisionTime=" + revisionTime + ", revisionComment=" + revisionComment + ", isDeleted=" + isDeleted
				+ ", isLatest=" + isLatest + ", isEnabled=" + isEnabled + ", id=" + id + ", advertiserId="
				+ advertiserId + ", exchangeId=" + exchangeId + ", auditStatusCode=" + auditStatusCode + "]";
	}
	public VersionedAdvertiserAudit(VersionedAdvertiserAudit audit) {
		this.setAdvertiserId(audit.getAdvertiserId());
		this.setAuditStatusCode(audit.getAuditStatusCode());
		this.setExchangeId(audit.getExchangeId());
		this.setIsDeleted(audit.getIsDeleted());
		this.setId(audit.getId());
		this.setIsEnabled(audit.getIsEnabled());
		this.setRevisionComment(audit.getRevisionComment());
		this.setRevisionTime(new Date());
		this.setRevisionUserId(audit.getRevisionUserId());
	}
}
