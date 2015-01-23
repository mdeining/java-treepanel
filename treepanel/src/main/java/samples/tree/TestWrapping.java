package samples.tree;

import trees.acessing.TreeWrapper;
import trees.layout.Root;

public class TestWrapping {

	public static void main(String[] args) {
		samples.tree.Sample s = new samples.tree.Sample();
		samples.tree.Root r = s.sample();
		System.out.println(r);
		
//		NodeWrapper wrapper1 = new NodeWrapper(r);
//		System.out.println(wrapper1);
//
//		NodeWrapper wrapper2 = new NodeWrapper(r.getLeft());
//		System.out.println(wrapper2);
//		
//		System.out.println(new NodeWrapper(r.getRight()));
		
		TreeWrapper wrapper = new TreeWrapper();
		Root root = wrapper.wrap(r);
		System.out.println(root);

	}

}
