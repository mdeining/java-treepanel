package trees.panel.style;

import java.util.HashMap;
import java.util.Map;

public class Value<T>{

	private T value; // default
	private Map<Class<?>, T> values = new HashMap<>(); // specific

	public T getValue() {
		return value;
	}
	
	public T getValue(Class<?> cls) {
		T value = values.get(cls);
		if(value != null)
			return value;
		else
			return this.value;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setValue(Class<?> cls, T value) {
		if(cls == null)
			this.value = value;
		else if(value != null)
			values.put(cls, value);
		else // remove and revert to default
			values.remove(cls);
	}

	@Override
	public String toString() {
		return "Value [value=" + value + ", values=" + values + "]";
	}
}
