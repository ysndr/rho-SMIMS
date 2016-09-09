package game.client.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import deps.*;
import game.Feld;
import game.GameInfo;
import game.Gebiet;
import game.Player;
import game.SpielStatus;
import game.client.ClientHandler;

@SuppressWarnings("serial")
public class Spielbrett extends JPanel {

	private boolean isPainted;

	private ArrayList<Feld> felder;
	private List<Edge> kanten;

	private Frame frame;
	private ArrayList<FeldButton> buttons;

	private JButton bs_angriffBeenden;
	private JLabel l_status;

	private static String[] Colors = new String[] { "f44336" /* Rot */, "2196f3" /* Blau */,
			"8bc34a" /* Gr�n */, "ffc107" /* Gelb */, "9c27b0" /* Lila */, "3f51b5" /* Indigo */, "e91e63" /* Pink */,
			"ff9800" /* Orange */
	};

	private Graphics graphics;

	// private ArrayList<Gebiet> gebiete;
	// private ArrayList<Player> players;

	private final int SIDE = 30;

	private Image m_BackgroundImage;

	public Spielbrett(Frame pFrame) {
		super();

		this.setLayout(null);

		buttons = new ArrayList<>();
		isPainted = false;

		frame = pFrame;

		// Angriff Beenden Button

	}
	/*
	 * public void knotenEinfuegen() {
	 * 
	 * int anzButtons = felder.size(); buttons = new ArrayList<FeldButton>();
	 * 
	 * 
	 * 
	 * for (int i = 0; i < anzButtons; i++) {
	 * 
	 * Feld actFeld = felder.get(i);
	 * 
	 * // neuer Button wird erstellt FeldButton button = new
	 * FeldButton(actFeld.getID()); button.setBounds((int) (actFeld.getX() *
	 * resize), (int) (actFeld.getY() * resize), (int) (this.SIDE * resize),
	 * (int) (this.SIDE * resize)); buttons.add(button);
	 * 
	 * 
	 * 
	 * // Gebiete werden anhand der farbigen Rändern der Buttons // verdeutlicht
	 * //gebietePaint(actFeld, i);
	 * 
	 * // Buttons werden in der Farbe des jeweiligen Spielers markiert
	 * //teamPaint(i);
	 * 
	 * // in die Buttons wird die Anzahl der dort stationierten Einheiten //
	 * geschrieben //buttonWrite(actFeld.getEinheiten().size(), i);
	 * 
	 * // ActionListener wird ergaenzt
	 * //buttons.get(i).addActionListener(frame);
	 * 
	 * // Button wird hinzugefuegt/dergestellt this.add(buttons.get(i)); }
	 * 
	 * }
	 */

	public void kantenEinfuegen(Graphics g, List<Edge> kanten, ArrayList<Feld> felder) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawString("HEllo", 100, 100);

		int x1, x2, y1, y2;
		x1 = x2 = y1 = y2 = 0;

