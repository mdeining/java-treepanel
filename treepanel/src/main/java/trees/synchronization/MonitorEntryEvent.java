package trees.synchronization;

import java.util.EventObject;

/**
 * Event class for providing an information on entering an breakpoint. 
 * Objects of this class are passed on calling {@link MonitorListener#monitorEntered(MonitorEntryEvent) monitorEntered()}, 
 * when a breakpoint is reached.
 * 
 * @author Marcus Deininger
 *
 */
@SuppressWarnings("serial")
public class MonitorEntryEvent extends EventObject {

	private String message;

	public MonitorEntryEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	/**
	 * Standard getter.
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return message + ": " + source.getClass().getSimpleName() + " " + source.toString();
	}

}
