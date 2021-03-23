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

import com.sns.dto.DmDTO;

public class DmDAO {

	//아래 3개 필드는 여러 메서드에서 사용할것이기 때문에 메서드 블록 밖에다 선언
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	//생성자 호출(객체 초기화를 하면 이놈이 제일 먼저 실행됨)
	//공통적으로 DB 연결 작업을 수행함
	public DmDAO() {
		try {
			//InitialContext 예외 발생있음
			//Context 객체화
			Context ctx = new InitialContext();
			//ctx에서 name을 찾은 다음 DataSource 형태로 담는다(명시적 형변환 필요)
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			//ds.getConnection 예외 발생 있음
			//DataSource로 부터 Connection을 가져와서 담는다.
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//공통 자원 종료 메서드
	private void resClose() {
			try {
				if(rs != null) {
				rs.close();
				}
				if(ps != null) {
				ps.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
public HashMap<String, Object> chatRoom(String id) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<DmDTO> list = new ArrayList<DmDTO>();

		String sql = "SELECT dm_idx,TO_CHAR(sendtime,'YY/MM/DD HH24:MI') as time,content,read_state,user_id,recieve_id FROM" + 
				" (SELECT dm_idx,sendtime,content,read_state,user_id,recieve_id," + 
				"ROW_NUMBER() OVER(PARTITION BY dm_idx ORDER BY sendtime DESC) as rnum FROM dm2" + 
				" WHERE user_id = ? OR recieve_id = ?)" + 
				" WHERE rnum = 1 ORDER BY sendtime DESC";
		
		boolean success = false;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, id);
			ps.setString(2, id);
			
			rs = ps.executeQuery();

			while(rs.next()) {
					DmDTO dm = new DmDTO();
					dm.setDm_idx(rs.getInt("dm_idx"));
					dm.setSendtime(rs.getString("time"));
					dm.setContent(rs.getString("content"));
					dm.setRead_state(rs.getString("read_state"));
					dm.setSend_id(rs.getString("user_id"));
					dm.setRecieve_id(rs.getString("recieve_id"));
					list.add(dm);
					success = true;
				}
				
				map.put("list", list);
				map.put("success", success);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		System.out.println("방 개수 : "+list.size());
		return map;
	}


	//채팅방 존재 여부를 확인 후 채팅방 개설하는 메서드
	public HashMap<String, Object> createChat(String fromId, String toId) {
		
		boolean success = false;
		String sql = "";
		HashMap<String,Object> map = new HashMap<String,Object>();
		System.out.println(fromId+toId);
		//현재 대화 요청한 사람과 요청받은 사람의 방번호가 있는지 확인...
		sql = "SELECT chat_idx FROM chatroom2 WHERE (user_id=? AND recieve_id=?) OR (user_id=? AND recieve_id=?)";

		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, fromId);
			ps.setString(2, toId);
			ps.setString(3, toId);
			ps.setString(4, fromId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("방이 존재합니다..");
				success = true;
				map.put("chatIdx", rs.getInt("chat_idx"));
				map.put("success", success);
				System.out.println(map.get("chatIdx"));
				resClose();
				return map;
			}else {
				//현재 대화 요청한 사람과 요청받은 사람의 방번호를 새로 만듦
				sql = "INSERT INTO chatroom2(chat_idx,user_id,recieve_id) VALUES(chatroom2_seq.NEXTVAL,?,?)";
				
				ps = conn.prepareStatement(sql);
				
				ps.setString(1, fromId);
				ps.setString(2, toId);
				
				if(ps.executeUpdate() > 0) {
					success = true;
				}
				System.out.println("방 만들기 성공!!!"+success);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return map;
	}



	public void updateChat(String fromId, String toId, String content) {
		//보내는 사람과 받는 사람이 일치하거나 받는 사람과 보내는 사람이 일치하면...
		String sql = "INSERT INTO dm2(dm_idx,user_id,recieve_id,content) "
				+ "VALUES((SELECT chat_idx FROM chatroom2 WHERE (user_id=? AND recieve_id=?) OR (user_id=? AND recieve_id=?)),?,?,?)";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, fromId);
			ps.setString(2, toId);
			ps.setString(3, toId);
			ps.setString(4, fromId);
			ps.setString(5, fromId);
			ps.setString(6, toId);
			ps.setString(7, content);
			
			if(ps.executeUpdate() > 0) {
				
				sql = "SELECT alarm_kind FROM alarmsetting2 WHERE user_id = ? AND (alarm_kind = 'DM알림' AND alarm_state = '1')";
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, toId);
				
				rs = ps.executeQuery();
				//알림 수신 설정이 되어있다면 보내고...
				if(rs.next()) {
					System.out.println("채팅 알림 전송!!!");
					sql = "INSERT INTO alarmlist2(alarm_idx,user_id,other_id,alarm_content) VALUES(alarm2_seq.NEXTVAL,?,?,'DM알림')";
					ps = conn.prepareStatement(sql);
					ps.setString(1, fromId);
					ps.setString(2, toId);
					
					if(ps.executeUpdate() > 0) {
						System.out.println(fromId+"님이 받는사람("+toId+") 한테 DM을 보냈습니다.");
					}
				}else {
					System.out.println("상대방이 DM 수신을 거부했습니다.");
				}
				

			}
			
			System.out.println("채팅 업데이트 성공!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
	}



	public HashMap<String, Object> dmRoom(String id, String create, String idx) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<DmDTO> list = new ArrayList<DmDTO>();
		boolean success = false;
		boolean success_two = false;
		int chatIdx = 0;
		String sql = "SELECT d.content,TO_CHAR(d.sendtime,'YY/MM/DD HH24:MI') as time,d.user_id,d.recieve_id,d.read_state FROM dm2 d WHERE d.dm_idx = ? ORDER BY d.sendtime ASC";
		
		try {
			if(idx == null || Integer.parseInt(idx) == 0) {
				System.out.println("방번호 없음");
				sql = "SELECT chat_idx FROM chatroom2 WHERE (user_id=? AND recieve_id=?) OR (user_id=? AND recieve_id=?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, create);
				ps.setString(3, create);
				ps.setString(4, id);
				rs = ps.executeQuery();
				if(rs.next()) {
					chatIdx = rs.getInt("chat_idx");
					success = true;
				}
				
				map.put("success", success);
				map.put("chatIdx",chatIdx);
				System.out.println(chatIdx+" 번호의 방은 기존에 만들어졌습니다.");
				resClose();
				return map;
			}
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(idx));
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				DmDTO dmAll = new DmDTO();				
				dmAll.setSendtime(rs.getString("time"));
				dmAll.setContent(rs.getString("content"));
				dmAll.setRead_state(rs.getString("read_state"));
				dmAll.setSend_id(rs.getString("user_id"));
				dmAll.setRecieve_id(rs.getString("recieve_id"));
				list.add(dmAll);
			}
			
			map.put("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
			System.out.println("대화 개수 : "+list.size());
			System.out.println("파라메터 아이디 일치 여부 : "+success_two);
		}
		return map;
	}



