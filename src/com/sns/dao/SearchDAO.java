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
							" SELECT ROW_NUMBER() OVER(ORDER BY COUNT(h.hashtag) DESC) AS rnum," + 
							" h.hashtag, COUNT(h.hashtag) FROM hashtag2 h, board2 b" + 
							" WHERE b.board_idx = h.board_idx AND b.release_state != '003' AND TO_CHAR(b.writeDate,'YYYYMMDD') = TO_CHAR(SYSDATE, 'YYYYMMDD')" + 
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
		
	public ArrayList<SearchDTO> findTag(String find, String search, String loginId) {
		ArrayList<SearchDTO> list = new ArrayList<SearchDTO>();		
		//해시태그 검색
		if(find.equals("HashTag")) {
			String sql = "SELECT b.user_id,b.board_idx,b.content,h.hashTag FROM Board2 b, HashTag2 h" + 
							" WHERE b.board_idx = h.board_idx AND b.release_state != '003' AND b.board_idx IN (SELECT board_idx FROM HashTag2 WHERE hashTag LIKE ?)";
			try {
				ps = conn.prepareStatement(sql);//2. PrepareStatement 준비
				ps.setString(1, '%'+search+'%');//?대응
				rs = ps.executeQuery();//3. 쿼리실행
				if(search == null || search == "" || search == " " || search.equals("#")) {
					return list;
				}else {
					while(rs.next()) {//4. 데이터 존재 여부에 따라 값 넣기 -> DTO에 한번에 담기
						SearchDTO dto = new SearchDTO(); //DTO에 담기 위해 겍체화
						//dto에 담기
						dto.setUser_id(rs.getString("user_id"));
						dto.setBoard_idx(rs.getInt("board_idx"));
						dto.setContent(rs.getString("content"));
						dto.setHashTag(rs.getString("hashTag"));
						list.add(dto);//담긴 dto를 list에 넣기				
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resClose();//5. 자원정리
			}		
		//유저 검색
		}else if(find.equals("User")) {
			
			/*String sql = "SELECT user_id, bud_id, state FROM buddylist2 WHERE (user_id = ? AND bud_id = ?) AND state = '002'" + 
							" UNION" + 
							" SELECT user_id, bud_id, state FROM buddylist2 WHERE (bud_id = ? AND user_id = ?) AND state = '002'";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, search);
				ps.setString(2, loginId);
				ps.setString(3, search);
				ps.setString(4, loginId);
				rs = ps.executeQuery();
				while(rs.next()) {
					SearchDTO dto = new SearchDTO();
					dto.setUser_id(rs.getString("user_id"));
					dto.setBud_id(rs.getString("bud_id"));
					dto.setState(rs.getString("state"));
					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			
			String sql = "SELECT user_id,name FROM member2 WHERE user_id = ?";
			try {
				ps = conn.prepareStatement(sql);//2. PrepareStatement 준비
				ps.setString(1, search);//?대응
				rs = ps.executeQuery();//3. 쿼리실행
				if(search.equals(loginId)) {
					return list;
				}else {
					while(rs.next()) {
						SearchDTO dto = new SearchDTO(); //DTO에 담기 위해 겍체화
						//dto에 담기
						dto.setUser_id(rs.getString("user_id"));
						dto.setName(rs.getString("name"));
						list.add(dto);//담긴 dto를 list에 넣기				
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				resClose();//5. 자원정리
			}	
		}
		return list;			
	}

	public int budReq(String loginId, String budId) {
		int success = 0;
		//친구요청은 한 번만 가능하게(기존 데이터 있는지 확인)
		String sql = "SELECT user_id,bud_id FROM BuddyList2 "
							+ "WHERE user_id=? AND bud_id=? OR bud_id=? AND user_id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, budId);
			ps.setString(3, loginId);
			ps.setString(4, budId);
			rs = ps.executeQuery();
			if(rs.next()) {
				success = 0;
			}else if(!loginId.equals(budId)) {
				sql = "INSERT INTO BuddyList2(user_id,bud_id,state) VALUES(?,?,'001')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, loginId);
				ps.setString(2, budId);
				success = ps.executeUpdate();
				if(success > 0) {
				
					sql = "SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND (alarm_kind = '친구요청알림' AND alarm_state = '1')";
					
					ps = conn.prepareStatement(sql);
					ps.setString(1, budId);
					
					rs = ps.executeQuery();
					
					if(rs.next()) {
						sql = "INSERT INTO alarmlist2(alarm_idx,user_id,other_id,alarm_content) VALUES(alarm2_seq.NEXTVAL,?,?,'친구요청알림')";
						ps = conn.prepareStatement(sql);
						ps.setString(1, loginId);
						ps.setString(2, budId);
						
						if(ps.executeUpdate()>0) {
							System.out.println("친구요청알림 전송 완료");
						}
					}else {
						System.out.println("친구요청알림 전송 실패.. cause:상대방이 친구요청알림을 거부했습니다.");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();//5. 자원정리
		}
		return success;	
	}
	//친구 삭제
	public int budDel(String loginId, String budId) {
		int success = 0;
		/*String sql = "DELETE FROM BuddyList2 WHERE user_id=? AND bud_id=? AND state='002'";*/
		String sql = "DELETE FROM BuddyList2 WHERE (user_id=? AND bud_id=?)OR(bud_id=? AND user_id=?) AND state='002'";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, budId);
			ps.setString(3, loginId);
			ps.setString(4, budId);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return success;
	}

}