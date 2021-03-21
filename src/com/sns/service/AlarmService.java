package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;

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

	public void alarmList() throws ServletException, IOException{
		
		String loginId = req.getParameter("id");
		System.out.println("알람 리스트 호출 대상 아이디 : "+loginId);
		
		AlarmDAO dao = new AlarmDAO();
		ArrayList<AlarmDTO> alarmList = dao.alarmList(loginId);
		

		req.setAttribute("list", alarmList);
		dis = req.getRequestDispatcher("AlarmList.jsp?id="+loginId);
		dis.forward(req, resp);
		
	}

	public void buddyAdd() throws ServletException, IOException{
		String userId = req.getParameter("myId");
		String buddyId = req.getParameter("budId");
		
		System.out.println("내 아이디 : "+userId+"친구요청 아이디 : "+buddyId);
		
		AlarmDAO dao = new AlarmDAO();
		int success = dao.buddyAdd(userId,buddyId);
		
		req.setAttribute("success", success);
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
	}

	public void buddyIgnore() throws ServletException, IOException{

		String userId = req.getParameter("myId");
		String buddyId = req.getParameter("budId");
		
		System.out.println("내 아이디 : "+userId+"친구요청 무시 아이디 : "+buddyId);
		
		AlarmDAO dao = new AlarmDAO();
		int success = dao.buddyIgnore(userId,buddyId);
		
		req.setAttribute("success", success);
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
	}

	public void alarmAllChk() throws ServletException, IOException{
		
		String userId = req.getParameter("myId");
		
		System.out.println("친구요청알림을 제외한 모든 알림을 리스트에서 읽음 처리 합니다.");
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmAllChk(userId);
		
		dis = req.getRequestDispatcher("alarmList?id="+userId);
		dis.forward(req, resp);
	}

	public void alarmLikeDel() throws ServletException, IOException{

		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("좋아요 알림 삭제합니다.");
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmLikeDel(userId,idx);
	}

	public void alarmDmDel() {
		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("dm 알림 삭제합니다.");
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmDmDel(userId,idx);
		
	}

	public void alarmCommentDel() {
		String userId = req.getParameter("myId");
		String idx = req.getParameter("idx");
		
		System.out.println("좋아요 알림 삭제합니다.");
		
		AlarmDAO dao = new AlarmDAO();
		dao.alarmCommentDel(userId,idx);
		
	}

}
