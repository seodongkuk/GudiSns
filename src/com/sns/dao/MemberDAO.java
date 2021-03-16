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
	//--------------------------------------------------------------------로그인
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
	//--------------------------------------------------------------------------회원가입
	public boolean join(String id,String pw,String name,String phone,String email) {
		boolean success = false;
		String sql="INSERT INTO member2 (user_id,pw,name,phone,email)VALUES(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			ps.setString(2,pw);
			ps.setString(3,name);
			ps.setInt(4,Integer.parseInt(phone));
			ps.setString(5,email);
			rs = ps.getGeneratedKeys();
			if(ps.executeUpdate()>0) {
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		System.out.println("회원가입 성공 여부 : "+success);
		return success;
	}
//--------------------------------------------------------------------------임의로만든리스트
public ArrayList<MemberDTO> list() {

	return null;
}
//--------------------------------------------------------------------------아이디체크
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


