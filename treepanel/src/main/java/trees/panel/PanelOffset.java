package trees.panel;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import trees.layout.Root;
import trees.panel.style.Style;

@SuppressWarnings("serial")
public class PanelOffset<T> extends Dimension{
	
	private TreePanel<T> treePanel;
	
	protected PanelOffset(TreePanel<T> treePanel) {
		this.treePanel = treePanel;
		this.width = 0;
		this.height = 0;
	}

	protected void set(){
		Style style = treePanel.getStyle();
		Root root = treePanel.getRoot();

		if(root == null && !style.hasRootPointer())
			return;
		
		FontMetrics fm = style.getFontMetrics();
		final int margin = Style.TREE_MARGIN;
		final int arrow =  Style.ROOT_ARROW_LENGTH;
		final int rootWidth = fm.stringWidth(Style.ROOT);
		final int ascent = fm.getAscent();
		
		Rectangle rootArea, treeArea;
		if(root != null){
			rootArea = root.getNodeArea(style);
			treeArea = root.getTreeArea(style);
		}else{
			rootArea = new Rectangle();
			treeArea = new Rectangle(0, 0, fm.stringWidth(Style.ROOT), fm.getHeight());
		}
		
		int top = 0, bottom = 0, left = 0, right = 0;
		if(style.hasRootPointer())
			switch(style.getOrientation()){
				case NORTH: // Root is at the top
					top = arrow + ascent; break;
				case SOUTH: // Root is at the bottom
					bottom = arrow + ascent; break;
				case EAST: // Root is at the left
					left = rootWidth + arrow; break;
				case WEST: // Root is at the right	
					right = rootWidth + arrow; break;
			}		
		
		int panelWidth = treePanel.getWidth();
		int panelHeight = treePanel.getHeight();

		switch(style.getHorizontalAlignment()){
			case LEFT:			width = margin + left; break;
			case ROOT_CENTER:	width = (panelWidth - rootArea.width) / 2 - rootArea.x; break;
			case TREE_CENTER:	width = (panelWidth - treeArea.width) / 2; break;
			case RIGHT:			width = panelWidth - treeArea.width - right - margin; break;
			default:			width = 0;
		}

		switch(style.getVerticalAlignment()){
			case TOP:			height = top + margin; break;
			case ROOT_CENTER:	height = (panelHeight - rootArea.height) / 2 - rootArea.y; break;
			case TREE_CENTER:	height = (panelHeight - treeArea.height) / 2; break;
			case BOTTOM:		height = panelHeight - treeArea.height - bottom - margin; break;
			default:			height = 0;
		}
	}
}
