package trees.panel;

import java.util.EventListener;

@FunctionalInterface
public interface NodeSelectionListener<T> extends EventListener{
	
	public void nodeSelected(NodeSelectionEvent<T> event);

}
