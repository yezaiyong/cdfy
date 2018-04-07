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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PersonApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person_apply")
public class PersonApply implements java.io.Serializable {

	// Fields

	private Integer applyId;
	private DeputyPerson deputyPerson;
	private String applyGard;
	private Date applyDate =new Date();
	private String applyBatch;
	private Set<PersonApplyFiles> personApplyFileses = new HashSet<PersonApplyFiles>(
			0);

	// Constructors

	/** default constructor */
	public PersonApply() {
	}

	/** full constructor */
	public PersonApply(DeputyPerson deputyPerson, String applyGard,
			Date applyDate, String applyBatch,
			Set<PersonApplyFiles> personApplyFileses) {
		this.deputyPerson = deputyPerson;
		this.applyGard = applyGard;
		this.applyDate = applyDate;
		this.applyBatch = applyBatch;
		this.personApplyFileses = personApplyFileses;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "APPLY_ID", unique = true, nullable = false)
	public Integer getApplyId() {
		return this.applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPUTY_PERSON_ID")
	public DeputyPerson getDeputyPerson() {
		return this.deputyPerson;
	}

	public void setDeputyPerson(DeputyPerson deputyPerson) {
		this.deputyPerson = deputyPerson;
	}

	@Column(name = "APPLY_GARD", length = 50)
	public String getApplyGard() {
		return this.applyGard;
	}

	public void setApplyGard(String applyGard) {
		this.applyGard = applyGard;
	}

	@Column(name = "APPLY_DATE", length = 19)
	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	@Column(name = "APPLY_BATCH", length = 50)
	public String getApplyBatch() {
		return this.applyBatch;
	}

	public void setApplyBatch(String applyBatch) {
		this.applyBatch = applyBatch;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "personApply")
	public Set<PersonApplyFiles> getPersonApplyFileses() {
		return this.personApplyFileses;
	}

	public void setPersonApplyFileses(Set<PersonApplyFiles> personApplyFileses) {
		this.personApplyFileses = personApplyFileses;
	}

}