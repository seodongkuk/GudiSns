package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.MainDTO;

public class ProfileDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public ProfileDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resClose() {
		try {//널값 유무 확인해서 닫아주기 -> 널포인트익셉션 방지
			if(rs != null) {
				rs.close();				
			}if(ps != null) {
				ps.close();
			}if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<MainDTO> otherlist(String id) {
		MainDTO dto = null;
		ArrayList<MainDTO> list = new ArrayList<MainDTO>();
		
		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashtag FROM board2 b, photo2 p, hashtag2 h"
				+ " WHERE b.board_idx = p.board_idx(+) AND b.board_idx = h.board_idx(+) AND b.user_id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setRelease_state(rs.getString("release_state"));
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setHashTag(rs.getString("hashTag"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return list;
	}

}
