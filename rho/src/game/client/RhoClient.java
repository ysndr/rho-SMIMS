package game.client;

import java.util.Arrays;

import deps.Client;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;
import game.server.protocolls.ServerProtocoll;

public class RhoClient extends Client {

	private ClientAdapter handler;
	
	public RhoClient(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
	}
	
	public ClientAdapter getClientHandler() {
		if (handler == null) {
			handler = new ClientHandler(this);
		}
		return handler;
	}

	@Override
	public void processMessage(String pMessage) {
		String[] messageComponents = pMessage.split(CommonProtocoll.SEPERATOR);
		String key = messageComponents[0];
		String[] content = Arrays.copyOfRange(messageComponents, 1, messageComponents.length -1);

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
		case ServerProtocoll.PLYR_CON:
			handler.update(content);
		default:
			break;
		}
	}
	
	public void sendReady() {
		this.send(ClientProtocoll.READY);
	}
	

}
