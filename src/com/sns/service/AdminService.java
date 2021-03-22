package com.sns.service;




import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.AdminDAO;
import com.sns.dto.AdminDTO;

public class AdminService {
	
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;
	String page = "";
	String msg = "";

	
	public AdminService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void admin_login() throws ServletException, IOException {
	
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		String loginId = req.getParameter("userId");
		AdminDAO dao= new AdminDAO();
		String pageParam = req.getParameter("page");
		if(loginId != null||pageParam!=null) {
			System.out.println("admin 로그인 요청");
			boolean success = dao.admin_login(id,pw);
			System.out.println("admin 로그인 결과 : " + success);
			page = "index.jsp";
			msg = "아이디와 비밀번호를 확인해 주세요.";
		

			if (success||pageParam!=null) {
					page = "manager_report.jsp";
					String admin_loginId = req.getParameter("userId");
					msg = admin_loginId + "관리자님 반갑습니다.";
					req.getSession().setAttribute("admin_loginId", admin_loginId);
					
					
					int group = 1;
					if(pageParam != null) {
						group = Integer.parseInt(pageParam);
					}
							
					AdminDAO reportListDAO = new AdminDAO();
					HashMap<String, Object> map = reportListDAO.reportList(group);
					
					req.setAttribute("maxPage", map.get("maxPage"));
					req.setAttribute("reportList", map.get("reportList"));
					req.setAttribute("currPage", group);
					
					
				}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			
			dis.forward(req, resp);
			}else {
				resp.sendRedirect("./index.jsp");
			}
		
		
	}

	public void report_content() throws ServletException, IOException {
		String report_idx =req.getParameter("report_idx");
		System.out.println("report_idx :"+report_idx+"입니다.");
		
		
		AdminDAO dao= new AdminDAO();
		AdminDTO dto=dao.report_content(report_idx);
		if(dto != null) {
			dao = new AdminDAO();
			page="manger_report.jsp";
		
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		
		dis.forward(req, resp);
		
	}
	

}
