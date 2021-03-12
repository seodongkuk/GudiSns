
package com.sns.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sns.dto.MemberDTO;
import com.sns.service.MemberService;

@WebServlet({"/login","/join","/logout","/main"})
public class MemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String sub = uri.substring(context.length());
		
		req.setCharacterEncoding("UTF-8");
		RequestDispatcher dis = null;
		String page="";
		String msg="";
		MemberService service = new MemberService(req,resp);
		System.out.println("sub address: "+sub);
		
		HttpSession session = req.getSession();
		if(session.getAttribute("msg")!=null) {
			session.removeAttribute("msg");
		}
				
				
		
		
		switch(sub) {
		case "/login":
			System.out.println("로그인 요청");
			boolean success = service.login();
			System.out.println("로그인 결과 : "+success);
			
			page = "index.jsp";
			msg="아이디와 비밀번호를 확인해 주세요.";
			
			if(success) {
				String loginId = req.getParameter("userId");
				page = "main";		
				msg=loginId+"님, 반갑습니다.";				
				req.getSession().setAttribute("loginId", loginId);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);			
			break;
		
		case "/logout":
			req.getSession().removeAttribute("loginId");
			resp.sendRedirect("index.jsp");
			break;
			
		case "/main":
			System.out.println("main Page!!!");
			
			 ArrayList<MemberDTO> list = service.main();
			 req.setAttribute("list", list);
			 dis = req.getRequestDispatcher("main.jsp");
			 dis.forward(req, resp);
			break;
			
		case "/join":
			System.out.println("회원가입 요청");
			
			msg="회원가입에 실패 했습니다.";
			page="join.jsp";
			
			if(service.join()) {//성공
				msg="회원가입을 축하 드립니다.";
				page="index.jsp";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			break;		
			
		}
	}
}	