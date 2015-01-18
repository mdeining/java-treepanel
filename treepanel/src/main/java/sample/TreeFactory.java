package sample;

import layout.NodeClass;

public class TreeFactory {
	
	public NodeClass sample1(){
		NodeClass a = node("A"), b = node("B"), c = node("C"), d = node("D"), e = node("E"),
				  f = node("F"), g = node("G"), h = node("H"), i = node("I"), j = node("J"), 
				  k = node("K"), l = node("L"), m = node("M"), n = node("N"), o = node("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);		
		return o;
	}
	
	private NodeClass node(String data){
		return new NodeClass(data);
	}

}
