package game.server;

import java.util.ArrayList;

import deps.Server;
import game.ErrorManaged;
import game.ManageClass;
import game.SpielStatus;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;
import game.server.protocolls.ServerProtocoll;
import util.Logbuch;

public class ServerKlasse extends Server {

	private ManageClass MC;
	private ArrayList<Spieler> playerList;
	int curTeam;
	int lineNumber;
	private ErrorManaged m_ErrorManager;


	public ServerKlasse(int PortNr) {
		super(PortNr);
		MC = new ManageClass();
		playerList = new ArrayList<Spieler>();
		curTeam = 0;
		lineNumber = Logbuch.readLog().size();
	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		if (MC.getStatus() == SpielStatus.nichtAngegeben) {
			m_ErrorManager.setErrorManaged(true);
			Spieler sp = new Spieler(curTeam, pClientIP, pClientPort); //�nderung
			playerList.add(sp);
			MC.addPlayer(sp);
			curTeam += 1;
			if (playerList.size() == 4) {
				MC.setup();
				int i = 0; 
				for (Spieler s : playerList) {
					s.gameStart(i);
					send(s.getIP(), s.getPort(),ServerProtocoll.TURN_START+ServerProtocoll.SEPERATOR+s.getTeam()+ServerProtocoll.SEPERATOR+MC.graphToString());
					i++;
				}
			}
			if (!m_ErrorManager.getErrorManaged())
			{
				ArrayList<String> log = Logbuch.readLog();
				send(pClientIP,pClientPort,ServerProtocoll.ERR + ServerProtocoll.SEPERATOR + log.get(log.size()-1));
				m_ErrorManager.setErrorManaged(true);
			}
		} else {
			errMessage(pClientIP, pClientPort, "Das Spiel hat bereits begonnen");
		}
	}
	
	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		m_ErrorManager.setErrorManaged(true);
		for (Spieler plyr : playerList) {

			if (plyr.getPort() != pClientPort || plyr.getIP() != pClientIP) {
				continue;
			}
			
			if (MC.getTeamsTurn() != plyr.getTeam()) {
				errMessage(pClientIP, pClientPort, "Du bist nicht am Zug!");
				return;
			}
			else
			{
				String[] stringar = pMessage.split(CommonProtocoll.SEPERATOR);
				switch (stringar[0]) {
				case ClientProtocoll.FELD_ADD:
					if (MC.getStatus().equals(SpielStatus.Versorgung)) {
						if (stringar.length != 3) {
							errMessage(pClientIP, pClientPort, "add syntax");
						} else {
							MC.versorgungButtonClicked(stringar[1], stringar[2]);
							schickeFeld();
						}
					} else {
						errMessage(pClientIP, pClientPort, "Nicht in Versorgungsphase");
					}

					break;
				case ClientProtocoll.FELD_ATK:
					if (MC.getStatus().equals(SpielStatus.Angriff)) {
						if (stringar.length != 4) {
							errMessage(pClientIP, pClientPort, "atk syntax");
						} else {
							try {
								int anz = Integer.parseInt(stringar[3]);
								MC.angreifen(stringar[1], stringar[2], anz);
								schickeFeld();
							} catch (Exception e) {
								errMessage(pClientIP, pClientPort, "atk syntax: nicht numerisch");
								break;
							}

						}
					} else {
						errMessage(pClientIP, pClientPort, "Nicht in Angriffsphase");
					}
					break;
				case ClientProtocoll.FELD_MOVE:
					if (MC.getStatus().equals(SpielStatus.Truppenbewegung)) {
						if (stringar.length != 4) {
							errMessage(pClientIP, pClientPort, "move syntax");
						} else {
							try {
								int number = Integer.parseInt(stringar[3]);
								MC.Truppenbewegen(stringar[1], stringar[2], number);
								schickeFeld();

							} catch (Exception e) {
								errMessage(pClientIP, pClientPort, "move syntax: nicht numerisch");
								break;
							}
						}
					} else {
						errMessage(pClientIP, pClientPort, "Nicht in Bewegungsphase");
					}

					break;
				case ClientProtocoll.AG_ENDE:
					if (MC.getStatus().equals(SpielStatus.Angriff)) {
						MC.beendeAngriff();
						schickeFeld();
					} else {
						errMessage(pClientIP, pClientPort, "AngriffBeenden in falscher Phase");
					}
					break;
				case ClientProtocoll.TURN_ENDE:
					if (MC.getStatus().equals(SpielStatus.Truppenbewegung)) {
						MC.endTruppenbewegung();
						schickeFeld();
					} else {
						errMessage(pClientIP, pClientPort, "EndTurn in falscher Phase");
					}
					break;
					
				
				case ClientProtocoll.READY:
					if(MC.getStatus().equals(SpielStatus.nichtAngegeben)){
						if(!plyr.getReady()){
							plyr.ready();
							for(Spieler p: playerList){
								if(!p.getReady()){
									return;
								}
							}
							MC.beginn();
							schickeFeld();
							break;
						}
					}
				}
				if (!m_ErrorManager.getErrorManaged())
				{
					ArrayList<String> log = Logbuch.readLog();
					send(pClientIP,pClientPort,ServerProtocoll.ERR + ServerProtocoll.SEPERATOR + log.get(log.size()-1));
					m_ErrorManager.setErrorManaged(true);
				}
				
				return;
				
			}
		}
	}

	private void errMessage(String pClientIP, int pClientPort, String fehler) {
		send(pClientIP, pClientPort, CommonProtocoll.ERR + CommonProtocoll.SEPERATOR + fehler);
	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {
		for (Spieler sp : playerList) {
			if (sp.getIP() == pClientIP && sp.getPort() == pClientPort) {
				if (MC.getStatus() != SpielStatus.nichtAngegeben) {
					MC.end();
				}
				MC.removePlayer(sp);
				playerList.remove(sp);
				
			}
		}

	}

	private void schickeFeld() {
		for (Spieler s : playerList) {
			send(s.getIP(), s.getPort(), ServerProtocoll.INFO + ServerProtocoll.SEPERATOR + MC.gameInfoData());
		}

		// send(pClientIP, pClientPort, MC.gameInfoData());
		// Hier aktualisiertes Feld verschicken
	}
	
	public ManageClass getMC(){
		return MC;
	}
	
	public void SendError(Spieler s){
		ArrayList<String> l =Logbuch.readLog();
		if(l.size() == lineNumber + 1){
			send(s.getIP(), s.getPort(), ServerProtocoll.ERR+ServerProtocoll.SEPERATOR+l.get(lineNumber));
			lineNumber+=1;
		}
	}
}
