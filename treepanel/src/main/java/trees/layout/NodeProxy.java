package trees.layout;

import trees.acessing.AbstractNode;

public class NodeProxy extends AbstractNode{
	
	private Class<?> cls;

	public NodeProxy(Class<?> cls) {
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

}
