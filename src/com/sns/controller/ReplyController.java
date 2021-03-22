package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.ReplyService;

@WebServlet({"/rlist","/rwrite","/rdel","/redit"})
public class ReplyController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");//한글깨짐 방지
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String subAddr = uri.substring(ctx.length());
		
		ReplyService reply = new ReplyService(req,resp);
		
		switch(subAddr) {
		case "/rlist":
			System.out.println("댓글리스트 요청");
			reply.rlist();
			break;
			
		case "/rwrite":
			System.out.println("댓글쓰기 요청");
			reply.rwrite();
			break;
		
		case "/rdel":
			System.out.println("댓글삭제 요청");
			reply.rdel();
			break;
			
		case "/redit":
			System.out.println("댓글수정 요청");
			reply.rdel();
			break;
		}
	}
}