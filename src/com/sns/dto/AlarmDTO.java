package com.sns.dto;

import java.sql.Date;

public class AlarmDTO {
	private String alarm_state;
	private String user_id;
	private Date proc_date;
	private String alarm_kind;
	
	private int alarm_idx;
	private Date alarm_reg_date;
	private String other_id;
	private String alarm_read_state;
	private String alarm_content;
	
	public String getAlarm_state() {
		return alarm_state;
	}
	public void setAlarm_state(String alarm_state) {
		this.alarm_state = alarm_state;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getProc_date() {
		return proc_date;
	}
	public void setProc_date(Date proc_date) {
		this.proc_date = proc_date;
	}
	public String getAlarm_kind() {
		return alarm_kind;
	}
	public void setAlarm_kind(String alarm_kind) {
		this.alarm_kind = alarm_kind;
	}
	public int getAlarm_idx() {
		return alarm_idx;
	}
	public void setAlarm_idx(int alarm_idx) {
		this.alarm_idx = alarm_idx;
	}
	public Date getAlarm_reg_date() {
		return alarm_reg_date;
	}
	public void setAlarm_reg_date(Date alarm_reg_date) {
		this.alarm_reg_date = alarm_reg_date;
	}
	public String getOther_id() {
		return other_id;
	}
	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}
	public String getAlarm_read_state() {
		return alarm_read_state;
	}
	public void setAlarm_read_state(String alarm_read_state) {
		this.alarm_read_state = alarm_read_state;
	}
	public String getAlarm_content() {
		return alarm_content;
	}
	public void setAlarm_content(String alarm_content) {
		this.alarm_content = alarm_content;
	}
	
	
}
