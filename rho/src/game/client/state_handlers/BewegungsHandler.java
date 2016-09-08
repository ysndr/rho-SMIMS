package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientHandler;
import game.client.NumberDeliverant;

public class BewegungsHandler extends StateHandler {

	private Feld self;
	private Feld other;
	private NumberDeliverant numberDeliverant;
	
	public BewegungsHandler(GameInfo info, ClientHandler clientHandler) {
		super(info, clientHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleAction(Feld feld) {
		if (self == null) {
			if (self.getEinheiten().size() <= 1) return;		
			self = feld;
		}
		else if (self == feld) {
			self = null;			
		}
		else if (other == null) {
			int einheiten = self.getEinheiten().size();
			int possible = einheiten > 3 ? 3 : einheiten -1;
			int number = (int) numberDeliverant.getNumber(possible);
			if (number < 0) {}
		}
	}

}
