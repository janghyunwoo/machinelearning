package com.kwon.smb.rating;

import java.math.BigDecimal;

public class Rating {
	private BigDecimal userid;
	private BigDecimal movieid;
	private BigDecimal rating;
	private String comment;
	
	public Rating() {
		
	}
	
	public Rating(BigDecimal userid, BigDecimal movieid, BigDecimal rating, String comment) {
		super();
		this.userid = userid;
		this.movieid = movieid;
		this.rating = rating;
		this.comment = comment;
	}
	public BigDecimal getUserid() {
		return userid;
	}
	public void setUserid(BigDecimal userid) {
		this.userid = userid;
	}
	public BigDecimal getMovieid() {
		return movieid;
	}
	public void setMovieid(BigDecimal movieid) {
		this.movieid = movieid;
	}
	public BigDecimal getRating() {
		return rating;
	}
	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
