package game;
import java.util.Random;

public class Kampfklasse {
	
	private int angreifer;
	private int verteidiger;
	
	
	public Kampfklasse(int angreifer, int verteidiger) {

		this.angreifer = angreifer;
		this.verteidiger = verteidiger;
	}
	
	public String sieger() {
		String sieger = null;
		while(this.angreifer * this.verteidiger != 0 ){
			
			Random wuerfel = new Random();
			int wurf1 = wuerfel.nextInt(6);
			int wurf2 = wuerfel.nextInt(6);
			
			if(wurf1 > wurf2){
				this.verteidiger--;
			}
			
			else{
				this.angreifer--;
			}
				
		}
	
	if(this.angreifer==0){
		sieger  = "v";
	}
	if(this.verteidiger==0){
		sieger = "a";
	}
	return sieger;
	}	

}
