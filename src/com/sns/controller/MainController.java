package com.sns.controller;

import com.sns.service.MainService;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/newWrite", "/writeEdit", "/MyProfile", "/edit", "/del", "/detail","/flist","/like","/likecnt","/array"})
public class MainController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.dual(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		System.out.println(uri);
		String ctx = req.getContextPath();
		System.out.println(ctx);
		String sub = uri.substring(ctx.length());
		System.out.println(sub);
		
		
		MainService service = new MainService(req, resp);
		
		
		req.setCharacterEncoding("UTF-8");
		
		switch (sub) {
			case "/writeEdit" :
			
					System.out.println("글 수정 요청들어옴");
					service.writeEdit();
				
				break;
			case "/newWrite" :
				
					System.out.println("글작성요청");
					service.newWrite();
				
				break;
			case "/MyProfile" :
				
					System.out.println("마이프로필에들어옴list보여줄준비");
					service.mylist();
				
				break;
			case "/del" :
				
					System.out.println("삭제");
					service.del();
				
				break;
			case "/edit" :
				
					System.out.println("수정해줘");
					service.edit();
				
				break;
			case "/detail" :
				
					System.out.println("더보기 (상세보기)");
					service.detail();
				break;
				
			case "/flist" :
				
				System.out.println("list보여줄준비");
				service.flist();
		
			break;

			case "/like" :
				
				System.out.println("추천 준비됬나?");
				service.like();
				break;

//			case "/likecnt" :
//				
//				System.out.println("추천수 보여줄게");
//				//service.likecnt();
//				break;
			
			case "/array" :
				
				System.out.println("정렬 들어간다.");
				service.array();
		
			break;
		}

	}
}
