package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
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
			page = "/todayTag";
			if(search == null || search == "" || search.equals(" ")) {
				msg = "검색 할 해시태그를 입력해주세요!";
				page = "/todayTag";
			}
			// 성공 할 경우
			if (list != null && list.size() > 0) {// 리스트가 널값도 아니고, 크기도 1개 이상일 경우
				msg = "";//msg 초기화
				req.setAttribute("list", list);// list 실어서
				page = "find_tag.jsp";
			}
			req.setAttribute("msg", msg);// 성공여부에 따른 msg 실어서
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		
		//유저 검색
		}else if(find.equals("User")) {
			list = dao.findTag(find,search);
			System.out.println("리스트 널값? " + list);// 반환받은 리스트 널값 확인
			System.out.println("리스트 사이즈? " + list.size());// 반환받은 리스트 사이즈 확인
			// 실패 할 경우
			msg = "검색하신 유저가 존재하지 않습니다.";
			page = "/todayTag";
			if(search == null || search == "" || search.equals(" ")) {
				msg = "검색 할 유저를 입력해주세요!";
				page = "/todayTag";
			}
			// 성공 할 경우
			if (list != null && list.size() > 0) {// 리스트가 널값도 아니고, 크기도 1개 이상일 경우
				msg = "";//msg 초기화
				page = "find_user.jsp";
				req.setAttribute("list", list);// list 실어서
				req.getSession().setAttribute("budId", search);
			}
			req.setAttribute("msg", msg);// 성공여부에 따른 msg 실어서
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else if(find.equals("none")) {
			msg = "검색 항목을 선택해주세요!";
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("/todayTag");
			dis.forward(req, resp);
		}
	}

	public void budReq() throws ServletException, IOException {
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		//String budId = (String) req.getSession().getAttribute("budId");
		String budId = req.getParameter("budId");
		System.out.println("로그인 한 아이디: "+loginId+" / "+"친구요청 한 아이디: "+budId);
		if(loginId != null) {			
			dao = new SearchDAO();
			HashMap<String, Object> map = new HashMap<String, Object>();
			int success = 0;
			System.out.println("친구요청 성공? "+success);

			// 실패 할 경우
			msg = "이미 친구요청을 한 유저 또는 상대방이 이미 친구요청을 했습니다.";
			if(loginId.equals(budId)) {
				msg = "자기 자신에게 친구요청을 할 수 없습니다.";
			}
			// 성공 할 경우
			if(dao.budReq(loginId,budId)>0) {
				msg = "친구 요청을 보냈습니다.";
				success = 1;
			}
			map.put("msg", msg);
			map.put("success", success);
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");//인코딩 타입
			resp.setHeader("Access-Contrl-Allow-origin", "*");//크로스도메인 허용
			resp.getWriter().print(json);//url에 출력
		}
	}

	public void budDel() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		//String budId = (String) req.getSession().getAttribute("budId");
		String budId = req.getParameter("budId");
		System.out.println("로그인 한 아이디: "+loginId+" / "+"친구 삭제 할 아이디: "+budId);
		
		if(loginId != null) {
			dao = new SearchDAO();
			HashMap<String, Object> map = new HashMap<String, Object>();
			int success = 0;
			// 실패
			msg = "친구 삭제에 실패했습니다.";
			// 성공 할 경우
			if(dao.budDel(loginId,budId)>0) {
				msg = "삭제를 완료했습니다.";
				success = 1;
			}
			map.put("msg", msg);
			map.put("success", success);
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");//인코딩 타입
			resp.setHeader("Access-Contrl-Allow-origin", "*");//크로스도메인 허용
			resp.getWriter().print(json);//url에 출력
		}
	}

}
