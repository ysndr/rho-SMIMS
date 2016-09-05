package game;
import java.util.ArrayList;

import deps.List;
import deps.Graph;
import deps.Vertex;

public class ManageClass {
	
	private int m_Runde;
	private int m_TeamsTurn;
	private SpielStatus m_SpielStatus; 
	private ArrayList<Player> m_Spieler;
	private Graph m_SpielPlan;
	private ArrayList<Feld> m_Felder;
	private ArrayList<Gebiet> m_Gebiete;
	
	private int m_unitsPlacedNum;
	
	public ManageClass()
	{
		m_Spieler = new ArrayList<Player>();
		m_SpielStatus = SpielStatus.nichtAngegeben;
	}
	
	public void addPlayer(Player spieler)
	{
		if (m_SpielStatus == SpielStatus.nichtAngegeben)
		{
			m_Spieler.add(spieler);
		}
	}
	
	private void beginn()
	{
		if (m_Spieler != null && !m_Spieler.isEmpty())
		{
			m_Runde = 0;
			readGraph();
			
			m_Felder = new ArrayList<Feld>();
			ArrayList<Vertex> vertextemp = convertAbiListToArrayList(m_SpielPlan.getVertices());
			for(Vertex vt : vertextemp)
			{
				m_Felder.add(new Feld(vt.getID()));
			}
			
			m_Gebiete = Gebiet.readGebiete();
			gebietszuordnung();
			m_TeamsTurn = m_Spieler.get(0).getTeam();
			m_SpielStatus = SpielStatus.Vorbereitung;
			
			routine();
		}
	}
	
	private void readGraph()
	{
		m_SpielPlan = new Graph();
	}
	
	private void gebietszuordnung()
	{
		ArrayList<Integer> FreieFelderTeam = new ArrayList<Integer>();
		ArrayList<Integer> freieTeam = new ArrayList<Integer>();
		for(int i = 0; i < m_Spieler.size(); i++)
		{
			FreieFelderTeam.add((int)(m_Felder.size()/m_Spieler.size()));
			freieTeam.add(m_Spieler.get(i).getTeam());
		}
		java.util.Random zufall = new java.util.Random();
		for(Feld fl : m_Felder)
		{
			if(freieTeam == null || freieTeam.isEmpty())
			{
				int temp = zufall.nextInt(m_Spieler.size());
				fl.addUnit("Soldat",1); 
				fl.setZg(freieTeam.get(temp));
			}
			else
			{
				int temp = zufall.nextInt(freieTeam.size());
				fl.addUnit("Soldat",1); 
				fl.setZg(freieTeam.get(temp));
				FreieFelderTeam.set(temp, FreieFelderTeam.get(temp)-1); 
				if (FreieFelderTeam.get(freieTeam.get(temp)) <= 0)
				{
					FreieFelderTeam.remove(temp);
					freieTeam.remove(temp);
				}
			}
		}
	}
	
	private void end()
	{}
	
	private void routine()
	{
		versorgung();
	}
	
	private void versorgung()
	{
		m_SpielStatus = SpielStatus.Versorgung;
		m_unitsPlacedNum = (int)Math.ceil((double)Feld.getFelderFromTeam(m_Felder, m_TeamsTurn).size()/3) + Gebiet.getTeamBonus(m_Felder, m_TeamsTurn, m_Gebiete);
	}
	
	private void versorgungButtonClicked(int index)
	{
		if (m_unitsPlacedNum > 0)
		{
			m_Felder.get(index).addUnit("Soldat", 1);
			m_unitsPlacedNum--;
		}
		else
		{
			m_SpielStatus = SpielStatus.Angriff;
			angriff();
		}
	}
	
	private void angriff()
	{}
	
	private void bewegeTruppen()
	{
		
	}
	
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
