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

public class AdminDAO {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	public AdminDAO() {
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
	public boolean admin_login(String id, String pw) {			
		boolean success = false;
		String sql="SELECT admin_id FROM admin2 WHERE admin_ID=? AND PW=?";
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
	public HashMap<String, Object> reportList(int page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int pagePerCnt = 2;
	
		int end = page * pagePerCnt;	
		int start = end-(pagePerCnt-1);
		
		
		String sql="SELECT * FROM "
				+"(SELECT ROW_NUMBER() OVER(ORDER BY report_idx DESC) AS rnum,report_idx,user_id,board_idx,content,report_date,report_state,admin_id	 FROM Report2)"
				+ "WHERE rnum BETWEEN ? AND ?";
		ArrayList<AdminDTO> reportList = new ArrayList<AdminDTO>();		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			while(rs.next()) {
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
			System.out.println("reportList size : "+reportList.size());			
			int maxPage = getMaxPage(pagePerCnt);
			map.put("reportList", reportList);
			map.put("maxPage", maxPage);
			System.out.println("max page : "+maxPage);			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return map;
		
	}
	private int getMaxPage(int pagePerCnt) {		
		String sql="SELECT COUNT(Report_idx) FROM Report2";		
		int max = 0;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				int cnt = rs.getInt(1);
				max = (int) Math.ceil(cnt/(double)pagePerCnt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return max;
	}
	public AdminDTO  report_content(String report_idx) {
		AdminDTO dto =null;
		String sql="SELECT * FROM Report2 Where report_idx=? ";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,report_idx);
			rs = ps.executeQuery();
			if(rs.next()){
				dto = new AdminDTO();
				dto.setReport_idx(rs.getInt("report_idx"));
				dto.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		
		return dto;

		
}
}

