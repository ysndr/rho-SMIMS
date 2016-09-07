package test.game;

import com.google.gson.Gson;

import game.FeldBuilder;
import game.GameInfo;

public class GameInfoSerialTest {
	
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		GameInfo gi = new GameInfo();
		FeldBuilder fb = new FeldBuilder();
		fb.readEdges("src/data/GraphbauerAnleitung");
		
		
		gi.setM_SpielPlan(fb.getGraph());
		
		
		String json = gi.toData();
		
		System.out.println(json);
		
		assert json != null;
		assert json.contains("m_Runde");
		assert json.contains("m_Spieler");
		assert !json.contains("m_SpielPlan");
		
		GameInfo gi2 = gson.fromJson(json, GameInfo.class);
		
		
		assert gi2.getM_Gebiete().size() == gi.getM_Gebiete().size();
		assert gi2.getM_SpielPlan().isEmpty();
		
	}
	
	
	
	
	
}
