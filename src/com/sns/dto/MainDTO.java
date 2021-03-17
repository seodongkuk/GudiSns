package com.sns.dto;

import java.sql.Date;

public class MainDTO {
	private int board_idx;  //글번호
	private String id; //id
	private String subject; //제목
	private String content; //글내용
	private Date date; //작성날짜
	private boolean declaration_state; // 신고 상태   t/f
	private int relase_state; //공개 상태 여부  t/f
	
	/*포토 파일 업로드 ori new*/
	private int file_idx; //
	private String oriFileName; //파일명
	private String newFileName; //후파일명
	
	//해쉬태그 테이블 수정 이야기 후 수정해보기 .. 해싀태그 1,2 로 태이블 생성 각각 받아오기and 없을 시처리방법 하기
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean getDeclaration_state() {
		return declaration_state;
	}
	public void setDeclaration_state(boolean declaration_state) {
		this.declaration_state = declaration_state;
	}
	public int getRelase_state() {
		return relase_state;
	}
	public void setRelase_state(int relase_state) {
		this.relase_state = relase_state;
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
	
	
}
