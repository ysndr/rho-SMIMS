package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import deps.Edge;
import deps.Graph;
import deps.Vertex;


/**
 * Diese Klasse erlaubt es, einen gewichteten, ungerichteten Graphen zu
 * erzeugen. Die Kanteninformationen werden dazu aus einer Datei entnommen.
 * 
 * Die Datei enthaelt zeilenweise die Information ueber eine Kante in der Form
 * 
 * GEWICHT KOMMA START KOMMA ZIEL
 * 
 * wobei die Gewichtsinformation als Dezimalzahl erwartet wird.
 * 
 * Beispiel
 * 
 * 600.5,Bielefeld,Muenchen
 * 
 * Beginnt die Zeile mit einem "/", wird sie ignoriert.
 * 
 * Ebenfalls kann man einen gewichteten, ungerichteten Graphen erzeugen, der in
 * Form einer Zeichenkette vorliegt. Dabei hat diese Zeichenkette die Form:
 * 
 * 
 * KANTE1 SEMIKOLON KANTE2 SEMIKOLON KANTE3 ...
 * 
 * Beide Methoden stellen nach Beendigung einen gewichteten, ungerichteten
 * Graphen zur Verfügung, dessen Knoten vom Typ ColoredVertex sind.
 * 
 * Gleichzeitig kann man eine Zeichenkette erfragen, die syntaktisch
 * 
 * KANTE1 SEMIKOLON KANTE2 SEMIKOLON KANTE3 ...
 * 
 * aufgebaut ist.
 * 
 * Diese Zeichenkette ist in der Regel identisch mit der eingegebenen
 * Zeichenkette, wenn die Methode StringToGraph benutzt wird. Wird die Methode
 * fileToGraph benutzt, werden sozusagen alle Zeilen der Datei miteinander
 * verschmolzen. Lediglich die Kommentarzeilen werden ignoriert.
 * 
 * Zu beachten ist, dass hiermit keine isolierten Knoten zu dem Graphen
 * hinzugefuegt werden koennen.
 * 
 * 
 * @version 4.1 vom 19.11.2015
 * @author Klaus Bovermann
 */
public class Graphbauer {
	private Graph derGraph;
	private String graphString;

	private HashMap<String, Feld> felderPool;

	/**
	 * eine Zeile in der Eingabedatei, die mit diesem String beginnt, wird als
	 * Kommentar interpretiert und ignoriert.
	 */
	public static final String KOMMENTAR_BEGINN = "/";

	/**
	 * Eine Kante besteht aus drei Teilen, die durch diesen Trenner getrennt
	 * sind.
	 * 
	 * Also GEWICHT KOMMA START KOMMA ZIEL
	 * 
	 */
	public static final String SYMBOLTRENNER = ",";

	/**
	 * Mehrere Kanten sind durch diesen Trenner getrennt.
	 * 
	 * Also KANTE1 SEMIKOLON KANTE2 SEMIKOLON KANTE3 ...
	 */
	public static final String KANTENTRENNER = ";";

	/**
	 * Erzeugt einen Graphbauer. Der Graph sowie der Graphstring sind noch leer.
	 */
	public Graphbauer() {
		init();
	}

	public Graphbauer(HashMap<String, Feld> fMap) {
		felderPool = fMap;
		init();
	}

	private void init() {
		this.derGraph = new Graph();
		this.graphString = "";
	}

	/**
	 * Aus der Eingabedatei wird ein Graph erstellt.
	 * 
	 * @param pFilename
	 *            Name der Datei z.B. "autobahn.txt"
	 *
	 * @throws IOException
	 *             Datei gibt es nicht
	 */
	public void fileToGraph(String pFilename) throws IOException {
		init();
		BufferedReader brEdges = new BufferedReader(new FileReader(pFilename));
		// Kanten
		String line = brEdges.readLine();
		while (line != null) {
			if (!line.startsWith(Graphbauer.KOMMENTAR_BEGINN)) {
				kanteHinzu(line);
			}
			line = brEdges.readLine();
		}
		brEdges.close();
	}

	/**
	 * Aus einem Graphbeschreibungstext wird ein Graph erzeugt.
	 * 
	 * @param pKanten
	 *            Kantenbeschreibung z.B. "1,A,B;2,A,C;7,B,C"
	 */
	public void stringToGraph(String pKanten) {
		init();
		StringTokenizer strtok = new StringTokenizer(pKanten, Graphbauer.KANTENTRENNER);
		// Kanten
		while (strtok.hasMoreTokens()) {
			kanteHinzu(strtok.nextToken());
		}
	}

	private void kanteHinzu(String pKantenbeschreibung) {
		if (!pKantenbeschreibung.trim().equals("")
				&& !pKantenbeschreibung.subSequence(0, 1).equals(Graphbauer.KOMMENTAR_BEGINN)) {

			StringTokenizer strtok = new StringTokenizer(pKantenbeschreibung, Graphbauer.SYMBOLTRENNER);

			double length = Double.parseDouble(strtok.nextToken()); // Kantenlaenge
			String name1 = strtok.nextToken().trim(); // Knoten1
			String name2 = strtok.nextToken().trim(); // Knoten2

			// der Graphstring wird gebaut
			if (!this.graphString.equals("")) {
				this.graphString += Graphbauer.KANTENTRENNER;
			}
			this.graphString += "" + length + Graphbauer.SYMBOLTRENNER + name1 + Graphbauer.SYMBOLTRENNER + name2;

			// Die Infos kommen in den Graphen
			if (felderPool != null) {
				
				Vertex n_1 = this.derGraph.getVertex(name1);

				if (n_1 == null && felderPool.containsKey(name1)) {
					n_1 = felderPool.get(name1);
				}else if(n_1 == null){
					n_1 = new Feld(name1);
				}

				Vertex n_2 = this.derGraph.getVertex(name2);

				if (n_2 == null && felderPool.containsKey(name2)) {
					n_2 = felderPool.get(name2);
				}else if(n_2 == null){
					n_2 = new Feld(name2);
				}

				this.derGraph.addVertex(n_1);
				this.derGraph.addVertex(n_2);
				
				Edge e = new Edge(n_1, n_2, length);

				this.derGraph.addEdge(e);
				
				
			}

		}
	}

	/**
	 * Der erzeugte Graph wird geliefert.
	 * 
	 * @return der Graph
	 */
	public Graph getGraph() {
		return this.derGraph;
	}

	/**
	 * Der erzeugte Graph-String wird geliefert.
	 * 
	 * @return der GraphString
	 */
	public String getGraphString() {
		return this.graphString;
	}
}
