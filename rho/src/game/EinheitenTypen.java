package game;

public class EinheitenTypen{
    //returns [min,max] [0,0] invalid typr
    public static int[] typeinfo(String typ){
	 	int[] temp;
	    switch(typ){
		   
	    case "Soldat":
			   temp = new int[2]; temp[0] = 1; temp[1] = 6;
			   return temp;
		default:
			   temp = new int[2]; temp[0] = 0; temp[1] = 0;
			   return temp;
	    }
    }
    
    
}
