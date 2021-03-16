package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sns.dto.MainDTO;

public class MainDAO {
	
	Connection conn= null;
	PreparedStatement ps= null;
	ResultSet rs = null;
	
	public MainDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void resClose() {
		try {
			if(rs != null) {rs.close();}
			if(ps != null) {ps.close();}
			if(conn!=null) {conn.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long write(MainDTO dto) {
		String sql ="INSERT INTO board2_test(board_idx,content,release_state)VALUES(board3_seq.NEXTVAL,?,?)";
		long bIdx=0;
		
		try {
			ps= conn.prepareStatement(sql,new String[] {"board_idx"});
			ps.setString(1, dto.getContent());
			ps.setInt(2, dto.getRelase_state());  // 001002 를 인트로 넣어주고.
			
			ps.executeUpdate();
			rs= ps.getGeneratedKeys();
			if(rs.next()) {
				bIdx =rs.getLong(1);
				System.out.println("bidx : " +bIdx);
				
				if(dto.getOriFileName() != null) {
					sql = "INSERT INTO Photo2_test(file_idx,oriFileName,newFileName,board_idx)VALUES(Photo2_test_seq.NEXTVAL,?,?,?)";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, dto.getOriFileName());
					ps.setString(2, dto.getNewFileName());
					ps.setLong(3, bIdx);
					
					ps.executeUpdate();
					
				}
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		
		return bIdx;
	}

}
