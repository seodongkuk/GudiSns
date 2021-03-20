package com.sns.dto;

import java.sql.Date;

public class MainDTO {
	private int board_idx;
	private String user_id;
	private String subject;
	private String content;
	private Date writedate;
	private boolean declaration_state;
	private int release_state;
	private int file_idx;
	private String oriFileName;
	private String newFileName;
	
<<<<<<< HEAD
	private String hashTag;
=======
	//리폿
	private String report_id;
>>>>>>> e11096da7757495038ff96bd809aff710842b336
	

	public int getRelease_state() {
		return release_state;
	}

	public void setRelease_state(int release_state) {
		this.release_state = release_state;
	}

	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getBoard_idx() {
		return this.board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean getDeclaration_state() {
		return this.declaration_state;
	}

	public void setDeclaration_state(boolean declaration_state) {
		this.declaration_state = declaration_state;
	}

	public int getFile_idx() {
		return this.file_idx;
	}

	public void setFile_idx(int file_idx) {
		this.file_idx = file_idx;
	}

	public String getOriFileName() {
		return this.oriFileName;
	}

	public void setOriFileName(String oriFileName) {
		this.oriFileName = oriFileName;
	}

	public String getNewFileName() {
		return this.newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}


	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
<<<<<<< HEAD

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
=======
	public String getReport_id() {
		return report_id;
	}
	
	public void setReport_id(String report_id) {
		this.report_id = report_id;
>>>>>>> e11096da7757495038ff96bd809aff710842b336
	}
}