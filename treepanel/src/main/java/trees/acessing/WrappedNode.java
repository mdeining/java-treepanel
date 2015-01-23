package trees.acessing;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WrappedNode extends AbstractNode{
	
	private Object node;
	private List<?> descendants;
	private List<Method> labelers;

	public WrappedNode(Object node) {
		super();
		this.node = node;
		this.descendants = initDecendants(node);
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
	
	public List<?> getDescendants() {
		return descendants;
	}
	
	@Override
	public String getLabel(){
		try {
			Method method = labelers.get(0);
			Object result = method.invoke(node);
			String label = result.toString(); 
			
			for(int i = 1; i < labelers.size(); i++)
				label = label + "\n" + labelers.get(i).invoke(node);
			return label;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node.toString();
	}

	public boolean hasDescendants() {
		for(Object descendant : descendants)
			if(descendant != null)
				return true;
		return false;
	}

	public Class<?> getFirstDescendant() {
		for(Object descendant : descendants)
			if(descendant != null)
				return descendant.getClass();
		return null;
	}

	private List<?> initDecendants(Object object) {
		List<Field> fields = getAllDeclaredFields(object.getClass());
		List<Field> recursiveFields = getAnnotatedFields(fields);		
		if(recursiveFields.isEmpty())
			recursiveFields = getRecursiveFields(object, fields);
		
		List<Object> descendants = new ArrayList<>();
		for(Field field : recursiveFields)
			descendants.addAll(getValues(object, field));
		return descendants;
	}

	private List<Field> getAllDeclaredFields(Class<?> cls){
		List<Field> fields = new ArrayList<>();
		while(cls != null){
			Field[] declaredFields = cls.getDeclaredFields();
			for(Field field : declaredFields){
				field.setAccessible(true);
				fields.add(field);
			}
			cls = cls.getSuperclass();
		}
		return fields;
	}
	
	private List<Field> getAnnotatedFields(List<Field> fields){
		List<Field> annotatedFields = new ArrayList<>();
		for(Field field : fields)
			if(field.isAnnotationPresent(Nodes.class))
				annotatedFields.add(field);
		return annotatedFields;
	}

	private List<Field>  getRecursiveFields(Object object, List<Field> fields) {
		List<Field> recursiveFields = new ArrayList<>();
		for(Field field : fields)				
			if(isRecursiveSkalarField(object, field))
				recursiveFields.add(field);
			else if(isRecursiveArrayField(object, field))
				recursiveFields.add(field);						
			else if(isRecursiveCollectionField(object, field))
				recursiveFields.add(field);
		return recursiveFields;
	}
	
	private boolean isRecursiveSkalarField(Object object, Field field){
		Class<?> cls = object.getClass();
		Class<?> type = field.getType();
		return type.isAssignableFrom(cls);
	}

	private boolean isRecursiveArrayField(Object object, Field field){
		Class<?> cls = object.getClass();
		Class<?> type = field.getType();
		if(!type.isArray())
			return false;
		Class<?> componentType = type.getComponentType();
		return componentType.isAssignableFrom(cls);		
	}

	private boolean isRecursiveCollectionField(Object object, Field field){
		// this is only a heuristic - for correct result use the Nodes-annotation
		Class<?> cls = object.getClass();
		Class<?> type = field.getType();
		if(!Collection.class.isAssignableFrom(type))
			return false;

		Collection<?> collection;
		try {
			collection = (Collection<?>) field.get(object);
		} catch (Exception e) {
			return false;
		}
		
		if(collection == null || collection.isEmpty())
			return false;
		
		boolean onlyNullElements = true;
		for(Object obj : collection)
			if(obj != null)
				onlyNullElements = false;
		if(onlyNullElements)
			return false;
		
		// Assume that the common class is one class below Object
		Class<?> componentType = cls;
		while(componentType.getSuperclass() != Object.class)
			componentType = componentType.getSuperclass();
		
		// If there is at least one object which does not conform,
		// it is not usable as a recursive collection
		for(Object obj : collection)
			if(obj != null && !componentType.isAssignableFrom(obj.getClass()))
				return false;
		
		return true;	
	}

	private List<?> getValues(Object obj, Field field) {
		List<Object> values = new ArrayList<>();

		Class<?> type = field.getType();
		Object value = null;
		try {
			value = field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return values;
		}

		if(type.isArray()){
			if(value != null)
				for(int i = 0; i < Array.getLength(value); i++)
					values.add(Array.get(value, i));
		}else if(Collection.class.isAssignableFrom(type)){
			if(value != null)
				values.addAll((Collection<?>) value);
		}else // skalar
			values.add(value);
		
		return values;
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
				
		if(labelers.isEmpty())
			try {
				Method toString = cls.getMethod("toString");
				labelers.add(toString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return labelers;
	}

	@Override
	public String toString() {
		return "NodeWrapper\n\t" + node + "\n\t" + descendants;
	}

}
