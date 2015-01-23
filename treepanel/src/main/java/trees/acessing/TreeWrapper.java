package trees.acessing;

import trees.layout.Child;
import trees.layout.Node;
import trees.layout.PlaceHolder;
import trees.layout.Root;

public class TreeWrapper {
	
	public Root wrap(Object object){
		if(object == null)
			return null;
		
		NodeWrapper wrappedRoot = new NodeWrapper(object);		
		Root root = new Root(wrappedRoot);
		
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
				NodeWrapper wrappedChild = new NodeWrapper(object);
				Node child = new Child(wrappedChild);
				node.add(child);
				wrap(child, wrappedChild);
			}		
	}

}
