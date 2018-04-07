package com.bsco.app.model;
// default package

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import com.bsco.app.parameter.Constants.*;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "users" )
public class Users implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userName;
	private String passWord;
	private String name;
	private String sex;
	private String role;
	private Date createDate =new Date();
	private STAUTS stauts;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** full constructor */
	public Users(String userName, String passWord, String name, String sex,
			String role, Date createDate, STAUTS stauts) {
		this.userName = userName;
		this.passWord = passWord;
		this.name = name;
		this.sex = sex;
		this.role = role;
		this.createDate = createDate;
		this.stauts = stauts;
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

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASS_WORD", length = 50)
	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SEX", length = 2)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "ROLE", length = 2)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "CREATE_Date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "STAUTS", length = 2)
	public STAUTS getStauts() {
		return this.stauts;
	}

	public void setStauts(STAUTS stauts) {
		this.stauts = stauts;
	}

}