package trees.building;

import java.util.HashSet;
import java.util.Set;

import trees.building.ModelNodeProcessor.Value;
import trees.layout.Node;
import trees.layout.ModelData;
import trees.panel.style.Style;

public class TreeBuilder {
	
	private Set<Object> processed = new HashSet<>();
	
	public Node build(Object obj, Style style){
		processed.clear(); // clear up from previous run
		if(obj == null)
			return null;
		
		ModelNodeProcessor processor = new ModelNodeProcessor(obj);		
		Class<?> cls = processor.getType();
		String label = processor.getLabel();
				
		ModelData model = ModelData.newElement(obj, cls, label, style);
		processed.add(obj);
		
		Node root = new Node(model, style);
		this.build(root, processor, style);
		processed.clear(); // clear up for next run
		return root;
	}

	private void build(Node node, ModelNodeProcessor processor, Style style){
		for(Value value : processor.getChildren())
			if(value.obj == null && !usePlaceHolder(value, style))
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
