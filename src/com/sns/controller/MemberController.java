package com.sns.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.Severity;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sns.dao.MainDAO;
import com.sns.dto.MainDTO;
import com.sns.dto.MemberDTO;
import com.sns.service.MemberService;

@WebServlet({ "/login", "/join", "/logout", "/main", "/idChk", "/idfind", "/pwfind"
	,"/pwupdate","/infopw","/userinfoupdate","/userinfo","/delid","/idfind1","/memberdel"})
public class MemberController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
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
		MemberService service = new MemberService(req, resp);

		RequestDispatcher dis = null;
		String page = "";
		String msg = "";

		HttpSession session = req.getSession();
		if (session.getAttribute("msg") != null) {
			session.removeAttribute("msg");
		}
// ---------------------------------------------------------------------------
		switch (sub) {

		case "/login":
			System.out.println("로그인요청");
			service.login();
			break;
//---------------------------------------------------------------------------		
		case "/logout":
			req.getSession().removeAttribute("loginId");
			req.getSession().removeAttribute("admin_loginId"); //관리자로그인세션 널값처리
			resp.sendRedirect("index.jsp");
			System.out.println(session.getAttribute("userId"));				//세션 널값 처리
			break;

		case "/join":
			service.join();
			System.out.println("회원가입요청");
			break;

		case "/idChk":
			System.out.println("중복체크 요청");
			service.idChk();
			System.out.println(req.getParameter("id"));
			break;
			
			
			
		case "/idfind":
			System.out.println("아이디 찾기요청");	
			service.idfind();
			break;
			
		case "/idfind1":
			System.out.println("아이디 찾기요청");	
			service.idfind1();
			break;
		
		case "/pwfind":
			System.out.println("비번 찾기요청");	
			service.pwfind();
			break;
			
		
		
		
	
		case "/pwupdate":
			System.out.println("비번 수정요청");	
			service.pwupdate();
			break;
			
			
			
		
			
			
		case "/infopw":
			System.out.println("인포 패스워드 요청");	
			service.infopw();
			break;
		
	
		
		
		case "/userinfo":
			System.out.println("상세보기 요청");
			MemberDTO dto = service.userinfo();
			System.out.println(dto);
			
			msg="상세정보 불러오기에 실패 했습니다.";
			page="flist";				
			if(dto != null) {
				msg="회원정보를 수정하세요.";
				page="updateProfile.jsp";
				req.setAttribute("info", dto);
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);						
			break;
		
			
			
		case "/userinfoupdate":
			System.out.println("업데이트 요청");
			msg="수정에 실패 했습니다.";
			page="/updateProfile.jsp";
			
			if(service.userinfoupdate()!=0) {
				msg="수정에 성공 하였습니다.";
				page="flist";
			}
			System.out.println("page : "+page);
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);				
			break;
			
		case"/delid":
			System.out.println("회원탈퇴");
			service.delid();
			break;
			
			
		case"/memberdel":
			System.out.println("회원탈퇴");
			service.memberdel();
			break;
		
			
			
			
		
		}
	}
}

