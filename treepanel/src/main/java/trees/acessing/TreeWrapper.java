package trees.acessing;

import trees.layout.Child;
import trees.layout.Node;
import trees.layout.PlaceHolder;
import trees.layout.Root;

public class TreeWrapper {
	
	public Root wrap(Object object){
		if(object == null)
			return null;
		
		WrappedNode wrappedRoot = new WrappedNode(object);		
		Root root = new Root(wrappedRoot);
		
		wrap(root, wrappedRoot);
		
		return root;
	}

	private void wrap(Node node, WrappedNode wrappedNode){
		Class<?> descendantClass = wrappedNode.getFirstDescendant();
		boolean usePlaceHolders = (descendantClass != null);
		for(Object object : wrappedNode.getDescendants())
			if(object == null)
				if(usePlaceHolders)
					node.add(new PlaceHolder(descendantClass));
				else
					node.add((Node)null);
			else{
				WrappedNode wrappedChild = new WrappedNode(object);
				Node child = new Child(wrappedChild);
				node.add(child);
				wrap(child, wrappedChild);
			}		
	}

}
