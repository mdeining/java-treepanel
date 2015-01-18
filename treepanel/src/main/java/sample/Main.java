package sample;

import layout.LayoutAlgorithm;
import layout.Node;
import static layout.LayoutAlgorithm.Orientation.*;

public class Main {

	public static void main(String[] args) {
		TreeFactory factory = new TreeFactory();
		Node apex = factory.sample1();
		
		LayoutAlgorithm alg = new LayoutAlgorithm();
		
		alg.rootOrientation = WEST;
		alg.positionTree(apex);
		
		apex.postOrderOut();
		
	}

}
