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
 * ItemApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="item_apply"
     
)

public class ItemApply  implements java.io.Serializable {


    // Fields    

     private Integer applyId;
     private DeputyItem deputyItem;
     private String applyGard;
     private Date applyDate =new Date();
     private String applyBatch;
     private Set<ItemApplyFiles> itemApplyFileses = new HashSet<ItemApplyFiles>(0);


    // Constructors

    /** default constructor */
    public ItemApply() {
    }

    
    /** full constructor */
    public ItemApply(DeputyItem deputyItem, String applyGard, Date applyDate, String applyBatch, Set<ItemApplyFiles> itemApplyFileses) {
        this.deputyItem = deputyItem;
        this.applyGard = applyGard;
        this.applyDate = applyDate;
        this.applyBatch = applyBatch;
        this.itemApplyFileses = itemApplyFileses;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="APPLY_ID", unique=true, nullable=false)

    public Integer getApplyId() {
        return this.applyId;
    }
    
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DEPUTY_ITEM_ID")

    public DeputyItem getDeputyItem() {
        return this.deputyItem;
    }
    
    public void setDeputyItem(DeputyItem deputyItem) {
        this.deputyItem = deputyItem;
    }
    
    @Column(name="APPLY_GARD", length=50)

    public String getApplyGard() {
        return this.applyGard;
    }
    
    public void setApplyGard(String applyGard) {
        this.applyGard = applyGard;
    }
    @Column(name="APPLY_DATE", length=19)

    public Date getApplyDate() {
        return this.applyDate;
    }
    
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    
    @Column(name="APPLY_BATCH", length=50)

    public String getApplyBatch() {
        return this.applyBatch;
    }
    
    public void setApplyBatch(String applyBatch) {
        this.applyBatch = applyBatch;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="itemApply")

    public Set<ItemApplyFiles> getItemApplyFileses() {
        return this.itemApplyFileses;
    }
    
    public void setItemApplyFileses(Set<ItemApplyFiles> itemApplyFileses) {
        this.itemApplyFileses = itemApplyFileses;
    }
   








}