package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		String msg="작성 실패!";
		String page="replyForm.jsp";
		
		if(dao.write(board_idx,user_id,content)) {
			page="/detail?board_idx="+board_idx;
			msg="댓글 작성을 완료했습니다.";			
		}
		req.setAttribute("msg", msg);
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	public void redit() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");
		String cmt_idx = req.getParameter("idx");
		String user_id = req.getParameter("id");
		String content = req.getParameter("content");
		System.out.println(board_idx+"/"+cmt_idx+"/"+user_id+"/"+content);
		
		String msg="댓글 수정 실패!";
		String page="/detail?board_idx="+board_idx;
		
		
		/*
		 * int result = dao.edit(content,cmt_idx);
		 * System.out.println("댓글 수정 result"+result);
		 */
		
		/*
		 * if(result==1) { page="/detail?board_idx="+board_idx; msg="댓글 작성을 완료했습니다.";
		 * }else { page="/detail?board_idx="+board_idx; msg="댓글 작성을 완료했습니다."; }
		 */
		 
		req.setAttribute("msg", msg);
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}
	
	public void rdel() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");
		String cmt_idx = req.getParameter("cmt_idx");
		System.out.println("delete idx : "+cmt_idx);
		String page="/";
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
