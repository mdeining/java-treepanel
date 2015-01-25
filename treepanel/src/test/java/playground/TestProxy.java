package playground;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestProxy {
	

	public static class EventLogger implements InvocationHandler {
	
	  public Object invoke(Object proxy, Method method, Object[] args) 
	       throws IllegalArgumentException {
	    if (args == null)
	      throw new IllegalArgumentException("Missing event object argument");
	    if (! (args[0] instanceof AWTEvent))
	      throw new IllegalArgumentException("Expecting event object");
	    System.err.println("EVENT: "+args[0]);
	    return null;
	  }
	  
	}

	public static void main(String[] args) {
	    JFrame frame = new JFrame("Proxy Demo");
	    JButton button = new JButton("OK");
	    frame.getContentPane().add(button);    
	    frame.setSize(200, 100);

	    // InvocationHandler erzeugen
	    InvocationHandler logger = new EventLogger();

	    // Array mit den Interfaces erzeugen,
	    // die der Proxy haben soll
	    Class[] listenerInterfaces = new Class[] {
	      java.awt.event.ActionListener.class, 
	      java.awt.event.WindowListener.class};

	    // Erzeugung des Proxy-Objekts
	    Object proxy = Proxy.newProxyInstance(
	      ClassLoader.getSystemClassLoader(), 
	      listenerInterfaces, logger);

	    // Proxy als Event-Listener registrieren
	    button.addActionListener((ActionListener)proxy);
	    frame.addWindowListener((WindowListener)proxy);

	    frame.show();
	}

}
