package org.xiem.com.hibernate.object.metric.impressions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.xiem.com.hibernate.object.metric.MetricId;
import org.xiem.com.hibernate.object.metric.MetricValue;

@MappedSuperclass
abstract public class MetricImpressions extends MetricValue {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Double aggrStormMediaCost;
    private Long aggrStormImpressions;
    private Long aggrStormClicks;
    private Double aggrStormLastMediaCost;
    private Long aggrStormLastImpressions;
    private Long aggrStormLastClicks;
    private Double aggrHiveMediaCost;
    private Long aggrHiveImpressions;

    public MetricImpressions() {
    }

    public MetricImpressions(MetricId id) {
    	setId(id);
    }

    @Column(name = "aggr_storm_media_cost", precision = 11, scale = 3)
    public Double getAggrStormMediaCost() {
        return this.aggrStormMediaCost;
    }

    public MetricImpressions setAggrStormMediaCost(Double aggrStormMediaCost) {
        this.aggrStormMediaCost = aggrStormMediaCost;
        return this;
    }

    @Column(name = "aggr_storm_impressions")
    public Long getAggrStormImpressions() {
        return this.aggrStormImpressions;
    }

    public MetricImpressions setAggrStormImpressions(Long aggrStormImpressions) {
        this.aggrStormImpressions = aggrStormImpressions;
        return this;
    }

    @Column(name = "aggr_storm_clicks")
    public Long getAggrStormClicks() {
        return this.aggrStormClicks;
    }

    public MetricImpressions setAggrStormClicks(Long aggrStormClicks) {
        this.aggrStormClicks = aggrStormClicks;
        return this;
    }
    
    @Column(name = "aggr_storm_last_media_cost", precision = 11, scale = 3)
    public Double getAggrStormLastMediaCost() {
        return this.aggrStormLastMediaCost;
    }

    public MetricImpressions setAggrStormLastMediaCost(Double aggrStormMediaCost) {
        this.aggrStormLastMediaCost = aggrStormMediaCost;
        return this;
    }

    @Column(name = "aggr_storm_last_impressions")
    public Long getAggrStormLastImpressions() {
        return this.aggrStormLastImpressions;
    }

    public MetricImpressions setAggrStormLastImpressions(Long aggrStormImpressions) {
        this.aggrStormLastImpressions = aggrStormImpressions;
        return this;
    }

    @Column(name = "aggr_storm_last_clicks")
    public Long getAggrStormLastClicks() {
        return this.aggrStormLastClicks;
    }

    public MetricImpressions setAggrStormLastClicks(Long aggrStormClicks) {
        this.aggrStormLastClicks = aggrStormClicks;
        return this;
    }
    
    @Column(name = "aggr_hive_media_cost", precision = 11, scale = 3)
    public Double getAggrHiveMediaCost() {
        return this.aggrHiveMediaCost;
    }

    public MetricImpressions setAggrHiveMediaCost(Double aggrHiveMediaCost) {
        this.aggrHiveMediaCost = aggrHiveMediaCost;
        return this;
    }

    @Column(name = "aggr_hive_impressions")
    public Long getAggrHiveImpressions() {
        return this.aggrHiveImpressions;
    }

    public MetricImpressions setAggrHiveImpressions(Long aggrHiveImpressions) {
        this.aggrHiveImpressions = aggrHiveImpressions;
        return this;
    }

    @Override
    public void revertToStormLast() {
        aggrStormImpressions = aggrStormLastImpressions;
        aggrStormMediaCost = aggrStormLastMediaCost;
        aggrStormClicks = aggrStormLastClicks;
    }

    @Override
    public void saveStormToLast() {
        aggrStormLastMediaCost = aggrStormMediaCost;
        aggrStormLastImpressions = aggrStormImpressions;
        aggrStormLastClicks = aggrStormClicks;
    }

    @Entity
    @Table(name = "metric_impressions_day")
    static public class Day extends MetricImpressions {
        private static final long serialVersionUID = 1L;
    }
    @Entity
    @Table(name = "metric_impressions_hour")
    static public class Hour extends MetricImpressions {
        private static final long serialVersionUID = 1L;
    }
    @Entity
    @Table(name = "metric_impressions_minute")
    static public class Minute extends MetricImpressions {
        private static final long serialVersionUID = 1L;
    }
}
