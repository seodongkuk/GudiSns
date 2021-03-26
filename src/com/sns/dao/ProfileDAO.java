package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.MainDTO;
import com.sns.dto.SearchDTO;

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
		
		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, b.writedate,p.oriFileName, p.newFileName, h.hashtag, cnt.cnt, r.rcnt \r\n" + 
				"FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx)r\r\n" + 
				"WHERE b.board_idx = p.board_idx(+) AND b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND b.user_id = ? ORDER BY board_idx DESC";
		
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
				dto.setWritedate(rs.getDate("writedate"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setRcnt(rs.getInt("rcnt"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return list;
	}

	public ArrayList<MainDTO> mylist(String user_id) {
		MainDTO dto = null;
		ArrayList<MainDTO> list = new ArrayList<MainDTO>();

		//최신순 정렬
		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashtag, b.writedate,cnt.cnt, r.rcnt "
				+ "FROM board2 b, photo2 p, hashtag2 h,(SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx)r\r\n"
				+ "    WHERE b.board_idx = p.board_idx(+) AND b.board_idx = h.board_idx(+) AND b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.user_id = ? ORDER BY board_idx DESC";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new MainDTO();
				dto.setRelease_state(rs.getString("release_state"));
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setHashTag(rs.getString("hashTag"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setRcnt(rs.getInt("rcnt"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}

		return list;
	}	

	public HashMap<String, Object> budlist(String loginId, int page) {
		SearchDTO dto = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ArrayList<SearchDTO> budlist = new ArrayList<SearchDTO>();
		
		//3개씩 페이징
		int pagePercnt = 3;
		int end = page * pagePercnt;
		int start = end - (pagePercnt -1);
		
		/*String sql = "SELECT bud_id FROM buddylist2 WHERE (bud_id != ? AND user_id = ?) AND state = '002' UNION" + 
				" SELECT user_id FROM buddylist2 WHERE (bud_id = ? AND user_id != ?) AND state = '002'";*/
		
		String sql = "SELECT * FROM (" + 
				"SELECT ROW_NUMBER() OVER(ORDER BY bud_id DESC) AS rnum, bud_id FROM(" + 
				"SELECT * FROM (" + 
				"SELECT ROW_NUMBER() OVER(ORDER BY state DESC) AS rnum," + 
				" bud_id FROM buddylist2 WHERE (bud_id != ? AND user_id = ?) AND state = '002')" + 
				" UNION" + 
				" SELECT * FROM (" + 
				" SELECT ROW_NUMBER() OVER(ORDER BY state DESC) AS rnum," + 
				" user_id FROM buddylist2 WHERE (bud_id = ? AND user_id != ?) AND state = '002'))) WHERE rnum BETWEEN ? AND ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			ps.setString(3, loginId);
			ps.setString(4, loginId);
			ps.setInt(5, start);
			ps.setInt(6, end);
			rs = ps.executeQuery();
			while(rs.next()) {
				dto = new SearchDTO();
				dto.setBud_id(rs.getString("bud_id"));
				budlist.add(dto);
			}
			int maxPage = getMaxPage(loginId, pagePercnt);
			map.put("budlist", budlist);
			map.put("maxPage", maxPage);
			System.out.println("max page: "+maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return map;
	}
	
	private int getMaxPage(String loginId, int pagePercnt) {
		
		String sql = "SELECT COUNT(bud_id) AS cnt FROM(" + 
				"SELECT bud_id FROM buddylist2 WHERE (bud_id != ? AND user_id = ?) AND state = '002' UNION" + 
				" SELECT user_id FROM buddylist2 WHERE (bud_id = ? AND user_id != ?) AND state = '002')";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			ps.setString(3, loginId);
			ps.setString(4, loginId);
			rs = ps.executeQuery();
			while(rs.next()){
				int cnt = rs.getInt("cnt");
				max = (int) Math.ceil(cnt/(double)pagePercnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return max;
		
	}

}
