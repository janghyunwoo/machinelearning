package com.kwon.smb.rating;

import java.util.ArrayList;

public interface RatingMapper {
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�
	public abstract int registerRating(Rating r);
	public abstract ArrayList<Comment> getComment(Rating r);
	
}












