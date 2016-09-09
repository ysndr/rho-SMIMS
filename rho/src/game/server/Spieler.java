package game.server;

import game.Player;

public class Spieler extends Player{

	
	private String Ip;
	private int port;
	private boolean ready;
	
	public Spieler(int team, String ip, int mport) {
		super(team);
		Ip = ip;
		port = mport;
		ready=false;
	}
	
	public String getIP() {return Ip;}
	public int getPort() {return port;}
	public boolean getReady() {return ready;}
	public void ready() {ready=true;}

}
