package com.sns.service;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.sns.dto.MainDTO;

public class FileService {
	HttpServletRequest req = null;
	
	public FileService(HttpServletRequest req) {
		this.req =req;
	}

	public MainDTO regist() {
		String savePath ="C:/upload/";
		//리얼패스로 해보고 안되면 직접 경로 "C:/upload/ 로"
		System.out.println(savePath);
		
		int maxSize = 10*1024*1024;
		MainDTO dto = null;
		
		try {
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8");
			dto =new MainDTO();
			
			String idx = multi.getParameter("board_idx");   //글번호 
			String userid = multi.getParameter("userid");   //유저아이디 스코프로받아옴
			String content = multi.getParameter("textArea");  //1글내용
			//받아올값에 해싀태그까지 1,2로 받아오자 논의해볼것. //2 해시태그
//			String subject =multi.getParameter("subject")  우리는글쓰기에 글제목이없음 컬럼 존재이유?
			
			String release = multi.getParameter("release_state"); //3.공개도 수준 가져오기 001/2/3과같이넘어올것임
			
			System.out.println(userid+"/"+idx+"/"+content+"/"+release);
			//param 재대로넘어오나 체크 해보
			
			if(idx != null) {
				dto.setBoard_idx(Integer.parseInt(idx));
			}
			dto.setUserid(userid);
			dto.setContent(content);
			dto.setRelase_state(Integer.parseInt(release));
			
			
			String oriFileName = multi.getFilesystemName("photo");
			
			if(oriFileName != null) {
				String ext =oriFileName.substring(oriFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis()+ext;
				
				File oriFile = new File(savePath+oriFileName);
				File newFile = new File(savePath+newFileName);
				
				oriFile.renameTo(newFile);
				dto.setOriFileName(oriFileName);
				dto.setNewFileName(newFileName);
				
			}else {
				System.out.println("파일을 않너음");
			}
				//
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return dto;
	}

	public void delete(String newFileName) {
		File file = new File("C:/upload/"+newFileName);
		if(file.exists()) {
			boolean success = file.delete();
			System.out.println("success 여부" +success);
		}
		
	}

}
