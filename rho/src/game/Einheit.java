package game;

class Einheit{
    
	private int anzahl;
    private String typ;
    
    
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
}