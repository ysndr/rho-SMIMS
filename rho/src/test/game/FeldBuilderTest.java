package test.game;

import java.io.IOException;

import deps.Graph;
import deps.List;
import deps.Vertex;
import game.Feld;
import game.FeldBuilder;
import game.Graphbauer;

public class FeldBuilderTest {

	public FeldBuilderTest(){
		
		
		FeldBuilder fb = new FeldBuilder();
		fb.readFelder("txt/button-koordinaten.txt");
		fb.readEdges("txt/graphenbauanleitung.txt");
		
		Graph erwuenschterGraph = fb.getGraph();
		List<Vertex> vertices = erwuenschterGraph.getVertices();
		vertices.toFirst();
		while (vertices.hasAccess()){
			Vertex id = vertices.getContent();
			Feld content = fb.getIdFeldMap().get(id);
			
			System.out.print(content.getID()+": ");			
			vertices.next();
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		new FeldBuilderTest();
	}
	
}
