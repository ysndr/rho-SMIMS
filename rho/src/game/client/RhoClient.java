package game.client;

import java.util.Arrays;

import deps.Client;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;
import game.server.protocolls.ServerProtocoll;

public class RhoClient extends Client {

	public RhoClient(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
	}

	public ClientHandler getClientHandler() {
		return ClientHandler.getClientHandler(this);
	}

	@Override
	public void processMessage(String pMessage) {
		ClientHandler handler = this.getClientHandler();
		String[] messageComponents = pMessage.split(CommonProtocoll.SEPERATOR);
		String key = messageComponents[0];
		String[] content = Arrays.copyOfRange(messageComponents, 1, messageComponents.length -1);

		switch (key) {
		case ServerProtocoll.INFO:
			handler.updateInfo(content);
			break;
		case ServerProtocoll.ERR:
			handler.displayServerError(content);
			break;
		case ServerProtocoll.TURN_START:
			handler.setupGame(content);
			break;
		default:
			break;
		}
	}
	
	public void sendReady() {
		this.send(ClientProtocoll.READY);
	}
	

}
