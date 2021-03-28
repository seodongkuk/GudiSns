package com.sns.service;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sns.dao.MainDAO;
import com.sns.dao.ReplyDAO;
import com.sns.dto.MainDTO;
import com.sns.dto.ReplyDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
		
		System.out.println(loginId);
		
		if (loginId != null) {
			FileService fileupload = new FileService(req);
			MainDTO dto = fileupload.regist();
			System.out.println(dto.getOriFileName() + "<<원래명" + dto.getNewFileName() + "<<CTM한명");
			MainDAO dao = new MainDAO();
			String msg = "글 등록에 실패 하였습니다.";
			long idx = dao.write(dto);
			if (idx > 0) {
				msg = "글 등록에 성공 하였습니다.";
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("newWriting.jsp");
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
		
		if(dto.getRelease_state().equals("004")) {
			String msg = "블라인드 게시글 이므로 수정할 수 없습니다.";
			req.setAttribute("editmsg", msg);
			dis = req.getRequestDispatcher("/detail");
			dis.forward(req, resp);
		}else {
			String page = "/MyProfile";
			if (dto != null) {
				page = "writingEdit.jsp";
				req.setAttribute("dto", dto);
		}

		dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		}
	}

	public void edit() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if(loginId != null) {
			
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
	}
	
	public void del() throws IOException, ServletException{
		String loginId = (String) req.getSession().getAttribute("loginId");
		if(loginId !=null) {
			String idx = req.getParameter("board_idx");
			System.out.println("delete idx => " + idx);
			FileService upload = new FileService(req);
			MainDAO dao = new MainDAO();
			String newFileName = dao.getFileName(idx);
			int success = dao.del(idx, newFileName);
			System.out.println("파일삭제여부"+newFileName+success);
			String delmsg= "블라인드 게시글이므로 삭제가 불가능합니다.";
			
			if(success>0) {
				delmsg="글삭제되었습니다.";
			}
			if (success > 0 && newFileName != null) {
				System.out.println("파일삭제");
				upload.delete(newFileName);
				delmsg="글과사진이삭제되었습니다.";
				}
			
			req.setAttribute("delmsg", delmsg);
			dis=req.getRequestDispatcher("/MyProfile");
			dis.forward(req, resp);
			
			}
	}

	public void detail() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if(loginId !=null) {
			String board_idx = req.getParameter("board_idx");
			
			System.out.println("글번호 : "+board_idx);
	
			
			String page = "/main.jsp";
			MainDAO dao = new MainDAO();
			MainDTO dto = dao.detail(board_idx);
			ReplyDAO rao = new ReplyDAO();
			ArrayList<ReplyDTO> list= rao.list(board_idx);
			rao = new ReplyDAO();
			int rcnt = rao.rcnt(board_idx);
			
			if (dto != null) {
				page = "/detail.jsp";
				req.setAttribute("dto", dto);
				req.setAttribute("list", list);	
				req.setAttribute("rcnt", rcnt);
			
			}
	
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}
		else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void flist() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if(loginId != null) {
			
			System.out.println(loginId+" : loginid");
			MainDAO dao = new MainDAO();
			
			ArrayList<MainDTO> flist = dao.flist(loginId);
			System.out.println(flist.size());
			String msg = "친구없음";
			
			
			
			
			if (flist != null && flist.size() > 0) {
				req.setAttribute("flist", flist);
				msg = "";
			}
			
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("main.jsp");
			dis.forward(req, resp);
			
		}
	}
	
	public void likecnt() throws ServletException, IOException {

		String board_idx = req.getParameter("idx");
		MainDAO dao = new MainDAO();
		
		HashMap<String,Object> map = new HashMap<String, Object>();
	
		int cnt = dao.like_cnt(board_idx);
			System.out.println(cnt);
			map.put("cnt",cnt);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.getWriter().println(json);
		}

	public void like() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		
		String user_id = req.getParameter("id");
		String board_idx = req.getParameter("idx");
		HashMap<String,Object> map = new HashMap<String, Object>();
		
		System.out.println("아이디: "+user_id+"/"+"게시글 번호: "+board_idx);
		MainDAO dao =  new MainDAO();
		
		// 동일 게시글에 대한 이전 추천 여부 검색
		int result = dao.likeChk(user_id,board_idx);
		System.out.println(result);
		if(result == 0) {//추천하지 않았으면 추천 추가
			dao =  new MainDAO();
			dao.likeupdate(user_id,board_idx);
			map.put("result",result);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.getWriter().println(json);
		}else {//추천했으면 추천 삭제
			dao =  new MainDAO();
			dao.likedelete(user_id,board_idx);
			map.put("result",result);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			resp.getWriter().println(json);
		}
		
	}
	public void array() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		MainDAO dao =null;
		
		//ArrayList<MainDTO> array = dao.latest_array(loginId);
		String select = req.getParameter("select");
		System.out.println(select);
