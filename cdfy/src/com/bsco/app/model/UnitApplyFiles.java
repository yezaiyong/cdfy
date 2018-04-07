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
 * UnitApplyFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="unit_apply_files"
     
)

public class UnitApplyFiles  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Files files;
     private UnitApply unitApply;


    // Constructors

    /** default constructor */
    public UnitApplyFiles() {
    }

    
    /** full constructor */
    public UnitApplyFiles(Files files, UnitApply unitApply) {
        this.files = files;
        this.unitApply = unitApply;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="FILE_ID")

    public Files getFiles() {
        return this.files;
    }
    
    public void setFiles(Files files) {
        this.files = files;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="APPALY_ID")

    public UnitApply getUnitApply() {
        return this.unitApply;
    }
    
    public void setUnitApply(UnitApply unitApply) {
        this.unitApply = unitApply;
    }
   








}