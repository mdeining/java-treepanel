package trees.acessing;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DuplicateWrappedNode extends AbstractWrappedNode{
	
	private Object node;
	private List<Method> labelers;

	public DuplicateWrappedNode(Object node) {
		super();
		this.node = node;
		this.labelers = initLabelers(node);
	};	
	
	@Override
	public Class<?> getNodeClass(){
		return node.getClass();
	}
	
	@Override
	public Object getNode(){
		return node;
	}
	
	@Override
	public String getLabel(){
		if(labelers.isEmpty())
			return node.toString();
		
		String label = null;
		
		try {
			Method method = labelers.get(0);
			Object result = method.invoke(node);
			label = result.toString(); 			
			for(int i = 1; i < labelers.size(); i++)
				label = label + "\n" + labelers.get(i).invoke(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return label;
	}


	private List<Method> getAllDeclaredMethods(Class<?> cls){
		List<Method> methods = new ArrayList<>();
		while(cls != null){
			Method[] declaredMethods = cls.getDeclaredMethods();
			for(Method method : declaredMethods){
				method.setAccessible(true);
				methods.add(method);
			}
			cls = cls.getSuperclass();
		}
		return methods;
	}

	private List<Method> initLabelers(Object node) {
		Class<?> cls = node.getClass();
		List<Method> labelers = new ArrayList<>();
		
		List<Method> methods = getAllDeclaredMethods(cls);
		for(Method method : methods)
			if(method.isAnnotationPresent(Label.class) && method.getParameterTypes().length == 0 && method.getReturnType() != void.class)
				labelers.add(method);
				
		return labelers;
	}

	@Override
	public String toString() {
		return "DuplicateNodeWrapper\n\t" + node;
	}
	
	@Override
	public boolean isDuplicate() {
		return true;
	}



}
