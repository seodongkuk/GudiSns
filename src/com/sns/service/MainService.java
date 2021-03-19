package com.sns.service;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sns.dao.MainDAO;
import com.sns.dao.ReplyDAO;
import com.sns.dto.MainDTO;
import com.sns.dto.ReplyDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
			String page = "/flist";
			String msg = "글 등록에 실패 하였습니다.";
			long idx = dao.write(dto);
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
		String filename = req.getParameter("NewFileName");
		System.out.println("글번호: " +idx + "파일명 :"+filename);
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(idx);
		String page = "/MyProfile";
		if (dto != null) {
			page = "writingEdit.jsp";
			req.setAttribute("dto", dto);
		}

		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void edit() throws IOException, ServletException {
		FileService upload = new FileService(req);

		MainDTO dto = upload.regist();
		MainDAO dao = new MainDAO();
		dao.edit(dto);
		
		if (dto.getOriFileName() != null) {
			int idx = dto.getBoard_idx();
			System.out.println("업로드 할 게시판 번호: "+idx);
			dao = new MainDAO();
			
			String delFileName = dao.getFileName(String.valueOf(idx));
			System.out.println("삭제할파일"+delFileName);
			
			dao = new MainDAO();
			
			dao.updateFileName(delFileName, dto);
			
			if (delFileName != null) {
				upload.delete(delFileName);
			}
		}
		System.out.println(dto.getBoard_idx());
		dis =req.getRequestDispatcher("detail?board_idx="+dto.getBoard_idx());
		dis.forward(req, resp);
	}

	public void mylist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
	
		String idx = req.getParameter("board_idx");

		System.out.println(loginId+"/"+idx);
		
		
		MainDAO dao = new MainDAO();
		ArrayList<MainDTO> list = dao.mylist(loginId);
		System.out.println(list.size());
		String msg = "게시글없음";
		if (list != null && list.size() > 0) {
			req.setAttribute("list", list);
			msg = loginId;
		}

		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("MyProfile.jsp");
		dis.forward(req, resp);
	}

	public void del() throws IOException {
		String idx = this.req.getParameter("board_idx");
		System.out.println("delete idx => " + idx);
		FileService upload = new FileService(req);
		MainDAO dao = new MainDAO();
		String newFileName = dao.getFileName(idx);
		dao = new MainDAO();
		int success = dao.del(idx, newFileName);
		if (success > 0 && newFileName != null) {
			System.out.println("파일삭제");
			upload.delete(newFileName);
		}

		this.resp.sendRedirect("./MyProfile");
	}

	public void detail() throws ServletException, IOException {
		String board_idx = req.getParameter("board_idx");

		String loginId = (String) req.getSession().getAttribute("");
		System.out.println(loginId);
		System.out.println("글번호 : "+board_idx);
		
		String page = "/main.jsp";
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(board_idx);
		ReplyDAO rao = new ReplyDAO();
		ArrayList<ReplyDTO> list= rao.list(board_idx);
		rao = new ReplyDAO();
		int rcnt = rao.rcnt(board_idx);
		
		if (dto != null) {
			dao = new MainDAO();
			page = "/detail.jsp";
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);	
			req.setAttribute("rcnt", rcnt);
		
		}

		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void flist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		MainDAO dao = new MainDAO();
		String user_id = req.getParameter("user_id");
		String board_idx = req.getParameter("board_idx");

//		System.out.println("loginId:"+loginId);
		System.out.println("user_id:"+user_id);

		ReplyDAO rao = new ReplyDAO();
		int rcnt = rao.rcnt(board_idx);

		System.out.println("board_idx :"+board_idx+"댓글개수:"+rcnt);
		
		ArrayList<MainDTO> flist = dao.flist(loginId);
		dao = new MainDAO();
		ArrayList<MainDTO> mylist = dao.mylist(user_id);
		System.out.println(flist.size());
//		System.out.println(blist.size());
		
		String msg = "친구없음";
		if (flist != null && flist.size() > 0) {
			req.setAttribute("flist", flist);
			req.setAttribute("mylist", mylist);
			req.setAttribute("rcnt", rcnt);
			msg = "";
		}
		
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("main.jsp");
		dis.forward(req, resp);
		
	}

	public void like() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		String board_idx = req.getParameter("board_idx");
		
		boolean success = false;
		System.out.println("idx: "+board_idx+",id: "+loginId);
		MainDAO dao =  new MainDAO();
		// 동일 게시글에 대한 이전 추천 여부 검색
		//추가해야함!!!
	}

	public void array() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		MainDAO dao = new MainDAO();
		ArrayList<MainDTO> array = dao.array(loginId);
		
		String msg = "전체공개 게시글이 없고 친구도 없고 정렬할 게시글도 없네";
		if (array != null && array.size() > 0) {
			req.setAttribute("array", array);
			msg = "";
		}

		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("main.jsp");
		dis.forward(req, resp);
	}
}
