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
 * PersonApplyFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="person_apply_files"
     
)

public class PersonApplyFiles  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Files files;
     private PersonApply personApply;


    // Constructors

    /** default constructor */
    public PersonApplyFiles() {
    }

    
    /** full constructor */
    public PersonApplyFiles(Files files, PersonApply personApply) {
        this.files = files;
        this.personApply = personApply;
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

    public PersonApply getPersonApply() {
        return this.personApply;
    }
    
    public void setPersonApply(PersonApply personApply) {
        this.personApply = personApply;
    }
   








}