package game.server;

public class ServerStarter {

	public static void main(String[] args){
		int ServerPort = 6631;
		try
		{
			ServerPort = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{
			System.out.println("Serverport konnte nicht ermittelt werden! Benutzte 631");
		}
		System.out.println(ServerPort);
		new ServerKlasse(ServerPort);
	}
}
