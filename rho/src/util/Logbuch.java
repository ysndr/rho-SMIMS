package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Michael Die Logbuchklasse erstellt und schreibt in einer Textdatei.
 */
public class Logbuch {
	private FileWriter schreiber1;
	private BufferedWriter schreiber2;
	public static final String LOGPATH = "Log.txt"; 
	private static Logbuch dasLogbuch = null;

	private Logbuch() throws IOException {
		FileWriter schreiber1 = new FileWriter(LOGPATH);
		BufferedWriter schreiber2 = new BufferedWriter(schreiber1);

	}

	/**
	 * @return erst muss beim Aufruf das Logbuch eingeholt werden. Falls noch
	 *         kein Logbuch vorhanden ist wird ein Logbuch erzeugt.
	 * @throws IOException
	 */
	public static Logbuch getLogbuch() throws IOException {
		if (dasLogbuch == null) {
			dasLogbuch = new Logbuch();
		}
		return dasLogbuch;
	}

	/**
	 * @param eingeBefehl
	 *            = der ins Logbuch zu schreibende Ausdruck.
	 * @throws IOException
	 */
	public void schreiben(String eingeBefehl) throws IOException {

		try {
			schreiber2.write(eingeBefehl);
			schreiber2.write("\n");
			schreiber2.close();
		}

		catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}
	
	public static ArrayList<String> readLog()
	{
		ArrayList<String> Log = new ArrayList<String>();
		try
		{
			FileReader fr = new FileReader(new File(LOGPATH));
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while(line != null && line != "")
			{
				Log.add(line);
				line = br.readLine();
			}
		}
		catch (Exception e)
		{}
		return Log;
	}
}
