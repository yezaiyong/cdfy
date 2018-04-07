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
 * ActivitiesContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "activities_content")
public class ActivitiesContent implements java.io.Serializable {

	// Fields

	private Integer activitiesContentId;
	private ActivitiesPeriod activitiesPeriod;
	private String activitiesContentTitle;
	private String activitiesContentDesc;
	private Date createDate;
	private String createId;

	// Constructors

	/** default constructor */
	public ActivitiesContent() {
	}

	/** full constructor */
	public ActivitiesContent(ActivitiesPeriod activitiesPeriod,
			String activitiesContentTitle, String activitiesContentDesc,
			Date createDate,String createId) {
		this.activitiesPeriod = activitiesPeriod;
		this.activitiesContentTitle = activitiesContentTitle;
		this.activitiesContentDesc = activitiesContentDesc;
		this.createDate = createDate;
		this.createId = createId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ACTIVITIES_CONTENT_ID", unique = true, nullable = false)
	public Integer getActivitiesContentId() {
		return this.activitiesContentId;
	}

	public void setActivitiesContentId(Integer activitiesContentId) {
		this.activitiesContentId = activitiesContentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACTIVITIES_PERIOD_ID")
	public ActivitiesPeriod getActivitiesPeriod() {
		return this.activitiesPeriod;
	}

	public void setActivitiesPeriod(ActivitiesPeriod activitiesPeriod) {
		this.activitiesPeriod = activitiesPeriod;
	}

	@Column(name = "ACTIVITIES_CONTENT_TITLE", length = 50)
	public String getActivitiesContentTitle() {
		return this.activitiesContentTitle;
	}

	public void setActivitiesContentTitle(String activitiesContentTitle) {
		this.activitiesContentTitle = activitiesContentTitle;
	}

	@Column(name = "ACTIVITIES_CONTENT_DESC")
	public String getActivitiesContentDesc() {
		return this.activitiesContentDesc;
	}

	public void setActivitiesContentDesc(String activitiesContentDesc) {
		this.activitiesContentDesc = activitiesContentDesc;
	}

	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "CREATE_ID", length = 50)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

}