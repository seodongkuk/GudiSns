package com.sns.service;




import java.io.IOException;
import java.util.ArrayList;
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
		//첫번째 if문에서 안걸릴 수 있으니 null로 초기화하고 if문 진입했을때 dao 객체화할 수 있게 변경
		AdminDAO dao= null;
		String pageParam = req.getParameter("page");
		if(loginId != null||pageParam!=null) {
			dao = new AdminDAO();
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

	public void report_detail() throws ServletException, IOException {
		String board_idx =req.getParameter("board_idx");
		System.out.println("해당글 글번호:"+board_idx+"입니다.");
		AdminDAO dao= new AdminDAO();
		AdminDTO report_detail= dao.report_detail(board_idx);
		if(report_detail != null) {
			req.setAttribute("report_detail", report_detail);
			page="report_detail.jsp";
		
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		
		dis.forward(req, resp);
		
	}

	public void report_list() throws ServletException, IOException {
		String pageParam = req.getParameter("page");
		
		String admin_id = (String)req.getSession().getAttribute("admin_loginId");
			
		
		int group = 1;
		if(admin_id!=null) {
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
				
		AdminDAO reportListDAO = new AdminDAO();
		HashMap<String, Object> map = reportListDAO.reportList(group);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("reportList", map.get("reportList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("manager_report.jsp");
		
		dis.forward(req, resp);
		}else {
			page="logout";
		}
	}
	

	public void black_list() throws ServletException, IOException {
		String pageParam = req.getParameter("page");
		
		String admin_id = (String)req.getSession().getAttribute("admin_loginId");
				
		
		int group = 1;
		if(admin_id!=null) {
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
				
		AdminDAO blacklistDAO = new AdminDAO();
		HashMap<String, Object> map = blacklistDAO.blackList(group);
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("blackList", map.get("blackList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("manager_black.jsp");
		
		dis.forward(req, resp);
		}else {
			page="logout";
		}
	}
	
	public void blind_list() throws ServletException, IOException {
		String pageParam = req.getParameter("page");
		
		String admin_id = (String)req.getSession().getAttribute("admin_loginId");
						
		int group = 1;
		if(admin_id!=null) {
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
		
		AdminDAO blindlistDAO = new AdminDAO();
		HashMap<String, Object> map = blindlistDAO.blindlist(group);
			
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("blindList", map.get("blindList"));
		req.setAttribute("currPage", group);
		dis = req.getRequestDispatcher("manager_blind.jsp");
		
		dis.forward(req, resp);
		}else {
			page="logout";
		}
	}
	
}