//		String msg = "전체공개 게시글이 없고 친구도 없고 정렬할 게시글도 없네";
		
		if(select.equals("최신순")) {
			dao =  new MainDAO();
			System.out.println("최신순으로 정렬해줄게!");
			ArrayList<MainDTO> array = dao.latest_array(loginId);
			if (array != null && array.size() > 0) {
				System.out.println(array);
				req.setAttribute("flist", array);
				req.setAttribute("select", select);
				dis = req.getRequestDispatcher("main.jsp");
				dis.forward(req, resp);
			}
		}else if (select.equals("추천순")){//추천했으면 추천 삭제
			dao =  new MainDAO();
			System.out.println("추천순으로 정렬해줄게!");
			ArrayList<MainDTO> array = dao.recommend_array(loginId);
			if (array != null && array.size() > 0) {
				System.out.println(array);
				req.setAttribute("flist", array);
				req.setAttribute("select", select);
				dis = req.getRequestDispatcher("main.jsp");
				dis.forward(req, resp);
				}
			}
	}
	
	public void singo() throws ServletException, IOException {
		//신고에 넘겨줄 idx content 신고한 아이디 get >>req
		String loginId = (String) req.getSession().getAttribute("loginId");
		if(loginId != null) {
			
			String idx = req.getParameter("board_idx");
			String user_id =req.getParameter("user_id");
			System.out.println(loginId+"/"+idx +"/"+user_id);
			
			MainDTO dto = new MainDTO();
			dto.setUser_id(loginId);
			dto.setBoard_idx(Integer.valueOf(idx));
			MainDAO dao = new MainDAO();
			int success = dao.singoCk(dto);
			System.out.println(success);
			String msg= "";
			if(success == 0) {
				req.setAttribute("loginId", loginId);
				req.setAttribute("idx", idx);
				req.setAttribute("user_id", user_id);
				msg="신고사유를 작성해주세요 ";
			}else if(success == 1) {
				msg ="이미신고한게시글입니다";
				//디스페처를 두번쓸생각을햇으면 됫음 .
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher("declaration.jsp");
			dis.forward(req, resp);
		}
	}

	public void reportAction() throws ServletException, IOException {
		String loginId = req.getParameter("loginId");
		String board_idx = req.getParameter("board_idx");
		String user_id = req.getParameter("user_id");
		String content = req.getParameter("content");
		
		System.out.println(loginId+" / "+board_idx+" / "+user_id+" / "+content+" / Ck");
		
		MainDTO dto =new MainDTO();
		
		dto.setUser_id(loginId);  //신고한애
		dto.setBoard_idx(Integer.valueOf(board_idx));
		dto.setContent(content);
		dto.setReport_id(user_id);//신고당한애
		
		MainDAO dao = new MainDAO();
		int success = dao.reportWriting(dto);
		String msg ="";
		if(success > 0) {
			msg ="해당 게시글이 신고 접수 되었습니다 관리자가 확인하고 처리하겟습니다.";
		}
		req.setAttribute("msg", msg);
		dis = req.getRequestDispatcher("declaration.jsp");
		dis.forward(req, resp);
	}

}
