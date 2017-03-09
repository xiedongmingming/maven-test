package org.xiem.com.hibernate.object.metric;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class MetricValue implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MetricId id;
    private Date aggrStormModtime;
    private Date aggrHiveModtime;
    private Long stormTxId;

    @EmbeddedId
    public MetricId getId() {
        return this.id;
    }

    public void setId(MetricId id) {
        this.id = id;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "aggr_storm_modtime", length = 19)
    public Date getAggrStormModtime() {
        return this.aggrStormModtime;
    }
    
    public void setAggrStormModtime(Date aggrStormModtime) {
        this.aggrStormModtime = aggrStormModtime;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "aggr_hive_modtime", length = 19)
    public Date getAggrHiveModtime() {
        return this.aggrHiveModtime;
    }

    public void setAggrHiveModtime(Date aggrHiveModtime) {
        this.aggrHiveModtime = aggrHiveModtime;
    }

    @Column(name = "storm_txid")
    public Long getStormTxId() {
        return stormTxId;
    }

    public void setStormTxId(long id) {
        stormTxId = id;
    }

    
	abstract public void revertToStormLast();
	abstract public void saveStormToLast();
}
