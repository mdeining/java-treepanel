package trees.synchronization;

import java.util.EventObject;

/**
 * Event class for providing the result after the asynchronous invocation
 * has finished. Objects of this class are passed on calling 
 * {@link MonitorListener#monitorExited(MonitorExitEvent) monitorExited()}.
 * The source of event object is the original caller object. Additionally,
 * it includes the name of the called method, the result object and
 * a flag which indicates the successful execution.
 * 
 * @author Marcus Deininger
 *
 */
@SuppressWarnings("serial")
public class MonitorExitEvent extends EventObject {
	 
	 private String methodName;
	 private boolean succeeded;
	 private Object result;

	public MonitorExitEvent(Object source, String methodName, boolean succeeded, Object result) {
		super(source);
		this.methodName = methodName;
		this.succeeded = succeeded;
		this.result = result;
	}

	/**
	 * Standard getter.
	 * @return The name of the called method.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Standard getter.
	 * @return true, if the invocation has succeeded.
	 */
	public boolean hasSucceeded() {
		return succeeded;
	}

	/**
	 * Standard getter.
	 * @return The result of the invocation.
	 */
	public Object getResult() {
		return result;
	}
}
