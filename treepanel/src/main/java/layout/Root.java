package layout;

public class Root extends Node {
	
	private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();
	private Settings settings = new Settings();
	
	public Root(String data) {
		super(data);
	}
	
	@Override
	protected void update(){
		layoutAlgorithm.positionTree(settings, this);
	}
	
	public void setMaxDepth(int maxDepth) {
		settings.setMaxDepth(maxDepth);
		this.update();
	}

	public int getMaxDepth() {
		return settings.getMaxDepth();
	}


	public void setSiblingSeparation(int siblingSeparation) {
		settings.setSiblingSeparation(siblingSeparation);
		this.update();
	}

	public void setSubtreeSeparation(int subtreeSeparation) {
		settings.setSubtreeSeparation(subtreeSeparation);
		this.update();
	}

	public void setLevelSepartion(int levelSepartion) {
		settings.setLevelSepartion(levelSepartion);
		this.update();
	}

	public Orientation getOrientation() {
		return settings.getRootOrientation();
	}

	public void setOrientation(Orientation orientation) {
		settings.setRootOrientation(orientation);
		this.update();
	}
	
	public boolean hasVerticalOrientation(){
		return settings.hasVerticalOrientation();
	}
	
	public boolean hasHorizontalOrientation(){
		return settings.hasHorizontalOrientation();
	}
	
	public Alignment getHorizontalAlignment() {
		return settings.getHorizontalAlignment();
	}

	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		settings.setHorizontalAlignment(horizontalAlignment);
	}

	public Alignment getVerticalAlignment() {
		return settings.getVerticalAlignment();
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		settings.setVerticalAlignment(verticalAlignment);;
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
	
	public int getXOffset(int panelWidth){
		int xOffset;
		switch(settings.getHorizontalAlignment()){
			case LEFT:			xOffset = 0; break;
			case ROOT_CENTER:	xOffset = -this.getX() + (panelWidth - this.getWidth()) / 2; break;
			case TREE_CENTER:	xOffset = (panelWidth - this.getDrawingWidth()) / 2; break;
			case RIGHT:			xOffset = panelWidth - this.getDrawingWidth() - 1; break;
			default:			xOffset = 0;
		}
		return xOffset;
	}

	public int getYOffset(int panelHeight){
		int yOffset;		
		switch(settings.getVerticalAlignment()){
			case TOP:			yOffset = 0; break;
			case ROOT_CENTER:	yOffset = -this.getY() + (panelHeight - this.getHeight()) / 2; break;
			case TREE_CENTER:	yOffset = (panelHeight - this.getDrawingHeight()) / 2; break;
			case BOTTOM:		yOffset = panelHeight - this.getDrawingHeight() - 1; break;
			default:			yOffset = 0;
		}
		return yOffset;
	}
}
