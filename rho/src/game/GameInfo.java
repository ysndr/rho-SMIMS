package game;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import deps.Graph;

public class GameInfo {

	@Expose private int m_Runde; // Die momentane Runde des Spiels
	@Expose private int m_TeamsTurn; // Die Id des Spielers, der am Zug ist
	@Expose private SpielStatus m_SpielStatus; // Der Spielstatus des Spiels
	@Expose private ArrayList<Player> m_Spieler; // Arraylist aller Spieler
	
	private Graph m_SpielPlan; // Der Graph der die Verkn�pfungen zwischen den
								// einzelnen Feldern speichert
	@Expose private ArrayList<Feld> m_Felder; // ArrayList von allen Feldern
	@Expose private ArrayList<Gebiet> m_Gebiete; // ArrayList von alles Gebieten
	@Expose private int m_unitsPlacedNum; // tempor�re Variable die
									// zwischenspeichert, wieviele Soldaten
									// der Spieler w�hrend der
									// Versorgungsphase noch setzten darf

	public GameInfo() {
		super();
		m_Runde = 0;
		m_TeamsTurn = 0;
		m_SpielStatus = SpielStatus.nichtAngegeben;
		this.m_Spieler = new ArrayList<>();
		this.m_SpielPlan = new Graph();
		this.m_Felder = new ArrayList<>();
		this.m_Gebiete = new ArrayList<>();
		this.m_unitsPlacedNum = 1;
	}

	public int getM_Runde() {
		return m_Runde;
	}

	public void setM_Runde(int m_Runde) {
		this.m_Runde = m_Runde;
	}

	public int getM_TeamsTurn() {
		return m_TeamsTurn;
	}

	public void setM_TeamsTurn(int m_TeamsTurn) {
		this.m_TeamsTurn = m_TeamsTurn;
	}

	public SpielStatus getM_SpielStatus() {
		return m_SpielStatus;
	}

	public void setM_SpielStatus(SpielStatus m_SpielStatus) {
		this.m_SpielStatus = m_SpielStatus;
	}

	public ArrayList<Player> getM_Spieler() {
		return m_Spieler;
	}

	public void setM_Spieler(ArrayList<Player> m_Spieler) {
		this.m_Spieler = m_Spieler;
	}

	public Graph getM_SpielPlan() {
		return m_SpielPlan;
	}

	public void setM_SpielPlan(Graph m_SpielPlan) {
		this.m_SpielPlan = m_SpielPlan;
	}

	public ArrayList<Feld> getM_Felder() {
		return m_Felder;
	}

	public void setM_Felder(ArrayList<Feld> m_Felder) {
		this.m_Felder = m_Felder;
	}

	public ArrayList<Gebiet> getM_Gebiete() {
		return m_Gebiete;
	}

	public void setM_Gebiete(ArrayList<Gebiet> m_Gebiete) {
		this.m_Gebiete = m_Gebiete;
	}

	public int getM_unitsPlacedNum() {
		return m_unitsPlacedNum;
	}

	public void setM_unitsPlacedNum(int m_unitsPlacedNum) {
		this.m_unitsPlacedNum = m_unitsPlacedNum;
	}
	
	public String toData() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = "MAP:" + gson.toJson(this);
		return json;
	}
	
	public static GameInfo fromData(String pJson) {
		Gson gson = new Gson();
		GameInfo gi = gson.fromJson(pJson.substring(4), GameInfo.class);
		return gi;	
	}

}
