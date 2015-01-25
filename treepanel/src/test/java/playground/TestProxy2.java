package playground;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestProxy2 {
	
	public static class EventLogger implements InvocationHandler {
	
	  public Object invoke(Object proxy, Method method, Object[] args) 
	       throws IllegalArgumentException {
//	    if (args == null)
//	      throw new IllegalArgumentException("Missing event object argument");
//	    if (! (args[0] instanceof AWTEvent))
//	      throw new IllegalArgumentException("Expecting event object");
//	    System.out.println("EVENT: "+args[0]);
		
		  System.out.println(method + " " + Arrays.toString(args));
		  
	    return null;
	  }
	  
	}
	
	public class Subject{
		
		private int n;

		public Subject(int n) {
			super();
			this.n = n;
		}

		public int getN() {
			return n;
		}

		public void setN(int n) {
			this.n = n;
		}

		@Override
		public String toString() {
			return "Subject [n=" + n + "]";
		}
	}
	
//	public class MyProxy{
//		
//		private Class<?> cls;
//
//		public MyProxy(Class<?> cls) {
//			super();
//			this.cls = cls;
//		}
//		
//		public Class<?> getClass(){ // getClass is final!
//			return cls;
//		}
//	}

	public static void main(String[] args) {

	    InvocationHandler invocationHandler = new InvocationHandler(){

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				  System.out.println(method + " " + Arrays.toString(args));
				return null;
			}
	    	
	    };

	    // Array mit den Interfaces erzeugen,
	    // die der Proxy haben soll
	    Class<?>[] classes = new Class[] {Subject.class};

	    // Erzeugung des Proxy-Objekts
	    Object proxy = Proxy.newProxyInstance(
	      ClassLoader.getSystemClassLoader(), 
	      classes, invocationHandler);

	    System.out.println(proxy.getClass());
	    
	    
	}

}
