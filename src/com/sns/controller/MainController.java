package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.MainService;

//새글쓰기 게시글수정
@WebServlet({"/newWrite","/writeEdit","/MyProfile","/edit","/del","/detail"})     
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
		System.out.println(uri);
		String ctx = req.getContextPath();
		System.out.println(ctx);
		String sub = uri.substring(ctx.length());
		System.out.println(sub);
		
		//메인서비
		MainService service = new MainService(req,resp);
		req.setCharacterEncoding("UTF-8");
		
		switch (sub) {
		case "/newWrite":
			System.out.println("글작성요청");
			service.newWrite();
			break;
		case "/MyProfile":
			System.out.println("마이프로필에들어옴list보여줄준비");
			service.mylist();
			break;
		case "/writeEdit":
			System.out.println("글 수정 요청들어옴");
			service.writeEdit();
			break;
		
		case "/edit" :
			System.out.println("수정해줘");
			service.edit();
			break;
			
		case "/del" :
			System.out.println("삭제");
			service.del();
			break;
		case "/detail":
			System.out.println("더보기 (상세보기)");
			service.detail();
		}
		
		
	}

}
