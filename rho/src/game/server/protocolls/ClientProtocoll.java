package game.server.protocolls;

public class ClientProtocoll extends CommonProtocoll {

	public final static String READY = "R";//R
	public final static String FELD_ADD = "ADD";//ADD::<feldID>::Soldat
	public final static String FELD_ATK = "ATK";//ATK::<feld1>::<feld2>::<anzahl>
	public final static String FELD_MOVE = "MV";//MV::<feld1>::<feld2>::<anzahl>
	public final static String TURN_ENDE = "TDONE";//TDONE
	public final static String AG_ENDE = "ADONE";//ADONE
	
}
