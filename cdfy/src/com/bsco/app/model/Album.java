package com.bsco.app.model;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Album entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="album"
     
)

public class Album  implements java.io.Serializable {


    // Fields    

     private Integer albumId;
     private String fileId;
     private ActivitiesPeriod activitiesPeriod;
     private String albumName;
     private Date creavateDate;
     private String createId;


    // Constructors

    /** default constructor */
    public Album() {
    }

    
    /** full constructor */
    public Album(String fileId, ActivitiesPeriod activitiesPeriod, String albumName, Date creavateDate, String createId) {
        this.fileId = fileId;
        this.activitiesPeriod = activitiesPeriod;
        this.albumName = albumName;
        this.creavateDate = creavateDate;
        this.createId = createId;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ALBUM_ID", unique=true, nullable=false)

    public Integer getAlbumId() {
        return this.albumId;
    }
    
    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }
    
    @Column(name="FILE_ID", length=250)    
    public String getFileId() {
		return fileId;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
    
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="ACTIVITIES_PERIOD_ID")

    public ActivitiesPeriod getActivitiesPeriod() {
        return this.activitiesPeriod;
    }
    
	public void setActivitiesPeriod(ActivitiesPeriod activitiesPeriod) {
        this.activitiesPeriod = activitiesPeriod;
    }
    
    @Column(name="ALBUM_NAME", length=50)

    public String getAlbumName() {
        return this.albumName;
    }
    
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    
    @Column(name="CREAVATE_DATE", length=19)

    public Date getCreavateDate() {
        return this.creavateDate;
    }
    
    public void setCreavateDate(Date creavateDate) {
        this.creavateDate = creavateDate;
    }
    
    @Column(name="CREATE_ID", length=50)

    public String getCreateId() {
        return this.createId;
    }
    
    public void setCreateId(String createId) {
        this.createId = createId;
    }
   








}