	public DmDTO dmList(String idx) {

		String sql = "SELECT user_id,recieve_id FROM chatroom2 WHERE chat_idx = ?";
		
		DmDTO dmId = new DmDTO();
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(idx));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dmId.setUser_id(rs.getString("user_id"));
				dmId.setRecieve_id(rs.getString("recieve_id"));
				dmId.setChat_idx(Integer.parseInt(idx));
				System.out.println("생성자, 초대유저 추출 완료");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return dmId;
	}



	//로그인 한 아이디가 내가 아니라면 상대방 메시지 읽음 처리
	public boolean readUpdate(String loginId, String idx) {
		String sql = "UPDATE dm2 SET read_state = 'true' WHERE dm_idx = ? AND user_id != ? AND read_state IS NULL";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, idx);
			ps.setString(2, loginId);
			if(ps.executeUpdate() > 0) {
				System.out.println("read state 읽음 처리 완료");
			}else {
				System.out.println("이미 읽음 상태입니다.");
				resClose();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return true;
	}

	public void userTwoJoin(int userSize, String idx) {
		String sql = "UPDATE dm2 SET read_state = 'true' WHERE dm_idx = ? AND read_state IS NULL";
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, idx);
			if(ps.executeUpdate() > 0) {
				System.out.println("read state 읽음 처리 완료");
			}else {
				System.out.println("이미 읽음 상태입니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
	}

}
