package game.client.state_handlers;

import game.Feld;
import game.GameInfo;
import game.client.ClientHandler;
import game.client.NumberDeliverant;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

public class AngriffsHandler extends StateHandler {
	
	private Feld self;
	private Feld opponent;
	private NumberDeliverant numberDeliverant;
	
	
	public AngriffsHandler(GameInfo info, ClientHandler clientHandler, NumberDeliverant numberDeliverant) {
		super(info, clientHandler);
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
		else if (opponent == null) {
			int einheiten = self.getEinheiten().size();
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
				
			}
			
		}
		
		// TODO Auto-generated method stub
	}

}
