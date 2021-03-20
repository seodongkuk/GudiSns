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
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}
	
	public ArrayList<MainDTO> flist(String loginId) {
		MainDTO dto = null;
		
		ArrayList<MainDTO> flist = new ArrayList<MainDTO>();
		
		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashTag, b.writedate FROM board2 b, photo2 p, hashtag2 h  \r\n"
				+ "							 WHERE b.board_idx = p.board_idx(+) AND b.board_idx = h.board_idx(+) AND release_state !=3 AND b.user_id\r\n"
				+ "                             IN (SELECT b.user_id FROM board2 b WHERE b.user_id IN(SELECT b.bud_id FROM member2 m ,buddylist2 b\r\n"
				+ "WHERE (m.user_id = b.user_id AND b.user_id = ? ) AND b.state = '002')) ORDER BY board_idx DESC";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
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
				
				flist.add(dto);
			}
			
		} catch (SQLException var5) {
			var5.printStackTrace();
			
		}finally {
			
			resClose();
		}
		
		return flist;
		
	}
	
	public ArrayList<MainDTO> mylist(String user_id) {
		MainDTO dto = null;
		ArrayList<MainDTO> list = new ArrayList<MainDTO>();

		String sql = "SELECT b.board_idx, b.content, b.user_id, b.release_state, p.oriFileName, p.newFileName, h.hashtag, b.writedate FROM board2 b, photo2 p, hashtag2 h\r\n"
				+ "    WHERE b.board_idx = p.board_idx(+) AND b.board_idx = h.board_idx(+) AND b.user_id = ?";


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
				list.add(dto);
			}
		} catch (SQLException var5) {
			var5.printStackTrace();
		}finally {
			resClose();
		}

		return list;
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
				if(dto.getHashTag() != null) {
					sql = "INSERT INTO hashTag2(board_idx, hashTag) VALUES(?,?)";
					ps = conn.prepareStatement(sql);
					ps.setLong(1, bIdx);
					ps.setString(2, dto.getHashTag());
					ps.executeUpdate();
				}
			}
		} catch (SQLException var9) {
			var9.printStackTrace();
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

			
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			resClose();
		}

		return dto;
	}

	public int edit(MainDTO dto) {
		String sql = "UPDATE board2 SET content=? ,release_state=? WHERE board_idx=?";
		int success = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getContent());
			ps.setString(2, dto.getRelease_state());
			ps.setInt(3, dto.getBoard_idx());
			success = ps.executeUpdate();
			System.out.println(success + "갯수");
		} catch (SQLException var8) {
			var8.printStackTrace();
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
		} catch (SQLException var9) {
			var9.printStackTrace();
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
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			resClose();
		}

		return newFileName;
	}

	public int del(String idx, String newFileName) {
		int success = 0;
		System.out.println("다오"+idx);
		String Sql;
		try {
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
			
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			resClose();
		}

		return success;
	}

	
	//추천순 게시글 정렬
	public ArrayList<MainDTO> latest_array(String loginId) {
		MainDTO dto = null;
		ArrayList<MainDTO> array = new ArrayList<MainDTO>();

		String sql = "SELECT b.board_idx,b.release_state, b.writedate, b.content,b.user_id, p.oriFileName, p.newFileName "
				+ "FROM board2 b LEFT OUTER JOIN photo2 p ON  p.board_idx=b.board_idx "
				+ "WHERE release_state != 3 AND b.user_id "
				+ "IN (SELECT b.user_id FROM board2 b WHERE b.user_id "
				+ "IN(SELECT b.bud_id FROM member2 m ,buddylist2 b "
				+ "WHERE (m.user_id = b.user_id AND b.user_id = ?) AND b.state = '002')) ORDER BY board_idx DESC";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setWritedate(rs.getDate("writedate"));
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
		String sql = "Insert INTO like2(board_idx,user_id) VALUES(?,?)";
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
		String sql ="SELECT * FROM REPORT2 WHERE USER_ID =? AND BOARD_IDX = ?";
		
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, dto.getUser_id());
			ps.setInt(2, dto.getBoard_idx());
			rs = ps.executeQuery();
			
			if (rs.next()== true) {
				success =0;
			}else if(rs.next() == false){
				sql="INSERT INTO report2(report_idx,user_id,board_idx,content,report_id)VALUES(report2_seq.NEXTVAL,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, dto.getUser_id());
				ps.setInt(2, dto.getBoard_idx());
				ps.setString(3, dto.getContent());
				ps.setString(4, dto.getReport_id());
				success=ps.executeUpdate();
			}
			
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
}