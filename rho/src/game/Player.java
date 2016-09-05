package game;
import java.util.ArrayList;

public class Player {
	
	private int m_Team;
	
	public Player(int Team)
	{
		m_Team = Team; 
		
	}
	
	public int getTeam()
	{
		return m_Team;
	}
	
	
	public static Player getPlayer(ArrayList<Player> spieler, int teamId)
	{
		for(Player pl : spieler)
		{
			if(pl.getTeam() == teamId)
				return pl;
		}
		return null;
	}
}
