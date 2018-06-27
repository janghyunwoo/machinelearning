package com.kwon.smb.rating;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.movie.MovieDAO;

@Controller
public class RatingController {
	
	// servlet-context.xml에 있는 객체 불러오기
	@Autowired 
	private MovieDAO d;
	

	


	
}

















