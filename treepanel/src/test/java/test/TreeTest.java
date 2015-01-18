package test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import layout.LayoutAlgorithm;
import layout.Node;

public class TreeTest {
	
	Node a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;
	
	@Before
	public void sample(){
		a = new Node("A");
		b = new Node("B");
		c = new Node("C");
		d = new Node("D");
		e = new Node("E");
		f = new Node("F");
		g = new Node("G");
		h = new Node("H");
		i = new Node("I");
		j = new Node("J");
		k = new Node("K");
		l = new Node("L");
		m = new Node("M");
		n = new Node("N");
		o = new Node("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);		
	}
	
	@Test
	public void test1(){
		LayoutAlgorithm alg = new LayoutAlgorithm();
		alg.positionTree(o);
		
		assertEquals(0, a.prelim, 0);	assertEquals(0, a.modifier, 0);	assertEquals(0, a.xCoordinate, 0);
		assertEquals(0, b.prelim, 0);	assertEquals(0, b.modifier, 0);	assertEquals(30, b.xCoordinate, 0);
		assertEquals(60, c.prelim, 0);	assertEquals(0, c.modifier, 0);	assertEquals(90, c.xCoordinate, 0);
		assertEquals(60, d.prelim, 0);	assertEquals(30, d.modifier, 0);	assertEquals(60, d.xCoordinate, 0);
		assertEquals(30, e.prelim, 0);	assertEquals(0, e.modifier, 0);	assertEquals(30, e.xCoordinate, 0);
		assertEquals(135, f.prelim, 0);	assertEquals(45, f.modifier, 0);	assertEquals(135, f.xCoordinate, 0);
		assertEquals(0, g.prelim, 0);	assertEquals(0, g.modifier, 0);	assertEquals(210, g.xCoordinate, 0);
		assertEquals(0, h.prelim, 0);	assertEquals(0, h.modifier, 0);	assertEquals(150, h.xCoordinate, 0);
		assertEquals(60, i.prelim, 0);	assertEquals(0, i.modifier, 0);	assertEquals(210, i.xCoordinate, 0);
		assertEquals(120, j.prelim, 0);	assertEquals(0, j.modifier, 0);	assertEquals(270, j.xCoordinate, 0);
		assertEquals(180, k.prelim, 0);	assertEquals(0, k.modifier, 0);	assertEquals(330, k.xCoordinate, 0);
		assertEquals(240, l.prelim, 0);	assertEquals(0, l.modifier, 0);	assertEquals(390, l.xCoordinate, 0);
		assertEquals(60, m.prelim, 0);	assertEquals(-60, m.modifier, 0);	assertEquals(270, m.xCoordinate, 0);
		assertEquals(240, n.prelim, 0);	assertEquals(210, n.modifier, 0);	assertEquals(240, n.xCoordinate, 0);
		assertEquals(135, o.prelim, 0);	assertEquals(0, o.modifier, 0);	assertEquals(135, o.xCoordinate, 0);
	}
}
