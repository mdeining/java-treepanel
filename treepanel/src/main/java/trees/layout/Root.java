package trees.layout;

import trees.acessing.WrappedNode;

public class Root extends Node {
	
	public Root(WrappedNode wrappedNode) {
		super(wrappedNode);
	}
	
	public void printPostOrder(){
		this.printPostOrder(this);
	}

	private void printPostOrder(Node node){
		if(node == null)
			return;
		for(Node child : node)
			printPostOrder(child);
		System.out.println(node.toString());
	}	
}
