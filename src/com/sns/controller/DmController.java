package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.DmService;
@WebServlet({"/DM_Room","/chatRoom","/DM_list"})
public class DmController extends HttpServlet {

	//상속받은 HttpServlet의 기능을 가져오기 위해서 Override 한다(doGet, doPost)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//공통 메서드 호출(request와 response 인자값전달)
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//공통 메서드 호출(request와 response 인자값전달)
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		//View로 부터 파라메터를 받았을 때 한글깨짐방지 해줌
		req.setCharacterEncoding("UTF-8");
		System.out.println("요청 확인");
		//요청이 들어온 URL의 주소를 가져온다(다 가져오지는 않음)
		//05_MemberService/...(다양하게 들어올 수 있음)
		String uri = req.getRequestURI();
		//현재 프로젝트 경로를 가져온다
		//05_MemberService
		String context = req.getContextPath();
		//URI 주소에서 프로젝트 경로만 잘라냄으로써 요청 주소만 확인할 수 있다.
		String subAddr = uri.substring(context.length());
		System.out.println("subAddr : "+subAddr);
		
		DmService dm = new DmService(req,resp);
		
		switch(subAddr) {
		
			case "/DM_Room":
				System.out.println("채팅방 요청");
				dm.dmRoom();
				break;
				
			case "/chatRoom":
				System.out.println("DM 리스트 요청");
				dm.chatRoom();
				break;
			case "/DM_list":
				System.out.println("dm to, from id 요청");
				dm.dmList();
				break;
		}
	}
	
	
}
