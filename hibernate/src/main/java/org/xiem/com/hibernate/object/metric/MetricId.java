package org.xiem.com.hibernate.object.metric;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class MetricId implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int campaignId;
    private int lineId;
    private int tacticId;
    private int adId;
    private int placementId;
    private int publisherId;
    private Date date;

    public MetricId() {
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, length = 19)
    public Date getDate() {
        return date;
    }

    public MetricId setDate(Date date) {
        this.date = date;
        return this;
    }

    @Column(name = "campaign_id", nullable = false)
    public int getCampaignId() {
        return campaignId;
    }

    public MetricId setCampaignId(int campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    @Column(name = "line_id", nullable = false)
    public int getLineId() {
        return lineId;
    }

    public MetricId setLineId(int lineId) {
        this.lineId = lineId;
        return this;
    }

    @Column(name = "tactic_id", nullable = false)
    public int getTacticId() {
        return tacticId;
    }

    public MetricId setTacticId(int tacticId) {
        this.tacticId = tacticId;
        return this;
    }

    @Column(name = "ad_id", nullable = false)
    public int getAdId() {
        return adId;
    }

    public MetricId setAdId(int adId) {
        this.adId = adId;
        return this;
    }

    @Column(name = "placement_id", nullable = false)
    public int getPlacementId() {
        return placementId;
    }

    public MetricId setPlacementId(int placementId) {
        this.placementId = placementId;
        return this;
    }

    @Column(name = "publisher_id", nullable = false)
    public int getPublisherId() {
        return publisherId;
    }

    public MetricId setPublisherId(int publisherId) {
        this.publisherId = publisherId;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + adId;
        result = prime * result + campaignId;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + lineId;
        result = prime * result + placementId;
        result = prime * result + publisherId;
        result = prime * result + tacticId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetricId other = (MetricId) obj;
        if (adId != other.adId)
            return false;
        if (campaignId != other.campaignId)
            return false;
        if (lineId != other.lineId)
            return false;
        if (placementId != other.placementId)
            return false;
        if (publisherId != other.publisherId)
            return false;
        if (tacticId != other.tacticId)
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }
}