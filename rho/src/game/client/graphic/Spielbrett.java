package game.client.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import deps.*;
import game.Feld;
import game.Gebiet;
import game.Player;

@SuppressWarnings("serial")
public class Spielbrett extends JPanel {

	ArrayList<Feld> felder;
	List<Edge> kanten;

	private Frame frame;
	private ArrayList<FeldButton> buttons;
	private ArrayList<Gebiet> gebiete;
	private ArrayList<Player> players;

	private final int SIDE = 30;

	private double resize; // Faktor zur Anpassung and die Darstellung

	public Spielbrett(List<Edge> pKanten, ArrayList<Feld> pFelder, ArrayList<Gebiet> pGebiete,
			ArrayList<Player> pPlayers, Frame pFrame) {
		super();
		this.setLayout(null);
		kanten = pKanten;
		felder = pFelder;
		gebiete = pGebiete;
		players = pPlayers;
		frame = pFrame;
		resize = frame.getWidth() / 950.0;
		System.out.println(resize);
	}

	public void knotenEinfuegen() {

		int anzButtons = felder.size();

		buttons = new ArrayList<FeldButton>();

		for (int i = 0; i < anzButtons; i++) {

			Feld actFeld = felder.get(i);

			buttons.add(new FeldButton(actFeld.getID()));

			buttons.get(i).setBounds((int) (actFeld.getX() * resize), (int) (actFeld.getY() * resize),
					(int) (this.SIDE * resize), (int) (this.SIDE * resize));

			// Gebiete werden anhand der farbigen R�ndern der Buttons
			// verdeutlicht
			Gebiet actGebiet = null;
			for (Gebiet g : gebiete) {
				if (actGebiet == null) {
					for (String id : g.getFelderIds()) {
						if (id.equals(actFeld.getID())) {
							actGebiet = g;
							break;
						}
					}
				}
			}

			if (actGebiet != null) {
				int int_color = Integer.parseInt(actGebiet.getColor(), 16);
				Color farbe = new Color(int_color);
				buttons.get(i).setBorder(new LineBorder(farbe, 5));
			} else
				System.out.println("Error: nicht alle Felder sind einem Gebiet zugeordnet!");

			// Felder werden in der Farbe des jeweiligen Spielers markiert

			Player actPlayer = null;
			if (players != null) {
				actPlayer = Player.getPlayer(players, felder.get(i).getZg());
				if (actPlayer != null) {
					int int_color = Integer.parseInt(actPlayer.getColor(), 16);
					Color farbe = new Color(int_color);
					buttons.get(i).setBackground(farbe);
				}
			}

			buttons.get(i).addActionListener(frame);
			// buttons.get(i).setText("" + actFeld.getEinheiten().size());
			buttons.get(i).setText(actFeld.getID());

			this.add(buttons.get(i));
		}

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		knotenEinfuegen();

		// Kanten hinzufuegen

		Graphics2D g2d = (Graphics2D) g;

		int x1, x2, y1, y2;
		x1 = x2 = y1 = y2 = 0;

		kanten.toFirst();
		while (kanten.hasAccess()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Vertex[] vertices = kanten.getContent().getVertices();

			Feld actFeld = getFeld(vertices[0].getID());
			if (actFeld != null) {
				x1 = (int) ((actFeld.getX() + this.SIDE / 2) * resize);
				y1 = (int) ((actFeld.getY() + this.SIDE / 2) * resize);
			}

			actFeld = getFeld(vertices[1].getID());
			if (actFeld != null) {
				x2 = (int) ((actFeld.getX() + this.SIDE / 2) * resize);
				y2 = (int) ((actFeld.getY() + this.SIDE / 2) * resize);
			}

			frame.getWidth();
			int abstand1 = Math.min(x1, frame.getWidth() - x1);
			int abstand2 = Math.min(x2, frame.getWidth() - x2);
			if ((x2 - x1) + (y2 - y1) < abstand1 + abstand2) {

				g2d.drawLine(x1, y1, x2, y2);

			}else{
				int l1x;
				int l2x;
				int y;
				if(x1>x2){
					l1x=frame.getWidth();
					l2x=0;
					
				}else{
					l1x=0;
					l2x=frame.getWidth();
				}
				if(y1>y2){
					y=y1-((y1-y2)/2);
				}else{
					y=y1+((y2-y1)/2);
				}
				g2d.drawLine(x1, y1, l1x, y);
				g2d.drawLine(x2, y2, l2x, y);
				
			}
			
			kanten.next();
		}
	}

	public void enableAlleButtons(boolean enabling) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setEnabled(enabling);
		}
	}

	/*
	 * public HashMap<String, Feld> felderMapping(ArrayList<Feld> felder) {
	 * hm_felder = new HashMap<String, Feld>(); for (int i = 0; i <
	 * felder.size(); i++) { hm_felder.put(felder.get(i).getID(),
	 * felder.get(i)); }
	 * 
	 * for(Feld actFeld : hm_felder){
	 * 
	 * }
	 * 
	 * hm_felder.get return hm_felder; }
	 */

	public void buttonsBackground() {
		for (int i = 0; i < buttons.size(); i++) {
			// buttons[i].setBackground(colors[felder.get(i).get]);
		}
	}

	public Feld getFeld(String pID) {
		for (Feld actFeld : felder) {
			if (actFeld.getID().equals(pID))
				return actFeld;
		}
		return null;
	}

	public void enableButtons(ArrayList<Feld> pFelder, boolean enabling) {
		for (Feld f : pFelder) {
			FeldButton.getButton(buttons, f.getID()).setEnabled(enabling);
		}
	}

	public ArrayList<Feld> getFelder() {
		return felder;
	}

}
