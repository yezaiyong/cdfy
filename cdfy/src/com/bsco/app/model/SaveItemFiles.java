package com.bsco.app.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SaveItemFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "save_item_files")
public class SaveItemFiles implements java.io.Serializable {

	// Fields

	private Integer id;
	private Files files;
	private SaveItem saveItem;

	// Constructors

	/** default constructor */
	public SaveItemFiles() {
	}

	/** full constructor */
	public SaveItemFiles(Files files, SaveItem saveItem) {
		this.files = files;
		this.saveItem = saveItem;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_ID")
	public Files getFiles() {
		return this.files;
	}

	public void setFiles(Files files) {
		this.files = files;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	public SaveItem getSaveItem() {
		return this.saveItem;
	}

	public void setSaveItem(SaveItem saveItem) {
		this.saveItem = saveItem;
	}

}