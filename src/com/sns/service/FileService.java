package com.sns.service;

import com.oreilly.servlet.MultipartRequest;
import com.sns.dto.MainDTO;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public class FileService {
	HttpServletRequest req = null;

	public FileService(HttpServletRequest req) {
		this.req = req;
	}

	public MainDTO regist() {
		String savePath = "C:/upload/";
		System.out.println(savePath);
		int maxSize = 10*1024*1024;
		MainDTO dto = null;

		try {
			MultipartRequest multi = new MultipartRequest(req, savePath, maxSize, "UTF-8");
			dto = new MainDTO();
			String idx = multi.getParameter("board_idx");  //null
			String user_id = multi.getParameter("user_id");
			String content = multi.getParameter("textArea");
			String release = multi.getParameter("release_state");
			System.out.println(user_id + "/" + idx + "/" + content + "/" + release);
			if (idx != null) {
				dto.setBoard_idx(Integer.parseInt(idx));
			}

			dto.setUser_id(user_id);
			dto.setContent(content);
			dto.setRelase_state(Integer.parseInt(release));
			String oriFileName = multi.getFilesystemName("photo");
			if (oriFileName != null) {
				String ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + ext;
				File oriFile = new File(savePath + oriFileName);
				File newFile = new File(savePath + newFileName);
				oriFile.renameTo(newFile);
				dto.setOriFileName(oriFileName);
				dto.setNewFileName(newFileName);
			} else {
				System.out.println("파일을 않너음");
			}
		} catch (IOException var14) {
			var14.printStackTrace();
		}

		return dto;
	}


	public void delete(String newFileName) {
		File file = new File("C:/upload/" + newFileName);
		if (file.exists()) {
			boolean success = file.delete();
			System.out.println("success 여부" + success);
		}
		
	}
}