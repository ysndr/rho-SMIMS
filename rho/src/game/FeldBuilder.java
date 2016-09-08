package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import deps.Edge;
import deps.Graph;
import deps.List;
import deps.Vertex;

public class FeldBuilder {

	public static final String IDTRENNER = ":";
	public static final String KOOTRENNER = ",";
	public static final String KOMMENTAR_BEGINN = "/";
	
	private HashMap<String, Feld> felderPool;
	private Graphbauer gb;
	
	public FeldBuilder() {
		felderPool = new HashMap<String, Feld>();	
		gb = new Graphbauer();
	}
	
	
	public String readEdges(String pFilename) {
		try {
			gb.fileToGraph(pFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return "Fehler beim einlesen des Graphen";
		}
		return "";
	}
	
	
	public String readFelder(String pFilename) {

		try {
			BufferedReader brEdges = new BufferedReader(new FileReader(pFilename));

			String line = brEdges.readLine();
			while (line != null) {
				if (!line.startsWith(Graphbauer.KOMMENTAR_BEGINN)) {
					feldHinzu(line);
				}
				line = brEdges.readLine();
			}
			brEdges.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Die Felderdatei konnte nicht gefunden werden.";
		} catch (IOException e) {
			
			e.printStackTrace();
			return "Fehler beim einlesen der Felderdatei";
		}
		return "";
	}

	public void feldHinzu(String line) {
		if (!line.trim().equals("")
				&& !line.subSequence(0, 1).equals(FeldBuilder.KOMMENTAR_BEGINN)) {
			
			StringTokenizer strtok = new StringTokenizer(line, FeldBuilder.IDTRENNER);
			
			String id = strtok.nextToken();
			if(!felderPool.containsKey(id)){
				
				strtok = new StringTokenizer(strtok.nextToken().trim(), FeldBuilder.KOOTRENNER);
			
				int x = Integer.parseInt(strtok.nextToken().trim()); 
				int y = Integer.parseInt(strtok.nextToken().trim()); 

				felderPool.put(id, new Feld(id, x, y));
			}
			
		}
	}

	
	/**
	 * @return Eine mapping von feld-id nach Feld-Objekt
	 */
	public HashMap<String, Feld> getIdFeldMap(){
		return felderPool;
	}
	
	
	/**
	 * @return eine Liste aller Felder
	 */
	public ArrayList<Feld> getFelder() {
		return new ArrayList<Feld>(felderPool.values());
	}
	
	/**
	 * @return den erzeugten graphen
	 */
	public Graph getGraph() {
		return gb.getGraph();
	}
	
	
	
	
	
	
}
