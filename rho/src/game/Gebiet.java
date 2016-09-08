package game;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Gebiet {
	@Expose private int m_UnitBonus;
	@Expose private ArrayList<String> m_FelderIds;
	@Expose private String m_color;
	
	public Gebiet(int UnitBonus, ArrayList<String> FelderIds, int colorindex)
	{
		m_UnitBonus = UnitBonus;
		m_FelderIds = FelderIds;
		switch(colorindex)
		{
		case 0:
			m_color = "ff8f00";
			break;
		case 1:
			m_color = "18ffff";
			break;
		case 2:
			m_color = "b71c1c";
			break;
		case 3:
			m_color = "1b5e20";
			break;
		case 4:
			m_color = "aa00ff";
			break;
		case 5:
			m_color = "1a237e";
			break;
		}
	}
	
	public int getGebietBonus()
	{
		return m_UnitBonus;
	}
	
	public ArrayList<String> getFelderIds()
	{
		return m_FelderIds;
	}
	
	public String getColor()
	{
		return m_color;
	}
	
	public static ArrayList<Gebiet> readGebiete()
	{
		ArrayList<Gebiet> GebieteAL = new ArrayList<Gebiet>();		
		BufferedReader GebieteBR;
		int zaehler = 0;
		try {
			GebieteBR = new BufferedReader(new FileReader("data/Weltkarte/Gebiete.txt"));
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
					
					GebieteAL.add(new Gebiet(BonusTMP,FelderIdsTMP, zaehler));
					zaehler++;
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
