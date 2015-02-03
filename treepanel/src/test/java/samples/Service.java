package samples;

public class Service {
	
	public void service(Object obj){
		System.out.println("Object");
	}

	public void service(Class<?> cls){
		System.out.println("Class");
	}

}
