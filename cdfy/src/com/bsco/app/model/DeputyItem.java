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
 * DeputyItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deputy_item")
public class DeputyItem implements java.io.Serializable {

	// Fields

	private Integer deputyItemId;
	private String deputyItemName;
	private String deputyItemType;
	private String deputyItemGrade;
	private String deputyItemNo;
	private String deputyItemNumber;
	private String declarationBatch;
	private String itemAddress;
	private String protectionUnit;
	private String remark;
	private Date createDate;
	private String createId;
	private Set<DeputyUnit> deputyUnits = new HashSet<DeputyUnit>(0);
	private Set<DeputyPerson> deputyPersons = new HashSet<DeputyPerson>(0);
	private Set<ItemApply> itemApplies = new HashSet<ItemApply>(0);

	// Constructors

	/** default constructor */
	public DeputyItem() {
	}

	/** full constructor */
	public DeputyItem(String deputyItemName, String deputyItemType,
			String deputyItemGrade, String deputyItemNo,
			String declarationBatch, String itemAddress, String protectionUnit,
			String remark, Date createDate, String createId,
			Set<DeputyUnit> deputyUnits, Set<DeputyPerson> deputyPersons,
			Set<ItemApply> itemApplies) {
		this.deputyItemName = deputyItemName;
		this.deputyItemType = deputyItemType;
		this.deputyItemGrade = deputyItemGrade;
		this.deputyItemNo = deputyItemNo;
		this.declarationBatch = declarationBatch;
		this.itemAddress = itemAddress;
		this.protectionUnit = protectionUnit;
		this.remark = remark;
		this.createDate = createDate;
		this.createId = createId;
		this.deputyUnits = deputyUnits;
		this.deputyPersons = deputyPersons;
		this.itemApplies = itemApplies;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DEPUTY_ITEM_ID", unique = true, nullable = false)
	public Integer getDeputyItemId() {
		return this.deputyItemId;
	}

	public void setDeputyItemId(Integer deputyItemId) {
		this.deputyItemId = deputyItemId;
	}

	@Column(name = "DEPUTY_ITEM_NAME", length = 50)
	public String getDeputyItemName() {
		return this.deputyItemName;
	}

	public void setDeputyItemName(String deputyItemName) {
		this.deputyItemName = deputyItemName;
	}

	@Column(name = "DEPUTY_ITEM_TYPE", length = 4)
	public String getDeputyItemType() {
		return this.deputyItemType;
	}

	public void setDeputyItemType(String deputyItemType) {
		this.deputyItemType = deputyItemType;
	}

	@Column(name = "DEPUTY_ITEM_GRADE", length = 4)
	public String getDeputyItemGrade() {
		return this.deputyItemGrade;
	}

	public void setDeputyItemGrade(String deputyItemGrade) {
		this.deputyItemGrade = deputyItemGrade;
	}

	@Column(name = "DEPUTY_ITEM_NO", length = 50)
	public String getDeputyItemNo() {
		return this.deputyItemNo;
	}

	public void setDeputyItemNo(String deputyItemNo) {
		this.deputyItemNo = deputyItemNo;
	}

	
	@Column(name = "DEPUTY_ITEM_NUMBER", length = 50)
	public String getDeputyItemNumber() {
		return this.deputyItemNumber;
	}

	public void setDeputyItemNumber(String deputyItemNumber) {
		this.deputyItemNumber = deputyItemNumber;
	}
	@Column(name = "DECLARATION_BATCH", length = 50)
	public String getDeclarationBatch() {
		return this.declarationBatch;
	}

	public void setDeclarationBatch(String declarationBatch) {
		this.declarationBatch = declarationBatch;
	}

	@Column(name = "ITEM_ADDRESS", length = 200)
	public String getItemAddress() {
		return this.itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	@Column(name = "PROTECTION_UNIT", length = 200)
	public String getProtectionUnit() {
		return this.protectionUnit;
	}

	public void setProtectionUnit(String protectionUnit) {
		this.protectionUnit = protectionUnit;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_DATE", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date date) {
		this.createDate = date;
	}

	@Column(name = "CREATE_ID", length = 50)
	public String getCreateId() {
		return this.createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deputyItem")
	public Set<DeputyUnit> getDeputyUnits() {
		return this.deputyUnits;
	}

	public void setDeputyUnits(Set<DeputyUnit> deputyUnits) {
		this.deputyUnits = deputyUnits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deputyItem")
	public Set<DeputyPerson> getDeputyPersons() {
		return this.deputyPersons;
	}

	public void setDeputyPersons(Set<DeputyPerson> deputyPersons) {
		this.deputyPersons = deputyPersons;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deputyItem")
	public Set<ItemApply> getItemApplies() {
		return this.itemApplies;
	}

	public void setItemApplies(Set<ItemApply> itemApplies) {
		this.itemApplies = itemApplies;
	}

}