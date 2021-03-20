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
	,"/pwupdate","/infopw","/userinfoupdate","/userinfo","/memberDel/"})
public class MemberController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			System.out.println("로그인 요청");
			boolean success = service.login();
			System.out.println("로그인 결과 : " + success);
			
			String loginId = req.getParameter("userId");

			page = "index.jsp";
			msg = "아이디와 비밀번호를 확인해 주세요.";

				
			
			if(loginId.indexOf("admin") > -1){
			System.out.println("어드민 로그인");
			page = "admin_login";
			req.getSession().setAttribute("loginId", loginId);
			}else {
			if (success) {			
				if (service.checkBlackList()) {
					page = "index.jsp";
					msg = loginId + "블랙리스트 입니다.";
					req.getSession().setAttribute("loginId", loginId);
				} else {
					page = "/flist";
					msg = loginId + "님, 반갑습니다.";
					req.getSession().setAttribute("loginId", loginId);
					System.out.println(loginId.indexOf("admin"));				
				}
			}
			}
			
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			break;
//---------------------------------------------------------------------------		
		case "/logout":
			req.getSession().removeAttribute("loginId");
			resp.sendRedirect("index.jsp");
			System.out.println(session.getAttribute("userId"));				//세션 널값 처리
			break;
//---------------------------------------------------------------------------			
//		case "/main":
//			
//			String loginId = req.getParameter("userId");
//			System.out.println("main Page!!!");
//			if(req.getSession().getAttribute("loginId") != null){
//				
//			MainDAO dao = new MainDAO();
//			ArrayList<MainDTO> list = dao.flist(loginId);
//			req.setAttribute("list", list);
//			dis = req.getRequestDispatcher("main.jsp");
//			dis.forward(req, resp);
//			}else {
//				resp.sendRedirect("./index.jsp");
//			}
//			break;
//---------------------------------------------------------------------------			
		case "/join":
			System.out.println("회원가입 요청");

			msg = "회원가입에 실패 했습니다.";
			page = "joinForm.jsp";

			if (service.join()) {// 성공
				msg = "회원가입을 축하 드립니다.";
				page = "index.jsp";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
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
		
		case "/pwfind":
			System.out.println("비번 찾기요청");	
			service.pwfind();
			break;
			
		case "/pwupdate":
			System.out.println("패스워드 변경 요청");
			msg="변경에 실패 했습니다.";
			page="pw_Find";
		
			if(service.pwupdate() > 0) {//성공
				msg="변경 완료 되었습니다.";
				page="index.jsp";
			}
			System.out.println("page : "+page);
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);				
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
			page="main";				
			if(dto != null) {
				msg="개인정보";
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
			page="MyProfile.jsp";
			if(service.userinfoupdate()==0) {//성공
				msg="수정에 성공 하였습니다.";
				page="main";
			}
			System.out.println("page : "+page);
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);				
			break;
			
			
			
		case "/memberDel": 
	
		System.out.println("회원 삭제 요청");
		service.memberDel();
		break;
		
		}
	}
}

