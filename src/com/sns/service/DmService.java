package com.sns.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.DmDAO;
import com.sns.dto.DmDTO;

public class DmService {

	//생성자에서 가져온 것을 넣기위해 필드로 만들어줌
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	String msg = "";
	RequestDispatcher dis = null;

	public DmService(HttpServletRequest req, HttpServletResponse resp) {
		//전달 받은 인자값을 모두 저장한다(안그러면 저장이 안된다..)
		//헷갈릴 수 있으니 this 키워드를 통해 현재 클래스를 지칭해준다.
		this.req = req;
		this.resp = resp;
	}

	public void newMsg(String fromId, String toId, String content) throws UnsupportedEncodingException {

		//String id = (String) req.getSession().getAttribute("loginId");
		
		//System.out.println("보내는사람 아이디:"+id);
		System.out.println("보내는 메시지 :"+content);
		
		DmDAO dao = new DmDAO();
		
		HashMap<String,Object> map = dao.createChat(fromId,toId);
		
		if((boolean) map.get("success")) {
			System.out.println("현재 존재하는 방입니다.");
			dao = new DmDAO();
			dao.updateChat(fromId,toId,content);
		}else {
			dao = new DmDAO();
			dao.updateChat(fromId,toId,content);
		}
		
	}

	public void chatRoom() throws ServletException, IOException{
		String id = req.getParameter("id");
		
		DmDAO dao = new DmDAO();
		
		HashMap<String,Object> map = dao.chatRoom(id);
		
		req.setAttribute("success", map.get("success"));
		req.setAttribute("list", map.get("list"));
		
		System.out.println(map.get("list"));
		
		RequestDispatcher dis = req.getRequestDispatcher("ChatList.jsp?id="+id);
		dis.forward(req, resp);
	}

	public void dmRoom() throws ServletException, IOException{
		
		String loginId = (String) req.getSession().getAttribute("loginId");

		DmDAO dao = new DmDAO();
		
		//상대방
		String id = req.getParameter("id");
		//나
		String create = req.getParameter("create");
		String idx = req.getParameter("idx");
		
		//현재 방의 내용을 모두 가져온다.
		HashMap<String,Object> map = dao.dmRoom(id,create,idx);
		dao = new DmDAO();
		//로그인한 유저가 dm룸에 들어갔을때 상대방 글을 읽음 처리
		boolean success = dao.readUpdate(loginId,idx);
		//DM_Room.jsp 페이지 설정
		String page = "DM_Room.jsp";
		//채팅내용을 담는다.
		req.setAttribute("list", map.get("list"));
		
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void dmList() throws ServletException, IOException{
		String idx = req.getParameter("idx");
		
		DmDAO dao = new DmDAO();
		
		//해당 글번호의 생성자와 초대받은 사람을 가져오기 위해 dao 호출
		DmDTO dm = dao.dmList(idx);
		

			String chat_idx = Integer.toString(dm.getChat_idx());
			String userId = dm.getUser_id();
			String recieveId = dm.getRecieve_id();
			
			System.out.println(chat_idx+userId+recieveId);
			
			//가져온 파라메터를 모두 담는다
			String page = "DM_Room?id="+recieveId+"&&create="+userId+"&&idx="+chat_idx;
		
		//최종 페이지가 보여져야 하기 때문에 resp 방식으로 페이지 이동
		resp.sendRedirect(page);
	}

}
