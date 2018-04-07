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
 * ItemApplyFiles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="item_apply_files"
     
)

public class ItemApplyFiles  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Files files;
     private ItemApply itemApply;


    // Constructors

    /** default constructor */
    public ItemApplyFiles() {
    }

    
    /** full constructor */
    public ItemApplyFiles(Files files, ItemApply itemApply) {
        this.files = files;
        this.itemApply = itemApply;
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

    public ItemApply getItemApply() {
        return this.itemApply;
    }
    
    public void setItemApply(ItemApply itemApply) {
        this.itemApply = itemApply;
    }
   








}