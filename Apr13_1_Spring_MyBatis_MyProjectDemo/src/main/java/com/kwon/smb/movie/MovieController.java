package com.kwon.smb.movie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.movie.MovieDAO;

//	1. 만들기만해서 실행
//
//	2. post방식 parameter한글처리 - web.xml
//		교재 6번
//
//	3. jar파일들 - maven - pom.xml
//		교재 7번(2.1,  2.2)
//	---------------------------------------
//	4. mapper.xml - SQL 적을 파일
//	   Java Interface - mapper.xml과 한 세트
//	---------------------------------------
//	5. 필요한 객체(SqlSession) 만들러 - servlet-context.xml
//		교재7번(3)

@Controller
public class MovieController {
	
	// servlet-context.xml에 있는 객체 불러오기
	@Autowired 
	private MovieDAO d;
	
	@RequestMapping(value = "/top100.go", method = RequestMethod.GET)
	public String home(HttpServletRequest req, HttpServletResponse res) {
		d.getTop100Movies(req, res);
		req.getSession().setAttribute("ContentPage", "top100.jsp");
		
		return "home";
	}
	


	
}

















