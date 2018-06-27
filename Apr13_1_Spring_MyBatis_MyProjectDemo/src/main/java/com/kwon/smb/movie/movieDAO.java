package com.kwon.smb.movie;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// servlet-context.xml¿¡ °´Ã¼ ÇÏ³ª ¸¸µé¾îÁü
@Service
public class movieDAO {

	@Autowired
	private SqlSession ss;

	
	public void getTop100Movies(
			HttpServletRequest req, HttpServletResponse res) {
		
		List<Genre> genres = ss.getMapper(MovieMapper.class).getGenre();
		
		List<Movie> movies = ss.getMapper(MovieMapper.class).getMovies();
		String temp[];
		String idtochangeGenre=""; 
		for (Movie movie : movies) {
			String movieGenre = movie.getGenreid();
			temp = movieGenre.split(":");
			for (Genre genre : genres) {
				for(int i = 0;i<temp.length;i++) {
					if(genre.getGenreid().toString().equals(temp[i])) {
						idtochangeGenre += genre.getName()+" ";
					}
					
				}
			}
			movie.setGenreid(idtochangeGenre);
			idtochangeGenre="";
		}
		
		
		
		req.setAttribute("MovieContent", movies);
	}
	
	
	
}
