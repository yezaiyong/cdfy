package com.bsco.app.model;
// default package

import java.sql.Timestamp;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DeputyPerson entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "deputy_person")
public class DeputyPerson implements java.io.Serializable {

	// Fields

	private Integer deputyPersonId;
	private DeputyItem deputyItem;
	private String deputyPersonName;
	private String deputyPersonDesc;
	private String deputyPersonGrade;
	private String deputyPersonBatch;
	private String death;
	private Date createDate=new Date();
	private String createId;
	private Set<PersonApply> personApplies = new HashSet<PersonApply>(0);

	// Constructors

	/** default constructor */
	public DeputyPerson() {
	}

	/** full constructor */
	public DeputyPerson(DeputyItem deputyItem, String deputyPersonName,String deputyPersonBatch,
			String deputyPersonDesc,String death, Date createDate, String createId,String  deputyPersonGrade,
			Set<PersonApply> personApplies) {
		this.deputyItem = deputyItem;
		this.deputyPersonName = deputyPersonName;
		this.deputyPersonDesc = deputyPersonDesc;
		this.createDate = createDate;
		this.deputyPersonGrade=deputyPersonGrade;
		this.createId = createId;
		this.death=death;
		this.personApplies = personApplies;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DEPUTY_PERSON_ID", unique = true, nullable = false)
	public Integer getDeputyPersonId() {
		return this.deputyPersonId;
	}

	public void setDeputyPersonId(Integer deputyPersonId) {
		this.deputyPersonId = deputyPersonId;
	}

	@Column(name = "DEATH", length = 50)
	public String getDeath() {
		return death;
	}

	public void setDeath(String death) {
		this.death = death;
	}

	@Column(name = "DEPUTY_PERSON_GRADE", length = 50)
	public String getDeputyPersonGrade() {
		return deputyPersonGrade;
	}

	public void setDeputyPersonGrade(String deputyPersonGrade) {
		this.deputyPersonGrade = deputyPersonGrade;
	}

	@Column(name = "DEPUTY_PERSON_BATCH", length = 50)
	public String getDeputyPersonBatch() {
		return deputyPersonBatch;
	}

	public void setDeputyPersonBatch(String deputyPersonBatch) {
		this.deputyPersonBatch = deputyPersonBatch;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPUTY_IETM_ID")
	public DeputyItem getDeputyItem() {
		return this.deputyItem;
	}

	public void setDeputyItem(DeputyItem deputyItem) {
		this.deputyItem = deputyItem;
	}

	@Column(name = "DEPUTY_PERSON_NAME", length = 50)
	public String getDeputyPersonName() {
		return this.deputyPersonName;
	}

	public void setDeputyPersonName(String deputyPersonName) {
		this.deputyPersonName = deputyPersonName;
	}

	@Column(name = "DEPUTY_PERSON_DESC")
	public String getDeputyPersonDesc() {
		return this.deputyPersonDesc;
	}

	public void setDeputyPersonDesc(String deputyPersonDesc) {
		this.deputyPersonDesc = deputyPersonDesc;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deputyPerson")
	public Set<PersonApply> getPersonApplies() {
		return this.personApplies;
	}

	public void setPersonApplies(Set<PersonApply> personApplies) {
		this.personApplies = personApplies;
	}

}