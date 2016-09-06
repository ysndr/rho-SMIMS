package game;
import java.util.ArrayList;

import deps.Graph;
import deps.List;
import deps.Vertex;

public class ManageClass {
	
	private int m_Runde;
	private int m_TeamsTurn;
	private SpielStatus m_SpielStatus; 
	private ArrayList<Player> m_Spieler;
	private Graph m_SpielPlan;
	private ArrayList<Feld> m_Felder;
	private ArrayList<Gebiet> m_Gebiete;
	private Kampfklasse m_kampfklasse;
	
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
			
			versorgung();
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
	{
		
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
		}
	}
	
	public void angreifen(String startId, String endId, int AnzUnits)
	{
		if(Feld.searchFeld(m_Felder, startId).getZg() == m_TeamsTurn
			&& Feld.searchFeld(m_Felder, endId).getZg() != m_TeamsTurn
			&& convertAbiListToArrayList(m_SpielPlan.getNeighbours(m_SpielPlan.getVertex(startId))).contains(m_SpielPlan.getVertex(endId)))
		{
			ArrayList<Einheit> AngriffEinheiten = Feld.searchFeld(m_Felder, startId).getEinheiten();
			ArrayList<Einheit> VerteidigungsEinheiten = Feld.searchFeld(m_Felder, endId).getEinheiten();
			for(Einheit ei : AngriffEinheiten)
			{
				if(ei.getTyp().equals("Soldat")
					&& ei.getAnzahl() > AnzUnits)
				{
					for(Einheit ai : VerteidigungsEinheiten)
					{
						if (ai.getTyp().equals("Soldat"))
						{
							int angriffwuerfel = ei.getAnzahl(), verteidigungswuerfel = ai.getAnzahl();
							if (verteidigungswuerfel > 2)
								verteidigungswuerfel = 2;
							if (angriffwuerfel > 3)
								angriffwuerfel = 3;
							int result = Kampfklasse.sieger(angriffwuerfel, verteidigungswuerfel);
							if (result == 1)
							{
								ai.setAnzahl(ai.getAnzahl()-1);
								if (ai.getAnzahl() <= 0)
								{
									Feld erobertesfeld = Feld.searchFeld(m_Felder, endId);
									erobertesfeld.setZg(m_TeamsTurn);
									erobertesfeld.addUnit("Soldat", angriffwuerfel);
									ei.setAnzahl(-angriffwuerfel);
								}
							}
							else
							{
								ei.setAnzahl(ei.getAnzahl()-1);
							}
						}
					}
				}
			}
		}
	}
	
	private void bewegeTruppen()
	{
		m_SpielStatus = SpielStatus.Truppenbewegung;
	}
	
	
	private void Truppenbewegen(String startid, String endID, int AnzUnits)
	{
		ArrayList<Einheit> einheiten = Feld.searchFeld(m_Felder, startid).getEinheiten();
		for(Einheit ei : einheiten)
		{
			if(ei.getTyp().equals("Soldat"))
			{
				if (ei.getAnzahl() > AnzUnits &&
						convertAbiListToArrayList(m_SpielPlan.getNeighbours(m_SpielPlan.getVertex(startid))).contains(m_SpielPlan.getVertex(endID))
						&& Feld.searchFeld(m_Felder, startid).getZg() == m_TeamsTurn 
						&& Feld.searchFeld(m_Felder, endID).getZg() == m_TeamsTurn )
					
				{
					Feld.searchFeld(m_Felder, startid).addUnit("Soldat", -AnzUnits);
					Feld.searchFeld(m_Felder, endID).addUnit("Soldat", AnzUnits);
				}
				else
				{
					//Fehler!
				}
			}
		}
	}
	
	private void endTruppenbewegung()
	{
		m_SpielStatus = SpielStatus.Versorgung;
		for(int i = 0; i < m_Spieler.size(); i++)
		{
			if (m_Spieler.get(i).getTeam() == m_TeamsTurn)
			{
				if(i >= m_Spieler.size()-1)
				{
					m_TeamsTurn = m_Spieler.get(0).getTeam();
				}
				else
				{
					m_TeamsTurn = m_Spieler.get(i+1).getTeam();
				}
			}
		}
		versorgung();
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
