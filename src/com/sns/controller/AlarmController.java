package com.sns.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.AlarmService;
import com.sns.service.MainService;
@WebServlet({"/alarmList","/buddyAdd","/buddyIgnore","/alarmAllChk","/alarmSet","/likeAlarmDel","/dmAlarmDel","/commentAlarmDel"})
public class AlarmController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}



	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		
		String sub = uri.substring(ctx.length());
		System.out.println(sub);
		
		
		AlarmService alarm = new AlarmService(req, resp);
		
		
		req.setCharacterEncoding("UTF-8");
		
		switch(sub) {
		
			case "/alarmList":
				System.out.println("알람 리스트 호출");
				alarm.alarmList();
				break;
				
			case "/buddyAdd":
				System.out.println("친구추가 요청");
				alarm.buddyAdd();
				break;
				
			case "/buddyIgnore":
				System.out.println("친구추가 요청 무시하기");
				alarm.buddyIgnore();
				break;
				
			case "/alarmAllChk":
				System.out.println("모든 알림 삭제 요청(친구요청 제외)");
				alarm.alarmAllChk();
				break;
				
			case "/alarmSet":
				System.out.println("알람 셋팅 요청");
				break;
				
			case "/likeAlarmDel":
				System.out.println("추천 알림 삭제 요청");
				alarm.alarmLikeDel();
				break;
				
			case "/dmAlarmDel":
				System.out.println("DM 알림 삭제 요청");
				alarm.alarmDmDel();
				break;
				
			case "/commentAlarmDel":
				System.out.println("댓글 알림 삭제 요청");
				alarm.alarmCommentDel();
				break;
		}
		
		
	}
	
}
