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
import javax.persistence.Table;

/**
 * SaveItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "save_item")
public class SaveItem implements java.io.Serializable {

	// Fields

	private Integer itemId;
	private String itemName;
	private String type;
	private String batch;
	private String area;
	private Date createDate =new Date();
	private Set<SaveItemFiles> saveItemFileses = new HashSet<SaveItemFiles>(0);

	// Constructors

	/** default constructor */
	public SaveItem() {
	}

	/** minimal constructor */
	public SaveItem(Integer itemId) {
		this.itemId = itemId;
	}

	/** full constructor */
	public SaveItem(Integer itemId, String itemName, String type, String batch,
			String area, Set<SaveItemFiles> saveItemFileses,Date createDate) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.type = type;
		this.batch = batch;
		this.area = area;
	    this.createDate= createDate;
		this.saveItemFileses = saveItemFileses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ITEM_ID", unique = true, nullable = false)
	public Integer getItemId() {
		return this.itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	@Column(name = "ITEM_NAME", length = 50)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "TYPE", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "BATCH", length = 50)
	public String getBatch() {
		return this.batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column(name = "AREA", length = 50)
	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date date) {
		this.createDate = date;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "saveItem")
	public Set<SaveItemFiles> getSaveItemFileses() {
		return this.saveItemFileses;
	}

	public void setSaveItemFileses(Set<SaveItemFiles> saveItemFileses) {
		this.saveItemFileses = saveItemFileses;
	}

}