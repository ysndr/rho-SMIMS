package game;

public class Kampfklasse {
	private int aP;
	private int vP;
	
	public Kampfklasse(int angreiferP, int verteidigerP) {
		aP = angreiferP;
		vP = verteidigerP;
	}
	
	public String ermittleSieger() {
		if( (aP-vP) > 0) {
			return "v";
		} else {
			return "a";
		}
	
	}
	
	
	
}
