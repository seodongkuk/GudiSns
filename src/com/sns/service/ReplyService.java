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
import com.sns.dao.ReplyDAO;
import com.sns.dto.ReplyDTO;

public class ReplyService {

	HttpServletRequest req = null;	
	HttpServletResponse resp = null;
	ReplyDAO dao = null;
	
	public ReplyService(HttpServletRequest req, HttpServletResponse resp) {
		this.req=req;
		this.resp=resp;
		dao=new ReplyDAO();
	}
	
	public void rwrite() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");
		String user_id = req.getParameter("user_id");
		String content = req.getParameter("content");
		System.out.println(board_idx+"/"+user_id+"/"+content);
		String page="/detail?board_idx="+board_idx;
		String msg="";
		boolean write = dao.write(board_idx,user_id,content);
		
		System.out.println("댓글내용없음");
		msg="댓글에 내용을 넣어주세요.";
		
		if(write) {
		  msg="댓글 작성을 완료했습니다.";
		 }
		req.setAttribute("msg", msg);
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	public void redit() throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		int cmt_idx = Integer.parseInt(req.getParameter("idx"));
		String content = req.getParameter("content");
		System.out.println("댓글번호 : "+cmt_idx+"/ 댓글 내용 : "+content);
		
		String result = dao.edit(content,cmt_idx);
		System.out.println("수정한 내용 : "+result);
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("edit",result);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		
		System.out.println(json);
		
		resp.setContentType("text/html charset-UTF-8");
		resp.getWriter().println(json);
		
	}
	
	public void rdel() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");
		String cmt_idx = req.getParameter("cmt_idx");
		System.out.println("delete idx : "+cmt_idx);
		String page="detail?board_idx="+board_idx;
		String msg="삭제에 실패하였습니다.";
		
		if(dao.del(cmt_idx)) {
			page="detail?board_idx="+board_idx;
			msg="삭제에 성공하였습니다.";
		}
		req.setAttribute("msg", msg);
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void rlist() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");
		ArrayList<ReplyDTO> list= dao.list(board_idx);
		System.out.println("댓글리스트사이즈:"+list.size());
		req.setAttribute("list", list);
		RequestDispatcher dis = req.getRequestDispatcher("detail.jsp");
		dis.forward(req, resp);
	}

}
