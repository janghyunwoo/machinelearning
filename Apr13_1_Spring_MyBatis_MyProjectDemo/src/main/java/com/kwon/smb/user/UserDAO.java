package com.kwon.smb.user;


import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;




// servlet-context.xml에 객체 하나 만들어짐
@Service
public class UserDAO {

	@Autowired
	private SqlSession ss;

	
	public void login(User user, HttpServletRequest req, HttpServletResponse res) {
		try {
			User dbM = ss.getMapper(UserMapper.class).getUserById(user);

			if (dbM != null) {
				if (user.getPw().equals(dbM.getPw())) {
					req.getSession().setAttribute("loginMember", dbM);
					req.getSession().setMaxInactiveInterval(1200);

					// 자동 로그인에 체크가 되어 있다면
					if (req.getParameter("im_autologin") != null) {
						Cookie c = new Cookie("ihwacAutoLoginID", dbM.getId());
						c.setMaxAge(86400);
						res.addCookie(c);
					}

				} else {
					req.setAttribute("r", "비번 오류");
				}
			} else {
				req.setAttribute("r", "그런 계정 없음");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("r", "DB서버문제");
		}
	}


	public boolean loginCheck(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		User m = (User) req.getSession().getAttribute("loginMember");

		if (m != null) {
			return true;
		} else {
			return false;
		}
	}


	public void logout(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		req.getSession().setAttribute("loginMember", null);

		Cookie[] cookies = req.getCookies();

		//자동로그인 해제 하기 위해 쿠키값 제거
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("ihwacAutoLoginID")) {
					c.setValue(null); // id값 삭제
					res.addCookie(c);
				}
			}
		}
	}
	
	//자동 로그인이 설정 되어있는지 확인
		public void autologin(HttpServletRequest req, HttpServletResponse res) {
			Cookie[] cookies = req.getCookies();

			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("ihwacAutoLoginID")) {
						String im_id = c.getValue();
						if (im_id != null) {
							User m = new User();
							m.setId(im_id);

							User dbM = ss.getMapper(UserMapper.class).getUserById(m);

							if (dbM != null) {
								req.getSession().setAttribute("loginMember", dbM);
								req.getSession().setMaxInactiveInterval(1200);
							}

						}
					}
				}
			}
		}


		public void join(User m, HttpServletRequest req, HttpServletResponse res) {
			// TODO Auto-generated method stub
			try {

				
				String im_y = req.getParameter("im_y"); // "1982"
				String im_m = req.getParameter("im_m"); // "1"
				int im_m2 = Integer.parseInt(im_m); // 1
				String im_d = req.getParameter("im_d"); // "2"
				int im_d2 = Integer.parseInt(im_d); // 2

				String birthday = String.format("%s-%02d-%02d", im_y, im_m2, im_d2);
				// 19820102
				Date dt1 = new SimpleDateFormat("yyyyy-mm-dd").parse(birthday);
				
				m.setBirthday(dt1);
				
				
				if (ss.getMapper(UserMapper.class).join(m) == 1) {
					req.setAttribute("r", "가입 성공");
				} else {
					req.setAttribute("r", "가입 실패");
				}
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("r", "가입 실패");
			}
		}
	
	
}
