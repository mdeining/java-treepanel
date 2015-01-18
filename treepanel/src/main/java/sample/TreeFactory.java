package sample;

import layout.Node;

public class TreeFactory {
	
	public Node sample1(){
		Node a = node("A"), b = node("B"), c = node("C"), d = node("D"), e = node("E"),
				  f = node("F"), g = node("G"), h = node("H"), i = node("I"), j = node("J"), 
				  k = node("K"), l = node("L"), m = node("M"), n = node("N"), o = node("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);		
		return o;
	}
	
	private Node node(String data){
		return new Node(data);
	}

}
