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
		fb.readData("txt/button-koordinaten.txt");
		Graphbauer gb = new Graphbauer(fb.getFelderPool());
		try {
			gb.fileToGraph("txt/graphenbauanleitung.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graph erwuenschterGraph = gb.getGraph();
		List<Vertex> vertices = erwuenschterGraph.getVertices();
		vertices.toFirst();
		while (vertices.hasAccess()){
			Vertex content = vertices.getContent();
			System.out.print(content.getID()+": ");
			if(content instanceof Feld){
				Feld actFeld = (Feld) content;
				if(actFeld.getX()==0 && actFeld.getY()==0)
				System.out.print(actFeld.getX()+", "+actFeld.getY());
			}
			vertices.next();
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		new FeldBuilderTest();
	}
	
}
