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

import com.sns.service.DmService;

import vo.ChatClient;

//이 어노테이션을 명시함으로서 WEB 소켓으로 접속 가능한 URL 정보를 명시하여 소켓 서버를 생성해주며 프로퍼티를 통해 decoder나 encoder를 명시할 수 있다. 
@ServerEndpoint("/webChatServer")
@WebServlet({"/webChatServer"})
public class WebChatServer extends HttpServlet {
	private static Map<Session, ChatClient> users = Collections.synchronizedMap(new HashMap<Session, ChatClient>());

	//자바 웹소켓 모듈 어노테이션
	@OnMessage
	public void onMsg(String message, Session session) throws IOException, ServletException {
		//String userName = users.get(session).getName();
		System.out.println(message);
	
		
		synchronized (users) {
			Iterator<Session> it = users.keySet().iterator();
			while (it.hasNext()) {
				Session currentSession = it.next();
				if (!currentSession.equals(session)) { 
					currentSession.getBasicRemote().sendText(message);
				}
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		DmService service = new DmService(req,resp);
		
		req.setCharacterEncoding("UTF-8");
		String fromId = req.getParameter("fromId");
		String toId = req.getParameter("toId");
		String content = req.getParameter("content");
		content = URLDecoder.decode(content, "UTF-8");
		System.out.println("ajax 송신 완료");
		System.out.println(fromId+" : "+toId+" : "+content);
		
		service.newMsg(fromId,toId,content);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		DmService service = new DmService(req,resp);
		
		req.setCharacterEncoding("UTF-8");
		String fromId = req.getParameter("fromId");
		String toId = req.getParameter("toId");
		String content = req.getParameter("content");
		System.out.println("ajax 송신 완료");
		System.out.println(fromId+" : "+toId+" : "+content);
		
		service.newMsg(fromId,toId,content);
	}

	//자바 웹소켓 모듈 어노테이션
	@OnOpen
	public void onOpen(Session session) {
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
	@OnClose
	public void onClose(Session session) {
		String userName = users.get(session).getName();
		users.remove(session);
		//sendNotice(userName + "님이 퇴장하셨습니다. 현재 사용자 " + users.size() + "명");
	}

}