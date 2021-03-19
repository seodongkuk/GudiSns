package com.sns.service;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	
	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	
	public boolean login() {
		MemberDAO dao = new MemberDAO();
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id+"/"+pw);
		return dao.login(id, pw);				
	}
	
	
	public boolean checkBlackList() {
		MemberDAO dao = new MemberDAO();
		String id = req.getParameter("userId");
		String blackListState= dao.findBlackListbyuserId(id);
		System.out.println("블랙리스트여부"+blackListState);
		if(blackListState.equals("FALSE")){
			return false;
		}
		return true;
		
	}
	
	public ArrayList<MemberDTO> main() {
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberDTO> list = dao.list();
		return list;
	}
	
	
	public boolean join() {
		boolean success = false;
		MemberDAO dao = new MemberDAO();
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		String name=req.getParameter("name");
		String phone=req.getParameter("phone");
		String email=req.getParameter("email");
		System.out.println(id+"/"+pw+"/"+name+"/"+phone+"/"+email);


	      return dao.join(id,pw,name,phone,email);
	}

public boolean idChk() throws IOException {
	
	String id = req.getParameter("id");
	boolean success = false;
	System.out.println("id : "+id);
	MemberDAO dao = new MemberDAO();
	
	
	HashMap<String, Object> map = new HashMap<String, Object>();
			
	try {
		success = dao.idChk(id);
		System.out.println("아이디 사용여부 : "+success);			
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		
		dao.resClose();						
		map.put("use", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
	
		resp.getWriter().print(json);
	}
	return success;
}
//---------------------------------------------------------------//아이디 찾기.

public void idfind()throws ServletException, IOException{
	MemberDAO dao = new MemberDAO();
	
	String name=req.getParameter("userName");
	String phone = req.getParameter("userPhone");
	System.out.println(name + "/"+ phone);
	
	HashMap<String, Object> map = dao.idfind(name,phone);
	

	String id = (String) map.get("id");
	Date reg_date = (Date)map.get("reg_date");
	

	System.out.println("아이디 찾기:"+id);
	
	
	page = "id_Find.jsp";
	msg = "이름, 핸드폰 번호를 다시 입력해 주세요.";
	
	   if(id!="") {
		page = "id_result.jsp";
		msg = name+"님의 아이디는"+id+"입니다.";
		
		req.getSession().setAttribute("id", id);
		req.getSession().setAttribute("reg_date", reg_date);
	
	
	}
	req.setAttribute("msg", msg);
	dis = req.getRequestDispatcher(page);
	dis.forward(req,resp);
}


//----------------------------------------------------------------------//비밀번호 찾기.


public void pwfind()throws ServletException, IOException{
	MemberDAO dao = new MemberDAO();
	
	String id=req.getParameter("userId");
	String email = req.getParameter("userEamil");
	System.out.println(id + "/"+ email);

	String pw = dao.pwfind(id,email);
	
	System.out.println("비번 찾기:"+pw);
	
	page = "pw_Find.jsp";
	msg = "아이디, 이메일을 다시 입력해 주세요.";
	
	if(pw!="") {
		page = "pw_reset.jsp";
		msg = id+"님의 비번은"+pw+"입니다.";
		
		req.getSession().setAttribute("pw", pw);
		req.getSession().setAttribute("id", id);
		req.getSession().setAttribute("email", email);
	}
	req.setAttribute("msg", msg);
	dis = req.getRequestDispatcher(page);
	dis.forward(req,resp);
}

//-------------------------------------------------------------------//비밀번호 변경.
      public int pwupdate() throws ServletException, IOException{
		//파라메터 값이 잘 들어 오는가?
		String pw=req.getParameter("userPw");
		String id = req.getParameter("userId");
		String email = req.getParameter("userEmail");
		System.out.println(pw);
		MemberDAO dao = new MemberDAO();
		
		MemberDTO dto = new MemberDTO();
		dto.setPw(pw);
		dto.setId(id);
		dto.setEmail(email);
		
		return dao.pwupdate(dto);
}
      
//-----------------------------------------------------------------------------//정보수정 접근시 패스워드 확인.
     
  public void infopw() throws ServletException, IOException {
	MemberDAO dao = new MemberDAO();
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
      
    public MemberDTO userinfo() {
		String id = (String)req.getSession().getAttribute("loginId");
		
		System.out.println("상세보기 할 아이디 : "+id);
//		if(id ==null) {
//			id = (String) req.getAttribute("id");
//		}
		
		
		MemberDAO dao = new MemberDAO();		
		return dao.userinfo(id);
	}
  
		
//------------------------------------------------------------//회원정보 업데이트


public int userinfoupdate()throws ServletException, IOException {
	
	String id=req.getParameter("userId");
	String pw=req.getParameter("userPw");
	String name=req.getParameter("userName");
	String email=req.getParameter("email");
	String phone=req.getParameter("phone");
	
	System.out.println(id+"/"+pw+"/"+name+"/"+email+"/"+phone);
	MemberDAO dao = new MemberDAO();
	
	MemberDTO dto = new MemberDTO();
	dto.setId(id);
	dto.setPw(pw);
	dto.setName(name);
	dto.setEmail(email);	
	dto.setPhone(Integer.parseInt(phone));

	return dao.userinfoupdate(dto);
}
//----------------------------------------------------------//회원탈퇴


public void memberDel() throws ServletException, IOException {
	MemberDAO dao = new MemberDAO();
	
	String id = (String)req.getSession().getAttribute("loginId");
	String pw = req.getParameter("userPw");
	
	
	boolean inpw = dao.infopw(id,pw);
	
	System.out.println(inpw);
	
	page = "MyProfile.jsp";
	msg = "패스워드를 다시 입력해 주세요.";
	
	if(inpw) {
		page = "index.jsp";
		msg = "회원탈퇴 진행!!";
		
		req.getSession().setAttribute("pw", pw);
		req.getSession().setAttribute("id", id);
	}
	req.setAttribute("msg", msg);
	dis = req.getRequestDispatcher(page);
	dis.forward(req,resp);
}

}

