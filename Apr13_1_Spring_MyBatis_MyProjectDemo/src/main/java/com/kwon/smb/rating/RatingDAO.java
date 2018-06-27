package com.kwon.smb.rating;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


// servlet-context.xml¿¡ °´Ã¼ ÇÏ³ª ¸¸µé¾îÁü
@Service
public class RatingDAO {

	@Autowired
	private SqlSession ss;

	
	public void getTop100Movies(
			HttpServletRequest req, HttpServletResponse res) {
		
		
	
	}
}
