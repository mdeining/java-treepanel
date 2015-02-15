package trees.synchronization;

import java.util.Observable;

/**
 * Subclasses of this class can set breakpoints with the help of the
 * method {@link trees.synchronization.Interruptable#breakpoint(String, Object) breakpoint(String, Object)}.
 * For controlling the breakpoints the sibling class {@link trees.synchronization.Synchronizable Synchronizable}
 * is needed.
 * 
 * @see trees.synchronization.Synchronizable
 * 
 * @author Marcus deininger
 *
 */
public abstract class Interruptable extends Observable{

	/**
	 * Inner class for providing an intermediate result. Objects of this class
	 * are sent (through notification), when a breakpoint is reached.
	 * @author Marcus Deininger
	 *
	 */
	public class IntermediateResult{
		public String message;
		public Object source;
		public IntermediateResult(String message, Object source) {
			this.message = message; this.source = source;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return message + ": " + source.getClass().getSimpleName() + " " + source.toString();
		}
	}
	
	private static boolean stepping = false;
	private boolean suspended = false;
	
	// Notification //////////////////////////////////////////
	
	/**
	 * This method has to provide a list of senders which are able
	 * to notify an observer about a change. When using in a recursive
	 * structure typically only a root object is registered as 
	 * an observable for an observer. With the help of this method
	 * an intermediate result (which is transported by the notify-method)
	 * is passed through recursively until a possible registered
	 * observable is reached.<p>
	 * The method may return null, too. In this case nothing will happen.
	 * @return An array of designated senders.
	 */
	protected abstract Observable[] getSenders();

	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object argument){
		Observable[] senders = this.getSenders();
		if(senders != null)
			for(Observable sender : senders)
				if(sender != null){
					sender.notifyObservers(argument);
				}
	}
		
	// Synchronization //////////////////////////////////////////
	
	/**
	 * This method has to provide a list of receivers which are able
	 * to resume on a possible {@link java.lang.Object#notify() Object.notify()}. 
	 * When using in a recursive structure the waiting object (which is set to wait
	 * by {@link trees.synchronization.Interruptable#breakpoint(String, Object) breakpoint(String, Object)}
	 * is not accessible directly but only through a root-object.
	 * With the help of this method a notify-call
	 * is passed through recursively until a possible waiting object reached.<p>
	 * The method may return null, too. In this case nothing will happen.
	 * @return An array of designated receivers.
	 */
	protected abstract Interruptable[] getReceivers();

	/**
	 * This methods sets the current thread on wait. Before suspending
	 * observers are notified with an intermediate result about the current state.
	 * @param message Informal message about the state.
	 * @param source Additional argument, typically this would be the target object.
	 * @see trees.synchronization.Interruptable#getSenders()
	 */
	protected final void breakpoint(String message, Object source) {
		if(!stepping)
			return;
		
		suspended = true; // set yourself on waiting
		this.notifyObservers(new IntermediateResult(message, source));
		
		synchronized(this){
			while(suspended)
				try {
					this.wait();
				} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Resumes this object and all receivers, accessed through 
	 * {@link trees.synchronization.Interruptable#getReceivers() getReceivers()}.
	 * 
	 */
	protected synchronized void resume(){
		if(suspended){
			suspended = false;
			this.notifyAll();
	    }
			
		Interruptable[] receivers = this.getReceivers();
		if(receivers != null)
			for(Interruptable receiver : receivers)
				if(receiver != null)
					receiver.resume();
	}
	
	/**
	 * Activates / deactivates the interrupting property.
	 * @param stepping
	 */
	public static void setBreakpointEnabled(boolean stepping) {
		Interruptable.stepping = stepping;
	}
}
