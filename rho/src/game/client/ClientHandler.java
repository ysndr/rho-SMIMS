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
import game.server.protocolls.CommonProtocoll;

/**
 * @author yannik
 *
 */
public class ClientHandler {

	private static ClientHandler handler = null;
	
	public static ClientHandler getClientHandler(RhoClient client) {
		if (handler == null) {
			handler = new ClientHandler(client);
		}
		return handler;
	}
	
	private Callback onSetupChanged;
	private RhoClient client;	
	
	private StateHandler stateHandler;
	private int playerID;
	private Graph map;
	private GameInfo info;
	
	
	private ClientHandler(RhoClient client) {
		this.client = client;
	}
	
	public void setupGame(String[] message) {
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
	
	public void updateInfo(String[] json) {
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
		default:
			stateHandler = new EmptyHandler(info, this);
			break;
		
		
		}
		
		
		this.info = info;
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
	
		
	
	public void send(String protocoll, String data) {
		client.send(protocoll + CommonProtocoll.SEPERATOR + data);		
	}	
	
	
	// Callback
	private void update() {
		if (onSetupChanged != null) {
			onSetupChanged.update();			
		}		
	}
	
	public void setCallback(Callback cb) {
		this.onSetupChanged = cb;		
	}
	
	
	public static interface Callback {
		void update();
	}
	
}
