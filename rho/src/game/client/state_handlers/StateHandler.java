package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientHandler;

public abstract class StateHandler {
	
	protected GameInfo info;
	protected ClientHandler clientHandler;
	public StateHandler(GameInfo info, ClientHandler clientHandler) {
		this.info = info;
		this.clientHandler = clientHandler;
	}
	
	abstract public void handleAction(Feld feld);
	
}
