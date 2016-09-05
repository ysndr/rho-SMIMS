package game;
import java.util.ArrayList;
import deps.Vertex;


public class Feld extends Vertex {
    private ArrayList<Einheit>  einheiten;
    private int zg; //zugeh�hrigkeit

    
    public Feld(String pID){
    	super(pID);
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
    
    public static ArrayList<Feld> getFelderFromTeam(ArrayList<Feld> Gebiete, int teamId)
    {
    	ArrayList<Feld> result = new ArrayList<Feld>();
    	for(Feld fl : Gebiete)
			if (fl.getZg() == teamId)
				result.add(fl);
    	return result;
    }
    
}
