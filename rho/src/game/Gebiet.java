package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import deps.Vertex;

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
	
	public static ArrayList<Gebiet> readGebiete() throws IOException
	{
		ArrayList<Gebiet> GebieteAL = new ArrayList<Gebiet>();
	
		
		BufferedReader GebieteBR = new BufferedReader(new FileReader("/Users/jan/eclipse/workspace/rho/src/jan/Gebieteordnung"));
		String line = GebieteBR.readLine();
		
		int AnzahlGebiete = 0;
		while(line!=null) {
			while(!line.equals("-")) {
				int BonusTMP = Integer.parseInt(line);
				line = GebieteBR.readLine();
				ArrayList<String> FelderIdsTMP = new ArrayList<String>();
				while(!line.equals("-")) {
					line = GebieteBR.readLine();
					FelderIdsTMP.add(line);
				}
				
				// Fehler hier
				//line = Gebiete;
			}
			line = GebieteBR.readLine();
			AnzahlGebiete++;
		}
		
		
		
		//erste Zeile ist Anzahl 
		
		return null;
	}
	
}
