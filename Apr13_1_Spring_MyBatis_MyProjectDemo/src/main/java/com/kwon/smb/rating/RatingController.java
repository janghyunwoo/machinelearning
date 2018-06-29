package com.kwon.smb.rating;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RatingController {
	
	// servlet-context.xml에 있는 객체 불러오기
	@Autowired 
	private RatingDAO d;
	

	@RequestMapping(value = "/rating.do", method = RequestMethod.POST)
	public void ratingdo(HttpServletRequest req, HttpServletResponse res) throws IOException {

		System.out.println("rating.do 접속");
		
		res.setContentType("text/html;charset=UTF-8");
		res.getWriter().println(d.registerComment(req, res));
		
	}

	@RequestMapping(value = "/getrating.do", method = RequestMethod.POST)
	public void getrating(HttpServletRequest req, HttpServletResponse res) throws IOException {

		System.out.println("getrating.do 접속");
		res.setContentType("text/html;charset=UTF-8");
		d.getComment(req,res);
		d.paging(1, req, res);
		
		
//		res.getWriter().println(d.registerComment(req, res));
		
	}
	

	
}

















