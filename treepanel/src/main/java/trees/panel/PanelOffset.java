package trees.panel;

import java.awt.Rectangle;

import trees.layout.Root;
import trees.panel.style.Style;

public class PanelOffset<T> {
	
	private TreePanel<T> treePanel;
	private int x, y;
	
	protected PanelOffset(TreePanel<T> treePanel) {
		this.treePanel = treePanel;
		this.x = 0;
		this.y = 0;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	protected void update(){
		Style style = treePanel.getStyle();
		Root root = treePanel.getRoot();
		Rectangle area = root.getTreeArea(style);
		
		int panelWidth = treePanel.getWidth();
		int panelHeight = treePanel.getHeight();

		switch(style.getHorizontalAlignment()){
			case LEFT:			x = 0; break;
			case ROOT_CENTER:	x =  + (panelWidth - root.getWidth(style)) / 2 - root.getX(); break;
			case TREE_CENTER:	x = (panelWidth - area.width) / 2; break;
			case RIGHT:			x = panelWidth - area.width - 1; break;
			default:			x = 0;
		}

		switch(style.getVerticalAlignment()){
			case TOP:			y = 0; break;
			case ROOT_CENTER:	y = (panelHeight - root.getHeight(style)) / 2 -root.getY(); break;
			case TREE_CENTER:	y = (panelHeight - area.height) / 2; break;
			case BOTTOM:		y = panelHeight - area.height - 1; break;
			default:			y = 0;
		}
	}


}
