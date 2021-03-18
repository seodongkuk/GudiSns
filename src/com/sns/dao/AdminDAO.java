package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.board.dto.BoardDTO;
import com.sns.dto.AdminDTO;

public class AdminDAO {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	public AdminDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds  = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void resClose() {
		try {
			if(rs != null) {	rs.close();}
			if(ps != null) {	ps.close();}
			if(conn != null) {	conn.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean admin_login(String id, String pw) {			
		boolean success = false;
		String sql="SELECT admin_id FROM admin2 WHERE admin_ID=? AND PW=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setString(2,pw);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return success;
	}
	public ArrayList<AdminDTO> reportList() {
		String sql="SELECT idx,subject,user_name,bHit FROM bbs ORDER BY idx DESC";
		ArrayList<AdminDTO> reportList = new ArrayList<AdminDTO>();		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			
			while(rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setSubject(rs.getString("subject"));
				dto.setUser_name(rs.getString("user_name"));
				dto.setbHit(rs.getInt("bHit"));
				list.add(dto);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();//자원닫기
		}		
		return list;
	}

}
