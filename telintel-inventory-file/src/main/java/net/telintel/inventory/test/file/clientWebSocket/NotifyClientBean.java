package net.telintel.inventory.test.file.clientWebSocket;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Receiptable;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import net.telintel.inventory.test.file.model.ProcessFile;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.core.env.Environment;

/**
 * Created by nick on 30/09/2015.
 * 
 * Modify by Freddy Lemus Date 02-10-2017
 * 
 * Implement the logic in order to connect with webSocket and send a message
 * 
 * 
 */
public class NotifyClientBean {

	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(NotifyClientBean.class);

	@Autowired
	private Environment ev;

	private ListenableFuture<StompSession> listenableFuture;

	/**
	 * Header http Sock
	 */
	private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

	/**
	 * Object contains the session stomp
	 */
	private StompSession stompSession;

	/**
	 * 
	 */
	private boolean connected;

	@Autowired
	private Environment env;

	private static String HOST = "notify.websocket.host";

	private static String PORT = "notify.websocket.port";

	private static String END_POINT = "notify.websocket.endpoint";

	private static String DESTINATION = "notify.websocket.destination";

	/**
	 * 
	 * @return
	 */
	public NotifyClientBean() {

		logger.info(":::::::::::::::::::::::::NotifyClientBean Constructor ::::::::::::::::::::::::::::");

	}

	
	/**
	 * 
	 * @return
	 */
	public StompSession getSession() {

		logger.info(":::::::::::::::::::::::::getSession ::::::::::::::::::::::::::::");
		
		try {
			if (this.stompSession == null || ! this.stompSession.isConnected()) {
				listenableFuture = this.connect();
				this.stompSession = listenableFuture.get();

				logger.info("connect topic using session " + stompSession.getSessionId());
			}
			// this.subscribeGreetings(stompSession);
			connected = true;
		} catch (ExecutionException e) {
			logger.error(e.getMessage(), e);
			connected = false;
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			connected = false;
		}

		return this.stompSession;
	}

	/**
	 * Do connection
	 * 
	 * @return
	 */
	public ListenableFuture<StompSession> connect() {

		Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
		List<Transport> transports = Collections.singletonList(webSocketTransport);

		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

		String url = "ws://{host}:{port}/" + env.getProperty(NotifyClientBean.END_POINT);
		logger.debug("url ============= " + url);
		logger.debug(" - " + env.getProperty(NotifyClientBean.HOST));
		logger.debug(" - " + env.getProperty(NotifyClientBean.PORT));
		return stompClient.connect(url, headers, new SessionHandler(), env.getProperty(NotifyClientBean.HOST),
				env.getProperty(NotifyClientBean.PORT));
	}

	/**
	 * Subscribe this session
	 * 
	 * @param stompSession
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void subscribeSessionClient(StompSession stompSession) throws ExecutionException, InterruptedException {
		stompSession.subscribe(env.getProperty(NotifyClientBean.DESTINATION), new StompFrameHandler() {

			public Type getPayloadType(StompHeaders stompHeaders) {
				return byte[].class;
			}

			public void handleFrame(StompHeaders stompHeaders, Object o) {
				logger.debug("Received ... " + new String((byte[]) o));

			}
		});
	}

	/**
	 * Send json message to topic
	 * 
	 * @param jsonMessage
	 *            String in format Json
	 * 
	 */
	public Receiptable sendMessage(String jsonMessage) {
		logger.debug(":::: sendMessage " + jsonMessage);
		logger.debug(":::: sendMessage " + env.getProperty(NotifyClientBean.DESTINATION));
		
		 stompSession.send("/topic/message" , jsonMessage.getBytes());
		 return null;
	}

	private class SessionHandler extends StompSessionHandlerAdapter {
		public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
			logger.debug(":::::::::::::::::::::: Now connected");
			connected = true;
		}
	}

	public boolean isConnected() {
		return connected;
	}

}
