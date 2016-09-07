package deps;

/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2018
 * </p>
 * <p>
 * Klasse Connection
 * </p>
 * <p>
 * Objekte der Klasse Connection ermoeglichen eine Netzwerkverbindung zu einem Server
 * mittels TCP/IP-Protokoll. Nach Verbindungsaufbau k�nnen Zeichenketten (Strings)
 * zum Server gesendet und von diesem empfangen werden. Zur Vereinfachung geschieht
 * dies zeilenweise, d. h., beim Senden einer Zeichenkette wird ein Zeilentrenner
 * erg�nzt und beim Empfang wird dieser entfernt. Aus Gr�nden der Vereinfachung
 * findet nur eine rudiment�re Fehlerbehandlung statt, so dass z.B. der Zugriff auf
 * abgebrochene oder bereits geschlossene Verbindungen nicht zu einem Programmabbruch
 * f�hrt.
 * </p>
 *
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule
 * @version 11.04.2016
 */

import java.net.*;
import java.io.*;

public class Connection
{
    private Socket socket;
    private BufferedReader fromServer;
    private PrintWriter toServer;

    public Connection(String pServerIP, int pServerPort)
    {       
        try
        {
            socket = new Socket(pServerIP, pServerPort);
            toServer = new PrintWriter(socket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception e)
        {
            //Erstelle eine nicht-verbundene Instanz von Socket, wenn die avisierte
            //Verbindung nicht hergestellt werden kann
            socket = null;
            toServer = null;
            fromServer = null;
        }
    }

    public String receive()
    {
        if(fromServer != null)
            try
            {
                return fromServer.readLine();
            }
            catch (IOException e)
            {
            }
        return(null);
    }

    public void send(String pMessage)
    {
        if(toServer != null)
        {
            toServer.println(pMessage);
         }
    }

    public void close()
    {

        if(socket != null && !socket.isClosed())
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                /*
                 * Falls eine Verbindung geschlossen werden soll, deren Endpunkt nicht
                 * mehr existiert bzw. seinerseits bereits geschlossen worden ist oder
                 * die nicht korrekt instanziiert werden konnte (socket == null), geschieht
                 * nichts.
                 */
            }
    }
}
