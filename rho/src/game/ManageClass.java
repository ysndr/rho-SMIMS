package game;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import deps.Edge;
import deps.List;
import deps.Vertex;
import util.Logbuch;

public class ManageClass {

	private Logbuch m_LogBuch;
	private GameInfo gi;
	private Kampfklasse m_kampfklasse; // Kampfklasse
	private FeldBuilder fb;
	private ErrorManaged m_ErrorManager;
	public ManageClass() {
		fb = new FeldBuilder();
		gi = new GameInfo();
		try {
			m_LogBuch = Logbuch.getLogbuch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param spieler
	 *            F�gt den Spieler zum Spiel hinzu
	 */
	public void addPlayer(Player spieler) {
		if (gi.getM_SpielStatus() == SpielStatus.nichtAngegeben) {
			gi.getM_Spieler().add(spieler);
		}
	}
	
	public void removePlayer(Player spieler)
	{
		if (gi.getM_SpielStatus() == SpielStatus.nichtAngegeben) {
			gi.getM_Spieler().remove(spieler);
		}
	}

	
	public void setup(){
		String result;
		fb = new FeldBuilder();
		result = fb.readFelder("data/Weltkarte/Koordinaten.txt");
		if (!result.equals(""))
		{
			m_ErrorManager.setErrorManaged(false);
			try {
				m_LogBuch.schreiben(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		gi.setM_Felder(fb.getFelder());
		fb.readEdges("data/Weltkarte/Graphen.txt");
		if (!result.equals(""))
		{
			m_ErrorManager.setErrorManaged(false);
			try {
				m_LogBuch.schreiben(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		gi.setM_SpielPlan(fb.getGraph());
		
		/*gi.setM_Felder(new ArrayList<Feld>());
		ArrayList<Vertex> vertextemp = convertAbiListToArrayList(gi.getM_SpielPlan().getVertices());
		for (Vertex vt : vertextemp) {
			gi.getM_Felder().add(new Feld(vt.getID()));
		}*/
		gi.setM_Gebiete(Gebiet.readGebiete());
	}
	/**
	 * Das Spiel beginnt und wird initialisiert. Keine Spieler k�nnen nun zum
	 * Spiel hinzugef�gt werden.
	 */
	public void beginn() {
		if (gi.getM_SpielStatus() == SpielStatus.nichtAngegeben) {
			if (gi.getM_Spieler() != null && !gi.getM_Spieler().isEmpty()) {
				gi.setM_SpielStatus(SpielStatus.Vorbereitung);
				gi.setM_Runde(0);
				
				gebietszuordnung();
				gi.setM_TeamsTurn(gi.getM_Spieler().get(0).getTeam());
				
				versorgung();
			}
			else
			{
				m_ErrorManager.setErrorManaged(false);
				try {
					m_LogBuch.schreiben("Spieler nicht vorhanden");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Teilt alle Felder an alle Spieler in gleichm��iger Anzahl zuf�llig auf.
	 */
	private void gebietszuordnung() {
		ArrayList<Integer> FreieFelderTeam = new ArrayList<Integer>();
		ArrayList<Integer> freieTeam = new ArrayList<Integer>();
		for (int i = 0; i < gi.getM_Spieler().size(); i++) {
			FreieFelderTeam.add((int) (gi.getM_Felder().size() / gi.getM_Spieler().size()));
			freieTeam.add(gi.getM_Spieler().get(i).getTeam());
		}
		java.util.Random zufall = new java.util.Random();
		for (Feld fl : gi.getM_Felder()) {
			if (freieTeam == null || freieTeam.isEmpty()) {
				int temp = zufall.nextInt(gi.getM_Spieler().size());
				fl.addUnit("Soldat", 1);
				fl.setZg(gi.getM_Spieler().get(temp).getTeam());
			} else {
				int temp = zufall.nextInt(freieTeam.size());
				fl.addUnit("Soldat", 1);
				fl.setZg(freieTeam.get(temp));
				FreieFelderTeam.set(temp, FreieFelderTeam.get(temp) - 1);
				if (FreieFelderTeam.get(temp) <= 0) {
					FreieFelderTeam.remove(temp);
					freieTeam.remove(temp);
				}
			}
		}
	}

	/**
	 * Berechnet wieviele Soldaten der Spieler auf das Spielfeld neu verteilen
	 * kann.
	 */
	private void versorgung() {
		gi.setM_SpielStatus(SpielStatus.Versorgung);
		gi.setM_unitsPlacedNum(
				(int) Math.ceil((double) Feld.getFelderFromTeam(gi.getM_Felder(), gi.getM_TeamsTurn()).size() / 3)
						+ Gebiet.getTeamBonus(gi.getM_Felder(), gi.getM_TeamsTurn(), gi.getM_Gebiete()));
	}

	/**
	 * Ein Soldat wird auf das angegbende Feld gesetzt.
	 * 
	 * @param index
	 *            des geklickten Buttons (gleich des Index im Feldarray)
	 */
	public void versorgungButtonClicked(String id, String typ) {
		if (gi.getM_SpielStatus() == SpielStatus.Versorgung) {
			Feld feld = Feld.searchFeld(gi.getM_Felder(), id);
			if (gi.getM_unitsPlacedNum() > 0 && feld.getZg() == gi.getM_TeamsTurn()) {
				Feld.searchFeld(gi.getM_Felder(), id).addUnit("Soldat", 1);

				gi.setM_unitsPlacedNum(gi.getM_unitsPlacedNum() - 1);
				if (gi.getM_unitsPlacedNum() <= 0) {
					gi.setM_SpielStatus(SpielStatus.Angriff);
				}
			}
			else
			{
				m_ErrorManager.setErrorManaged(false);
				try {
					m_LogBuch.schreiben("Die Einheit konnte nicht auf einem Feld platziert werden, da es nicht dem Spieler der am Zug ist geh�rt.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * es wird ein Angriff durchgef�hrt
	 * 
	 * @param startId
	 *            FeldId der Angreifenden Armee
	 * @param endId
	 *            FeldId der Verteidigenden Armee
	 * @param AnzUnits
	 *            Anzahl an Soldaten, die vom angreifenden Feld aus angreifen
	 * 
	 * 
	 */
	public void angreifen(String startId, String endId, int AnzUnits) {
		if (gi.getM_SpielStatus() != SpielStatus.Angriff) {
			return;
		}

		Feld feldStart = Feld.searchFeld(gi.getM_Felder(), startId);
		Feld feldEnd = Feld.searchFeld(gi.getM_Felder(), endId);

		if (feldStart.getZg() == gi.getM_TeamsTurn() 
				&& feldEnd.getZg() != gi.getM_TeamsTurn()
				&& convertAbiListToArrayList(gi.getM_SpielPlan().getNeighbours(gi.getM_SpielPlan().getVertex(startId)))
						.contains(gi.getM_SpielPlan().getVertex(endId))) {

			ArrayList<Einheit> angriffEinheiten = feldStart.getEinheiten();
			ArrayList<Einheit> verteidigungsEinheiten = feldEnd.getEinheiten();
						
			for (Einheit ei : angriffEinheiten) {
				
				if (ei.getTyp().equals("Soldat") 
					&& ei.getAnzahl() > AnzUnits
					&& AnzUnits > 0) {
					
					for (Einheit ai : verteidigungsEinheiten) {
						if (ai.getTyp().equals("Soldat")) {
							int angriffwuerfel = AnzUnits, verteidigungswuerfel = ai.getAnzahl();
							
							if (verteidigungswuerfel > 2)	verteidigungswuerfel = 2;
							if (angriffwuerfel > 3)			angriffwuerfel = 3;
							int result = Kampfklasse.sieger(angriffwuerfel, verteidigungswuerfel);
							if (result == 1) {
								ai.setAnzahl(ai.getAnzahl() - 1);
								if (ai.getAnzahl() <= 0) {
									Feld erobertesfeld = Feld.searchFeld(gi.getM_Felder(), endId);
									erobertesfeld.setZg(gi.getM_TeamsTurn());
									erobertesfeld.addUnit("Soldat", angriffwuerfel);
									ei.setAnzahl(ei.getAnzahl() - angriffwuerfel);
								}
							} else {
								ei.setAnzahl(ei.getAnzahl() - 1);
							}
						}
					}
				}
				else
				{
					try {
						m_LogBuch.schreiben("Es wurde ein ung�ltiger Soldatentyp oder eine ung�ltige Anzahl an Soldaten f�r den Angriff ausgew�hlt");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			try {
				m_LogBuch.schreiben("Ung�ltige Felder wurden f�r den Angriff gew�hlt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Wenn der Spieler keinen Angriff mehr durchf�hren will, wird damit die
	 * Truppenbewegungsphase eingeleited.
	 */
	public void beendeAngriff() {
		if (gi.getM_SpielStatus() == SpielStatus.Angriff) {
			gi.setM_SpielStatus(SpielStatus.Truppenbewegung);
		}
	}

	/**
	 * Bewegt eine Anzahl an Soldaten vom Startfeld aufs EndFeld, wenn diese
	 * benachbart sind und dem Spieler geh�ren
	 * 
	 * @param startid
	 *            die Id des Feldes von dem die Truppen bewegt werden sollen.
	 * @param endID
	 *            die Id des Feldes auf das die Truppen bewegt werden sollen.
	 * @param AnzUnits
	 *            die Anzahl an Soldaten, die bewegt werden sollen.
	 */
	public void Truppenbewegen(String startid, String endID, int AnzUnits) {
		if (gi.getM_SpielStatus() == SpielStatus.Truppenbewegung) {
			ArrayList<Einheit> einheiten = Feld.searchFeld(gi.getM_Felder(), startid).getEinheiten();
			for (Einheit ei : einheiten) {
				if (ei.getTyp().equals("Soldat")) {
					if (ei.getAnzahl() > AnzUnits
							&& convertAbiListToArrayList(
									gi.getM_SpielPlan().getNeighbours(gi.getM_SpielPlan().getVertex(startid)))
											.contains(gi.getM_SpielPlan().getVertex(endID))
							&& Feld.searchFeld(gi.getM_Felder(), startid).getZg() == gi.getM_TeamsTurn()
							&& Feld.searchFeld(gi.getM_Felder(), endID).getZg() == gi.getM_TeamsTurn())

					{
						Feld.searchFeld(gi.getM_Felder(), startid).addUnit("Soldat", -AnzUnits);
						Feld.searchFeld(gi.getM_Felder(), endID).addUnit("Soldat", AnzUnits);
					} else {
						m_ErrorManager.setErrorManaged(false);
						try {
							m_LogBuch.schreiben("Es wurden ung�ltige Felder f�r die Truppenbewegung ausgew�hlt");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				else
				{
					m_ErrorManager.setErrorManaged(false);
					try {
						m_LogBuch.schreiben("Ung�ltiger Armeetyp bei der Truppenbewegung ausgew�hlt.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Der Spieler m�chte keine Truppen mehr bewegen und beendet damit seinen
	 * Zug
	 */
	public void endTruppenbewegung() {
		if (gi.getM_SpielStatus() == SpielStatus.Truppenbewegung) {
			gi.setM_SpielStatus(SpielStatus.Versorgung);
			for (int i = 0; i < gi.getM_Spieler().size(); i++) {
				if (gi.getM_Spieler().get(i).getTeam() == gi.getM_TeamsTurn()) {
					if (i >= gi.getM_Spieler().size() - 1) {
						gi.setM_TeamsTurn(gi.getM_Spieler().get(0).getTeam());
						break;
					} else {
						gi.setM_TeamsTurn(gi.getM_Spieler().get(i + 1).getTeam());
						break;
					}
				}
			}
			gi.setM_Runde(gi.getM_Runde() + 1);
			versorgung();
		}
	}

	/**
	 * �berpr�ft, ob der Spieler, der am Zug ist gewonnen hat
	 * 
	 * @return gibt wieder, ob der Spieler der am Zug ist gewonnen hat
	 */
	private boolean checkSpielVorbei() {
		return Feld.getFelderFromTeam(gi.getM_Felder(), gi.getM_TeamsTurn()).containsAll(gi.getM_Felder());
	}

	/**
	 * beendet das Spiel
	 */
	public void end() {

	}

	/**
	 * convertiert eine AbiListe zu einer ArrayListe
	 * 
	 * @param felder
	 *            Abiturliste von Vertexen
	 * @return gibt eine ArrayList von Vertexen wieder
	 */
	public static ArrayList<Vertex> convertAbiListToArrayList(List<Vertex> felder) {
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		felder.toFirst();
		while (felder.hasAccess()) {
			result.add(felder.getContent());
			felder.next();
		}
		return result;
	}

	public SpielStatus getStatus() {
		return gi.getM_SpielStatus();
	}

	public int getTeamsTurn() {
		return gi.getM_TeamsTurn();
	}

	public String graphToString() {

		List<Edge> edges = fb.getGraph().getEdges();
		String felder = "";
		edges.toFirst();
		for (; edges.hasAccess(); edges.next()) {
			Vertex[] verticies = edges.getContent().getVertices();
			double weight = edges.getContent().getWeight();
			felder += weight + "," + verticies[0].getID() + "," + verticies[1].getID() + ";";
		}
		return felder;
	}

	public String gameInfoData() {
		return gi.toData();
	}
	
	public ArrayList<Feld> getAlleFelder()
	{
		return gi.getM_Felder();
	}

}
