package com.sns.dto;

import java.sql.Date;

public class MainDTO {
	private int board_idx;
	private String user_id;

	private String subject;
	private String content;
	private Date writedate;
	private boolean declaration_state;
	private int relase_state;
	private int file_idx;
	private String oriFileName;
	private String newFileName;

	
	private String muser_id;
	private String bbud_id;
	private int bstate ;
	
	
	public String getMuser_id() {
		return muser_id;
	}

	public void setMuser_id(String muser_id) {
		this.muser_id = muser_id;
	}

	public String getBbud_id() {
		return bbud_id;
	}

	public void setBbud_id(String bbud_id) {
		this.bbud_id = bbud_id;
	}
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getBstate() {
		return bstate;
	}

	public void setBstate(int bstate) {
		this.bstate = bstate;
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


	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public boolean getDeclaration_state() {
		return this.declaration_state;
	}

	public void setDeclaration_state(boolean declaration_state) {
		this.declaration_state = declaration_state;
	}

	public int getRelase_state() {
		return this.relase_state;
	}

	public void setRelase_state(int relase_state) {
		this.relase_state = relase_state;
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
}