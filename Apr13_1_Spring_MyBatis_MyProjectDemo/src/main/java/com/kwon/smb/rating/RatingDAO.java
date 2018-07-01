package com.kwon.smb.rating;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwon.smb.user.User;
import com.kwon.smb.user.UserMapper;


// servlet-context.xml�� ��ü �ϳ� �������
@Service
public class RatingDAO {

	@Autowired
	private SqlSession ss;

	public Rating ratingObject;
	public ArrayList<Comment> comments;
	
	public String registerComment(
			HttpServletRequest req, HttpServletResponse res) {
		try {
			User user = (User)req.getSession().getAttribute("loginMember");
			if(user == null) {
				return "�α��� ���ּ���.";
			}
			BigDecimal userid = user.getUserid();
			
			BigDecimal movieid = new BigDecimal(req.getParameter("movieid"));
			BigDecimal rating = new BigDecimal(req.getParameter("rating"));
			String comment = req.getParameter("comment");
			comment = comment.replace("\n", "<br>");
			
			ratingObject = new Rating(userid,movieid,rating,comment);
			
			if (ss.getMapper(RatingMapper.class).registerRating(ratingObject) == 1) {
				return "���� ��� �Ǿ����ϴ�.";
			} else {
				return "��Ͽ� �����Ͽ����ϴ�.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ͽ� �����Ͽ����ϴ�.";
		}
	
	}
	

	public void getComment(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		try {
			BigDecimal movieid = new BigDecimal(req.getParameter("movieid"));
			ratingObject = new Rating(null,movieid,null,null);
			
			comments = ss.getMapper(RatingMapper.class).getComment(ratingObject);
			System.out.println(comments.size());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void paging( HttpServletRequest request, HttpServletResponse response) {
		// ��ü ������ �� ���
		int page = Integer.parseInt(request.getParameter("selectNum"));
		double cnt = 10; // �� �������� ���� �ı� ��
		int commentsSize = comments.size(); // �� �ı� ��
		int pageCount = (int) Math.ceil(commentsSize / cnt);
		//request.setAttribute("pageCount", pageCount);

		int start = commentsSize - ((int) cnt * (page - 1));
		int end = (page == pageCount) ? -1 : start - ((int) cnt + 1);

		ArrayList<Comment> comments2 = new ArrayList<Comment>();

		// 22 21 20 19 18 17 16 15 14 13
		for (int i = start - 1; i > end; i--) {
			comments2.add(comments.get(i));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		 HashMap<String,Object> map=new HashMap<String,Object>();
		 map.put("Comments",comments2);
		 map.put("resert","��� ����");
		 map.put("pageCount",pageCount);

		 JSONObject jsonObject= new JSONObject();

		 jsonObject.putAll(map);
		

		 try {
				response.getWriter().print(mapper.writeValueAsString(jsonObject));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

	}
}
