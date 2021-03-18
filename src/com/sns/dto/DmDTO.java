package com.sns.dto;

import java.sql.Date;

public class DmDTO {
	//채팅방 리스트 테이블
		private int chat_idx;
		private String user_id;
		private Date create_date;
		
		//DM 테이블
		private int dm_idx;
		private String content;
		private String sendtime;
		private String read_state;
		private String send_id;
		private String recieve_id;
		
		public int getChat_idx() {
			return chat_idx;
		}
		public void setChat_idx(int chat_idx) {
			this.chat_idx = chat_idx;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
		public Date getCreate_date() {
			return create_date;
		}
		public void setCreate_date(Date create_date) {
			this.create_date = create_date;
		}
		public int getDm_idx() {
			return dm_idx;
		}
		public void setDm_idx(int dm_idx) {
			this.dm_idx = dm_idx;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getSendtime() {
			return sendtime;
		}
		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}
		public String getRead_state() {
			return read_state;
		}
		public void setRead_state(String read_state) {
			this.read_state = read_state;
		}
		public String getSend_id() {
			return send_id;
		}
		public void setSend_id(String send_id) {
			this.send_id = send_id;
		}
		public String getRecieve_id() {
			return recieve_id;
		}
		public void setRecieve_id(String recieve_id) {
			this.recieve_id = recieve_id;
		}
}
