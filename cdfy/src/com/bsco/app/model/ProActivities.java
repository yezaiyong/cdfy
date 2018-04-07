package com.bsco.app.model;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


/**
 * ProActivities entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="pro_activities"
     
)

public class ProActivities  implements java.io.Serializable {


    // Fields    

     private Integer proActivitiesId;
     private String proActivitiesName;
     private Date createDate;
     private String createId;
     private Set<ActivitiesPeriod> activitiesPeriods = new HashSet<ActivitiesPeriod>(0);


    // Constructors

    /** default constructor */
    public ProActivities() {
    }

    
    /** full constructor */
    public ProActivities(String proActivitiesName, Date createDate, String createId, Set<ActivitiesPeriod> activitiesPeriods) {
        this.proActivitiesName = proActivitiesName;
        this.createDate = createDate;
        this.createId = createId;
        this.activitiesPeriods = activitiesPeriods;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="PRO_ACTIVITIES_ID", unique=true, nullable=false)

    public Integer getProActivitiesId() {
        return this.proActivitiesId;
    }
    
    public void setProActivitiesId(Integer proActivitiesId) {
        this.proActivitiesId = proActivitiesId;
    }
    
    @Column(name="PRO_ACTIVITIES_NAME", length=50)

    public String getProActivitiesName() {
        return this.proActivitiesName;
    }
    
    public void setProActivitiesName(String proActivitiesName) {
        this.proActivitiesName = proActivitiesName;
    }
    
    @Column(name="CREATE_DATE", length=19)

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="CREATE_ID", length=50)

    public String getCreateId() {
        return this.createId;
    }
    
    public void setCreateId(String createId) {
        this.createId = createId;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="proActivities")
@OrderBy("activitiesPeriodId")
    public Set<ActivitiesPeriod> getActivitiesPeriods() {
        return this.activitiesPeriods;
    }
    
    public void setActivitiesPeriods(Set<ActivitiesPeriod> activitiesPeriods) {
        this.activitiesPeriods = activitiesPeriods;
    }
   








}