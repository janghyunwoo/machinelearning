package com.kwon.smb.user;

import java.math.BigDecimal;
import java.util.Date;

public class User {
	private BigDecimal userid;
	private String id;
	private String pw;
	private String sex;
	private Date birthday;
	private BigDecimal isadmin;

	public User() {
		// TODO Auto-generated constructor stub
	}

	
	public User(BigDecimal userid, String id, String pw, String sex, Date birthday, BigDecimal isadmin) {
		super();
		this.userid = userid;
		this.id = id;
		this.pw = pw;
		this.sex = sex;
		this.birthday = birthday;
		this.isadmin = isadmin;
	}


	public BigDecimal getUserid() {
		return userid;
	}

	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(BigDecimal isadmin) {
		this.isadmin = isadmin;
	}

	

}
