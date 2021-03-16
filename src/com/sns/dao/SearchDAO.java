package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.SearchDTO;

public class SearchDAO {
	//중복적으로 사용되므로 전역변수 선언
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public SearchDAO() {
		try {//DB접속
			Context ctx = new InitialContext();//Context 객체화
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");//DataSource 변환
			conn = ds.getConnection();//DB연결
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//자원정리 메서드(중복코드방지)
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
	
	public ArrayList<SearchDTO> todayTag() {
		ArrayList<SearchDTO> tag = new ArrayList<SearchDTO>();
		//해시태그 TOP3
		String sql = "SELECT * FROM (" + 
				"SELECT ROW_NUMBER() OVER(ORDER BY COUNT(h.hashtag) DESC) AS rnum," + 
				" h.hashtag, COUNT(h.hashtag) FROM hashtag2 h, board2 b" + 
				" WHERE b.board_idx = h.board_idx AND TO_CHAR(b.reg_date,'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')" + 
				" GROUP BY h.hashtag" + 
				" ) WHERE rnum <= 3";
		try {
			ps = conn.prepareStatement(sql);//2. PrepareStatement 준비
			rs = ps.executeQuery();//3. 쿼리실행
			while(rs.next()) {//4. 데이터 존재 여부에 따라 값 넣기 -> DTO에 한번에 담기
				SearchDTO dto = new SearchDTO(); //DTO에 담기 위해 겍체화
				//dto에 담기
				dto.setHashTag(rs.getString("hashTag"));
				dto.setRnum(rs.getInt("rnum"));
				tag.add(dto);//담긴 dto를 list에 넣기	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();//5. 자원정리
		}
		return tag;		
	}	
	
	public ArrayList<SearchDTO> findTag(String find, String search) {
		ArrayList<SearchDTO> list = new ArrayList<SearchDTO>();
		//해시태그 검색
		if(find.equals("HashTag")) {
			String sql = "SELECT b.user_id,b.board_idx,b.content,h.hashTag FROM Board2 b, HashTag2 h "
					+"WHERE b.board_idx = h.board_idx AND b.board_idx IN (SELECT board_idx FROM HashTag2 WHERE hashTag = ?)";
			try {
				ps = conn.prepareStatement(sql);//2. PrepareStatement 준비
				ps.setString(1, search);//?대응
				rs = ps.executeQuery();//3. 쿼리실행
				while(rs.next()) {//4. 데이터 존재 여부에 따라 값 넣기 -> DTO에 한번에 담기
					SearchDTO dto = new SearchDTO(); //DTO에 담기 위해 겍체화
					//dto에 담기
					dto.setUser_id(rs.getString("user_id"));
					dto.setBoard_idx(rs.getInt("board_idx"));
					dto.setContent(rs.getString("content"));
					dto.setHashTag(rs.getString("hashTag"));
					list.add(dto);//담긴 dto를 list에 넣기				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resClose();//5. 자원정리
			}	
		//유저 검색
		}else if(find.equals("User")) {
			String sql = "SELECT user_id,name FROM member2 WHERE user_id = ?";
			try {
				ps = conn.prepareStatement(sql);//2. PrepareStatement 준비
				ps.setString(1, search);//?대응
				rs = ps.executeQuery();//3. 쿼리실행
				while(rs.next()) {//4. 데이터 존재 여부에 따라 값 넣기 -> DTO에 한번에 담기
					SearchDTO dto = new SearchDTO(); //DTO에 담기 위해 겍체화
					//dto에 담기
					dto.setUser_id(rs.getString("user_id"));
					dto.setName(rs.getString("name"));
					list.add(dto);//담긴 dto를 list에 넣기				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resClose();//5. 자원정리
			}	
		}
		return list;			
	}
	
	

}
