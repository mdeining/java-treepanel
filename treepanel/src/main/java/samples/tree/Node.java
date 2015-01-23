package samples.tree;

import trees.acessing.Nodes;
import trees.acessing.Label;

public class Node {
	
	private String data;
	
	@Nodes
	private Node left, right;
		
	protected Node(String data) {
		super();
		this.data = data;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public boolean add(String data){
		int comp = this.data.compareTo(data);
		if(comp == 0)
			return false;
		if(comp > 0)
			if(left != null)
				return left.add(data);
			else{
				left = new Node(data);
				return true;
			}
		else
			if(right != null)
				return right.add(data);
			else{
				right = new Node(data);
				return true;
			}
	}

	@Label
	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return data;
	}
	
	

}
