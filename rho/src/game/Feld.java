package game;

import java.util.ArrayList;
import deps.Vertex;

/**
 * @author lukas Feld stellt ein Spielfeld mit dessen Soldaten und der
 *         Spielzugeh�rigkeit dar.
 */
public class Feld extends Vertex {
	private ArrayList<Einheit> einheiten; // Die Soldaten, die auf dem Spielfeld
											// stehen
	private int zg; // zugeh�hrigkeit (wessen Spieler das Feld geh�rt)

	private int x; // Position x des Feldes
	private int y; // Position y des Feldes

	public Feld(String pID) {
		this(pID, 1, 1);
	}

	public Feld(String pID, int pX, int pY) {
		super(pID);
		x = pX;
		y = pY;
		einheiten = new ArrayList<>();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int pX) {
		if (x == 0)
			x = pX;
	}

	public void setY(int pY) {
		if (y == 0)
			y = pY;
	}

	/**
	 * F�gt die Soldaten vom Typ und Anzahl dem Feld hinzu (Man kann auch
	 * Soldaten l�schen indem man n negativ angibt)
	 * 
	 * @param typ
	 *            der Typ der Armee
	 * @param n
	 *            die Anzahl der Armee
	 */
	public void addUnit(String typ, int n) {
		boolean added = false;
		if (einheiten != null) {
			for (Einheit e : einheiten) {
				if (e.getTyp() == typ) {
					e.setAnzahl(e.getAnzahl() + n);
					if (e.getAnzahl() < 0) {
						e.setAnzahl(0);
					}
					added = true;
				}
			}
			if (!added) {
				einheiten.add(new Einheit(typ, n));
			}
		}
	}

	public ArrayList<Einheit> getEinheiten() {
		return einheiten;
	}

	public int getZg() {
		return zg;
	}

	public void setZg(int pZg) {
		zg = pZg;
	}

	/**
	 * gibt eine Arraylist an Feldern wieder, die dem mitgegebenen Team geh�ren
	 * 
	 * @param Felder
	 *            die Felder, die �berpr�ft werden sollen
	 * @param teamId
	 *            das Team(Spieler) dessen Felder gesucht werden sollen
	 * @return gibt eine Arraylist an Feldern wieder, die dem mitgegebenen Team
	 *         geh�ren
	 */
	public static ArrayList<Feld> getFelderFromTeam(ArrayList<Feld> Felder, int teamId) {
		ArrayList<Feld> result = new ArrayList<Feld>();
		for (Feld fl : Felder)
			if (fl.getZg() == teamId)
				result.add(fl);
		return result;
	}

	/**
	 * Sucht das Feld in der Liste mit der angegebenen id
	 * 
	 * @param felder: die Liste
	 * @param id: das Feld mit der zu suchenden Id
	 * @return Sucht das Feld in der Liste mit der angegebenen id
	 */
	public static Feld searchFeld(ArrayList<Feld> felder, String id) {
		for (Feld fl : felder) {
			if (fl.getID().equals(id)) {
				return fl;
			}
		}
		return null;
	}

	public String toData() {
		return zg + "," + x + "," + y;
	}

	public static Feld fromData(String pData) {
		String[] data = pData.split(";");

		return null;
	}

}
