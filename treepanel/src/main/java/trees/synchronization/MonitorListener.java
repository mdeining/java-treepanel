package trees.synchronization;

import java.util.EventListener;

/**
 * The listener interface for receiving monitor events. The class that is interested 
 * in processing an monitor event implements this interface, and the object 
 * created with that class is registered with a component, using the component's 
 * addMonitorListener method.<p>
 * The monitor is entered and thus the {@link #monitorEntered(MonitorEntryEvent) 
 * monitorEntered() } is invoked when {@link Monitor#breakpoint(Object, String) 
 * monitor.breakpoint()} is executed.<p>
 * The monitor is exited and thus the {@link #monitorExited(MonitorExitEvent) 
 * monitorExited() } is invoked when the monitor has ended its hosted method called
 * through {@link Monitor#invokeAsync(Object, String, Object...) monitor.invokeAsync()}.
 * 
 * @author Marcus Deininger
 *
 */
@FunctionalInterface
public interface MonitorListener extends EventListener{
	
	/**
	 * Method invoked when {@link Monitor#breakpoint(Object, String) 
	 * monitor.breakpoint()} is executed.<p>
	 * @param event The monitor event.
	 */
	public default void monitorEntered(MonitorEntryEvent event){
		// By default do nothing.
	}

	/**
	 * Method invoked when the monitor has ended its hosted method called
	 * through {@link Monitor#invokeAsync(Object, String, Object...) 
	 * monitor.invokeAsync()}.
	 * @param event The monitor event.
	 */
	public void monitorExited(MonitorExitEvent event);

}
