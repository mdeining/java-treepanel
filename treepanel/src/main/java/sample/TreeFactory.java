package sample;

import layout.Child;
import layout.Node;
import layout.Root;

public class TreeFactory {
	
	public Root sample1(){
		Node a = child("A"), b = child("B"), c = child("C"), d = child("D"), e = child("E"),
				  f = child("F"), g = child("G"), h = child("H"), i = child("I"), j = child("J"), 
				  k = child("K"), l = child("L"), m = child("M"), n = child("N");
		Root o = root("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);		
		return o;
	}
	
	private Node child(String data){
		return new Child(data);
	}

	private Root root(String data){
		return new Root(data);
	}

}