		kanten.toFirst();
		while (kanten.hasAccess()) {
			// try {
			// Thread.sleep(1);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			Vertex[] vertices = kanten.getContent().getVertices();

			Feld actFeld = Feld.searchFeld(felder, vertices[0].getID());
			if (actFeld != null) {
				x1 = (int) ((actFeld.getX() + this.SIDE / 2));
				y1 = (int) ((actFeld.getY() + this.SIDE / 2));
			}

			actFeld = Feld.searchFeld(felder, vertices[1].getID());
			if (actFeld != null) {
				x2 = (int) ((actFeld.getX() + this.SIDE / 2));
				y2 = (int) ((actFeld.getY() + this.SIDE / 2));
			}

			frame.getWidth();
			int abstand1 = Math.min(x1, frame.getWidth() - x1);
			int abstand2 = Math.min(x2, frame.getWidth() - x2);
			if ((x2 - x1) + (y2 - y1) < abstand1 + abstand2) {

				g2d.drawLine(x1, y1, x2, y2);

			} else {
				int l1x;
				int l2x;
				int y;
				if (x1 > x2) {
					l1x = frame.getWidth();
					l2x = 0;

				} else {
					l1x = 0;
					l2x = frame.getWidth();
				}
				if (y1 > y2) {
					y = y1 - ((y1 - y2) / 2);
				} else {
					y = y1 + ((y2 - y1) / 2);
				}
				g2d.drawLine(x1, y1, l1x, y);
				g2d.drawLine(x2, y2, l2x, y);

			}
			kanten.next();
		}
	}

	public void drawLines(Graphics graphic, List<Edge> kanten, ArrayList<Feld> felder) {
		Graphics2D graphic2d = (Graphics2D) graphic;
		int width = this.getWidth();
		int feld1XCoord = 0;
		int feld1YCoord = 0;
		int feld2XCoord = 0;
		int feld2YCoord = 0;
		ArrayList<Edge> edges = parseNRWList(kanten);
		for (Edge edge : edges) {
			Vertex[] vertices = edge.getVertices();
			String feld1ID = vertices[0].getID();
			String feld2ID = vertices[1].getID();
			Feld feld1 = Feld.searchFeld(felder, feld1ID);
			Feld feld2 = Feld.searchFeld(felder, feld2ID);
			if (feld1 != null) {
				feld1XCoord = feld1.getX() + 15;
				feld1YCoord = feld1.getY() + 15;
			}
			if (feld2 != null) {
				feld2XCoord = feld2.getX() + 15;
				feld2YCoord = feld2.getY() + 15;
			}

			int distance1 = Math.min(feld1XCoord, width - feld1XCoord);
			int distance2 = Math.min(feld2XCoord, width - feld2XCoord);

			if ((feld2XCoord + feld2YCoord - feld1XCoord - feld2YCoord) < (distance1 + distance2)) {
				graphic2d.drawLine(feld1XCoord, feld1YCoord, feld2XCoord, feld2YCoord);
			} else {
				int endLineFeld1BorderX = 0;
				int endLineFeld2BorderX = 0;
				int endLineFeldBorderY = feld1XCoord + (feld2YCoord - feld2YCoord) / 2;
				if (feld1XCoord > feld2XCoord) {
					endLineFeld1BorderX = width;
				} else {
					endLineFeld2BorderX = width;
				}

				graphic2d.drawLine(feld1XCoord, feld1YCoord, endLineFeld1BorderX, endLineFeldBorderY);
				graphic2d.drawLine(feld2XCoord, feld2YCoord, endLineFeld2BorderX, endLineFeldBorderY);
			}
		}
	}

	public <E> ArrayList<E> parseNRWList(List<E> pList) {
		ArrayList<E> result = new ArrayList<E>();
		pList.toFirst();
		while (pList.hasAccess()) {
			E content = pList.getContent();
			result.add(content);
			pList.next();
		}
		return result;
	}

	public ClientHandler.Callback getOnUpdate() {
		JPanel panel = this;
		return new ClientHandler.Callback() {

			@Override
			public void update(GameInfo info, Graph map, int sID) {

				felder = info.getM_Felder();
				ArrayList<Gebiet> gebiete = info.getM_Gebiete();
				ArrayList<Player> players = info.getM_Spieler();
				kanten = map.getEdges();
				int playerOnTurn = info.getM_TeamsTurn();
				SpielStatus status = info.getM_SpielStatus();

				ButtonUtils buttonUtils = new ButtonUtils(buttons, felder, gebiete, players);

				if (!isPainted) {
					// beenden Button
					bs_angriffBeenden = new JButton();
					bs_angriffBeenden.setBounds(800, 550, 100, 20);
					bs_angriffBeenden.setText("beenden");
					bs_angriffBeenden.addActionListener(frame);
					add(bs_angriffBeenden);

					// status Label
					l_status = new JLabel();
					l_status.setBounds(265, 550, 300, 20);
					add(l_status);

					JLabel l_background = new JLabel();
					l_background.setBounds(0, 0, 950, 615);
					l_background.setIcon(new ImageIcon("data/Weltkarte/Background.png"));
					add(l_background);

					setComponentZOrder(l_background, getComponentCount() - 1);
					// Knoten hinzufuegen
					buttonUtils.addButtons(panel, frame);
					buttonUtils.updateGebiete();

					Player pl = null;
					for (Player player : info.getM_Spieler()) {
						if (player.getTeam() == sID) {
							pl = player;
						}

						// static String[] Colors = new String[] {
						// "f44336" /*Rot*/,
						// "2196f3" /*Blau*/,
						// "8bc34a" /*Gr�n*/,
						// "ffc107" /*Gelb*/,
						// "9c27b0" /*Lila*/,
						// "3f51b5" /*Indigo*/,
						// "e91e63" /*Pink*/,
						// "ff9800" /*Orange*/

						String farbe = null;
						if (pl != null) {
							switch (pl.getColor()) {
							case "f44336":
								farbe = "rot";
								break;
							case "2196f3":
								farbe = "blau";
								break;
							case "8bc34a":
								farbe = "gruen";
								break;
							case "ffc107":
								farbe = "gelb";
								break;
							case "9c27b0":
								farbe = "lila";
								break;
							case "3f51b5":
								farbe = "indigo";
								break;
							case "e91e63":
								farbe = "pink";
								break;
							case "ff9800":
								farbe = "orange";
								break;
							default:
								farbe = "Fehler";
							}
						}

						l_status.setText("Du bist Spieler: " + farbe);
					}
					// Kanten hinzufuegen

					// Label veraendern
					statusWrite(GuiMeldungen.BEGINN);
					// Hintergrund
					// setBackground();

					isPainted = false;

				}
				if (info != null) {
					buttonUtils.updatePlayer();

				}
				repaint();

			}

		};
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (kanten != null && felder != null) {
			// drawBackground(g);
			drawLines(g, kanten, felder);
		} else {
			System.out.println("Da war was null");
		}

	}

	private void drawBackground(Graphics g) {
		try {
			if (m_BackgroundImage == null) {
				m_BackgroundImage = ImageIO.read(new File("data/Weltkarte/Background.png"));
			}
			g.drawImage(m_BackgroundImage, 0, 0, 950 - 10, 600 - 2, null);
		} catch (Exception e) {
		}
	}

	// public void enableAlleButtons(boolean enabling) {
	// for (int i = 0; i < buttons.size(); i++) {
	// buttons.get(i).setEnabled(enabling);
	// }
	// }
	//
	// public Feld getFeld(String pID) {
	// for (Feld actFeld : felder) {
	// if (actFeld.getID().equals(pID))
	// return actFeld;
	// }
	// return null;
	// }
	//
	// public void enableButtons(ArrayList<Feld> pFelder, boolean enabling) {
	// for (Feld f : pFelder) {
	// FeldButton.getButton(buttons, f.getID()).setEnabled(enabling);
	// }
	// }
	//
	// public ArrayList<Feld> getFelder() {
	// return felder;
	// }
	//
	// private void gebietePaint(Feld actFeld, int index) {
	// Gebiet actGebiet = null;
	// for (Gebiet g : gebiete) {
	// if (actGebiet == null) {
	// for (String id : g.getFelderIds()) {
	// if (id.equals(actFeld.getID())) {
	// actGebiet = g;
	// break;
	// }
	// }
	// }
	// }
	//
	// if (actGebiet != null) {
	// int int_color = Integer.parseInt(actGebiet.getColor(), 16);
	// Color farbe = new Color(int_color);
	// buttons.get(index).setBorder(new LineBorder(farbe, (int) (3 * resize)));
	// } else
	// System.out.println("Error: nicht alle Felder sind einem Gebiet
	// zugeordnet!");
	// }
	//
	// private void teamPaint(int index) {
	// Player actPlayer = null;
	// if (players != null) {
	// actPlayer = Player.getPlayer(players, felder.get(index).getZg());
	// if (actPlayer != null) {
	// int int_color = Integer.parseInt(actPlayer.getColor(), 16);
	// Color farbe = new Color(int_color);
	// buttons.get(index).setBackground(farbe);
	// }
	// }
	// }
	//
	// private void buttonWrite(int size, int index) {
	// buttons.get(index).setText("" + size);
	// }

	private void statusWrite(String str) {
		System.out.println(str);
	}

}
