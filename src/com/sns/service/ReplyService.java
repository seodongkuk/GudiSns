package com.sns.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.ReplyDAO;

public class ReplyService {

	HttpServletRequest req = null;	
	HttpServletResponse resp = null;
	ReplyDAO dao = null;
	
	public ReplyService(HttpServletRequest req, HttpServletResponse resp) {
		this.req=req;
		this.resp=resp;
		dao=new ReplyDAO();
	}
	

	public void rlist() {
		// TODO Auto-generated method stub
		
	}

	public void rwrite() {
		// TODO Auto-generated method stub
		
	}

	public void rdel() {
		// TODO Auto-generated method stub
		
	}

}
