package game.server;

import game.Player;

public class Spieler extends Player{
	private String Ip;
	private int port;
	public Spieler(int team, String ip, int mport) {
		super(team);
		Ip = ip;
		port = mport;
	}
	
	public String getIP() {return Ip;}
	public int getPort() {return port;}

}
