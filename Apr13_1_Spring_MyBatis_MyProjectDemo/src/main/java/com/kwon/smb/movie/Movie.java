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
public class Movie {
	private BigDecimal movieid;
	private String title;
	private Date releaseday;
	private String genreid;
	private String img;
	private BigDecimal avgrating;

	public Movie() {
		// TODO Auto-generated constructor stub
	}
	
	public Movie(BigDecimal movieid, String title, Date releaseday, String genreid, String img, BigDecimal avgrating) {
		super();
		this.movieid = movieid;
		this.title = title;
		this.releaseday = releaseday;
		this.genreid = genreid;
		this.img = img;
		this.avgrating = avgrating;
	}

	public BigDecimal getMovieid() {
		return movieid;
	}

	public void setMovieid(BigDecimal movieid) {
		this.movieid = movieid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseday() {
		return releaseday;
	}

	public void setReleaseday(Date releaseday) {
		this.releaseday = releaseday;
	}

	public String getGenreid() {
		return genreid;
	}

	public void setGenreid(String genreid) {
		this.genreid = genreid;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public BigDecimal getAvgrating() {
		return avgrating;
	}

	public void setAvgrating(BigDecimal avgrating) {
		this.avgrating = avgrating;
	}
}
