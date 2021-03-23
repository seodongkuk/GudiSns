package com.sns.dto;

import java.sql.Date;

public class SearchDTO {
	private String user_id;
	private String name;
	private int board_idx;
	private String content;
	private Date writedate;
	private boolean declaration_state;
	private String release_state;
	
	private String hashTag;
	private int rnum;
	
	private String bud_id;
	private String state;
	
	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public boolean isDeclaration_state() {
		return declaration_state;
	}

	public void setDeclaration_state(boolean declaration_state) {
		this.declaration_state = declaration_state;
	}

	public String getRelease_state() {
		return release_state;
	}

	public void setRelease_state(String release_state) {
		this.release_state = release_state;
	}

	//리폿
	private String report_id;
	
	private int file_idx;
	private String oriFileName;
	private String newFileName;

	public String getUser_id() {
		return user_id;
	}

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public int getFile_idx() {
		return file_idx;
	}

	public void setFile_idx(int file_idx) {
		this.file_idx = file_idx;
	}

	public String getOriFileName() {
		return oriFileName;
	}

	public void setOriFileName(String oriFileName) {
		this.oriFileName = oriFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getBoard_idx() {
		return board_idx;
	}

	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHashTag() {
		return hashTag;
	}

	public void setHashTag(String hashTag) {
		this.hashTag = hashTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBud_id() {
		return bud_id;
	}

	public void setBud_id(String bud_id) {
		this.bud_id = bud_id;
	}
}
