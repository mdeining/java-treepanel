package application.model;

import trees.acessing.TreeWrapper;
import trees.layout.Root;

public class TestWrapping {

	public static void main(String[] args) {
		application.model.Sample s = new application.model.Sample();
		application.model.Root r = s.sample();
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
