package game.server;

public class ServerStarter {

	public static void main(String[] args){
		int ServerPort = 6688;
		try
		{
			ServerPort = Integer.parseInt(args[0]);
		}
		catch(Exception e)
		{		}
		System.out.println("ServerPort: " + ServerPort);
		new ServerKlasse(ServerPort);
	}
}
