package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.AlarmDTO;

public class AlarmDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;

	public AlarmDAO() {
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

	public ArrayList<AlarmDTO> alarmList(String loginId) {
		
		ArrayList<AlarmDTO> list = new ArrayList<AlarmDTO>();
		//현재 로그인한 사람의 알람리스트를 모두 가져온다..(알람 수신 여부가 0이 아닌 얘들로만 그리고 읽음 처리 안된 얘들만)
		String sql = "SELECT * FROM alarmlist2 WHERE ((other_id=?) AND (alarm_read_state = 0)) "+ 
			    "AND alarm_content IN (SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND alarm_state != 0)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, loginId);
			rs = ps.executeQuery();
			while(rs.next()) {
				AlarmDTO dto = new AlarmDTO();
				dto.setAlarm_idx(rs.getInt("alarm_idx"));
				dto.setUser_id(rs.getString("user_id"));
				dto.setAlarm_reg_date(rs.getDate("alarm_reg_date"));
				dto.setOther_id(rs.getString("other_id"));
				dto.setAlarm_content(rs.getString("alarm_content"));
				dto.setAlarm_read_state(rs.getString("alarm_read_state"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return list;
	}

	public int buddyAdd(String userId, String buddyId) {
		int success = 0;
		String sql = "SELECT user_id,bud_id FROM BuddyList2 "
				+ "WHERE (user_id=? AND bud_id=?) OR (bud_id=? AND user_id=?) AND state= '001'";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, buddyId);
			ps.setString(3, userId);
			ps.setString(4, buddyId);
			
			rs = ps.executeQuery();
			
			//만약의 친구요청한 사람과 받은사람이 있고 state가 001이 맞다면...
			if(rs.next()) {
				System.out.println("친구요청 상태 전환 -> 002");
				sql = "UPDATE buddylist2 SET state='002' WHERE (user_id=? AND bud_id=?) OR (bud_id=? AND user_id=?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, buddyId);
				ps.setString(3, userId);
				ps.setString(4, buddyId);
				
				success = ps.executeUpdate();
			}else {
				System.out.println("친구요청 상태 전환 실패 -> 이미 친구상태 입니다.");
			}
			
			//로그인한 유저가 요청을 했고 친구요청알람을 받는 사람이라면.. 읽음 처리
			sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE (user_id=? AND other_id=?) AND alarm_content='친구요청알림'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, buddyId);
			ps.setString(2, userId);
			if(ps.executeUpdate() > 0) {
				System.out.println("친구요청알림 읽음 처리 완료!!!");
			}else {
				System.out.println("친구요청알림 읽음 처리 실패...");
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return success;
		
	}

	public int buddyIgnore(String userId, String buddyId) {
		int success = 0;
		String sql = "SELECT user_id,bud_id FROM BuddyList2 "
				+ "WHERE (user_id=? AND bud_id=?) OR (bud_id=? AND user_id=?) AND state= 001";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, buddyId);
			ps.setString(3, userId);
			ps.setString(4, buddyId);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				System.out.println("친구요청 상태 전환 -> 003");
				sql = "UPDATE buddylist2 SET state=003 WHERE (user_id=? AND bud_id=?) OR (bud_id=? AND user_id=?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, buddyId);
				ps.setString(3, userId);
				ps.setString(4, buddyId);
				
				success = ps.executeUpdate();
			}else {
				System.out.println("친구요청 상태 전환 실패 -> 이미 친구요청 무시상태 입니다.");
			}
			
			//로그인한 유저가 요청을 했고 친구요청알람을 받는 사람이라면.. 읽음 처리
			sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE (user_id=? AND other_id=?) AND alarm_content='친구요청알림' AND alarm_read_state = 0";
			ps = conn.prepareStatement(sql);
			ps.setString(1, buddyId);
			ps.setString(2, userId);
			if(ps.executeUpdate() > 0) {
				System.out.println("친구요청알림 읽음 처리 완료!!!");
			}else {
				System.out.println("친구요청알림 읽음 처리가 이미 되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return success;
	}

	public void alarmAllChk(String userId) {

		//친구요청알림을 제외한 로그인 유저의 알람확인여부를 읽음 처리 한다.
		String sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE (other_id=? AND alarm_content != '친구요청알림') AND alarm_read_state = 0";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			
			if(ps.executeUpdate() > 0) {
				System.out.println(userId+"님의 친구요청알림을 제외한 모든 알림을 읽음 처리 하였음");
			}else {
				System.out.println("이미 모든 알림을 읽음 처리 했습니다..");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

	public void alarmLikeDel(String userId, String idx) {
		//친구요청알림을 제외한 로그인 유저의 알람확인여부를 읽음 처리 한다.
		String sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE alarm_idx=? AND (other_id=? AND alarm_content = '게시글알림') AND alarm_read_state = 0";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			ps.setString(2, userId);
			
			if(ps.executeUpdate() > 0) {
				System.out.println(userId+"님의 좋아요 알림을 읽음 처리 하였음");
			}else {
				System.out.println("이미 좋아요 알림을 읽음 처리 했습니다..");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

	public void alarmDmDel(String userId, String idx) {
		String sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE alarm_idx=? AND (other_id=? AND alarm_content = 'DM알림') AND alarm_read_state = 0";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			ps.setString(2, userId);
			
			if(ps.executeUpdate() > 0) {
				System.out.println(userId+"님의 DM 알림을 읽음 처리 하였음");
			}else {
				System.out.println("이미 DM 알림을 읽음 처리 했습니다..");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

	public void alarmCommentDel(String userId, String idx) {
		String sql = "UPDATE alarmlist2 SET alarm_read_state = 1 WHERE alarm_idx=? AND (other_id=? AND alarm_content = '댓글알림') AND alarm_read_state = 0";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			ps.setString(2, userId);
			
			if(ps.executeUpdate() > 0) {
				System.out.println(userId+"님의 댓글 알림을 읽음 처리 하였음");
			}else {
				System.out.println("이미 댓글 알림을 읽음 처리 했습니다..");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

}
