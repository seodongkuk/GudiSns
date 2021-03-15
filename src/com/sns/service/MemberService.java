package com.sns.service;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sns.dao.MemberDAO;
import com.sns.dto.MemberDTO;


public class MemberService {
	
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	
	
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
		String email=req.getParameter("userEmail");
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


public void idChk() throws IOException {
	
	String id = req.getParameter("userId");
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
}
}