package application1.model;

import static trees.panel.style.Size.FIXED;
import trees.acessing.TreeWrapper;
import trees.layout.Root;
import trees.panel.style.Style;

public class TestWrapping {

	public static void main(String[] args) {
		application1.model.Sample s = new application1.model.Sample();
		application1.model.Root r = s.sample();
		System.out.println(r);
		
//		NodeWrapper wrapper1 = new NodeWrapper(r);
//		System.out.println(wrapper1);
//
//		NodeWrapper wrapper2 = new NodeWrapper(r.getLeft());
//		System.out.println(wrapper2);
//		
//		System.out.println(new NodeWrapper(r.getRight()));
		
		TreeWrapper wrapper = new TreeWrapper();
		Root root = wrapper.wrap(r, new Style(40, 40, 40, FIXED(20, 20)));
		System.out.println(root);

	}

}
