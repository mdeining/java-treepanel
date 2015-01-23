package playground;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class TestReflection {

	public static void main(String[] args) {
		String s = "abc";
		List<String> sl = new ArrayList<>();
		
//		System.out.println(sl instanceof List<?>);
//		System.out.println(Collection.class.isAssignableFrom(sl.getClass()));
		
		sl.add(null);
		sl.add(null);
		sl.add(s);
		sl.add(null);
		
		System.out.println(sl);
	}

}
