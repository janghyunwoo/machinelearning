package com.kwon.smb.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kwon.smb.DateManager;
@Controller
public class UserController {
	
	// servlet-context.xml에 있는 객체 불러오기
	@Autowired private UserDAO DAO;
	
	@RequestMapping(value = "/login.go", method = RequestMethod.GET)
	public String loginpage(HttpServletRequest req, HttpServletResponse res) {
		DateManager.getCurrentYear(req, res);
		
		return "user/login";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String logingo(User user,HttpServletRequest req, HttpServletResponse res) {
		DateManager.getCurrentYear(req, res);
		
		DAO.login(user, req, res);
		
		if (DAO.loginCheck(req, res)) {
//			Homecontroller.home컨트롤러 호출
			return "redirect:/";
		} else {
			return "user/login";
		}
		
	}
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logoutdo(User user,HttpServletRequest req, HttpServletResponse res) {
		DateManager.getCurrentYear(req, res);
		
		DAO.logout(req, res);
//		DAO.loginCheck(req, res);
		
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	public String joinDo(User m, HttpServletRequest req, HttpServletResponse res) {
		DateManager.getCurrentYear(req, res);
		DAO.join(m, req, res);
		
		
		return "user/login";
	}

	
}

















