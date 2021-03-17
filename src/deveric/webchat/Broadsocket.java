package deveric.webchat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

@ServerEndpoint("/broadsocket")
public class Broadsocket extends HttpServlet {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException{
		System.out.println(message);
		synchronized (clients) {
			for(Session client : clients){
				if(!client.equals(session)){
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	
	@OnOpen
	public void opOpen(Session session){
		System.out.println(session);
		clients.add(session);
	}
	
	@OnClose
	public void onClost(Session session){
		clients.remove(session);
	}
}