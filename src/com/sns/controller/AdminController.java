package com.sns.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sns.service.AdminService;

@WebServlet({ "/admin_login" })
public class AdminController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String sub = uri.substring(context.length());
		System.out.println("sub : " + sub);
		AdminService service = new AdminService(req, resp);

		RequestDispatcher dis = null;
		String page = "";
		String msg = "";

		HttpSession session = req.getSession();
		if (session.getAttribute("msg") != null) {
			session.removeAttribute("msg");
		}
		switch (sub) {

		case "/admin_login":
			System.out.println("admin 로그인 요청");
			boolean success = service.admin_login();
			System.out.println("admin 로그인 결과 : " + success);

			page = "index.jsp";
			msg = "아이디와 비밀번호를 확인해 주세요.";
			

			if (success) {
					page = "manager_report.jsp";
					String admin_loginId = req.getParameter("userId");
					msg = admin_loginId + "관리자님 반갑습니다.";
					req.getSession().setAttribute("admin_loginId", admin_loginId);
				}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			break;
			}
	}
}

