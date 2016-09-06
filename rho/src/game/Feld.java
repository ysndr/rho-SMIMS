package game;
import java.util.ArrayList;
import deps.Vertex;

public class Feld extends Vertex {
    private ArrayList<Einheit>  einheiten;
    private int zg; //zugehöhrigkeit
    
    private int x;
    private int y;

    
    public Feld(String pID){
    	super(pID);
    }
    
    public Feld(String pID, int pX, int pY){
    	super(pID);
    	x = pX;
    	y = pY;
    }
    
    public int getX(){
    	return x;
    }
    
    public int getY(){
    	return y;
    }
    
    public void setX(int pX){
    	if(x==0)x = pX;
    }
    
    public void setY(int pY){
    	if(y==0)y = pY;
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
    
    public static Feld searchFeld(ArrayList<Feld> felder, String id)
    {
    	for(Feld fl : felder)
    	{
    		if(fl.equals(id))
    		{
    			return fl;
    		}
    	}
    	return null;
    }
}
