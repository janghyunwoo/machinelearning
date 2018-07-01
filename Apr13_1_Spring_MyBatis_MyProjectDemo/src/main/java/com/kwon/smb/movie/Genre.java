package com.kwon.smb.movie;

import java.math.BigDecimal;
import java.util.Date;
// MyBatis
//		DB필드명 = 멤버변수명
//		숫자는 BigDecimal

// Spring
//		요청파라메터명 = 멤버변수명

// => DB필드명 = 멤버변수명 = 요청파라메터명
//	   숫자는 BigDecimal
public class Genre {
	private BigDecimal genreid;
	private String name;

	public Genre(BigDecimal genreid, String name) {
		super();
		this.genreid = genreid;
		this.name = name;
	}

	public BigDecimal getGenreid() {
		return genreid;
	}

	public void setGenreid(BigDecimal genreid) {
		this.genreid = genreid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Genre() {
		// TODO Auto-generated constructor stub
	}
	
	

}
