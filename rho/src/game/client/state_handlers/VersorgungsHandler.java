package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientHandler;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

public class VersorgungsHandler extends StateHandler {

	public VersorgungsHandler(GameInfo info, ClientHandler clientHandler) {
		super(info, clientHandler);
	}

	@Override
	public void handleAction(Feld feld) {
		clientHandler.send(ClientProtocoll.FELD_ADD,
				feld.getID()
				+ CommonProtocoll.SEPERATOR 
				+ "Soldat");
	}

}
