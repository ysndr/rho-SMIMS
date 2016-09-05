package test.game;

import game.FeldBuilder;

public class FeldBuilderTest {

	public FeldBuilderTest(){
		FeldBuilder fb = new FeldBuilder();
		fb.readData("txt/button-koordinaten.txt");
	}
	
	public static void main(String[] args){
		new FeldBuilderTest();
	}
	
}
