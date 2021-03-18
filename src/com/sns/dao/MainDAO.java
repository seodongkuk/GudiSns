package com.sns.dao;

import com.sns.dto.MainDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	public long write(MainDTO dto , String loginId) {
		String sql = "INSERT INTO board2(board_idx,content,release_state,user_id)VALUES(board2_seq.NEXTVAL,?,?,?)";
		long bIdx = 0L;

		try {
			ps = conn.prepareStatement(sql, new String[]{"board_idx"});
			ps.setString(1, dto.getContent());
			ps.setInt(2, dto.getRelase_state());
			ps.setString(3, loginId);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
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
		String sql = "SELECT b.board_idx, b.content, b.release_state, p.oriFileName, p.newFileName FROM board2 b, photo2 p WHERE b.board_idx = p.file_idx(+) AND b.board_idx = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			System.out.println("상세보기중 해당하는애 스타트");
			if (rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setRelase_state(rs.getInt("release_state"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
			}

			
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			resClose();
		}

		return dto;
	}

	public ArrayList<MainDTO> mylist(String user_id) {
		MainDTO dto = null;
		ArrayList<MainDTO> list = new ArrayList<MainDTO>();

		String sql = "SELECT * FROM post WHERE user_id=?";


		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				list.add(dto);
			}
		} catch (SQLException var5) {
			var5.printStackTrace();
		}finally {
			resClose();
		}

		return list;
	}

	public int edit(MainDTO dto) {
		String sql = "UPDATE board2 SET content=? ,release_state=? WHERE board_idx=?";
		int success = 0;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getContent());
			ps.setInt(2, dto.getRelase_state());
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

		try {
			String boardSql;
			if (newFileName != null) {
				boardSql = "DELETE FROM photo2 WHERE file_idx=?";
				ps = conn.prepareStatement(boardSql);
				ps.setString(1, idx);
				ps.executeUpdate();
			}

			boardSql = "DELETE FROM board2 WHERE board_idx=?";
			ps = conn.prepareStatement(boardSql);
			ps.setString(1, idx);
			success = ps.executeUpdate();
		} catch (SQLException var8) {
			var8.printStackTrace();
		} finally {
			resClose();
		}

		return success;
	}

	public ArrayList<MainDTO> flist(String loginId) {
		MainDTO dto = null;
		
		ArrayList<MainDTO> flist = new ArrayList<MainDTO>();

		String sql = "SELECT * FROM post WHERE user_id IN "
				+ "(SELECT b.bud_id FROM member2 m ,buddylist2 b "
				+ "WHERE (m.user_id = b.user_id AND b.user_id = ?) AND b.state = '002')";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				dto = new MainDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				flist.add(dto);
					}
			
			
		} catch (SQLException var5) {
			var5.printStackTrace();
		}finally {
			resClose();
		}

		return flist;
		
	}
}