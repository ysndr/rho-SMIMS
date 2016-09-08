package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientAdapter;

public abstract class StateHandler {
	
	protected GameInfo info;
	protected ClientAdapter clientHandler;
	public StateHandler(GameInfo info, ClientAdapter clientHandler) {
		this.info = info;
		this.clientHandler = clientHandler;
	}
	
	abstract public void handleAction(Feld feld);
	
}
