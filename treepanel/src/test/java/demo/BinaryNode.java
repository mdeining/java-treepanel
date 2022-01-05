package demo;

import trees.annotations.Nodes;

public class BinaryNode extends Node {
	
	@Nodes
	private Node left, right;

	public BinaryNode(String value) {
		super(value);
	}

	public BinaryNode(String label, Node left, Node right) {
		super(label);
		this.left = left;
		this.right = right;
	}

	@Override
	public void add(Node... nodes) {
		if(left == null){
			if(nodes.length >= 1)
				this.left = nodes[0];
			if(right == null && nodes.length >= 2)
				this.right = nodes[1];
		}else{ // left != null
			if(right == null && nodes.length >= 1)
				this.right = nodes[0];
		}
	}
	
	public boolean delete(Node node) {
		if(left == node){
			left = null;
			return true;
		}		
		if(right == node){
			right = null;
			return true;
		}
		
		if(left != null)
			if(left.delete(node))
				return true;
		
		if(right != null)
			if(right.delete(node))
				return true;
		
		return false;
	}
}
