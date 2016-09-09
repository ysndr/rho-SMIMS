package game.server.protocolls;

public class ServerProtocoll extends CommonProtocoll {
	
	public final static String TURN_START = "TS"; //TS::<id>::<map>
	public final static String TURN_MOVE = "TM";
	public final static String TURN_ATK = "TA";
	public final static String TURN_VERSORGUNG = "TV";
	public final static String TURN_END= "TE";

	public final static String INFO = "INFO"; //INFO::<info>
	public final static String PLYR_CON = "PLCON";//PLCON::<num player>
}