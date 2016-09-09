package game.client;

import javax.swing.JOptionPane;

public class NumberPicker implements NumberDeliverant {	
	@Override
	public Number getNumber(Number maxValue) {
		String[] possible = new String[(int) maxValue];
		for (int i = 0; i < possible.length; i++) { possible[i] = "" + (i+1); };
		String s = (String)JOptionPane.showInputDialog(
				null,
				"Anzahl Truppen",
				"Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				possible,
				"Wahle aus");
		int z = -1;
		try { z = Integer.parseInt(s); }
		catch (Exception e) {
			z = -1;			
		};
		return z;
	}

}
