package trees.building;

import java.util.HashSet;
import java.util.Set;

import trees.building.ModelNodeProcessor.Value;
import trees.layout.Node;
import trees.layout.ModelData;
import trees.panel.style.Style;

/**
 * Helper class for analyzing a given tree. Only to be used 
 * within the TreePanel.
 * 
 * @author Marcus Deininger
 *
 */
public class TreeBuilder {
	
	private Set<Object> processed = new HashSet<>();
	
	/**
	 * Builds a tree-like structure from a given object. This object is 
	 * considered the root of a tree which should contain recursively
	 * objects of the same type. <b> This method is intended to be used by
	 * the TreePanel and not for outside use.</b>
	 * 
	 * @param tree - The root of the tree structure to be displayed.
	 * @param style - The style of the tree to be displayed. 
	 * 			This is needed for calculating the size of the nodes.
	 * @return The tree representation for displaying.
	 */
	public Node build(Object tree, Style style){
		processed.clear(); // clear up from previous run
		if(tree == null)
			return null;
		
		ModelNodeProcessor processor = new ModelNodeProcessor(tree);		
		Class<?> cls = processor.getType();
		String label = processor.getLabel();
				
		ModelData model = ModelData.newElement(tree, cls, label, style);
		processed.add(tree);
		
		Node root = new Node(model, style);
		this.build(root, processor, style);
		processed.clear(); // clear up for next run
		return root;
	}

	private void build(Node node, ModelNodeProcessor processor, Style style){
		boolean hasNoChildren = !processor.hasChildren();
		for(Value value : processor.getChildren())
			if(hasNoChildren) // value must be null then
				node.add((Node)null);
			else if(value.obj == null && !usePlaceHolder(value, style))
				node.add((Node)null);
			else if(value.obj == null){ // usePlaceHolder
				ModelData model = ModelData.newPlaceHolder(value.cls, style);
				node.add(new Node(model, style));
			}else if(processed.contains(value.obj)){ // Self reference
				ModelNodeProcessor childProcessor = new ModelNodeProcessor(value.obj);
				ModelData model = ModelData.newDuplicate(value.obj, value.cls, childProcessor.getLabel(), style);
				Node childNode = new Node(model, style);
				node.add(childNode);
			}else{ // regular node
				ModelNodeProcessor childProcessor = new ModelNodeProcessor(value.obj);
				ModelData model = ModelData.newElement(value.obj, value.cls, childProcessor.getLabel(), style);
				Node childNode = new Node(model, style);
				node.add(childNode);
				this.build(childNode, childProcessor, style);
			}
	}
	
	private boolean usePlaceHolder(Value value, Style style){
		if(value.obj != null || value.cls == null)
			return false;
		
		return style.usesPlaceHolder(value.cls);
	}
}
