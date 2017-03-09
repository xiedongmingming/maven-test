package org.xiem.com.hibernate.object;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "versioned_advertisement")
public class VersionedAdvertisement extends AbstractVersionedWithIdGen implements Versioned {

	private static final long serialVersionUID = 1329800105299112305L;
    
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

    @Temporal(value = TemporalType.TIMESTAMP)//时间类型
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
    protected void setIdAsBigInt(BigInteger bi) {//自动生成的ID
        this.id = bi.intValue();
    }

    @Column(name = "advertiser_id", nullable = false)
    private int advertiserId;
    public int getAdvertiserId() {
        return this.advertiserId;
    }
    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    @Column(name = "name", nullable = false)
    private String name;
    public void setName(final String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Column(name = "max_daily_budget", nullable = true)
    private Double maxDailyBudget;
    public Double getMaxDailyBudget() {
        return maxDailyBudget;
    }
    public void setMaxDailyBudget(final Double maxDailyBudget) {
        this.maxDailyBudget = maxDailyBudget;
    }

    @Column(name = "width", nullable = false)
    private int width;
    public int getWidth() {
        return this.width;
    }
    public void setWidth(final int width) {
        this.width = width;
    }

    @Column(name = "height", nullable = false)
    private int height;
    public int getHeight() {
        return this.height;
    }
    public void setHeight(final int height) {
        this.height = height;
    }

    @Column(name = "ad_type", nullable = false)
    private short typeCode;
    public short getTypeCode() {
        return this.typeCode;
    }
    public void setTypeDbCode(final short typeCode) {
        this.typeCode = typeCode;
    }

    @Column(name = "has_iframe", nullable = false)
    private boolean hasIframe;
    public boolean getHasIframe() {
        return this.hasIframe;
    }
    public void setHasIframe(final boolean hasIframe) {
        this.hasIframe = hasIframe;
    }

    @Lob
    @Column(name = "landing_url", nullable = true)
    private String landingUrl;
    public String getLandingUrl() {
        return landingUrl;
    }
    public void setLandingUrl(final String landingUrl) {
        this.landingUrl = landingUrl;
    }

    @Lob
    @Column(name = "content_url", nullable = true)
    private String contentUrl;
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(final String contentUrl) {
        this.contentUrl = contentUrl;
    }

    @Lob
    @Column(name = "visible_url", nullable = true)
    private String visibleUrl;
    public String getVisibleUrl() {
        return visibleUrl;
    }
    public void setVisibleUrl(final String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    @Lob
    @Column(name = "click_through_url", nullable = true)
    private String clickThroughUrl;
    public String getClickThroughUrl() {
        return clickThroughUrl;
    }
    public void setClickThroughUrl(final String clickThroughUrl) {
        this.clickThroughUrl = clickThroughUrl;
    }

    @Lob
    @Column(name = "impression_tracking_url", nullable = true)
    private String impressionTrackingUrl;
    public String getImpressionTrackingUrl() {
        return impressionTrackingUrl;
    }
    public void setImpressionTrackingUrl(final String impressionTrackingUrl) {
        this.impressionTrackingUrl = impressionTrackingUrl;
    }

    @Lob
    @Column(name = "click_tracking_url", nullable = true)
    private String clickTrackingUrl;
    public String getClickTrackingUrl() {
        return clickTrackingUrl;
    }
    public void setClickTrackingUrl(final String clickTrackingUrl) {
        this.clickTrackingUrl = clickTrackingUrl;
    }

	public VersionedAdvertisement() {
		super();
	}

	@Column(name = "storage", nullable = false)
    private short storageTypeCode;
    public short getStorageTypeCode() {
        return this.storageTypeCode;
    }
    public void setStorageTypeCode(final short code) {
        this.storageTypeCode = code;
    }	
    public AdStorageType getStorageType() {//解析出对应的类型
        return AdStorageType.fromDbCode(this.storageTypeCode);
    }
}
