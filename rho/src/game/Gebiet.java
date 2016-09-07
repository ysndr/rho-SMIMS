package game;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
		ArrayList<Gebiet> GebieteAL = new ArrayList<Gebiet>();		
		BufferedReader GebieteBR;
		
		try {
			GebieteBR = new BufferedReader(new FileReader("data/Gebieteordnung"));
			String line = GebieteBR.readLine();
			
			while(line!=null && !line.equals("")) {
				while(!line.equals("-")) {
					
					int BonusTMP = Integer.parseInt(line);
					line = GebieteBR.readLine();
					ArrayList<String> FelderIdsTMP = new ArrayList<String>();
					
					while(!line.equals("-")) {
						FelderIdsTMP.add(line);
						line = GebieteBR.readLine();
					}
					
					GebieteAL.add(new Gebiet(BonusTMP,FelderIdsTMP));
				}
				line = GebieteBR.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return GebieteAL;
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
