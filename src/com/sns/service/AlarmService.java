package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.AlarmDAO;
import com.sns.dto.AlarmDTO;

public class AlarmService {
	
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;

	public AlarmService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	//현재 존재하는 알람 리스트와 알림 수신 여부 리스트를 가져오는 메서드
	public void alarmList() throws ServletException, IOException{
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		String loginId = req.getParameter("id");
		System.out.println("알람 리스트 호출 대상 아이디 : "+loginId);
		
		//파라메터 아이디가 로그인한 유저가 아니면 내보냄
		if(loginId.equals(sessionId)) {
		AlarmDAO dao = new AlarmDAO();
		HashMap<String, Object> map = dao.alarmList(loginId);
		//알림리스트와 셋팅값을 가져온다.
		ArrayList<AlarmDTO> alarmList = (ArrayList<AlarmDTO>) map.get("list");
		ArrayList<AlarmDTO> setting = (ArrayList<AlarmDTO>) map.get("setting");
		

		req.setAttribute("list", alarmList);
		req.setAttribute("setting", setting);
		dis = req.getRequestDispatcher("AlarmList.jsp?id="+loginId);
		dis.forward(req, resp);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void buddyAdd() throws ServletException, IOException{
		String userId = req.getParameter("myId");
		String buddyId = req.getParameter("budId");
		
		System.out.println("내 아이디 : "+userId+"친구요청 아이디 : "+buddyId);
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
		AlarmDAO dao = new AlarmDAO();
		int success = dao.buddyAdd(userId,buddyId);
		
		req.setAttribute("success", success);
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void buddyIgnore() throws ServletException, IOException{

		String userId = req.getParameter("myId");
		String buddyId = req.getParameter("budId");
		
		System.out.println("내 아이디 : "+userId+"친구요청 무시 아이디 : "+buddyId);
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
		
		AlarmDAO dao = new AlarmDAO();
		int success = dao.buddyIgnore(userId,buddyId);
		
		req.setAttribute("success", success);
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void alarmAllChk() throws ServletException, IOException{
		
		String userId = req.getParameter("myId");
		
		System.out.println("친구요청알림을 제외한 모든 알림을 리스트에서 읽음 처리 합니다.");
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmAllChk(userId);
		
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void alarmLikeDel() throws ServletException, IOException{

		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("내 글에 달린 좋아요 알림 삭제합니다.");
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmLikeDel(userId,idx);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void alarmDmDel() throws ServletException, IOException{
		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("dm 알림 삭제합니다.");
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
	
		AlarmDAO dao = new AlarmDAO();
		dao.alarmDmDel(userId,idx);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}

	public void alarmCommentDel() throws ServletException, IOException{
		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("내 글의 댓글 알림 삭제합니다.");
		
		String sessionId = (String) req.getSession().getAttribute("loginId");
		
		//세션 처리(파라메터 내 아이디값과 세션 아이디와 일치하지 않으면 내보냄
		if(userId.equals(sessionId)) {
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmCommentDel(userId,idx);
		}else {
			resp.sendRedirect("./index.jsp");
		}
	}
	public void alarmSetting() throws ServletException, IOException {
		//sql NOT IN 명령어에 인식하려면 한 칸 띄워야 함
		String arr1 = " ";
		String arr2 = " ";
		String arr3 = " ";
		String arr4 = " ";
		
		String[] array = new String[] {"","","",""};
				
		array = req.getParameterValues("alarmChk");
		String loginId = (String) req.getSession().getAttribute("loginId");

		//만약 체크된 값이 하나도 없다면
		if(array == null) {
			System.out.println("alarm_state 모두 0으로 바꿈");
			AlarmDAO dao = new AlarmDAO();
			dao.alarmSetting(loginId,arr1,arr2,arr3,arr4);
		//체크된 값이 하나라도 있다면 담는다
		}else {
		
			for(int i=0;i<array.length;i++) {
				if(i == 0) {
					arr1 = array[i];
				}
				else if(i == 1) {
					arr2 = array[i];
				}
				else if(i == 2) {
					arr3 = array[i];
				}else {
					arr4 = array[i];
				}
				
			}
		
			System.out.println("alarm_state 업데이트...");
			//알람 상태값 업데이트....
			AlarmDAO dao = new AlarmDAO();
			dao.alarmSetting(loginId,arr1,arr2,arr3,arr4);
		}
			//작업이 모두 끝나면 알람리스트로 이동
			resp.sendRedirect("alarmList?id="+loginId);
		}
	

}
