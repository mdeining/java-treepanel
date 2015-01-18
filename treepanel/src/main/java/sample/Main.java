package sample;

import layout.Root;

public class Main {

	public static void main(String[] args) {
		TreeFactory factory = new TreeFactory();
		Root root = factory.sample1();
		
		root.printPostOrder();
		
		System.out.println(root.getDrawingWidth());
		System.out.println(root.getDrawingHeight());
		
	}

}
