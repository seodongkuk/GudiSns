package com.sns.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.AdminService;

@WebServlet({ "/admin_login", "/report_content" })
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
		AdminService service = new AdminService(req, resp);
		req.setCharacterEncoding("UTF-8");
		System.out.println("sub : " + sub);

		
		switch (sub) {

		case "/admin_login":
			service.admin_login();
			System.out.println("관리자로그인요청");
			break;

		case "/report_content":
			System.out.println("신고사유보기");
			service.report_content();
			break;

		}

	}
}
