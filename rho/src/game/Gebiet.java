package game;
import java.util.ArrayList;

public class Gebiet {
	private int m_UnitBonus;
	private ArrayList<String> m_FelderIds;
	
	public Gebiet(int UnitBonus, ArrayList<String> FelderIds)
	{
		m_UnitBonus = UnitBonus;
		m_FelderIds = FelderIds;
	}
	
	public int getGebietBonus()
	{
		return m_UnitBonus;
	}
	
	public ArrayList<String> getFelderIds()
	{
		return m_FelderIds;
	}
	
	public static ArrayList<Gebiet> readGebiete()
	{
		return null;
	}
	
	public static int getTeamBonus(ArrayList<Feld> felder, int teamId, ArrayList<Gebiet> gebiete)
	{
		int bon=0;
		ArrayList<Feld> playerFelder = Feld.getFelderFromTeam(felder, teamId);
		ArrayList<String> playerFelderIds = new ArrayList<String>();
		for(Feld f : playerFelder)
			playerFelderIds.add(f.getID());
		for(Gebiet gb : gebiete)
		{
			if(playerFelderIds.containsAll(gb.getFelderIds())){
				bon+=gb.getGebietBonus();
			}
		}
		return bon;
	}
}
