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
				
				sql = "SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND (alarm_kind = '댓글알림' AND alarm_state = '1')";
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, user_id);
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
				//댓글등록 후 해당 게시글의 작성자한테 댓글을 작성했다는 알림을 보냄(댓글 쓴 사람 아이디와 함께)
				sql = "SELECT b.user_id FROM board2 b "+
						"WHERE b.board_idx IN (SELECT board_idx FROM Reply2 WHERE board_idx=? AND user_id=?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, board_idx);
				ps.setString(2, user_id);
				
				rs = ps.executeQuery();
				//만약 해당 게시글 작성자가 있다면...
				if(rs.next()) {
					
					String boardId = rs.getString("user_id");
					sql = "SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND (alarm_kind = '댓글알림' AND alarm_state = '1')";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, boardId);
					
					rs = ps.executeQuery();
					
					if(rs.next()) {
					//해당 게시글 작성자한테 댓글 알림을 보냅니다
					sql = "INSERT INTO alarmlist2(alarm_idx,user_id,other_id,alarm_content) VALUES(alarm2_seq.NEXTVAL,?,?,'댓글알림')";
					ps = conn.prepareStatement(sql);
					ps.setString(1, user_id);
					ps.setString(2, boardId);
					
					if(ps.executeUpdate() > 0) {
						System.out.println("게시글 작성자한테 댓글알림 전송 완료!!!");
					}
					}else {
						System.out.println("해당 유저는 댓글알림이 거부 상태입니다.");
					}
				}
				}
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

	
	public String edit(String content, int cmt_idx) {
		int success=0;
		String result = "";
		String sql = "UPDATE Reply2 SET content=? where cmt_idx=?";
		//댓글 수정하기
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, content);
			ps.setInt(2, cmt_idx);
			success=ps.executeUpdate();
			//수정할 댓글 가져오기
			if(success>0) {
				sql = "SELECT content FROM Reply2 where cmt_idx=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, cmt_idx);
				rs = ps.executeQuery();
				//수정한 댓글 result 에 저장
				if(rs.next()) {
					result = rs.getString("content");
					System.out.println("댓글 수정 성공");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return result;
	}
	
	
}
