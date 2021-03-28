package com.sns.service;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sns.dao.MemberDAO;
import com.sns.dto.MemberDTO;


public class MemberService {
	
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	String page = "";
	String msg = "";	
	RequestDispatcher dis = null;
	MemberDAO dao = null;
	
	
	
	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
		// 서비스 객체화 하자마자 dao 호출하지말것 (안쓸 수 도 있으니)
	}
	
	
	public void login() throws ServletException, IOException {
		
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id+"/"+pw);
		String loginId = req.getParameter("userId");
		System.out.println(loginId);
		page = "index.jsp";
		msg = "아이디와 비밀번호를 확인해 주세요.";
		dao = new MemberDAO();
		boolean success=dao.login(id,pw);
		
		if(loginId.indexOf("admin") > -1){
		System.out.println("어드민 로그인");
		page = "admin_login";
		req.getSession().setAttribute("loginId", loginId);
		}else {
		if (success) {			
			if (checkBlackList()) {
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
		System.out.println("로그인결과"+success);
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	
	public boolean checkBlackList() throws ServletException, IOException{
		dao = new MemberDAO();
		String id = req.getParameter("userId");
		String blackListState= dao.findBlackListbyuserId(id);
		System.out.println("블랙리스트여부"+blackListState);
		if(blackListState.equals("FALSE")){
			return false;
		}
		return true;
		
	}
	
//	public ArrayList<MemberDTO> main() {
//		MemberDAO dao = new MemberDAO();
//		ArrayList<MemberDTO> list = dao.list();
//		return list;
//	}
	
	
	public void join() throws ServletException, IOException {
		
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		String name=req.getParameter("name");
		String phone=req.getParameter("phone");
		String email=req.getParameter("email");
		System.out.println(id+pw+name+phone+email);
		String join =req.getParameter(id+pw+name+phone+email);
		System.out.println(req.getParameter(join));
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setPhone(phone);
		dto.setEmail(email);
		dao = new MemberDAO();
		boolean success=dao.join(dto);
		
		
		msg = "회원가입에 실패 했습니다.";
		page = "joinForm.jsp";

		if (success) {// 성공
			msg = "회원가입을 축하 드립니다.";
			page = "index.jsp";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	

	}

public boolean idChk() throws ServletException, IOException {
	
	String id = req.getParameter("id");
	boolean success = false;
	System.out.println("id : "+id);
	dao = new MemberDAO();
	
	
	HashMap<String, Object> map = new HashMap<String, Object>();
			
	
		success = dao.idChk(id);
		System.out.println("아이디 사용여부 : "+success);			
	
		
							
		map.put("use", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
	
		resp.getWriter().print(json);
	
	return success;
}
//---------------------------------------------------------------//아이디 찾기.

/*아이디 찾기*/
public void idfind() throws ServletException, IOException {
	
	String name = req.getParameter("name");
	String phone = req.getParameter("phone");
	System.out.println(name + "/" + phone);			
	HashMap<String, Object> map = new HashMap<String, Object>();
	String id = "";
	dao = new MemberDAO();

		id = dao.idfind(name, phone);
		System.out.println("아이디찾기 : "+id);

		map.put("use", id);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);
		// 페이지에 그려주는것.
		
	

}

/*아이디찾기 두번째 요청*/
public void idfind1() throws ServletException, IOException{
	
	String id = req.getParameter("id");
	System.out.println(id);	
	page="id_result.jsp";

	req.setAttribute("idfind", id);
	dis = req.getRequestDispatcher(page);
	dis.forward(req, resp);
	
}






//----------------------------------------------------------------------//비밀번호 찾기.


/*비밀번호 찾기*/
public void pwfind() throws ServletException, IOException{
	
	
	String id = req.getParameter("userId");
	String name = req.getParameter("userName");
	String phone = req.getParameter("userPhone");
	System.out.println("여기냐"+id+"/"+name+"/"+phone);
	
	dao = new MemberDAO();
    msg = "아이디, 이름, 전화번호를 다시 확인 후 입력해주세요.";
	
	page = "pw_Find.jsp";
	
	if(dao.pwfind(id, name, phone)) {
		page = "pw_reset.jsp";
		msg = "비밀번호를 수정해주세요.";
		req.setAttribute("id", id);
	}
	req.setAttribute("msg", msg);
	dis = req.getRequestDispatcher(page);
	dis.forward(req, resp);
	
}

/*비밀번호 찾기 후 수정*/
public void pwupdate() throws ServletException, IOException {
	dao = new MemberDAO();
	
	boolean success = false;
	String newPw = req.getParameter("newPw");
	String id = req.getParameter("id");
	System.out.println("새비밀번호: "+newPw);
	
	msg="비밀번호를 다시 확인해주세요.";
	page = "pw_Find.jsp";
	
	success = dao.pwupdate(id,newPw);
	System.out.println("비밀번호 수정 : " + success);
	if(success) {
		msg="비밀번호가 수정 되었습니다.";
		page="index.jsp";
	}
	req.setAttribute("msg", msg);
	dis=req.getRequestDispatcher(page);
	dis.forward(req, resp);
	}
//-----------------------------------------------------------------------------//정보수정 접근시 패스워드 확인.
   
public void infopw() throws ServletException, IOException {
	dao = new MemberDAO();
	String id = (String) req.getSession().getAttribute("loginId");
	String pw = req.getParameter("userPw");
	
	boolean inpw = dao.infopw(id,pw);
	
	System.out.println(inpw);
	
	page = "MyProfile.jsp";
	msg = "패스워드를 다시 입력해 주세요.";
	
	if(inpw) {
		page = "userinfo";
		msg = "회원정보 수정!!";
		
		req.getSession().setAttribute("pw", pw);
		req.getSession().setAttribute("id", id);
	}
	req.setAttribute("msg", msg);
	dis = req.getRequestDispatcher(page);
	dis.forward(req,resp);
}

		

//------------------------------------------------------------//회원정보 창
    
  public MemberDTO userinfo() throws ServletException, IOException{
		String id = (String)req.getSession().getAttribute("loginId");
		
		System.out.println("상세보기 할 아이디 : "+id);
//		if(id ==null) {
//			id = (String) req.getAttribute("id");
//		}
		
		
		dao = new MemberDAO();		
		return dao.userinfo(id);
	}

		
//------------------------------------------------------------//회원정보 업데이트


  public int userinfoupdate() throws ServletException, IOException {
		
		String id=req.getParameter("userId");
		String pw=req.getParameter("userPw");
		String name=req.getParameter("userName");
		String email=req.getParameter("email");
		String phone=req.getParameter("phone");
		
		System.out.println(id+"/"+pw+"/"+name+"/"+email+"/"+phone);
		dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setEmail(email);	
		dto.setPhone(phone);

		return dao.userinfoupdate(dto);
}
//----------------------------------------------------------
  public void delid() throws ServletException, IOException {
		String id = (String) req.getSession().getAttribute("loginId");
		MemberDTO dto= new MemberDTO();
		dto.setId(id);
		
		
		
		dao = new MemberDAO();
		
		boolean delid= dao.delid(dto);

		page="main.jsp";

		if(delid){
			System.out.println("성공");
			page="logout";
			msg = "탈퇴 되었습니다..";
		}else {
			System.out.println("실패");
		}

		
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
		
	}
  
//회원탈퇴 시 패스워드 확인.
  
  public void memberdel() throws ServletException, IOException {
		dao = new MemberDAO();
		String id = (String) req.getSession().getAttribute("loginId");
		String pw = req.getParameter("userPw");
		
		boolean inpw = dao.infopw(id,pw);
		
		System.out.println(inpw);
		
		page = "MyProfile.jsp";
		msg = "패스워드를 다시 입력해 주세요.";
		
		if(inpw) {
			page = "userinfo";
			msg = "비밀번호를 입력해 주세요";
			
			req.getSession().setAttribute("pw", pw);
			req.getSession().setAttribute("id", id);
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher(page);
		dis.forward(req,resp);
	}


}