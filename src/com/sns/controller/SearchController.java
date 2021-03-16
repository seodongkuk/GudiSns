package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.SearchService;

@WebServlet({"/find"})
public class SearchController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	//GET,POST 모두 받을 수 있도록
	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");//한글깨짐방지
		
		//요청 url 추출
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());
		
		//서비스에게 일 요청을 위해 서비스 객체화(request와 response의 객체들을 담아서)
		SearchService service = new SearchService(req,resp);
		
		//요청 url에 따라 switch문으로 일 분배
		switch (sub) {
		case "/find":
			System.out.println("검색 요청");		
			service.find();
			break;		
		}
	}
}
