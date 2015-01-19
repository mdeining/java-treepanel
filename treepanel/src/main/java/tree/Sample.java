package tree;

public class Sample {
	
	public Root sample(){
		Root root = new Root("M");
		String[] data = {"A", "Y","X",  "Z", "C", "B", "F"};
		for(String str : data)
			root.add(str);		
		return root;
	}
	
	public static void main(String[] args){
		Sample s = new Sample();
		Root r = s.sample();
		System.out.println(r);
	}

}
