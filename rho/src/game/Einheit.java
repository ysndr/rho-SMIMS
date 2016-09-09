package game;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * @author lukas
 *	Die Einheit, stellt die Soldaten auf einem Feld mit deren Anzahl und Typen dar.
 */
public class Einheit{

	@Expose private int anzahl; //Die Anzahl an Soldaten
	@Expose private String typ; //Der Typ (standartmae�ig "Soldat")
    
    
    public Einheit(String pTyp){
    	typ = pTyp;
    }
    
    public Einheit(String pTyp, int n){
    	typ = pTyp;
     	anzahl = n;
    }
    
    public int getAnzahl(){
    	return anzahl;
    }
    
    public void setAnzahl(int n){
     	anzahl = n;
    }
    
    public String getTyp(){
    	return typ;
    }
    
    public String toData() {
		Gson gson = new Gson();
		return gson.toJson(this);	
	}
	
	public static Einheit fromData(String data){
		Gson gson = new Gson();
		return gson.fromJson(data, Einheit.class);		
	}
    
}