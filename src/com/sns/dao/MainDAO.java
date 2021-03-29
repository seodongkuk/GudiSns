package com.sns.dao;

import com.sns.dto.MainDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MainDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MainDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			this.conn = ds.getConnection();
			
		} catch (Exception var3) {
			var3.printStackTrace();
		}

	}

	public void resClose() {
		try {
			if (this.rs != null) {
				this.rs.close();
			}

			if (this.ps != null) {
				this.ps.close();
			}

			if (this.conn != null) {
				this.conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public ArrayList<MainDTO> flist(String loginId) {
		MainDTO dto = null;
		
		ArrayList<MainDTO> flist = new ArrayList<MainDTO>();
		
		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND (release_state != 003 AND release_state != 004) AND b.user_id\r\n" + 
				"                IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.bud_id FROM member2 m ,buddylist2 b\r\n" + 
				"                WHERE (m.user_id = b.user_id AND b.user_id = ? ) AND b.state = '002'))\r\n" + 
				"				UNION SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND (release_state != 003 AND release_state != 004) AND b.user_id \r\n" + 
				"				IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.user_id FROM member2 m ,buddylist2 b \r\n" + 
				"				WHERE (m.user_id = b.bud_id AND b.bud_id = ? ) AND b.state = '002'))";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setHashTag(rs.getString("hashTag"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setRcnt(rs.getInt("rcnt"));
				
				flist.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			resClose();
		}
		
		return flist;
		
	}
	
	public long write(MainDTO dto) {
		String sql = "INSERT INTO board2(board_idx,content,release_state,user_id)VALUES(board2_seq.NEXTVAL,?,?,?)";
		long bIdx = 0L;
			
		try {
			
			ps = conn.prepareStatement(sql, new String[]{"board_idx"});
			ps.setString(1, dto.getContent());
			ps.setString(2, dto.getRelease_state());
			ps.setString(3, dto.getUser_id());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			System.out.println("ps.getGeneratedKeys()"+ps.getGeneratedKeys());
			if (rs.next()) {
				bIdx = rs.getLong(1);
				System.out.println("bidx : " + bIdx);
				if (dto.getOriFileName() != null) {
					sql = "INSERT INTO photo2(file_idx,oriFileName,newFileName,board_idx)VALUES(photo2_seq.NEXTVAL,?,?,?)";
					ps = conn.prepareStatement(sql);
					ps.setString(1, dto.getOriFileName());
					ps.setString(2, dto.getNewFileName());
					ps.setLong(3, bIdx);
					ps.executeUpdate();
				}
				if(!dto.getHashTag().equals("")) {
					
					System.out.println("다오"+dto.getHashTag());
					sql = "INSERT INTO hashTag2(board_idx, hashTag) VALUES(?,?)";
					ps = conn.prepareStatement(sql);
					ps.setLong(1, bIdx);
					ps.setString(2, dto.getHashTag());
					ps.executeUpdate();
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return bIdx;
	}

	public MainDTO detail(String idx) {
		MainDTO dto = null;
		String sql = "SELECT b.board_idx, b.writedate, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag FROM board2 b, photo2 p, hashtag2 h" + 
							" WHERE b.board_idx = p.board_idx(+) AND b.board_idx = h.board_idx(+) AND b.board_idx = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			System.out.println("상세보기중 해당하는애 스타트");
			if (rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setHashTag(rs.getString("hashTag"));
				dto.setWritedate(rs.getDate("writedate"));
			}

			
		} catch (SQLException e) {
			e.printStackTrace();;
		} finally {
			resClose();
		}

		return dto;
	}

	public int edit(MainDTO dto) {
		
		String sql = "";
		
		int success = 0;

		try {
			sql = "UPDATE board2 SET content=? ,release_state=? WHERE board_idx=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getContent());
			ps.setString(2, dto.getRelease_state());
			ps.setInt(3, dto.getBoard_idx());
			success = ps.executeUpdate();
			System.out.println(success + "갯수");
			
			if(dto.getHashTag() != null) {
				sql = "UPDATE hashTag2 SET hashTag=? WHERE board_idx=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getHashTag());
				ps.setInt(2, dto.getBoard_idx());;
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}

		return success;
	}

	public int updateFileName(String delFileName, MainDTO dto) {
		String sql = "";
		int success = 0;

		try {
			if (delFileName != null) {
				sql = "UPDATE photo2 SET newFileName=?,oriFileName=? WHERE board_idx=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getNewFileName());
				ps.setString(2, dto.getOriFileName());
				ps.setInt(3, dto.getBoard_idx());
			} else {
				sql = "INSERT INTO photo2(file_idx,oriFileName,newFileName,board_idx)VALUES(photo2_seq.NEXTVAL,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setInt(3, dto.getBoard_idx());
			}

			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}

		return success;
	}

	public String getFileName(String idx) {
		String newFileName = null;
		String sql = "SELECT newFileName FROM photo2 WHERE board_idx =?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			if (rs.next()) {
				newFileName = rs.getString("newFileName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		System.out.println("삭제파일 존재여부 : "+newFileName);
		return newFileName;
	}

	public int del(String idx, String newFileName) {
		int success = 0;
		System.out.println("다오"+idx);
		String Sql = "";
		
		try {
			Sql = "SELECT release_state FROM board2 WHERE board_idx = ?";
			ps = conn.prepareStatement(Sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("release_state").equals("004")) {
					resClose();
					return success;
				}
			}
			
			if (newFileName != null) {
				Sql = "DELETE FROM photo2 WHERE board_idx= ?";
				ps = conn.prepareStatement(Sql);
				ps.setString(1, idx);
				ps.executeUpdate();
			}
			
			Sql = "DELETE FROM board2 WHERE board_idx= ?";
			ps = conn.prepareStatement(Sql);
			ps.setString(1, idx);
			success = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}

		return success;
	}

	
	//추천순 게시글 정렬
	public ArrayList<MainDTO> latest_array(String loginId) {
		MainDTO dto = null;
		ArrayList<MainDTO> array = new ArrayList<MainDTO>();

		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND release_state !=3 AND b.user_id\r\n" + 
				"                IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.bud_id FROM member2 m ,buddylist2 b\r\n" + 
				"                WHERE (m.user_id = b.user_id AND b.user_id = ? ) AND b.state = '002'))\r\n" + 
				"				UNION SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND release_state !=3 AND b.user_id \r\n" + 
				"				IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.user_id FROM member2 m ,buddylist2 b \r\n" + 
				"				WHERE (m.user_id = b.bud_id AND b.bud_id = ? ) AND b.state = '002'))ORDER BY board_idx DESC";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setRcnt(rs.getInt("rcnt"));
				array.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return array;
	}
	public ArrayList<MainDTO> recommend_array(String loginId) {
		MainDTO dto = null;
		ArrayList<MainDTO> array = new ArrayList<MainDTO>();

		String sql = "SELECT board_idx BOARD_IDX,\r\n" + 
				"content CONTENT,\r\n" + 
				"user_id USER_ID,\r\n" + 
				"release_state RELEASE_STATE,\r\n" + 
				"orifilename ORIFILENAME,\r\n" + 
				"newfilename NEWFILENAME,\r\n" + 
				"hashtag HASHTAG,\r\n" + 
				"writedate WRITEDATE,\r\n" + 
				"cnt CNT,\r\n" + 
				"rcnt RCNT FROM(                \r\n" + 
				"                SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND release_state !=3 AND b.user_id\r\n" + 
				"				IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.bud_id FROM member2 m ,buddylist2 b\r\n" + 
				"                WHERE (m.user_id = b.user_id AND b.user_id = ? ) AND b.state = '002')) \r\n" + 
				"                UNION \r\n" + 
				"                SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate,cnt.cnt, r.rcnt FROM board2 b, photo2 p, hashtag2 h, (SELECT board_idx, COUNT(*) cnt FROM like2 GROUP BY board_idx) cnt, (SELECT board_idx, COUNT(*) rcnt FROM reply2 GROUP BY board_idx) r\r\n" + 
				"				WHERE b.board_idx = p.board_idx(+) AND  b.board_idx = r.board_idx(+) AND  b.board_idx = cnt.board_idx(+) AND b.board_idx = h.board_idx(+) AND release_state !=3 AND b.user_id \r\n" + 
				"                IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.user_id FROM member2 m ,buddylist2 b \r\n" + 
				"                WHERE (m.user_id = b.bud_id AND b.bud_id = ? ) AND b.state = '002'))) ORDER BY CNT DESC NULLS LAST";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setWritedate(rs.getDate("writedate"));
				dto.setCnt(rs.getInt("cnt"));
				dto.setRcnt(rs.getInt("rcnt"));
				array.add(dto);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return array;
	}
	
	public int likeChk(String user_id, String board_idx) {
		int success=0;
		String sql = "select count(*) as like_chk from like2 where board_idx=? and user_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			ps.setString(2, user_id);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				success = rs.getInt("like_chk");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	public boolean likeupdate(String user_id, String board_idx) {
		boolean success=false;
		String sql = "INSERT INTO like2(board_idx,user_id) VALUES(?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			ps.setString(2, user_id);
			if(ps.executeUpdate() > 0) {
				sql = "SELECT b.user_id FROM board2 b "+
						"WHERE b.board_idx IN (SELECT board_idx FROM like2 WHERE board_idx=? AND user_id=?)";
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, board_idx);
				ps.setString(2, user_id);
				
				rs = ps.executeQuery();
				//만약 해당 게시글 작성자가 있다면...
				if(rs.next()) {
					String boardId = rs.getString("user_id");
					sql = "SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND (alarm_kind = '게시글알림' AND alarm_state = '1')";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, boardId);
					
					rs = ps.executeQuery();
					
					if(rs.next()) {
						//해당 게시글 작성자한테 댓글 알림을 보냅니다
						
						sql = "INSERT INTO alarmlist2(alarm_idx,user_id,other_id,alarm_content) VALUES(alarm2_seq.NEXTVAL,?,?,'게시글알림')";
						ps = conn.prepareStatement(sql);
						ps.setString(1, user_id);
						ps.setString(2, boardId);
						
						if(ps.executeUpdate() > 0) {
							System.out.println("게시글 작성자한테 누가 추천했는지 송신");
						}
					}else {
						System.out.println("현재 상대방의 게시글알림수신여부가 거부 상태 입니다.");
					}
					

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return !success;
		
	}

	public boolean likedelete(String user_id, String board_idx) {
		boolean success=false;
		String sql = "delete from like2 WHERE board_idx=? AND user_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			ps.setString(2, user_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return !success;
	}

	public int reportWriting(MainDTO dto) {
		int success=0;
		
		String sql="INSERT INTO report2(report_idx,user_id,board_idx,content,report_id)VALUES(report2_seq.NEXTVAL,?,?,?,?)";
		try {
				ps=conn.prepareStatement(sql);
				ps.setString(1, dto.getUser_id());
				ps.setInt(2, dto.getBoard_idx());
				ps.setString(3, dto.getContent());
				ps.setString(4, dto.getReport_id());
				success=ps.executeUpdate();
			    System.out.println(success+"리폿 성공여부");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		return success;
	}

	public int like_cnt(String board_idx) {
		int success=0;
		String sql = "SELECT COUNT(*) as cnt from like2 where board_idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				success = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}

	public int singoCk(MainDTO dto) {
		int success = 0;
		String sql ="SELECT * FROM REPORT2 WHERE USER_ID =? AND BOARD_IDX = ? AND report_state = 'FALSE'";
		String t = "";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, dto.getUser_id());
			ps.setInt(2, dto.getBoard_idx());
			rs = ps.executeQuery();	
			if (rs.next()== true) {
				t = rs.getString("report_state");
				if(t.equals("FALSE")) {
				success = 1;
				}
			}else if(rs.next() == false) {
				success = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			resClose();
		}
		
		
		return success;
	}

}