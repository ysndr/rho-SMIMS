package game;
import java.util.Random;

public class Kampfklasse {	
	//return 0 = Verteidiger hat gewonnen | return 1 = Angreifer hat gewonnen
	public static int sieger(int angreifer, int verteidiger) {
		int[] angreiferwuerfel = new int[angreifer];
		int[] verteidigerwuerfel = new int[verteidiger];
		Random wuerfel = new Random();
		
		for(int i = 0; i < angreifer; i++)
			angreiferwuerfel[i] = wuerfel.nextInt(5)+1;
		
		for(int i = 0; i < verteidiger; i++)
			verteidigerwuerfel[i] = wuerfel.nextInt(5)+1;
		
		int topangriff, topverteidigung;
		topangriff = angreiferwuerfel[0];
		topverteidigung = verteidigerwuerfel[0];
		
		for(int j = 1; j < angreiferwuerfel.length; j++)			
			if(topangriff < angreiferwuerfel[j])
				topangriff = angreiferwuerfel[j];
		
		for(int j = 1; j < verteidigerwuerfel.length; j++)			
			if(topverteidigung < verteidigerwuerfel[j])				
				topverteidigung = verteidigerwuerfel[j];
		
		if(topverteidigung >= topangriff)
		{
			return 0;
		}
		else
		{
			return 1;
		}

	}	
}
