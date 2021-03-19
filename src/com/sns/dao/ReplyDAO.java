package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.ReplyDTO;


public class ReplyDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public ReplyDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void resClose() {	//자원닫기
		try {
			if(rs!=null) {rs.close();}
			if(ps!=null) {ps.close();}
			if(conn!=null) {conn.close();}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean del(String cmt_idx) {
		boolean success = false;
		String sql = "DELETE FROM Reply2 WHERE cmt_idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, cmt_idx);
			if(ps.executeUpdate()>0){
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	public ArrayList<ReplyDTO> list(String board_idx) {
		String sql = "SELECT * FROM Reply2 WHERE board_idx=? ORDER BY cmt_idx DESC";
		
		ArrayList<ReplyDTO> list = new ArrayList<ReplyDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			rs = ps.executeQuery();
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				dto.setCmt_idx(rs.getInt("cmt_idx"));
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setContent(rs.getString("content"));
				
				list.add(dto);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return list;
	}

	public boolean write(String board_idx, String user_id, String content) {
		boolean success = false;
		String sql = "INSERT INTO reply2(cmt_idx,user_id,board_idx,content) VALUES(reply2_seq.NEXTVAL,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			ps.setString(2, board_idx);
			ps.setString(3, content);
			if(ps.executeUpdate()>0){
				success=true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	public int rcnt(String board_idx ) {
		String sql = "SELECT COUNT(*) AS rcnt FROM Reply2 WHERE board_idx=?";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			rs = ps.executeQuery();
			if(rs.next()) {
				
				success = rs.getInt("rcnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}
	
	
}
