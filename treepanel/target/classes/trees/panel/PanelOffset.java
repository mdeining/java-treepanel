package trees.panel;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import trees.layout.Node;
import trees.panel.style.Style;

/**
 * Helper class for calculating and storing the offset of the displayed tree.
 * The offset is calculated with respect to the panel's current orientation
 * and alignment.
 * 
 * @author Marcus Deininger
 *
 */
@SuppressWarnings("serial")
public class PanelOffset<T> extends Dimension{
	
	private TreePanel<T> treePanel;
	
	/**
	 * Initializes the offset with the referring panel.
	 * @param treePanel - The panel for which the offset should be calculated.
	 */
	protected PanelOffset(TreePanel<T> treePanel) {
		this.treePanel = treePanel;
		this.width = 0;
		this.height = 0;
	}

	/**
	 * Calculated the current offset with respect to the panel's 
	 * current orientation and alignment.
	 */
	protected void set(){
		Style style = treePanel.getStyle();
		Node root = treePanel.getRoot();

		if(root == null && !style.hasRootPointer())
			return;
		
		int margin = Style.TREE_MARGIN;
		int rootArrow = 0, rootWidth = 0, rootHeight = 0, rootAscent = 0;		
		if(style.hasRootPointer()){
			FontMetrics fm = style.getFontMetrics();
			rootArrow =  Style.ROOT_ARROW_LENGTH;
			rootWidth = fm.stringWidth(style.getRootLabel());
			rootHeight = fm.getHeight();
			rootAscent = fm.getAscent();
		}

		Rectangle rootArea, treeArea;
		if(root != null){
			rootArea = root.getNodeArea();
			treeArea = root.getTreeArea(style);
		}else{
			rootArea = new Rectangle();
			treeArea = new Rectangle(0, 0, rootWidth, rootHeight);
		}
		
		int top = 0, bottom = 0, left = 0, right = 0;
		switch(style.getOrientation()){
			case NORTH: // Root is at the top
				top = rootArrow + rootAscent; break;
			case SOUTH: // Root is at the bottom
				bottom = rootArrow + rootAscent; break;
			case EAST: // Root is at the left
				left = rootWidth + rootArrow; break;
			case WEST: // Root is at the right	
				right = rootWidth + rootArrow; break;
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
