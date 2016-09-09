package game.client.graphic;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import game.Feld;
import game.Gebiet;
import game.Player;
import game.SpielStatus;

public class ButtonUtils {
	private ArrayList<FeldButton> buttons;
	private ArrayList<Feld> felder;
	private ArrayList<Gebiet> gebiete;
	private ArrayList<Player> players;

	public ButtonUtils(ArrayList<FeldButton> buttons, ArrayList<Feld> felder, ArrayList<Gebiet> gebiete,
			ArrayList<Player> players) {
		super();
		this.buttons = buttons;
		this.felder = felder;
		this.gebiete = gebiete;
		this.players = players;

	}

	
	public void addButtons(JPanel panel, ActionListener listener) {
		for (Feld feld : felder) {
			FeldButton button = new FeldButton(feld.getID());
			button.setBounds(feld.getX(), feld.getY(), 30, 30);
			button.addActionListener(listener);
			buttons.add(button);
			panel.add(button);
			
			panel.setComponentZOrder(button, 0);
		}
	}
	
	public void updatePlayer() {
		String id;
		int truppenZahl;
		for (FeldButton feldButton : buttons) {

			for (Player player : players) {
				if (Feld.searchFeld(felder,feldButton.getFeld_id()).getZg()==(player.getTeam())) {
					try {
						String colorST = player.getColor();
						int color = Integer.parseInt(colorST, 16);
						feldButton.setBackground(new Color(color));
						break;
					} catch (Exception e) {
						System.out.println("Fehler beim Färben der Spielfeldbuttons.");
					}
				}
			}

			id = feldButton.getFeld_id();
			for (Feld feld : felder) {
				if (feld.getID().equals(id)) {
					truppenZahl = feld.getEinheiten().get(0).getAnzahl();
					feldButton.setText(String.valueOf(truppenZahl));
				}
			}
		}
	}

	public void updateGebiete() {
		for (FeldButton button : buttons) {
			for (Gebiet gebiet : gebiete) {
				if (gebiet.getFelderIds().contains(button.getFeld_id())) {

					Color color = new Color(Integer.parseInt(gebiet.getColor(), 16));
					int size = 3;

					Border border = new LineBorder(color, size);

					button.setBorder(border);
					break;
				}
			}

		}

	}

	public void updateEnabled(SpielStatus status, int PlayersTurnID, int myPlayerID) {
		if (myPlayerID == PlayersTurnID) {
			switch (status) {
			case Angriff:
			case Truppenbewegung:
			case Versorgung:
				enableButtons(buttons, true);
				break;
			default:
				enableButtons(buttons, false);
				break;
			}
		} else {
			enableButtons(buttons, false);
		}
	}

	private void enableButtons(ArrayList<FeldButton> managedButtons, boolean enable) {
		for (FeldButton feldbutton : managedButtons) {
			feldbutton.setEnabled(enable);
		}
	}

}
