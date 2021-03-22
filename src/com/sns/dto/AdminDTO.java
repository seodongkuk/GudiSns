package com.sns.dto;

import java.sql.Date;

public class AdminDTO {
	private String user_id;
	private int board_idx;
	private String content;
	private Date report_date;
	private String report_state;
	private String admin_id;
	private int report_idx;
	private String release_state;
	private String OriFileName;
	private String NewFileName;
	private String HashTag;
	private Date Writedate;
	
	


	public String getOriFileName() {
		return OriFileName;
	}

	public void setOriFileName(String oriFileName) {
		OriFileName = oriFileName;
	}

	public String getNewFileName() {
		return NewFileName;
	}

	public void setNewFileName(String newFileName) {
		NewFileName = newFileName;
	}

	public String getHashTag() {
		return HashTag;
	}

	public void setHashTag(String hashTag) {
		HashTag = hashTag;
	}

	public Date getWritedate() {
		return Writedate;
	}

	public void setWritedate(Date writedate) {
		Writedate = writedate;
	}

	public String getRelease_state() {
		return release_state;
	}

	public void setRelease_state(String release_state) {
		this.release_state = release_state;
	}
	public int getReport_idx() {
		return report_idx;
	}
	public void setReport_idx(int report_idx) {
		this.report_idx = report_idx;
	}
	public String getUser_id() {
		return user_id;
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
	public Date getReport_date() {
		return report_date;
	}
	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}
	public String getReport_state() {
		return report_state;
	}
	public void setReport_state(String report_state) {
		this.report_state = report_state;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
//----------------------------------------------------------------------------------------------
	

}
