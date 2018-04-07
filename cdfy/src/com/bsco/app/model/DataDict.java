package com.bsco.app.model;
// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * DataDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="data_dict"
     
)

public class DataDict  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String type;
     private String code;
     private String name;
     private String createId;
     private Date createDate;


    // Constructors

    /** default constructor */
    public DataDict() {
    }

    
    /** full constructor */
    public DataDict(String type, String code, String name, String createId, Date createDate) {
        this.type = type;
        this.code = code;
        this.name = name;
        this.createId = createId;
        this.createDate = createDate;
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
    
    @Column(name="type", length=50)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="code", length=50)

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    @Column(name="NAME", length=50)

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="CREATE_ID", length=50)

    public String getCreateId() {
        return this.createId;
    }
    
    public void setCreateId(String createId) {
        this.createId = createId;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="create_date", length=10)

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
   








}