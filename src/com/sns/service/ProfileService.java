package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sns.dao.MainDAO;
import com.sns.dao.ProfileDAO;
import com.sns.dto.MainDTO;
import com.sns.dto.SearchDTO;

public class ProfileService {
	
	//생성자에서 가져온 것을 넣기위해 필드로 만들어줌
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	String msg = "";
	RequestDispatcher dis = null;

	public ProfileService(HttpServletRequest req, HttpServletResponse resp) {
		//전달 받은 인자값을 모두 저장한다(안그러면 저장이 안된다..)
		//헷갈릴 수 있으니 this 키워드를 통해 현재 클래스를 지칭해준다.
		this.req = req;
		this.resp = resp;
	}

	public void upload() {
		
	}

	//타인 게시글의 아이디를 눌렀을 때 해당 아이디의 프로필로 넘어가는 서비스
	public void otherProfile() throws ServletException, IOException{
		
		String id = req.getParameter("id");
		System.out.println("요청한 타인 프로필 아이디 : "+id);
		
		String idx = req.getParameter("board_idx");
		System.out.println("타인 id: "+id+"idx: "+idx);
		
		ProfileDAO dao = new ProfileDAO();
		ArrayList<MainDTO> list = dao.otherlist(id);
		System.out.println(list.size());
		if (list != null && list.size() > 0) {
			req.setAttribute("list", list);
		}
		req.setAttribute("id", id);
		dis = req.getRequestDispatcher("othersProfile.jsp");
		dis.forward(req, resp);
	}
	
	public void mylist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
	
		String idx = req.getParameter("board_idx");
		System.out.println(loginId+"/"+idx);
		
		ProfileDAO dao = new ProfileDAO();
		
		//페이징
		String pageParam = req.getParameter("page");
		System.out.println("page: "+pageParam);
		
		//1페이지 그룹 -> 1~10번
		int group = 1;
		if(pageParam != null) {
			group = Integer.parseInt(pageParam);
		}
		HashMap<String, Object> map = dao.budlist(loginId, group);
		
		dao = new ProfileDAO();
		ArrayList<MainDTO> list = dao.mylist(loginId);
		System.out.println("마이리스트 사이즈: "+list.size());
		System.out.println("친구리스트"+map.size());
		String msg = "게시글없음";
		if (list != null && list.size() > 0) {
			req.setAttribute("list", list);
			msg = loginId;
		}
		req.setAttribute("msg", msg);
		//페이징
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("budlist", map.get("budlist"));
		req.setAttribute("currPage", group);
		
		dis = req.getRequestDispatcher("MyProfile.jsp");
		dis.forward(req, resp);
	}

}