package com.sns.service;
import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;

public class MemberService {
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	public boolean login() {
		MemberDAO dao = new MemberDAO();
		String id = req.getParameter("userid");
		String pw = req.getParameter("userpw");
		System.out.println(id+"/"+pw);
		return dao.login(id, pw);				
	}
	public ArrayList<MemberDTO> main() {
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberDTO> list = dao.list();
		return list;
	}
public boolean join() {
		
		
		String id=req.getParameter("userId");
		String pw=req.getParameter("userPw");
		String name=req.getParameter("userName");
		String phone=req.getParameter("userPhone");
		String email=req.getParameter("email");
		System.out.println(id+"/"+pw+"/"+name+"/"+phone+"/"+email);
		
		MemberDAO dao = new MemberDAO();
		
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setPhone(Integer.parseInt(phone));
		dto.setEmail(email);		
		
		if(dao.join(dto)>0) {
			return true;
		}else {
			return false;
		}
		
	}
}