package com.sns.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.ProfileService;

@WebServlet({"/upload"})
public class ProfileController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ProfileService service = new ProfileService();//업로드 서비스 객체화함
		
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String sub = uri.substring(context.length());				
		
		req.setCharacterEncoding("UTF-8");
		switch(sub) {
		case "/upload":
			System.out.println("프로필사진등록");
			service.upload();
			break;
			}
		}
	}

