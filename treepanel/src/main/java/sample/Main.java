package sample;

import layout.LayoutAlgorithm;
import layout.Node;

public class Main {

	public static void main(String[] args) {
		TreeFactory factory = new TreeFactory();
		Node apex = factory.sample1();
		
		LayoutAlgorithm algorithm = new LayoutAlgorithm();
		
		algorithm.positionTree(null, apex);
		
		apex.printPostOrder();
		
	}

}
