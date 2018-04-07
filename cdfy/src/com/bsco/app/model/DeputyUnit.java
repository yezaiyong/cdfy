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
 * DeputyUnit entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="deputy_unit"
     
)

public class DeputyUnit  implements java.io.Serializable {


    // Fields    

     private Integer deputyUnitId;
     private DeputyItem deputyItem;
     private String deputyUnitType;
     private String deputyUnitName;
     private String deputyUnitDesc;
     private String itemAddress;
     private String deputyUnitBatch;
     private String deputyUnitGrade;
     private Date createDate =new Date();
     private String createId;
     private Set<UnitApply> unitApplies = new HashSet<UnitApply>(0);


    // Constructors

    /** default constructor */
    public DeputyUnit() {
    }

    
    /** full constructor */
    public DeputyUnit(DeputyItem deputyItem,String deputyUnitType, String deputyUnitName,String itemAddress,String deputyUnitBatch,  String deputyUnitDesc, Date createDate, String createId, Set<UnitApply> unitApplies) {
        this.deputyItem = deputyItem;
        this.deputyUnitType=deputyUnitType;
        this.deputyUnitName = deputyUnitName;
        this.deputyUnitDesc = deputyUnitDesc;
        this.createDate = createDate;
        this.createId = createId;
        this.itemAddress=itemAddress;
        this.deputyUnitBatch=deputyUnitBatch;
        this.unitApplies = unitApplies;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="DEPUTY_UNIT_ID", unique=true, nullable=false)

    public Integer getDeputyUnitId() {
        return this.deputyUnitId;
    }
    
    public void setDeputyUnitId(Integer deputyUnitId) {
        this.deputyUnitId = deputyUnitId;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DEPUTY_ITEM_ID")

    public DeputyItem getDeputyItem() {
        return this.deputyItem;
    }
    
    public void setDeputyItem(DeputyItem deputyItem) {
        this.deputyItem = deputyItem;
    }
    
    @Column(name="DEPUTY_UNIT_GRADE", length=50)
    public String getDeputyUnitGrade() {
		return deputyUnitGrade;
	}


	public void setDeputyUnitGrade(String deputyUnitGrade) {
		this.deputyUnitGrade = deputyUnitGrade;
	}


	@Column(name="DEPUTY_UNIT_TYPE", length=50)
    public String getDeputyUnitType() {
		return deputyUnitType;
	}


	public void setDeputyUnitType(String deputyUnitType) {
		this.deputyUnitType = deputyUnitType;
	}


  
	@Column(name="ITEM_ADDRESS", length=50)
	public String getItemAddress() {
		return itemAddress;
	}


	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}
	@Column(name="DEPUTY_UNIT_BATCH", length=50)
	public String getDeputyUnitBatch() {
		return deputyUnitBatch;
	}



	public void setDeputyUnitBatch(String deputyUnitBatch) {
		this.deputyUnitBatch = deputyUnitBatch;
	}


	@Column(name="DEPUTY_UNIT_NAME", length=200)

    public String getDeputyUnitName() {
        return this.deputyUnitName;
    }
    
    public void setDeputyUnitName(String deputyUnitName) {
        this.deputyUnitName = deputyUnitName;
    }
    
    @Column(name="DEPUTY_UNIT_DESC")

    public String getDeputyUnitDesc() {
        return this.deputyUnitDesc;
    }
    
    public void setDeputyUnitDesc(String deputyUnitDesc) {
        this.deputyUnitDesc = deputyUnitDesc;
    }
    
    @Column(name="CREATE_DATE", length=19)
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="CREATE_ID", length=50)

    public String getCreateId() {
        return this.createId;
    }
    
    public void setCreateId(String createId) {
        this.createId = createId;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="deputyUnit")

    public Set<UnitApply> getUnitApplies() {
        return this.unitApplies;
    }
    
    public void setUnitApplies(Set<UnitApply> unitApplies) {
        this.unitApplies = unitApplies;
    }
   








}