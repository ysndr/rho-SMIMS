package game;
import java.util.ArrayList;

import com.google.gson.Gson;

import deps.Vertex;


public class Feld extends Vertex {
    private ArrayList<Einheit>  einheiten;
    private int zg; //zugeh�hrigkeit

    
    public Feld(String pID){
    	super(pID);
    	einheiten = new ArrayList<>();
    }
    
    public void addUnit(String typ, int n){
      	boolean added = false;
		
      	for(Einheit e: einheiten){
		    if(e.getTyp()==typ){
			e.setAnzahl(e.getAnzahl()+n);
			if (e.getAnzahl()<0){
	                    e.setAnzahl(0);
			}
			added = true;
		    }
	 	}
		if (!added){
		    einheiten.add(new Einheit(typ,n));
		}
	
    }
    public ArrayList<Einheit> getEinheiten(){
    	return einheiten;
    }
    public int getZg(){
    	return zg;
    }
    public void setZg(int pZg){
    	zg = pZg;
    }
    
    public static ArrayList<Feld> getFelderFromTeam(ArrayList<Feld> felder, int teamId)
    {
    	ArrayList<Feld> result = new ArrayList<Feld>();
    	for(Feld fl : felder)
			if (fl.getZg() == teamId)
				result.add(fl);
    	return result;
    }
    
    public String toData() {
		Gson gson = new Gson();
		return gson.toJson(this);	
	}
	
	public static Feld fromData(String data){
		Gson gson = new Gson();
		return gson.fromJson(data, Feld.class);		
	}
    
    
}
