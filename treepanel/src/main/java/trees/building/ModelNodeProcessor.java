package trees.building;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import trees.annotations.Ignore;
import trees.annotations.Label;
import trees.annotations.Nodes;

/**
 * Helper class for analyzing a given node. Only to be used 
 * within the TreePanel.
 * 
 * @author Marcus Deininger
 *
 */
public class ModelNodeProcessor{
	
	private Class<?> type;	
	private String label;
	
	protected class Value{
		protected Class<?> cls;
		protected Object obj;
		
		public Value(Class<?> cls, Object obj) { 
			if(obj == null){
				this.cls = cls;
				this.obj = null;
			}else{
				this.cls = obj.getClass();
				this.obj = obj;
			}
		}
		
		public String toString(){ 
			return "<" + cls.getName() + ", " + obj + ">"; 
		}
	}
	private List<Value> children;

	protected ModelNodeProcessor(Object obj) {
		super();
		this.type = obj.getClass();		
		this.label = this.processLabel(obj);		
		this.children = this.processDecendants(obj);
	};

	protected Class<?> getType(){
		return type;
	}
	
	protected String getLabel() {
		return label;
	}

	protected List<Value> getChildren(){
		return children;
	}
	
	protected boolean hasChildren() {
		for(Value value : children)
			if(value.obj != null)
				return true;
		return false;
	}

	// Label extraction /////////////////////////////
	
	private String processLabel(Object obj){
		Class<?> cls = obj.getClass();
		
		List<Method> annotatedMethods = new ArrayList<>();
		
		while(cls != null){
			Method[] declaredMethods = cls.getDeclaredMethods();
			for(Method method : declaredMethods)
				if(method.isAnnotationPresent(Label.class) 
						&& method.getParameterTypes().length == 0 
						&& method.getReturnType() != void.class){
					method.setAccessible(true);
					annotatedMethods.add(method);
				}
			cls = cls.getSuperclass();
		}
		
		if(!annotatedMethods.isEmpty())		
			try {
				String label = annotatedMethods.get(0).invoke(obj).toString();
				for(int i = 1; i < annotatedMethods.size(); i++)
					label = label + "\n" + annotatedMethods.get(i).invoke(obj).toString();
				return label;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return obj.toString();		
	}

	// Descendant extraction /////////////////////////////

	private List<Value> processDecendants(Object object) {
		List<Field> fields = getAllDeclaredFields(object.getClass());
		List<Field> recursiveFields = getAnnotatedFields(fields);		
		if(recursiveFields.isEmpty())
			recursiveFields = getRecursiveFields(object, fields);
		
		List<Value> descendants = new ArrayList<>();
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
			if(!field.isAnnotationPresent(Ignore.class))
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

	private List<Value> getValues(Object obj, Field field) {
		List<Value> values = new ArrayList<>();
		Class<?> type = field.getType();
		if(type.isArray())
			values.addAll(this.getArrayValues(obj, field));
		else if(Collection.class.isAssignableFrom(type))
			values.addAll(this.getCollectionValues(obj, field));
		else // skalar
			values.add(this.getSkalarValue(obj, field));		
		return values;
	}
	
	private Value getSkalarValue(Object obj, Field field){
		try {
			Object value = field.get(obj);
			return new Value(obj.getClass(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Value(obj.getClass(), null);
	}

	private List<Value> getArrayValues(Object obj, Field field){
		List<Value> values = new ArrayList<>();
		try {
			Class<?> componentType = field.getType().getComponentType();
			Object array = field.get(obj);
			if(array != null)
				for(int i = 0; i < Array.getLength(array); i++)
					values.add(new Value(componentType, Array.get(array, i)));
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;			
	}

	private List<Value> getCollectionValues(Object obj, Field field){
		List<Value> values = new ArrayList<>();
		try {
			Collection<?> collection = (Collection<?>)field.get(obj);
			if(collection != null){
				Class<?> componentType = obj.getClass();
				for(Object element : collection)
					if(element != null){ // assume, that the first object determines the null-class
						componentType = element.getClass();
						break;
					}
				for(Object element : collection)
					values.add(new Value(componentType, element));
			}
			return values;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;			
	}
}
