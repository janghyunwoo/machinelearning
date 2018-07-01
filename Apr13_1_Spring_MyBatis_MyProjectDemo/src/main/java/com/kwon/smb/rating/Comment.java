package com.kwon.smb.rating;

import java.math.BigDecimal;

public class Comment {
	private String id;
	private BigDecimal rating;
	private String comment;
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}
	
	public Comment(String id, BigDecimal rating, String comment) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
