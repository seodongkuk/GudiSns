package com.sns.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import com.sns.dto.MemberDTO;

public class MemberDAO {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	public MemberDAO() {
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
	//--------------------------------------------------------------------로그인
	public boolean login(String id, String pw) {			
		boolean success = false;
		String sql="SELECT user_id FROM member2 WHERE USER_ID=? AND PW=? AND WITHDRAW_STATE='FALSE'";
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
	
	//--------------------------------------------------------------------------
	public String findBlackListbyuserId(String id) {
		
		String success ="";
		String sql="SELECT blk_state FROM member2 WHERE USER_ID=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,id);
			rs = ps.executeQuery();
			rs.next();
			success=rs.getString("blk_state");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
	}
	
	
	//--------------------------------------------------------------------------회원가입
	public boolean join(MemberDTO dto) {
		boolean success = false;
		String sql="INSERT INTO member2 (user_id,pw,name,phone,email)VALUES(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,dto.getId());
			ps.setString(2,dto.getPw());
			ps.setString(3,dto.getName());
			ps.setString(4,dto.getPhone());
			ps.setString(5,dto.getEmail());
			
			rs = ps.getGeneratedKeys();
			if(ps.executeUpdate()>0) {
				success = true;
				
				//회원가입시 기본알림셋팅을 부여하는 작업.. by 양상렬
				sql = "INSERT INTO alarmsetting2(user_id,alarm_kind) VALUES(?,'DM알림')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getId());
				ps.executeUpdate();
				
				sql = "INSERT INTO alarmsetting2(user_id,alarm_kind) VALUES(?,'댓글알림')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getId());
				ps.executeUpdate();
				
				sql = "INSERT INTO alarmsetting2(user_id,alarm_kind) VALUES(?,'친구요청알림')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getId());
				ps.executeUpdate();
				
				sql = "INSERT INTO alarmsetting2(user_id,alarm_kind) VALUES(?,'게시글알림')";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getId());
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		System.out.println("회원가입 성공 여부 : "+success);
		return success;
	}
//--------------------------------------------------------------------------임의로만든리스트
//public ArrayList<MemberDTO> list() {
//
//	return null;
//}
//--------------------------------------------------------------------------아이디체크
public boolean idChk(String id) throws SQLException {
	boolean success = false;
	String sql="SELECT user_id FROM member2 WHERE USER_ID=?";
	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1,id);
		rs = ps.executeQuery();
		success = rs.next();
	
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		resClose();
	}
	System.out.println("회원가입 성공 여부 : "+success);
		return !success;
	}


//--------------------------------------------------------------------아이디 찾기


/* 아이디찾기 */
public String idfind(String name, String phone) {

	String sql = "SELECT user_id FROM member2 WHERE name=? AND phone=?";
	String id = "";
	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, phone);
		rs = ps.executeQuery();
		if (rs.next()) {
			id = rs.getString("user_id");
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		resClose();
	}

	System.out.println(id);
	return id;
}




/* 비밀번호 찾기 */
public boolean pwfind(String id, String name, String phone) {

	String sql = "SELECT pw FROM member2 WHERE user_id=? AND name=? AND phone=?";
	boolean pw = false;
	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, name);
		ps.setString(3, phone);
		rs = ps.executeQuery();
		if (rs.next()) {
			pw = true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		resClose();
	}
	System.out.println(pw);
	return pw;
}

//-------------------------------------------------------------------비번 변경
		
	
		
public boolean pwupdate(String id, String newPw) {

	String sql = "UPDATE member2 SET pw=? WHERE user_id=?";
	boolean success = false;

	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, newPw);
		ps.setString(2, id);
		if (ps.executeUpdate() > 0) {
			success = true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		resClose();
	}

	return success;
}
		
//--------------------------------------------------------------정보수정 접근시 패스워드 확인.
		
		public boolean infopw(String id, String pw) {	
			System.out.println(id+pw);
			boolean success = false;
			String sql="SELECT pw FROM member2 WHERE user_id=? AND pw=?";
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
			
//-----------------------------------------------------------------------------//회원정보 창
	
			
				public MemberDTO userinfo (String id) {
				String sql="SELECT * FROM member2 WHERE user_id=?";		
				MemberDTO dto = null;
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, id);
					rs = ps.executeQuery();
					if(rs.next()) {
						dto = new MemberDTO();
						dto.setId(rs.getString("user_id"));
						dto.setPw(rs.getString("pw"));
						dto.setName(rs.getString("name"));
						dto.setEmail(rs.getString("email"));
						dto.setPhone(rs.getString("phone"));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					resClose();
				}				
				return dto;
			}
//-----------------------------------------------------------------------------//회원정보 업데이트				
				
	public int userinfoupdate(MemberDTO dto) {
		
		String sql="UPDATE member2 SET pw=?, name=?, email=?, phone=?  WHERE user_id=?";
		int success = -1;
		try {
			ps = conn.prepareStatement(sql);			
			ps.setString(1, dto.getPw());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getPhone());
			ps.setString(5, dto.getId());
			success = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}				
		return success;
	}
//-----------------------------------------------------------------------------//회원탈퇴			
	public boolean  delid(MemberDTO  dto) {
		String sql="UPDATE member2 SET WITHDRAW_STATE='TRUE' WHERE user_id=?";
		boolean success=false;
		try {
		ps=conn.prepareStatement(sql);
		ps.setString(1, dto.getId());
		if(ps.executeUpdate()>0) {
			success=true;
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return success;
		}
				
				
				

}

