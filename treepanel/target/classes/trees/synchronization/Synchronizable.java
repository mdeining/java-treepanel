package trees.synchronization;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * Subclasses of this class can run the subclass in a thread. The original
 * available methods can be executed in an asynchronous mode. The result
 * of these methods are returned through notification as an 
 * {@link trees.synchronization.Synchronizable.ActionResult ActionResult} object.
 * For suspending and resuming the sibling class {@link trees.synchronization.Interruptable Interruptable}
 * is needed.
 * 
 * @see trees.synchronization.Interruptable
 * 
 * @author Marcus Deininger
 *
 */
public abstract class Synchronizable extends Observable implements Runnable{

	private String action = null;	
	private Object[] parameters = null;

	/**
	 * Inner class for providing a final result. Objects of this class
	 * are sent (through notification), when the action is finished.
	 * While the object includes also the calling method name and
	 * parameters of main interest, when received are the two public
	 * fields:
	 * <ul>
	 * <li>{@link trees.synchronization.Synchronizable.ActionResult#succeeded succeeded}
	 * 		which returns true, if the invocation finished without an exception.</li>
	 * <li>{@link trees.synchronization.Synchronizable.ActionResult#returned returned}
	 * 		which contains the returned result after successful invocation (otherwise null).</li>
	 * </ul>
	 * @author Marcus Deininger
	 *
	 */
	public class ActionResult{
		/**
		 * The method name of the action to be invoked.
		 */
		public String action;
		/**
		 * The parameters of the action to be invoked.
		 */
		public Object[] parameters;
		/**
		 * true, if the action was executed successfully, false otherwise.
		 */
		public boolean succeeded;
		/**
		 * Result of the method invocation. <code>null</code> if the the return type was void.
		 */
		public Object returned;
	}	
	
	/**
	 * Invokes a method in an asynchronous way. <code>methodName</code> is
	 * the method name to be invoked on this object. <code>parameters</code>
	 * are the parameters to be used. With the help of the parameter types
	 * the corresponding declared method is selected. The method is invoked
	 * within a thread. The result will be sent through a notification
	 * and can be caught with {@link java.util.Observer#update(Observable, Object) 
	 * Observer.update(Observable, Object)}. As an argument (the second parameter)
	 * an object of type {@link trees.synchronization.Synchronizable.ActionResult ActionResult}
	 * will be passed.<p>
	 * 
	 * Attention: The wrapping types <code>Integer</code>, <code>Long</code>, 
	 * <code>Float</code>, <code>Double</code>, <code>Boolean</code>, and <code>Character</code>
	 * are converted to the corresponding primitive type. If a method relies
	 * on the wrapper type, this will fail - a more elaborated mechanism
	 * could easily fix this, but it is not needed at the moment.
	 * 
	 * @param methodName Name of the method to be invoked.
	 * @param parameters Parameters of the method to be invoked.
	 */
	public void invokeAsync(String methodName, Object ... parameters){
		this.setAction(methodName, parameters);
		Thread host = new Thread(this);
		host.start();
	}
	
	private void setAction(String action, Object ... parameters){
		this.action = action;
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers(java.lang.Object)
	 */
	@Override
	public void notifyObservers(Object argument) {
		this.setChanged();
		super.notifyObservers(argument);
		this.clearChanged();
	}

	/** 
	 * Allows a thread starting the execution separately. The method 
	 * specified by {@link trees.synchronization.Synchronizable#setAction(String, Object...) setAction(String, Object...)} 
	 * is executed. The result is returned through notification as an 
	 * {@link trees.synchronization.Synchronizable.ActionResult ActionResult} object.
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(action == null)
			return;
		
		ActionResult result = new ActionResult();
		result.action = action;
		result.parameters = parameters;
		
		Class<?>[] types = new Class<?>[parameters.length];
		for(int i = 0; i < parameters.length; i++)
			if(parameters[i] != null)
				types[i] = mapClass(parameters[i]);
			else
				types[i] = Object.class;
		Class<?> cls = this.getClass();
		try {
			Method method = cls.getDeclaredMethod(action, types);
			result.returned = method.invoke(this, parameters);
			result.succeeded = true;
		} catch (Exception e) {
			result.succeeded = false;
			e.printStackTrace();
		}

		this.setAction(null); // Reset action
		this.setChanged();
		this.notifyObservers(result);
		this.clearChanged();
	}
	
	private Class<?> mapClass(Object object) {
		Class<?> cls = object.getClass();
		if(cls == Integer.class)
			return int.class;
		else if(cls == Integer.class)
			return int.class;
		else if(cls == Long.class)
			return long.class;
		else if(cls == Float.class)
			return float.class;
		else if(cls == Double.class)
			return double.class;
		else if(cls == Boolean.class)
			return boolean.class;
		else if(cls == Character.class)
			return char.class;
		else return cls;
	}

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
	 * Resumes all receivers, accessed through 
	 * {@link trees.synchronization.Synchronizable#getReceivers() getReceivers()}.
	 * 
	 */
	public synchronized void resume(){
		Interruptable[] receivers = this.getReceivers();
		if(receivers != null)
			for(Interruptable receiver : receivers)
				if(receiver != null)
					receiver.resume();
	}

}
