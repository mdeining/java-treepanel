package trees.layout;

import trees.acessing.NodeWrapper;
import trees.panel.style.Style;

public class Root extends Node {
	
	public Root(NodeWrapper data) {
		super(data);
	}
	
	public void printPostOrder(){
		this.printPostOrder(this);
	}

	private void printPostOrder(Node node){
		if(node == null)
			return;
		for(Node child : node)
			printPostOrder(child);
		System.out.println(node.toString());
	}
	
	public int getXOffset(Style style, int panelWidth){
		int xOffset;
		switch(style.getHorizontalAlignment()){
			case LEFT:			xOffset = 0; break;
			case ROOT_CENTER:	xOffset = -this.getX() + (panelWidth - this.getWidth(style)) / 2; break;
			case TREE_CENTER:	xOffset = (panelWidth - this.getDrawingWidth(style)) / 2; break;
			case RIGHT:			xOffset = panelWidth - this.getDrawingWidth(style) - 1; break;
			default:			xOffset = 0;
		}
		return xOffset;
	}

	public int getYOffset(Style style, int panelHeight){
		int yOffset;		
		switch(style.getVerticalAlignment()){
			case TOP:			yOffset = 0; break;
			case ROOT_CENTER:	yOffset = -this.getY() + (panelHeight - this.getHeight(style)) / 2; break;
			case TREE_CENTER:	yOffset = (panelHeight - this.getDrawingHeight(style)) / 2; break;
			case BOTTOM:		yOffset = panelHeight - this.getDrawingHeight(style) - 1; break;
			default:			yOffset = 0;
		}
		return yOffset;
	}
}
