package com.sns.service;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sns.dao.AdminDAO;

public class AdminService {
	
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	AdminDAO dao=null;
	
	public AdminService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		dao = new AdminDAO();
	}

	public boolean admin_login() {
	
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id+"/"+pw);
		return dao.admin_login(id, pw);
	}

	

}
