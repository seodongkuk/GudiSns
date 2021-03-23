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

	//DM_Room.jsp에서 작성한 내용을 WebChatServer에서 받은 다음 DmService가 요청을 받음
	//여기에서는 특정 인덱스 번호에 DM내용을 추가하는 역할을 한다(보내는 사람과 받는 사람 포함)
	public void newMsg(String fromId, String toId, String content) throws ServletException, IOException {

		//메시지를 잘 전달 받았는지 체크..
		System.out.println("보내는 메시지 :"+content+"보낸 사람 : "+fromId+"받는 사람 : "+toId);
		
		DmDAO dao = new DmDAO();
		//채팅방을 만들기 위해 dao한테 요청한다.
		HashMap<String,Object> map = dao.createChat(fromId,toId);
		
		//이미 만들어진 방이여도 DM 내용을 INSERT하고
		if((boolean) map.get("success")) {
			System.out.println("현재 존재하는 방입니다.");
			dao = new DmDAO();
			dao.updateChat(fromId,toId,content);
		//처음 만들어진 방이여도 DM 내용을 INSERT 한다.
		}else {
			dao = new DmDAO();
			dao.updateChat(fromId,toId,content);
		}
		
	}

	//로그인한 사람의 채팅방을 가져오는 역할
	public void chatRoom() throws ServletException, IOException{
		
		String loginId = (String) req.getSession().getAttribute("loginId");
		String id = req.getParameter("id");
		
		//만약 URL에 직접 아이디를 입력했을때를 대비해서 세션 아이디와 URL 파라메터의 아이디를 비교해서 일치하면 chatList 정상 실행
		if(loginId.equals(id)) {
			DmDAO dao = new DmDAO();
			
			//현재 채팅방 리스트를 가져오기 위해 DAO 한테 요청
			HashMap<String,Object> map = dao.chatRoom(id);
			
			//반환받은 map의 키를 값에 담는다.
			req.setAttribute("success", map.get("success"));
			req.setAttribute("list", map.get("list"));
			
			System.out.println(map.get("list"));
			
			RequestDispatcher dis = req.getRequestDispatcher("ChatList.jsp?id="+id);
			dis.forward(req, resp);
		//임의의 값으로 들어오려고 했으니 내보낸다.
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void dmRoom() throws ServletException, IOException{
		
		String loginId = (String) req.getSession().getAttribute("loginId");

		DmDAO dao = new DmDAO();
		
		//상대방
		String id = req.getParameter("id");
		//나
		String create = req.getParameter("create");
		String idx = req.getParameter("idx");
		
		System.out.println("DM 정보"+id+"/"+create+"/"+idx);
		
		//나를 유저 검색해서 나한테 보내려고 하면 못하게 팅겨보냄
		if(create.equals(id)) {
			resp.sendRedirect("./flist");
		}else {
		
		//현재 방의 내용을 모두 가져온다.
		HashMap<String,Object> map = dao.dmRoom(id,create,idx);
		
		//혹시라도 URL 파라메터에 직접 입력해서 접근하려고 하면.. 일치 여부 확인후 내보낸다.
//		if(!(boolean) map.get("success_chk")) {
//			System.out.println("잘못된 접근입니다.");
//			resp.sendRedirect("./index.jsp");
//		//정상적인 경로로 들어왔다면 메인 구문 실행!!!
//		}else {
		
		//dao 자원 정리되었으니 다시 객체화 해줌
		dao = new DmDAO();
		//로그인한 유저가 dm룸에 들어갔을때 상대방 글을 읽음 처리
		boolean success = dao.readUpdate(loginId,idx);
		String page = "";
		
		//DM_list 를 통해 호출 받으면 해당 채팅방으로 이동된다.
		//DM_Room?id=yang0318&&create=yang4885&&idx=7 이런식으로 들어가지는데
		//request의 forward는 직전 URL을 보여주기 때문에 DM_Room.jsp는 안보이게 됨
		page = "DM_Room.jsp";
		
		//만약 유저검색을 통해서 DM 보내기를 했다면..(idx는 무조건 널값 들어오게 됨)
		if(idx == null) {
			//방번호를 만든다.
			System.out.println("방번호가 없으므로 새로 만듭니다.");
			dao = new DmDAO();
			dao.createChat(create,id);
			
			//만약.. 방번호가 있다면
			if(((boolean) map.get("success"))) {
			System.out.println("만들어진 방으로 이동합니다.");
			//방 번호를 가져온다
			int chat_idx = (int) map.get("chatIdx");
			System.out.println(chat_idx+"방으로 이동합니다.");
			//방 번호를 DM_list에 요청과 동시에 파라메터 함께 보냄
			page = "DM_list?idx="+chat_idx;
			}

		}	
		//채팅내용을 담는다.(DM에서 보여주기 위해)
		req.setAttribute("list", map.get("list"));
			
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
		
		}
	}
	
	//
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
