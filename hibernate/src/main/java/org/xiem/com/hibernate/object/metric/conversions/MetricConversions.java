package org.xiem.com.hibernate.object.metric.conversions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.xiem.com.hibernate.object.metric.MetricId;
import org.xiem.com.hibernate.object.metric.MetricValue;

@MappedSuperclass
abstract public class MetricConversions extends MetricValue {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Double aggrStormConversions;
    private Double aggrStormLastConversions;
    private Double aggrHiveConversions;

    public MetricConversions() {
    }

    public MetricConversions(MetricId id) {
        setId(id);
    }

    @Column(name = "aggr_storm_conversions")
    public Double getAggrStormConversions() {
        return this.aggrStormConversions;
    }

    public MetricConversions setAggrStormConversions(Double aggrStormConversions) {
        this.aggrStormConversions = aggrStormConversions;
        return this;
    }

    @Column(name = "aggr_storm_last_conversions")
    public Double getAggrStormLastConversions() {
        return this.aggrStormLastConversions;
    }

    public MetricConversions setAggrStormLastConversions(Double aggrStormConversions) {
        this.aggrStormLastConversions = aggrStormConversions;
        return this;
    }

    @Column(name = "aggr_hive_conversions")
    public Double getAggrHiveConversions() {
        return this.aggrHiveConversions;
    }

    public MetricConversions setAggrHiveConversions(Double aggrHiveConversions) {
        this.aggrHiveConversions = aggrHiveConversions;
        return this;
    }

    @Override
    public void revertToStormLast() {
        aggrStormConversions = aggrStormLastConversions;
    }

    @Override
    public void saveStormToLast() {
        aggrStormLastConversions = aggrStormConversions;
    }

    static public class ByConversionTime {
        @Entity
        @Table(name = "metric_conversions_ct_day")
        static public class Day extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
        @Entity
        @Table(name = "metric_conversions_ct_hour")
        static public class Hour extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
        @Entity
        @Table(name = "metric_conversions_ct_minute")
        static public class Minute extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
    }

    static public class ByImpressionTime {
        @Entity
        @Table(name = "metric_conversions_it_day")
        static public class Day extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
        @Entity
        @Table(name = "metric_conversions_it_hour")
        static public class Hour extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
        @Entity
        @Table(name = "metric_conversions_it_minute")
        static public class Minute extends MetricConversions {
            private static final long serialVersionUID = 1L;
        }
    }
}
