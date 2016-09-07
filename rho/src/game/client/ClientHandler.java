package game.client;

import com.google.gson.Gson;

import deps.Client;
import deps.Graph;
import game.GameInfo;
import game.Graphbauer;

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
	
	private int playerID;
	private Graph map;
	private GameInfo info;
	
	
	private ClientHandler(RhoClient client) {
		this.client = client;
	}
	
	public void setupGame(String message) {
		String [] content = message.split("\n");
		int id = Integer.parseInt(content[0]);
		Graphbauer gb = new Graphbauer();
		String mapString = content[1];
		gb.stringToGraph(mapString);
		Graph map = gb.getGraph();
		
		this.playerID = id;
		this.map = map;
		
		this.client.sendReady();
	}
	
	public void updateInfo(String json) {
		Gson gson = new Gson();
		GameInfo info = gson.fromJson(json, GameInfo.class);
		this.info = info;
	}
	
	public void displayServerError(String error) {
		displayError("Server-Error: " + error);
	}
	
	public void displayError(String message) {
		System.out.println(message);		
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
