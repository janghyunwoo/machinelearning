package com.kwon.smb.rating;

import java.util.ArrayList;

public interface RatingMapper {
	// return타입
		// insert, update, delete : int
		// select : List<자바빈> or 자바빈
	// method명 : id와 맞추기
	// parameter : parameterType과 맞추기
	public abstract int registerRating(Rating r);
	public abstract ArrayList<Comment> getComment(Rating r);
	
}












