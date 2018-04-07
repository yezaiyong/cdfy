package com.bsco.app.model;
// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OrderBy;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * ActivitiesPeriod entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="activities_period")

public class ActivitiesPeriod  implements java.io.Serializable {


    // Fields    

     private Integer activitiesPeriodId;
     private ProActivities proActivities;
     private String fileId;
     private String activitiesPeriodNum;
     private Set<ActivitiesContent> activitiesContents = new HashSet<ActivitiesContent>(0);
     private Set<Album> albums = new HashSet<Album>(0);


    // Constructors

    /** default constructor */
    public ActivitiesPeriod() {
    }

    
    /** full constructor */
    public ActivitiesPeriod(ProActivities proActivities, String fileId, String activitiesPeriodNum, Set<ActivitiesContent> activitiesContents, Set<Album> albums) {
        this.proActivities = proActivities;
        this.fileId = fileId;
        this.activitiesPeriodNum = activitiesPeriodNum;
        this.activitiesContents = activitiesContents;
        this.albums = albums;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="ACTIVITIES_PERIOD_ID", unique=true, nullable=false)
    public Integer getActivitiesPeriodId() {
        return this.activitiesPeriodId;
    }
    
    public void setActivitiesPeriodId(Integer activitiesPeriodId) {
        this.activitiesPeriodId = activitiesPeriodId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="PRO_ACTIVITIES_ID")

    public ProActivities getProActivities() {
        return this.proActivities;
    }
    
    public void setProActivities(ProActivities proActivities) {
        this.proActivities = proActivities;
    }

    @Column(name="FILE_ID", length=250)    
    public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
    
    @Column(name="ACTIVITIES_PERIOD_NUM", length=50)

    public String getActivitiesPeriodNum() {
        return this.activitiesPeriodNum;
    }
    
	public void setActivitiesPeriodNum(String activitiesPeriodNum) {
        this.activitiesPeriodNum = activitiesPeriodNum;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="activitiesPeriod")
@OrderBy("createDate desc")
    public Set<ActivitiesContent> getActivitiesContents() {
        return this.activitiesContents;
    }
    
    public void setActivitiesContents(Set<ActivitiesContent> activitiesContents) {
        this.activitiesContents = activitiesContents;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="activitiesPeriod")
@OrderBy("creavateDate desc")
    public Set<Album> getAlbums() {
        return this.albums;
    }
    
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }
   








}