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
import com.sns.dto.AdminDTO;
import com.sns.dto.MainDTO;

public class AdminDAO {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public AdminDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			System.out.println("자원 OPEN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("자원 CLOSE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean admin_login(String id, String pw) {
		boolean success = false;
		String sql = "SELECT admin_id FROM admin2 WHERE admin_ID=? AND PW=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			success = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

	public HashMap<String, Object> reportList(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 5;

		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT * FROM "
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY report_idx DESC) AS rnum,report_idx,user_id,board_idx,content,report_date,report_state,admin_id	 FROM Report2 WHERE REPORT_STATE='FALSE')"
				+ "WHERE rnum BETWEEN ? AND ?";
		ArrayList<AdminDTO> reportList = new ArrayList<AdminDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setReport_idx(rs.getInt("report_idx"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setReport_date(rs.getDate("report_date"));
				dto.setReport_state(rs.getString("report_state"));
				dto.setAdmin_id(rs.getString("admin_id"));
				reportList.add(dto);
			}
			System.out.println("reportList size : " + reportList.size());
			
			int maxPage = getMaxPage(pagePerCnt);
			map.put("reportList", reportList);
			map.put("maxPage", maxPage);
			System.out.println("max page : " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;

	}

	private int getMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(Report_idx) FROM Report2 WHERE REPORT_STATE='FALSE'";
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return max;
	}

	public AdminDTO report_detail(String board_idx) {
		AdminDTO dto = null;
		String sql = "select b.board_idx, b.user_id, b.content, b.writedate, b.report_state, b.release_state, p.orifilename, p.newfilename from board2 b left join photo2 p on b.board_idx = p.board_idx where b.board_idx=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, board_idx);
			rs = ps.executeQuery();
			if (rs.next()) {
				dto = new AdminDTO();
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				dto.setRelease_state(rs.getString("release_state"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setWritedate(rs.getDate("writedate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return dto;
	}
	public HashMap<String, Object> blackList(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;

		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT * FROM"
				+ " (SELECT ROW_NUMBER() OVER(ORDER BY BLK_IDX DESC) AS rnum,blk_idx,user_id,blk_state,proc_date,report_idx,admin_id FROM blacklist2 WHERE blk_state='TRUE')"
				+ " WHERE rnum BETWEEN ? AND ?";

		ArrayList<AdminDTO> blackList = new ArrayList<AdminDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setBlk_idx(rs.getInt("blk_idx"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setBlk_state(rs.getString("blk_state"));
				dto.setProc_date(rs.getDate("proc_date"));
				dto.setReport_idx(rs.getInt("report_idx"));
				dto.setAdmin_id(rs.getString("admin_id"));
				blackList.add(dto);
			}
			System.out.println("blackList size : " + blackList.size());
			int maxPage = blackgetMaxPage(pagePerCnt);
			map.put("blackList", blackList);
			map.put("maxPage", maxPage);
			System.out.println("max page : " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}
	private int blackgetMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(blk_idx) FROM blacklist2 WHERE blk_state='TRUE'";
		int max = 0; 
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return max;
	}
	public HashMap<String, Object> blindlist(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 10;

		int end = page * pagePerCnt;
		int start = end - (pagePerCnt - 1);

		String sql = "SELECT * FROM(SELECT ROW_NUMBER() OVER(ORDER BY b.report_idx DESC) AS rnum,b.report_idx,b.blind_date,b.admin_id,b.blind_state,r.content,r.board_idx\r\n" + 
				"    FROM blindlist2 b, report2 r WHERE b.report_idx = r.report_idx AND b.blind_STATE='TRUE') WHERE rnum BETWEEN ? AND ?";

		ArrayList<AdminDTO> blindlist = new ArrayList<AdminDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while (rs.next()) {
				AdminDTO dto = new AdminDTO();
				dto.setReport_idx(rs.getInt("report_idx"));;
				dto.setBlind_date(rs.getDate("blind_date"));
				dto.setAdmin_id(rs.getString("admin_id"));
				dto.setBlind_state(rs.getString("blind_state"));
				dto.setBoard_idx(rs.getInt("board_idx"));
				dto.setContent(rs.getString("content"));
				blindlist.add(dto);
			}
			System.out.println("blindlist size : " + blindlist.size());
			int maxPage = blindgetMaxPage(pagePerCnt);
			map.put("blindList", blindlist);
			map.put("maxPage", maxPage);
			System.out.println("max page : " + maxPage);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return map;
	}
	private int blindgetMaxPage(int pagePerCnt) {
		String sql = "SELECT COUNT(report_idx) FROM blindlist2 WHERE blind_state='TRUE'";
		int max = 0; 
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt / (double) pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return max;
	}

	public void reportProcess(String blind, String reportIdx, String admin) {
		
		//report_id가 처리대상자
		String sql = "";
		String report_id = "";
		String bIdx = "";
		
		try {
			if(blind.equals("blind")) {
				System.out.println("블라인드만 처리합니다.");
				sql = "UPDATE report2 SET report_state = 'TRUE' WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				
				if(ps.executeUpdate() > 0) {
						sql = "SELECT board_idx FROM report2 WHERE report_idx = ?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, reportIdx);
						rs = ps.executeQuery();
						if(rs.next()) {
							bIdx = rs.getString("board_idx");
							sql = "UPDATE board2 SET release_state = '004' WHERE board_idx = ?";
							ps = conn.prepareStatement(sql);
							ps.setString(1, bIdx);
							
							if(ps.executeUpdate() > 0) {
								sql = "INSERT INTO blindlist2(report_idx,blind_state,admin_id) VALUES(?,'TRUE',?)";
								ps = conn.prepareStatement(sql);
								ps.setString(1, reportIdx);
								ps.setString(2, admin);
								if(ps.executeUpdate() > 0) {
									System.out.println("블라인드 처리 성공");
								}else {
									System.out.println("블라인드 처리 실패...");
								}
							}
							
							
							

						}
					

				}
			}
			
			if(blind.equals("black")) {
				System.out.println("블랙(블라인드 포함) 처리합니다.");
				sql = "UPDATE report2 SET report_state = 'TRUE' WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				
				if(ps.executeUpdate() > 0) {
						sql = "SELECT report_id FROM report2 WHERE report_idx = ?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, reportIdx);
						rs = ps.executeQuery();
						if(rs.next()) {
							report_id = rs.getString("report_id");
							sql = "UPDATE board2 SET release_state = '004' WHERE user_id = ?";
							ps = conn.prepareStatement(sql);
							ps.setString(1, report_id);
							if(ps.executeUpdate() > 0) {
								sql = "INSERT INTO blacklist2(user_id,blk_idx,report_idx,blk_state,admin_id) VALUES(?,blacklist2_seq.NEXTVAL,?,'TRUE',?)";
								ps = conn.prepareStatement(sql);
								ps.setString(1, report_id);
								ps.setString(2, reportIdx);
								ps.setString(3, admin);
								if(ps.executeUpdate() > 0) {
									sql = "UPDATE member2 SET blk_state = 'TRUE' WHERE user_id = ?";
									ps = conn.prepareStatement(sql);
									ps.setString(1, report_id);
									ps.executeUpdate();
									System.out.println("블랙리스트 IN 블라인드 처리 성공");
								}else {
									System.out.println("블랙리스트 IN 블라인드 처리 실패...");
								}
							}
							

						}
					

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
	}

	public void blindCancel(String reportIdx) {
		
		String sql = "";
		int bIdx = 0;
		
		
		try {
			sql = "UPDATE blindlist2 SET blind_state = 'FALSE' WHERE report_idx = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, reportIdx);
			if(ps.executeUpdate() > 0) {
				sql = "UPDATE report2 SET report_state = 'DONE' WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				if(ps.executeUpdate() > 0) {
				sql = "SELECT board_idx FROM report2 WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				rs = ps.executeQuery();
				if(rs.next()) {
					bIdx = rs.getInt("board_idx");
					System.out.println(bIdx);
					sql = "UPDATE board2 SET release_state = '001' WHERE board_idx = ?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, bIdx);
					if(ps.executeUpdate() > 0) {
						System.out.println("블라인드 취소 하였습니다.");
					}else {
						System.out.println("블라인드 취소 실패...");
					}
				}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

	public void blackCancel(String reportIdx) {
		String sql = "";
		String id = "";
		
		
		try {
			sql = "UPDATE blacklist2 SET blk_state = 'FALSE' WHERE report_idx = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, reportIdx);
			if(ps.executeUpdate() > 0) {
				sql = "UPDATE report2 SET report_state = 'DONE' WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				if(ps.executeUpdate() > 0) {
				sql = "SELECT report_id FROM report2 WHERE report_idx = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, reportIdx);
				rs = ps.executeQuery();
				if(rs.next()) {
					id = rs.getString("report_id");
					System.out.println(id);
					sql = "UPDATE board2 SET release_state = '001' WHERE user_id = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					if(ps.executeUpdate() > 0) {
						sql = "UPDATE member2 SET blk_state = 'FALSE' WHERE user_id = ?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, id);
						ps.executeUpdate();
						System.out.println("블랙 취소 하였습니다.");
					}else {
						System.out.println("블랙 취소 실패...");
					}
				}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}
}
