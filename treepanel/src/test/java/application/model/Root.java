package application.model;

import java.util.ArrayList;
import java.util.List;

public class Root extends Node {
	
	List<Node> list = null;

	public Root(String data) {
		super(data);
		
		list = new ArrayList<>();
		list.add(null);
		list.add(this);
		list.add(new Node("x"));
//		list.add("xxx");
		list.add(null);
	}

}
