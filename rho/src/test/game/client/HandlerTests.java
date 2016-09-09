package test.game.client;

import game.Feld;
import game.GameInfo;
import game.client.ClientAdapter;
import game.client.NumberDeliverant;
import game.client.state_handlers.AngriffsHandler;
import game.client.state_handlers.StateHandler;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

public class HandlerTests {
	
	ClientAdapter angriffAdapter = new TestAdapter();
	ClientAdapter bewegungAdapter = new TestAdapter();
	ClientAdapter versorgungAdapter = new TestAdapter();
	
	
	
	
	
	GameInfo info = new GameInfo();
	
	StateHandler angriff = new AngriffsHandler(info, angriffAdapter, new TestNumbers());
	StateHandler bewegung = new AngriffsHandler(info, bewegungAdapter, new TestNumbers());
	StateHandler versorgung = new AngriffsHandler(info, versorgungAdapter, new TestNumbers());	
	
	
	
	
	public HandlerTests() {
		angriff.handleAction(new Feld("angriff"));
		angriff.handleAction(new Feld("angriff2"));
		bewegung.handleAction(new Feld("bewegung"));
		bewegung.handleAction(new Feld("bewegung2"));
		
	};
	
	public static void main(String[] args) {
		new HandlerTests();
	}
	
	
}

class TestNumbers implements NumberDeliverant {

	@Override
	public Number getNumber(Number maxValue) {
		// TODO Auto-generated method stub
		return 5;
	}
	
	
}


class TestAdapter implements ClientAdapter {

	@Override
	public void end() { }

	@Override
	public void send(String protocoll, String data) { 
		assert protocoll.equals(ClientProtocoll.FELD_ATK);
		assert data.equals("angriff"
						+ CommonProtocoll.SEPERATOR 
						+ "angriff2"
						+ CommonProtocoll.SEPERATOR
						+ "5");
		}

	@Override
	public void update(String[] json) {	}

	@Override
	public void setup(String[] message) { }
	
	
}
class VersorgungTestAdapter implements ClientAdapter {

	@Override
	public void end() { }

	@Override
	public void send(String protocoll, String data) {
		assert protocoll.equals(ClientProtocoll.FELD_ADD);
		assert data.equals(
				"versorgung"
				+ CommonProtocoll.SEPERATOR 
				+ "Soldat"
				);
		
	}

	@Override
	public void update(String[] json) {	}

	@Override
	public void setup(String[] message) { }
	
	
}
class BewegungTestAdapter implements ClientAdapter {

	@Override
	public void end() { }

	@Override
	public void send(String protocoll, String data) {
		assert protocoll.equals(ClientProtocoll.FELD_MOVE);
		assert data.equals(
		"bewegung"
		+ CommonProtocoll.SEPERATOR 
		+ "bewegung2"
		+ CommonProtocoll.SEPERATOR
		+ "5");
	}

	@Override
	public void update(String[] json) {	}

	@Override
	public void setup(String[] message) { }
	
	
}


