package trees.layout;

import trees.acessing.AbstractWrappedNode;

public class WrappedNodeProxy extends AbstractWrappedNode{
	
	private Class<?> cls;

	public WrappedNodeProxy(Class<?> cls) {
		super();
		this.cls = cls;
	}

	@Override
	public String getLabel() {
		return "";
	}

	@Override
	public Object getNode() {
		return null;
	}

	@Override
	public Class<?> getNodeClass() {
		return cls;
	};

	@Override
	public String toString() {
		return "Proxy [cls=" + cls + "]";
	}
	
	@Override
	public boolean isDuplicate() {
		return false;
	}

}
