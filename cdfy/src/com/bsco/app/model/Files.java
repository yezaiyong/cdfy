package com.bsco.app.model;
// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Files entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "files")
public class Files implements java.io.Serializable {

	// Fields

	private Integer fileId;
	private String fileUrl;
	private Date createDate;
	private String createId;
	private String fileName;
	private String type;
	private String businessType;
	private Integer deputyPersonId;
	private Set<SaveItemFiles> saveItemFileses = new HashSet<SaveItemFiles>(0);
	private Set<ItemApplyFiles> itemApplyFileses = new HashSet<ItemApplyFiles>(
			0);
	private Set<PersonApplyFiles> personApplyFileses = new HashSet<PersonApplyFiles>(
			0);
	private Set<UnitApplyFiles> unitApplyFileses = new HashSet<UnitApplyFiles>(
			0);

	// Constructors

	/** default constructor */
	public Files() {
	}

	/** full constructor */
	public Files(String fileUrl, Date createDate, String createId,String fileName,
			String type, String businessType, 
			Set<SaveItemFiles> saveItemFileses,
			Set<ItemApplyFiles> itemApplyFileses,
			Set<PersonApplyFiles> personApplyFileses,
			Set<UnitApplyFiles> unitApplyFileses) {
		this.fileUrl = fileUrl;
		this.fileName =fileName;
		this.createDate = createDate;
		this.createId = createId;
		this.type = type;
		this.businessType = businessType;
		this.saveItemFileses = saveItemFileses;
		this.itemApplyFileses = itemApplyFileses;
		this.personApplyFileses = personApplyFileses;
		this.unitApplyFileses = unitApplyFileses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FILE_ID", unique = true, nullable = false)
	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
	
	@Column(name = "DEPUTY_PERSON_ID", length = 255)
	public Integer getDeputyPersonId() {
		return deputyPersonId;
	}

	public void setDeputyPersonId(Integer deputyPersonId) {
		this.deputyPersonId = deputyPersonId;
	}

	@Column(name = "FILE_NAME", length = 100)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "FILE_URL", length = 200)
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "BUSINESS_TYPE", length = 50)
	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "files")
	public Set<SaveItemFiles> getSaveItemFileses() {
		return this.saveItemFileses;
	}

	public void setSaveItemFileses(Set<SaveItemFiles> saveItemFileses) {
		this.saveItemFileses = saveItemFileses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "files")
	public Set<ItemApplyFiles> getItemApplyFileses() {
		return this.itemApplyFileses;
	}

	public void setItemApplyFileses(Set<ItemApplyFiles> itemApplyFileses) {
		this.itemApplyFileses = itemApplyFileses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "files")
	public Set<PersonApplyFiles> getPersonApplyFileses() {
		return this.personApplyFileses;
	}

	public void setPersonApplyFileses(Set<PersonApplyFiles> personApplyFileses) {
		this.personApplyFileses = personApplyFileses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "files")
	public Set<UnitApplyFiles> getUnitApplyFileses() {
		return this.unitApplyFileses;
	}

	public void setUnitApplyFileses(Set<UnitApplyFiles> unitApplyFileses) {
		this.unitApplyFileses = unitApplyFileses;
	}

}