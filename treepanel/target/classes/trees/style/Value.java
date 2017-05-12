package trees.style;

import java.util.HashMap;
import java.util.Map;

/**
 * Value holder for holding class-specific style values. A value can be 
 * assigned in general through {@link trees.style.Value#setValue(Object) setValue(Object)}
 * or for a specific type through {@link trees.style.Value#setValue(Class, Object) setValue(Class, Object)}.
 * Analogous, values can be retrieved through 
 * {@link trees.style.Value#getValue(Class) getValue(Class)}
 * and {@link trees.style.Value#getValue(Class) getValue(Class)}.
 * 
 * @author Marcus Deininger
 *
 * @param <T> The value type objects of this class hold.
 */
public class Value<T>{

	private T value; // default
	private Map<Class<?>, T> values = new HashMap<>(); // specific

	/**
	 * Standard getter.
	 * @return The overall defined value.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Gets the class specific value. If no value (or null) is defined,
	 * the overall value is returned.
	 * @param cls The type for which the value should be retrieved.
	 * @return The value for this type.
	 */
	public T getValue(Class<?> cls) {
		T value = values.get(cls);
		if(value != null)
			return value;
		else
			return this.value;
	}
	
	/**
	 * Standard setter.
	 * @param value Sets the standard value.
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * Sets the value for a given type. If the type is null
	 * the standard value is set, if the value is null
	 * the previously specified value is removed.
	 * @param cls The type for which the value should be set. 
	 * @param value The value to be set.
	 */
	public void setValue(Class<?> cls, T value) {
		if(cls == null)
			this.value = value;
		else if(value != null)
			values.put(cls, value);
		else // remove and revert to default
			values.remove(cls);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Value [value=" + value + ", values=" + values + "]";
	}
}
