package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.MainService;

//새글쓰기 게시글수정
@WebServlet({"/newWrite"})     
public class MainController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());
		
		//메인서비
		MainService service = new MainService(req,resp);
		req.setCharacterEncoding("UTF-8");
		
		switch (sub) {
		case "/newWrite":
			System.out.println("글작성요청");
			service.newWrite();
			break;

		case "/writeEdit":
//			글수정 시~
			break;
		}
		
		
	}

}
