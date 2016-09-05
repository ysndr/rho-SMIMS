package game;

import java.io.IOException;
import java.util.ArrayList;

import deps.List;
import deps.Graph;
import deps.Vertex;

public class ManageClass {
	
	private int m_Runde;
	private int m_TeamsTurn;
	private int m_AnzFelder;
	//private SpielStatus m_SpielStatus; 
	//private ArrayList<Player> m_Spieler;
	private Graph m_SpielPlan;
	private ArrayList<Gebiet> m_Gebiete;
	
	public ManageClass()
	{
		//m_Spieler = new ArrayList<Player>();
		//m_SpielStatus = SpielStatus.nichtAngegeben;
	}
	
	/*public void addPlayer(Player spieler)
	{
		if (m_SpielStatus == SpielStatus.nichtAngegeben)
		{
			m_Spieler.add(spieler);
		}
	}*/
	
	private void beginn()
	{
		/*if (m_Spieler != null && !m_Spieler.isEmpty())
		{
			m_Runde = 0;
			readGraph();
			m_Gebiete = Gebiet.readGebiete();
			gebietszuordnung();
			m_TeamsTurn = m_Spieler.get(0).getTeam();
			m_SpielStatus = SpielStatus.Vorbereitung;
			
			routine();
		}*/
	}
	
	private void readGraph()
	{
		Graphbauer builder = new Graphbauer();
		try {
			builder.fileToGraph("/Users/jan/eclipse/workspace/rho/src/jan/GraphbauerAnleitung");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_SpielPlan = builder.getGraph();
		m_AnzFelder = 42;
	}
	
	private void gebietszuordnung()
	{/*
		ArrayList<Integer> FreieFelderTeam = new ArrayList<Integer>();
		ArrayList<Integer> freieTeam = new ArrayList<Integer>();
		ArrayList<Vertex> felder = convertAbiListToArrayList(m_SpielPlan.getVertices());
		Feld tempFeld = null;
		for(int i = 0; i < m_Spieler.size(); i++)
		{
			FreieFelderTeam.add((int)(m_AnzFelder/m_Spieler.size()));
			freieTeam.add(m_Spieler.get(i).getTeam());
		}
		java.util.Random zufall = new java.util.Random();
		for (int i = 0; i < m_AnzFelder; i++){
			if (felder.get(i) instanceof Feld)
			{
				tempFeld = (Feld)felder.get(i);
				
				if(freieTeam == null || freieTeam.isEmpty())
				{
					int temp = zufall.nextInt(m_Spieler.size());
					tempFeld.addUnit("Soldat",1); 
					tempFeld.setZg(freieTeam.get(temp));
				}
				else
				{
					int temp = zufall.nextInt(freieTeam.size());
					tempFeld.addUnit("Soldat",1); 
					tempFeld.setZg(freieTeam.get(temp));
					FreieFelderTeam.set(temp, FreieFelderTeam.get(temp)-1); 
					if (FreieFelderTeam.get(freieTeam.get(temp)) <= 0)
					{
						FreieFelderTeam.remove(temp);
						freieTeam.remove(temp);
					}
				}
			}
		}*/
	}
	
	private void end()
	{}
	
	private void routine()
	{
		versorgung();
	}
	
	private void versorgung()
	{
		//int newUnits = 
	}
	
	private void angriff()
	{}
	
	private void bewegeTruppen()
	{}
	
	public static ArrayList<Vertex> convertAbiListToArrayList(List<Vertex> felder)
	{
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		felder.toFirst();
		while(felder.hasAccess())
		{
			result.add(felder.getContent());
			felder.next();
		}
		return result;
	}
	
}
