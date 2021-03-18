package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sns.dao.AdminDAO;
import com.sns.dto.AdminDTO;

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
public void reportList() throws ServletException, IOException {		
		
		ArrayList<AdminDTO> list = dao.reportList();//DB 사용(O)
		System.out.println(list.size());
		req.setAttribute("list",list);//list를 설어 보내기		
		RequestDispatcher dis = req.getRequestDispatcher("list.jsp");//list.jsp 로 보내기		
		dis.forward(req, resp);
	}
	

}
