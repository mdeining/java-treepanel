package basic;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String label;
	private List<Node> children = new ArrayList<>();

	public Node(String label, Node ... nodes) {
		this.label = label;
		this.add(nodes);
	}
	
	public void add(Node ... nodes){
		for(Node node : nodes)
			children.add(node);
	}

	@Override
	public String toString() {
		return label;
	}
}
