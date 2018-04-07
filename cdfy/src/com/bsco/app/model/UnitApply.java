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
 * UnitApply entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="unit_apply"
     
)

public class UnitApply  implements java.io.Serializable {


    // Fields    

     private Integer applyId;
     private DeputyUnit deputyUnit;
     private String applyGard;
     private Date applyDate =new Date();
     private String applyBatch;
     private Set<UnitApplyFiles> unitApplyFileses = new HashSet<UnitApplyFiles>(0);


    // Constructors

    /** default constructor */
    public UnitApply() {
    }

    
    /** full constructor */
    public UnitApply(DeputyUnit deputyUnit, String applyGard, Date applyDate, String applyBatch, Set<UnitApplyFiles> unitApplyFileses) {
        this.deputyUnit = deputyUnit;
        this.applyGard = applyGard;
        this.applyDate = applyDate;
        this.applyBatch = applyBatch;
        this.unitApplyFileses = unitApplyFileses;
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
        @JoinColumn(name="DEPUTY_UNIT_ID")

    public DeputyUnit getDeputyUnit() {
        return this.deputyUnit;
    }
    
    public void setDeputyUnit(DeputyUnit deputyUnit) {
        this.deputyUnit = deputyUnit;
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
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="unitApply")

    public Set<UnitApplyFiles> getUnitApplyFileses() {
        return this.unitApplyFileses;
    }
    
    public void setUnitApplyFileses(Set<UnitApplyFiles> unitApplyFileses) {
        this.unitApplyFileses = unitApplyFileses;
    }
   








}