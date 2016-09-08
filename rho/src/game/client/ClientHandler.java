package game.client;

import com.google.gson.Gson;

import deps.Client;
import deps.Graph;
import game.Feld;
import game.GameInfo;
import game.Graphbauer;
import game.client.state_handlers.AngriffsHandler;
import game.client.state_handlers.BewegungsHandler;
import game.client.state_handlers.EmptyHandler;
import game.client.state_handlers.StateHandler;
import game.client.state_handlers.VersorgungsHandler;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

/**
 * @author yannik
 *
 */
public class ClientHandler implements ClientAdapter{	
	
	private Callback onSetupChanged;
	private RhoClient client;	
	
	private StateHandler stateHandler;
	private int playerID;
	private Graph map;
	private GameInfo info;
	
	
	public ClientHandler(RhoClient client) {
		this.client = client;
	}
	@Override
	public void setup(String[] message) {
		String [] content = message;
		int id = Integer.parseInt(content[0]);
		Graphbauer gb = new Graphbauer();
		String mapString = content[1];
		gb.stringToGraph(mapString);
		Graph map = gb.getGraph();
		
		this.playerID = id;
		this.map = map;
		
		this.client.sendReady();
	}
	
	@Override
	public void update(String[] json) {
		Gson gson = new Gson();
		GameInfo info = gson.fromJson(json[0], GameInfo.class);
		
		switch (info.getM_SpielStatus()) {
		case Versorgung:
			stateHandler = new VersorgungsHandler(info, this);
			break;
		case Angriff:
			stateHandler = new AngriffsHandler(info, this, null);
			break;
		case Truppenbewegung:
			stateHandler = new BewegungsHandler(info, this);
			break;
		case nichtAngegeben:
			// spieler laden --> kein Json sondern anahl spieler
			performUpdate(null, null, Integer.parseInt(json[0]));
		default:
			stateHandler = new EmptyHandler(info, this);
			break;		
		}
		
		
		this.info = info;
		performUpdate();
	}
	
	public void displayServerError(String[] error) {
		this.displayServerError(error[0]);
	}
	
	
	public void displayServerError(String error) {
		displayError("Server-Error: " + error);
	}
	
	public void displayError(String message) {
		System.out.println(message);		
	}
	
	
	public void handleAction(String feldID) {
		Feld feld = Feld.searchFeld(info.getM_Felder(), feldID);
		stateHandler.handleAction(feld);		
	}
	
		
	@Override
	public void send(String protocoll, String data) {
		client.send(protocoll + CommonProtocoll.SEPERATOR + data);		
	}	
	
	@Override
	public void end() {
		client.send(ClientProtocoll.TURN_ENDE);
	}	
	
	// Callback
	private void performUpdate() {
		performUpdate(info, map, playerID);			
	}
	
	private void performUpdate(GameInfo info, Graph map, int sID) {
		if (onSetupChanged != null) {
			onSetupChanged.update(info, map, sID);			
		}		
	};
	
	public void setCallback(Callback cb) {
		this.onSetupChanged = cb;		
	}
	
	
	public static interface Callback {
		void update(GameInfo info, Graph map, int sID);
	}
	
}
