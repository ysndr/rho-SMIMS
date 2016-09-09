package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientAdapter;
import game.client.ClientHandler;
import game.client.NumberDeliverant;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

public class AngriffsHandler extends StateHandler {
	
	private Feld self;
	private Feld opponent;
	private NumberDeliverant numberDeliverant;
	
	
	public AngriffsHandler(GameInfo info, ClientAdapter clientHandler, NumberDeliverant numberDeliverant) {
		super(info, clientHandler);
		this.numberDeliverant = numberDeliverant;
	}

	@Override
	public void handleAction(Feld feld) {
		if (self == null) {
			if (feld.getEinheiten().get(0).getAnzahl() <= 1) return;		
			self = feld;
		}
		else if (self.equals(feld)) {
			self = null;			
		}
		else if (opponent == null) {
			opponent = feld;
			int einheiten = self.getEinheiten().get(0).getAnzahl();
			int possible = einheiten > 3 ? 3 : einheiten -1;
			int number = (int) numberDeliverant.getNumber(possible);
			if (number < 0) {
				self = null;
				opponent = null;				
			} else {
				clientHandler.send(ClientProtocoll.FELD_ATK,
						self.getID()
						+ CommonProtocoll.SEPERATOR 
						+ opponent.getID()
						+ CommonProtocoll.SEPERATOR
						+ "" + number);		
				self = null;
				opponent = null;
			}
			
		}

		// TODO Auto-generated method stub
	}

}
