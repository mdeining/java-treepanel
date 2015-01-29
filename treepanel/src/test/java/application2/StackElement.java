package application2;

public class StackElement {
	
	public static StackElement bsp(){
		StackElement e1 = new StackElement();
		e1.data = "A";
		
		StackElement e2 = new StackElement();
		e2.data = "B";
		
		e1.next = e2;
		
		return e1;
	}
	
	private String data;
	
	public StackElement next;

	@Override
	public String toString() {
		return  data;
	}
	
	

}
