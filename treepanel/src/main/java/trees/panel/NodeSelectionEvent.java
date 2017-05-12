package trees.panel;

import java.awt.Component;
import java.awt.event.ComponentEvent;

@SuppressWarnings("serial")
public class NodeSelectionEvent<T> extends ComponentEvent{
	
	private T node;
	private boolean popUpTriggered;
	private int x, y;

	public NodeSelectionEvent(Component source, T node, boolean popUpTriggered, int x, int y) {
		super(source, ComponentEvent.COMPONENT_LAST);
		this.node = node;
		this.popUpTriggered = popUpTriggered;
		this.x = x;
		this.y = y;
	}

	public T getNode() {
		return node;
	}

	public boolean isPopUpTriggered() {
		return popUpTriggered;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "NodeSelectionEvent [node=" + node + ", popUpTriggered=" + popUpTriggered
				+ ", x=" + x + ", y=" + y + "]";
	}
	
	
}
