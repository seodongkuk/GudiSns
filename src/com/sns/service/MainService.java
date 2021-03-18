package com.sns.service;

import com.sns.dao.MainDAO;
import com.sns.dao.ReplyDAO;
import com.sns.dto.MainDTO;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainService {
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	RequestDispatcher dis = null;

	public MainService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void newWrite() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if (loginId != null) {
			FileService fileupload = new FileService(req);
			MainDTO dto = fileupload.regist();
			System.out.println(dto.getOriFileName() + "<<원래명" + dto.getNewFileName() + "<<CTM한명");
			MainDAO dao = new MainDAO();
			String page = "newWriting.html";
			String msg = "글 등록에 실패 하였습니다.";
			long idx = dao.write(dto ,loginId);
			if (idx > 0) {
				page = "/flist";
				msg = "글 등록에 성공 하였습니다.";
			}

			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("");
		}

	}

	public void writeEdit() throws ServletException, IOException {
		String idx = req.getParameter("board_idx");
		System.out.println(idx + "글번호 ");
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(idx);
		String page = "/MyProfile.jsp";
		if (dto != null) {
			page = "/writingEdit.jsp";
			req.setAttribute("dto", dto);
		}

		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void edit() throws IOException {
		FileService upload = new FileService(req);
		MainDTO dto = upload.regist();
		MainDAO dao = new MainDAO();
		dao.edit(dto);
		
		if (dto.getOriFileName() != null) {
			int idx = dto.getBoard_idx();
			System.out.println(idx+"업로드할이전");
			
			dao = new MainDAO();
			
			String delFileName = dao.getFileName(String.valueOf(idx));
			System.out.println("삭제할파일"+delFileName);
			
			dao = new MainDAO();
			
			dao.updateFileName(delFileName, dto);
			
			if (delFileName != null) {
				upload.delete(delFileName);
			}
		}
		resp.sendRedirect("detail?idx="+dto.getBoard_idx());
	}

	public void mylist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		System.out.println(loginId);
		
		
		MainDAO dao = new MainDAO();
		ArrayList<MainDTO> list = dao.mylist();
		System.out.println(list.size());
		String msg = "게시글없음";
		if (list != null && list.size() > 0) {
			req.setAttribute("list", list);
			msg = "";
		}

		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("MyProfile.jsp");
		dis.forward(req, resp);
	}

	public void del() throws IOException {
		String idx = this.req.getParameter("board_idx");
		System.out.println("delete idx => " + idx);
		FileService upload = new FileService(this.req);
		MainDAO dao = new MainDAO();
		String newFileName = dao.getFileName(idx);
		dao = new MainDAO();
		int success = dao.del(idx, newFileName);
		if (success > 0 && newFileName != null) {
			System.out.println("파일삭제");
			upload.delete(newFileName);
		}

		this.resp.sendRedirect("main.jsp");
	}

	public void detail() throws ServletException, IOException {
		String idx = req.getParameter("board_idx");
		
		String loginId = (String) req.getSession().getAttribute("");
		System.out.println(loginId);
		System.out.println(idx + "글번호");
		
		
		String page = "/main.jsp";
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(idx);
		if (dto != null) {
			new MainDAO();
			page = "/detail.jsp";
			req.setAttribute("dto", dto);
		}

		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void flist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		String board_idx = req.getParameter("board_idx");
		
		System.out.println("loginId:"+loginId+"board_idx : "+board_idx);
		
	
		ReplyDAO rao = new ReplyDAO();
		int rcnt = rao.rcnt(board_idx);
		
		MainDAO dao = new MainDAO();
		ArrayList<MainDTO> flist = dao.flist(loginId);
		System.out.println(flist.size());
		String msg = "친구없음";
		if (flist != null && flist.size() > 0) {
			req.setAttribute("flist", flist);
			req.setAttribute("rcnt", rcnt);
			msg = "";
		}
		
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("main.jsp");
		dis.forward(req, resp);
		
	}
}
