package game;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Player {
	public static String[] Colors = new String[] {
			 "f44336" /*Rot*/,
			 "2196f3" /*Blau*/,
			 "8bc34a" /*Gr�n*/,
			 "ffc107" /*Gelb*/,
			 "9c27b0" /*Lila*/,
			 "3f51b5" /*Indigo*/,
			 "e91e63" /*Pink*/,
			 "ff9800" /*Orange*/
	};
	
	@Expose private int m_Team;
	@Expose private String color;
	
	public Player(int Team)
	{
		m_Team = Team; 
	}
	
	public int getTeam()
	{
		return m_Team;
	}	
	
	/**
	 * Die Farbe des Spielers wird ermittelt
	 * @param PlayerIndex
	 */
	public void gameStart(int PlayerIndex)
	{
		if(PlayerIndex < Colors.length)
		{
			color = Colors[PlayerIndex];
		}
		else
		{
			color = "000000";
		}
	}
	
	public String getColor()
	{
		return color;
	}
	
	public static Player getPlayer(ArrayList<Player> spieler,  int teamId)
	{
		for(Player pl : spieler)
		{
			if(pl.getTeam() == teamId)
				return pl;
		}
		return null;
	}
}
