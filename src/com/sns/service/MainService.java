package com.sns.service;

import java.io.IOException;

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
//		String loginId =(String) req.getSession().getAttribute("loginId");
//		if(loginId != null) {
			FileService fileupload =new FileService(req);
			MainDTO dto = fileupload.regist();
			//
			System.out.println(dto.getOriFileName()+"<<원래명"+dto.getNewFileName()+"<<CTM한명");
			
			MainDAO dao =new MainDAO();
			
			String page = "newWriting.html";  //글실패시 메인으로 근데 아직jsp 수정안함..
			String msg = "글 등록에 실패 하였습니다.";
			
			long idx = dao.write(dto);
			
			if(idx >0) {
				page = "newWriting.html";//글실패시 메인으로 근데 아직jsp 수정안함..
				msg ="글 등록에 성공 하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis= req.getRequestDispatcher(page);
			dis.forward(req,resp);
			
//		}else {
//			resp.sendRedirect(""); //로그인 페이지 jsp페이지로 바꾸면 변경하자
//		}
		
	}

	public void writeEdit() {
		
		
	}
	
	public void edit() {
		
		
	}

}
