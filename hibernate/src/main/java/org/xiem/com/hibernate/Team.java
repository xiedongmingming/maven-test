package org.xiem.com.hibernate;

import java.io.Serializable;
import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
//是javax.persistence.Entity不是hibernate的hibernate.Entity
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
import org.hibernate.annotations.GenericGenerator;
 
 
@Entity
@Table(name = "t_team")
public class Team implements Serializable{
   
    @Override
	public String toString() {
		return "Team [id=" + id + ", customerId=" + customerId
				+ ", createTime=" + createTime + ", name=" + name + ", enName="
				+ enName + "]";
	}

	private static final long serialVersionUID = -6404879288222828448L;
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "id")
    private int id;//团队主键
   
     @Column(name = "customerId", nullable = true)
     private int customerId;//创建人客户主键
    
     @Column(name = "createTime", nullable = true)
     @Temporal(TemporalType.TIMESTAMP)
     private Date createTime;//创建时间
   
    @Column(name = "name", length = 100, nullable = true)
    private String name;//团队名称
   
    @Column(name = "enName", length = 32, nullable = true)
    private String enName;//团队名称
 
    public int getId() {
       return id;
    }
 
    public void setId(int id) {
       this.id = id;
    }
 
    public int getCustomerId() {
       return customerId;
    }
 
    public void setCustomerId(int customerId) {
       this.customerId = customerId;
    }
 
    public Date getCreateTime() {
       return createTime;
    }
 
    public void setCreateTime(Date createTime) {
       this.createTime = createTime;
    }
 
    public String getName() {
       return name;
    }
 
    public void setName(String name) {
       this.name = name;
    }
 
    public String getEnName() {
       return enName;
    }
 
    public void setEnName(String enName) {
       this.enName = enName;
    }
}