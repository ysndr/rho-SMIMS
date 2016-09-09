package game.client;

import java.util.Arrays;

import deps.Client;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;
import game.server.protocolls.ServerProtocoll;

public class RhoClient extends Client {

	private ClientHandler handler;
	
	public RhoClient(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
	}
	
	public ClientHandler getClientHandler() {
		if (handler == null) {
			handler = new ClientHandler(this);
		}
		return handler;
	}
	
	@Override
	public void processMessage(String pMessage) {
		String[] messageComponents = pMessage.split(CommonProtocoll.SEPERATOR);
		String key = messageComponents[0];
		String[] content = getTail(messageComponents);
		
		System.out.println("Message: " + pMessage);
		
		switch (key) {
		case ServerProtocoll.INFO:
			handler.update(content);
			break;
		case ServerProtocoll.ERR:
			((ClientHandler) handler).displayServerError(content);
			break;
		case ServerProtocoll.TURN_START:
			handler.setup(content);
			break;
		default:
			break;
		}
	}
	
	public void sendReady() {
		this.send(ClientProtocoll.READY);
	}
	
	// UTIL
	private String[] getTail(String[] input) {
		String[] output = new String[input.length -1];
		for (int i = 1; i < input.length; i++) output[i-1] = input[i];
		return output;
	}
	
	
}
