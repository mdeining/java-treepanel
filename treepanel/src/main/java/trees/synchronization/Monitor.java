package trees.synchronization;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class which allows methods to  be executed in an asynchronous mode. 
 * The result of these methods are made available through a {@link MonitorListener MonitorListener}.<p>
 * 
 * If a breakpoint is set by placing a call of {@link #breakpoint(Object, String) monitor.breakpoint}
 * in the execution chain of the invoked method, the execution is suspended at this 
 * point and can be resumed by calling {@link #resume() monitor.resume}.
 * 
 * @author Marcus Deininger
 *
 */
public class Monitor{
	
	public static final Monitor monitor = new Monitor();
	private Monitor(){}

	private boolean breakpointsEnabled = false;	
	private boolean suspended = false;

	private List<MonitorListener> listeners = new ArrayList<>();

	/**
	 * Standard getter.
	 * @return true, if the interrupting property is enabled.
	 */
	public boolean isBreakpointsEnabled() {
		return breakpointsEnabled;
	}

	/**
	 * Activates the interrupting property.
	 */
	public void enableBreakpoints() {
		this.breakpointsEnabled = true;
	}
	
	/**
	 * Deactivates the interrupting property.
	 */
	public void disableBreakpoints() {
		this.breakpointsEnabled = false;
	}
	
	/**
	 * Adds the specified monitor listener to receive selection events from this component. 
	 * If the listener is null, no exception is thrown and no action is performed.
	 * 
	 * @param listener the monitor listener
	 */
	public void addMonitorListener(MonitorListener listener){
		if(listener != null)
			listeners.add(listener);
	}
	
	/**
	 * Removes the specified monitor listener so that it no longer receives 
	 * selection events from this component. This method performs no function, 
	 * nor does it throw an exception, if the listener specified by the argument 
	 * was not previously added to this component. If the listener is null, 
	 * no exception is thrown and no action is performed. 
	 * 
	 * @param listener the monitor listener
	 */
	public void removeMonitorListener(MonitorListener listener){
		if(listener != null)
			listeners.remove(listener);
	}

	/**
	 * This methods sets the current thread on wait. Before suspending
	 * observers are notified with an intermediate result about the current state.
	 * 
	 * @param source typically this would be the target object.
	 * @param message informal message about the state.
	 */
	public void breakpoint(Object source, String message) {
		if(!breakpointsEnabled)
			return;
		
		suspended = true; // set yourself on waiting
		
		MonitorEntryEvent event = new MonitorEntryEvent(source, message);
		for(MonitorListener listener : listeners)
			listener.monitorEntered(event);
		
		synchronized(this){
			while(suspended)
				try {
					this.wait();
				} catch (InterruptedException e) {}
		}
	}

	/**
	 * Resumes the monitor.
	 * 
	 */
	public synchronized void resume(){
		if(suspended){
			suspended = false;
			this.notifyAll();
	    }
	}

	/**
	 * Invokes a method in an asynchronous way. <code>methodName</code> is
	 * the method name to be invoked on the <code>target</code>. <code>parameters</code>
	 * are the parameters to be used. With the help of the parameter types
	 * the corresponding declared method is selected. The method is invoked
	 * within a thread. <p>
	 * 
	 * The result will be made available through {@link MonitorExitEvent MonitorExitEvent}
	 * by executing {@link MonitorListener#monitorExited(MonitorExitEvent) monitorExited()}
	 * of all registered {@link MonitorListener MonitorListeners}.<p>
	 * 
	 * A method invocation can be paused by calling the method {@link #breakpoint(Object, String) breakpoint()}
	 * during the execution of the invoked method.<p>
	 * 
	 * Attention (i): The wrapping types <code>Integer</code>, <code>Long</code>, 
	 * <code>Float</code>, <code>Double</code>, <code>Boolean</code>, and <code>Character</code>
	 * are converted to the corresponding primitive type. If a method relies
	 * on the wrapper type, this will fail - a more elaborated mechanism
	 * could easily fix this, but it is not needed at the moment.<p>
	 * 
	 * Attention (ii): This is a workaround for missing function pointers in Java.
	 * This is inherently unsafe, as the method-name is only passed as a string.
	 * 
	 * @param target The target object which should declare this method.
	 * @param methodName Name of the method to be invoked.
	 * @param parameters Parameters of the method to be invoked.
	 */
	public void invokeAsync(Object target, String methodName, Object ... parameters){
		if(methodName == null) return;
		Method method = this.initializeInvocation(target, methodName, parameters);
		if(method == null) return; 
		Thread invoker = this.initializeInvocationThread(target, method, parameters);
		invoker.start();
	}

	private Method initializeInvocation(Object target, String methodName, Object[] parameters){
		Class<?>[] types = new Class<?>[parameters.length];
		for(int i = 0; i < parameters.length; i++)
			if(parameters[i] != null)
				types[i] = mapClass(parameters[i]);
			else
				types[i] = Object.class;
		Class<?> cls = target.getClass();
		Method method = null;
		try {
			method = cls.getDeclaredMethod(methodName, types);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}		
		return method;
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

	private Thread initializeInvocationThread(Object target, Method method, Object[] parameters) {
		return new Thread(){
			@Override public void run(){
				Object result = null;
				boolean succeeded = false;
				try {
					result = method.invoke(target, parameters);
					succeeded = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				MonitorExitEvent event = new MonitorExitEvent(target, method.getName(), succeeded, result);
				for(MonitorListener listener : listeners)
					listener.monitorExited(event);
			}
		};
	}
}
