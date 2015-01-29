package application2;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String label;
	private List<Node> children = new ArrayList<>();

	public Node(String label) {
		super();
		this.label = label;
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
