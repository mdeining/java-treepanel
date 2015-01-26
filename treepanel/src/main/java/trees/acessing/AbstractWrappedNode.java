package trees.acessing;

public abstract class AbstractWrappedNode {

	public abstract String getLabel();

	public abstract Class<?> getNodeClass();

	public abstract Object getNode();
	
	public abstract boolean isDuplicate();

}
