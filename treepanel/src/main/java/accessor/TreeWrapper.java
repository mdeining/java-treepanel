package accessor;

import layout.Child;
import layout.Node;
import layout.PlaceHolder;
import layout.Root;

public class TreeWrapper {
	
	public Root wrap(Object object){
		if(object == null)
			return null;
		
		NodeWrapper wrappedRoot = new NodeWrapper(object);		
		Root root = new Root(object.toString());
		
		wrap(root, wrappedRoot);
		
		return root;
	}
	
	
	private void wrap(Node node, NodeWrapper wrappedNode){
		boolean usePlaceHolders = wrappedNode.hasDescendants();
		for(Object object : wrappedNode.getDescendants())
			if(object == null)
				if(usePlaceHolders)
					node.add(new PlaceHolder());
				else
					node.add((Node)null);
			else{
				Node child = new Child(object.toString());
				node.add(child);
				NodeWrapper wrappedChild = new NodeWrapper(object);
				wrap(child, wrappedChild);
			}		
	}

}
