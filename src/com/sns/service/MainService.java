package com.sns.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.MainDAO;
import com.sns.dto.MainDTO;

public class MainService {
	HttpServletRequest req = null;
	HttpServletResponse resp =null;
	RequestDispatcher dis = null;
	
	public MainService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void newWrite() throws ServletException, IOException {
		String loginId =(String) req.getSession().getAttribute("loginId");
		
		if(loginId != null) {
			FileService fileupload =new FileService(req);
			MainDTO dto = fileupload.regist();
			//
			System.out.println(dto.getOriFileName()+"<<원래명"+dto.getNewFileName()+"<<CTM한명");
			
			MainDAO dao =new MainDAO();
			
			String page = "newWriting.html";  //글실패시 메인으로 근데 아직jsp 수정안함..
			String msg = "글 등록에 실패 하였습니다.";
			
			long idx = dao.write(dto);
			
			if(idx >0) {
				page = "main.jsp";//글실패시 메인으로 근데 아직jsp 수정안함..
				msg ="글 등록에 성공 하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis= req.getRequestDispatcher(page);
			dis.forward(req,resp);
			
		}else {
			resp.sendRedirect(""); //로그인 페이지 jsp페이지로 바꾸면 변경하자
		}
		
	}

	public void writeEdit() throws ServletException, IOException {
		String idx =req.getParameter("board_idx");
		//만들때idx값이 추가되니 게시글에 히든 리스트 번호 로 받아져있어야함..
		System.out.println(idx+"글번호 ");
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(idx);
		String page = "/MyProfile.html";
		
		if(dto !=null) {
			page="writingEdit.jsp";
			req.setAttribute("dto", dto);
			//dto로쓸수잇게
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}
	
	public void edit() {
		FileService upload = new FileService(req);
		MainDTO dto = upload.regist();
		
		MainDAO dao = new MainDAO();
		
		dao.edit(dto);
		
		if(dto.getOriFileName() !=null) {
			int idx = dto.getBoard_idx();
			dao = new MainDAO();
			
			String delFileName =dao.getFileName(String.valueOf(idx));
			
			dao = new MainDAO();
			dao.updateFileName(delFileName,dto);
			
			if(delFileName !=null) {
				upload.delete(delFileName);
			}
		}
	}

	public void mylist() throws ServletException, IOException {
//		String loginId =(String) req.getSession().getAttribute("userId");
		
//		if(loginId !=null) {
			
			MainDAO dao = new MainDAO();
			ArrayList<MainDTO> list = dao.mylist();
//			System.out.println(list);
			System.out.println(list.size());
			String msg ="게시글없음";
			if(list != null && list.size()>0) {
				req.setAttribute("list", list);
				msg ="";
			}
			req.setAttribute("msg", msg);
			dis=req.getRequestDispatcher("MyProfile.jsp");
			dis.forward(req, resp);
			
//		}else{
//			resp.sendRedirect("");
		}
		
	

	public void del() throws IOException {
		
		String idx = req.getParameter("board_idx");
		System.out.println("delete idx => "+idx);
		
		FileService upload = new FileService(req);
		
		MainDAO dao = new MainDAO();
		
		String newFileName = dao.getFileName(idx);
		
		dao= new MainDAO();
		int success = dao.del(idx,newFileName);
		
		if(success>0 && newFileName !=null) {
			System.out.println("파일삭제");
			upload.delete(newFileName);
		}
		resp.sendRedirect("main.jsp");
		
	}

	public void detail() throws ServletException, IOException {
		String idx = req.getParameter("board_idx");
		String loginId = (String) req.getSession().getAttribute("");
		System.out.println(loginId);
		System.out.println(idx+"글번호");
		
		String page = "/main.jsp";
		//메인으로 
		MainDAO dao = new MainDAO();
		MainDTO dto = dao.detail(idx);
		if(dto !=null) {
			dao = new MainDAO();
			page= "/detail.jsp";
			req.setAttribute("dto", dto);
		}
		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}

}
