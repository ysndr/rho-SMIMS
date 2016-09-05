package jan;

import java.io.IOException;

import deps.Graph;

public class test {
	
	private Graph g;

	public test() {
		Graphbauer bauer = new Graphbauer();
		
		try {
			bauer.fileToGraph("/Users/jan/eclipse/workspace/rho/src/jan/GraphbauerAnleitung");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print(bauer.getGraphString());
		 g = bauer.getGraph();
		 
		 /*
		 
		 Vertex v1 = g.getVertex("m");
		 Vertex v2 = g.getVertex("O");
		 if (g.getEdge(v1, v2) != null) {
			 System.out.println (" Kante AC vorhanden");
		 }
		 
		 
		 /*
		 Kampfklasse k = new Kampfklasse(3,1);
		 System.out.println(k.ermittleSieger());
		*/
	}
	
	public static void main(String[] args) {
		test t = new test();

	}
}
