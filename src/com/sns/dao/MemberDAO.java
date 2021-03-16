package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.MemberDTO;

public class MemberDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	public MemberDAO() {
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
	//--------------------------------------------------------------------
	public boolean login(String id, String pw) {
		boolean success = false;
		String sql="SELECT user_id FROM member2 WHERE USER_ID=? AND PW=?";
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
	public int join(MemberDTO dto) {
		
		String sql="INSERT INTO member2 (user_id,pw,name,phone,email)VALUES(?,?,?,?,?)";
		int success = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getName());
			ps.setInt(4, dto.getPhone());
			ps.setString(5, dto.getEmail());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}
public ArrayList<MemberDTO> list() {

	return null;
}
public boolean idChk(String id) throws SQLException {
	boolean success = false;
	String sql="SELECT user_id FROM member2 WHERE USER_ID=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1,id);
		rs = ps.executeQuery();
		success = rs.next();
		return !success;
	}

}




