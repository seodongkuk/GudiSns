package deveric.webchat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sns.dao.DmDAO;
import com.sns.service.DmService;

import vo.ChatClient;

//이 어노테이션을 명시함으로서 WEB 소켓으로 접속 가능한 URL 정보를 명시하여 소켓 서버를 생성해주며 프로퍼티를 통해 decoder나 encoder를 명시할 수 있다. 
@ServerEndpoint("/webChatServer")
//DM_Room.jsp에서 요청이 들어올떄..
@WebServlet({"/webChatServer"})
public class WebChatServer extends HttpServlet {
	private static Map<Session, ChatClient> users = Collections.synchronizedMap(new HashMap<Session, ChatClient>());

	//자바 웹소켓 모듈 어노테이션
	@OnMessage
	public void onMsg(String message, Session session) throws IOException, ServletException {
		//String userName = users.get(session).getName();
		System.out.println(message+":"+users.size());
		
		String idx = "";
		int userSize = users.size();
		String[] msg = message.split(":");
		String fromId = msg[0];
		String content = msg[1];
		String toId = msg[2];
		idx = msg[3];
		//접속 유저가 2명이라면...

		
		if(fromId != null && toId != null && content != null) {
			DmService dm = new DmService(null,null);
			
			dm.newMsg(fromId, toId, content);
			
			if(userSize >= 2 && idx != null) {
				System.out.println("현재 유저가 2명이므로 보낸 메시지는 자동 읽음 처리 됩니다..");
				DmDAO dao = new DmDAO();
				
				dao.userTwoJoin(userSize,idx);
			}else {
				System.out.println("현재 방은 idx가 없습니다.");
			}
			
		}else {
			return;
		}

	
		//상대방한테 메시지를 보내는 역할을 수행한다.(내 세션과 상대방 세션이 다르면..)
		synchronized (users) {
			Iterator<Session> it = users.keySet().iterator();
			while (it.hasNext()) {
				
				Session currentSession = it.next();
				System.out.println("현재 세션의 이름"+currentSession);
				//상대방한테 보내는 메시지
				if (!currentSession.equals(session)) { 
					currentSession.getBasicRemote().sendText(message);

				}
			}
		}

	}

	//DM내용 INSERT 요청
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
		
		System.out.println("ajax로 다시 방을 불러옵니다");
		String id = req.getParameter("fromId");
		String create = req.getParameter("toId");
		String idx = req.getParameter("idx");
		
		String page = "DM_Room?id="+id+"&&create="+create+"&&idx="+idx;
		
		resp.sendRedirect(page);
		
	}

	//자바 웹소켓 모듈 어노테이션
	//DM_Room.jsp에 입장하자마자 실행됨(세션 부여 역할을 한다..)
	@OnOpen
	public void onOpen(Session session) {
		int size = users.size();
		String userName = "user";
		int rand_num = (int) (Math.random() * 1000);

		ChatClient client = new ChatClient();
		System.out.println(session);
		client.setName(userName + rand_num);

		System.out.println(session + " connect");

		users.put(session, client);
		
		//sendNotice(client.getName() + "님이 입장하셨습니다. 현재 사용자 " + users.size() + "명");
	}

	/*사용자가 들어오거나 나갈때 사용할 메서드*/
//	public void sendNotice(String message) {
//		String userName = "server";
//		System.out.println(userName + " : " + message);
//
//		synchronized (users) {
//			Iterator<Session> it = users.keySet().iterator();
//			//while (it.hasNext()) {
//				Session currentSession = it.next();
//				try {
//					currentSession.getBasicRemote().sendText(userName + " : " + message);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			//}
//		}
//	}

	//자바 웹소켓 모듈 어노테이션
	//세션 연결되어 있는 사람이 접속을 끊으면 실행됨(세션 삭제)
	@OnClose
	public void onClose(Session session) {
		String userName = users.get(session).getName();
		users.remove(session);
		//sendNotice(userName + "님이 퇴장하셨습니다. 현재 사용자 " + users.size() + "명");
	}

}