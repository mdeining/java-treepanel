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


}
