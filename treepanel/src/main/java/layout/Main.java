package layout;

import sample.TreeFactory;
import static layout.LayoutAlgorithm.Orientation.*;

public class Main {

	public static void main(String[] args) {
		TreeFactory factory = new TreeFactory();
		NodeClass apex = factory.sample1();
		
		LayoutAlgorithm alg = new LayoutAlgorithm();
		
		alg.RootOrientation = WEST;
		alg.POSITIONTREE(apex, 600, 450);
		
		apex.postOrderOut();
		
	}

}
