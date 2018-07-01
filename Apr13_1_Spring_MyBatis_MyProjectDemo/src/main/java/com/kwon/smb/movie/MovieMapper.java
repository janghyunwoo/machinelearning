package com.kwon.smb.movie;

import java.util.ArrayList;
import java.util.List;

public interface MovieMapper {
	public abstract ArrayList<Movie> getMovies();
	public abstract List<Genre> getGenre();
	// return타입
		// insert, update, delete : int
		// select : List<자바빈> or 자바빈
	// method명 : id와 맞추기
	// parameter : parameterType과 맞추기
}












