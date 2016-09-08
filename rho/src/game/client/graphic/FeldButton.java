package game.client.graphic;

import javax.swing.JButton;

import game.Player;

import java.util.ArrayList;

public class FeldButton extends JButton {

	private String feld_id;

	public FeldButton(String id) {
		super();
		feld_id = id;
	}

	public String getFeld_id() {
		return feld_id;
	}

	public void setFeld_id(String feld_id) {
		this.feld_id = feld_id;
	}

	public static FeldButton getButton(ArrayList<FeldButton> buttons, String pID){
		for(FeldButton fButton : buttons)
		{
			if(fButton.getFeld_id() == pID)
				return fButton;
		}
		return null;
	}

}
