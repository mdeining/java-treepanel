package layout;

import sample.TreeFactory;

public class Main {

	public static void main(String[] args) {
		TreeFactory factory = new TreeFactory();
		NodeClass apex = factory.sample1();
		
		LayoutAlgorithm alg = new LayoutAlgorithm();
		
		alg.POSITIONTREE(apex);
		
		apex.postOrderOut();
		
	}

}
