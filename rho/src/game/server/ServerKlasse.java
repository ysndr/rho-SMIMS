package game.server;

import java.util.ArrayList;

import deps.Server;
import game.ManageClass;
import game.SpielStatus;
import game.server.protocolls.ClientProtocoll;
import game.server.protocolls.CommonProtocoll;

public class ServerKlasse extends Server {

	private ManageClass MC;
	private ArrayList<Spieler> playerList;
	int curTeam;

	public ServerKlasse(int PortNr) {
		super(PortNr);
		MC = new ManageClass();
		playerList = new ArrayList<Spieler>();
		curTeam = 0;
	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		if (MC.getStatus() == SpielStatus.nichtAngegeben) {
			playerList.add(new Spieler(curTeam, pClientIP, pClientPort));
			curTeam += 1;
			if (playerList.size() == 4) {
				MC.beginn();
				for (Spieler s : playerList) {
					send(s.getIP(), s.getPort(), MC.graphToString());

				}
			}
		} else {
			// sende error
		}
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		for (Spieler plyr : playerList) {

			if (plyr.getPort() != pClientPort) {
				return;
			}
			
			if (MC.getTeamsTurn() != plyr.getTeam()) {
				errMessage(pClientIP, pClientPort, "Du bist nicht am Zug!");
				return;
			}
				String[] stringar = pMessage.split(CommonProtocoll.SEPERATOR);
				switch (stringar[0]) {
				case ClientProtocoll.FELD_ADD:
					if (MC.getStatus().equals(SpielStatus.Versorgung)) {
						if (stringar.length != 3) {
							errMessage(pClientIP, pClientPort, "add syntax");
							break;
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
					} else {
						errMessage(pClientIP, pClientPort, "AngriffBeenden in falscher Phase");
					}
					break;
				case ClientProtocoll.TURN_ENDE:
					if (MC.getStatus().equals(SpielStatus.Truppenbewegung)) {
						MC.endTruppenbewegung();
					} else {
						errMessage(pClientIP, pClientPort, "EndTurn in falscher Phase");
					}
					break;

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
				playerList.remove(sp);
			}
		}

	}

	private void schickeFeld() {
		for (Spieler s : playerList) {
			send(s.getIP(), s.getPort(), MC.gameInfoData());
		}

		// send(pClientIP, pClientPort, MC.gameInfoData());
		// Hier aktualisiertes Feld verschicken
	}

}
