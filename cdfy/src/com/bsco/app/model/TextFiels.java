package com.bsco.app.model;
// default package

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * TextFiels entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "text_fiels")
public class TextFiels implements java.io.Serializable {

	// Fields

	private Integer textFilesId;
	private String textFilesName;
	private String type;
	private String state;
	private String gard;
	private Date createDate =new Date();
	private String createId;
	private String department;
	private String category;
	private String path;
	

	// Constructors

	@Column(name = "PATH", length = 50)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/** default constructor */
	public TextFiels() {
	}
	
	@Column(name = "DEPARTMENT", length = 50)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "CATEGORY", length = 50)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	/** full constructor */
	public TextFiels(String textFilesName, String type, String state,
			String gard, Date createDate, String createId) {
		this.textFilesName = textFilesName;
		this.type = type;
		this.state = state;
		this.gard = gard;
		this.createDate = createDate;
		this.createId = createId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TEXT_FILES_ID", unique = true, nullable = false)
	public Integer getTextFilesId() {
		return this.textFilesId;
	}

	public void setTextFilesId(Integer textFilesId) {
		this.textFilesId = textFilesId;
	}

	@Column(name = "TEXT_FILES_NAME", length = 50)
	public String getTextFilesName() {
		return this.textFilesName;
	}

	public void setTextFilesName(String textFilesName) {
		this.textFilesName = textFilesName;
	}

	@Column(name = "TYPE", length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "STATE", length = 10)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "GARD", length = 50)
	public String getGard() {
		return this.gard;
	}

	public void setGard(String gard) {
		this.gard = gard;
	}
	
	@Temporal(TemporalType.DATE)
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