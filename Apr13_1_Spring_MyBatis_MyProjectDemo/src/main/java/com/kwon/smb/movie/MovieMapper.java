package com.kwon.smb.movie;

import java.util.List;

public interface MovieMapper {
	public abstract List<Movie> getMovies();
	public abstract List<Genre> getGenre();
	// returnŸ��
		// insert, update, delete : int
		// select : List<�ڹٺ�> or �ڹٺ�
	// method�� : id�� ���߱�
	// parameter : parameterType�� ���߱�
}











