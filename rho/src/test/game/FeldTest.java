package test.game;

import java.util.ArrayList;
import java.util.Arrays;

import game.Feld;

public class FeldTest {

	public static void main(String[] args) {
		Feld feld = new Feld("x");
		assert feld != null : "feld ist null";
		
		feld.addUnit("Soldat", 2);
		assert feld.getEinheiten().size() == 1;
		
		feld.setZg(1);
		assert feld.getZg() == 1;
		
		
		String feldData = feld.toData();
		assert feldData.contains("einheiten") 
				&& feldData.contains("zg");
		
		assert Feld.fromData(feldData).equals(feld);
		
		
		Feld feld2 = new Feld("y");
		feld.setZg(2);
		assert Feld.getFelderFromTeam((ArrayList<Feld>) Arrays.asList(feld, feld2), 1).size() == 1;
		
		System.out.println("FeldTest: Done");
		
		
	}				
	
}
