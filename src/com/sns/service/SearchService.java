package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.SearchDAO;
import com.sns.dto.SearchDTO;

public class SearchService {
	
	//공통으로 사용되므로 전역변수 선언
	HttpServletRequest req = null; 
	HttpServletResponse resp = null;
	SearchDAO dao = null;//DB필요 -> DAO 객체화	
	String page = "";
	String msg = "";	
	RequestDispatcher dis = null;
	
	public SearchService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void todayTag() throws ServletException, IOException {
		ArrayList<SearchDTO> tag = new ArrayList<SearchDTO>();
		dao = new SearchDAO();
		tag = dao.todayTag();
		
		// 성공 할 경우
		if(tag != null && tag.size()>0) {
			System.out.println(tag);
			req.setAttribute("tag", tag);
		}
		dis = req.getRequestDispatcher("find.jsp");
		dis.forward(req, resp);
	}	
	
	public void find() throws ServletException, IOException {
		ArrayList<SearchDTO> list = new ArrayList<SearchDTO>();
		dao = new SearchDAO();
		String find = req.getParameter("find");
		String search = req.getParameter("search");
		System.out.println("검색 항목: "+find);
		System.out.println("검색 내용: "+search);
		
		//해시태그 검색
		if(find.equals("HashTag")) {
			list = dao.findTag(find,search);
			System.out.println("리스트 널값? " + list);// 반환받은 리스트 널값 확인
			System.out.println("리스트 사이즈? " + list.size());// 반환받은 리스트 사이즈 확인
			// 실패 할 경우
			msg = "검색한 해시태그의 게시글이 없습니다.";
			if(search == null || search == "" || search.equals(" ")) {
				msg = "검색 할 해시태그를 입력해주세요!";
			}
			// 성공 할 경우
			if (list != null && list.size() > 0) {// 리스트가 널값도 아니고, 크기도 1개 이상일 경우
				msg = "";//msg 초기화
				req.setAttribute("list", list);// list 실어서
			}
			req.setAttribute("msg", msg);// 성공여부에 따른 msg 실어서
			dis = req.getRequestDispatcher("find_tag.jsp");
			dis.forward(req, resp);
		
		//유저 검색
		}else if(find.equals("User")) {
			list = dao.findTag(find,search);
			System.out.println("리스트 널값? " + list);// 반환받은 리스트 널값 확인
			System.out.println("리스트 사이즈? " + list.size());// 반환받은 리스트 사이즈 확인
			// 실패 할 경우
			msg = "검색하신 유저가 존재하지 않습니다.";
			if(search == null || search == "" || search.equals(" ")) {
				msg = "검색 할 유저를 입력해주세요!";
			}
			// 성공 할 경우
			if (list != null && list.size() > 0) {// 리스트가 널값도 아니고, 크기도 1개 이상일 경우
				msg = "";//msg 초기화
				req.setAttribute("list", list);// list 실어서
			}
			req.setAttribute("msg", msg);// 성공여부에 따른 msg 실어서
			dis = req.getRequestDispatcher("find_user.jsp");
			dis.forward(req, resp);
		}else if(find.equals("none")) {
			msg = "검색 항목을 선택해주세요!";
			req.setAttribute("msg", msg);// 성공여부에 따른 msg 실어서
			dis = req.getRequestDispatcher("find.jsp");
			dis.forward(req, resp);
		}
	}

	

}
