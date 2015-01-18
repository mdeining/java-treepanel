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
		LayoutAlgorithm algorithm = new LayoutAlgorithm();
		algorithm.positionTree(null, o);
		
		assertEquals(0, a.getPrelim());		assertEquals(0, a.getModifier());	assertEquals(0, a.getX());
		assertEquals(0, b.getPrelim());		assertEquals(0, b.getModifier());	assertEquals(30, b.getX());
		assertEquals(60, c.getPrelim());	assertEquals(0, c.getModifier());	assertEquals(90, c.getX());
		assertEquals(60, d.getPrelim());	assertEquals(30, d.getModifier());	assertEquals(60, d.getX());
		assertEquals(30, e.getPrelim());	assertEquals(0, e.getModifier());	assertEquals(30, e.getX());
		assertEquals(135, f.getPrelim());	assertEquals(45, f.getModifier());	assertEquals(135, f.getX());
		assertEquals(0, g.getPrelim());		assertEquals(0, g.getModifier());	assertEquals(210, g.getX());
		assertEquals(0, h.getPrelim());		assertEquals(0, h.getModifier());	assertEquals(150, h.getX());
		assertEquals(60, i.getPrelim());	assertEquals(0, i.getModifier());	assertEquals(210, i.getX());
		assertEquals(120, j.getPrelim());	assertEquals(0, j.getModifier());	assertEquals(270, j.getX());
		assertEquals(180, k.getPrelim());	assertEquals(0, k.getModifier());	assertEquals(330, k.getX());
		assertEquals(240, l.getPrelim());	assertEquals(0, l.getModifier());	assertEquals(390, l.getX());
		assertEquals(60, m.getPrelim());	assertEquals(-60, m.getModifier());	assertEquals(270, m.getX());
		assertEquals(240, n.getPrelim());	assertEquals(210, n.getModifier());	assertEquals(240, n.getX());
		assertEquals(135, o.getPrelim());	assertEquals(0, o.getModifier());	assertEquals(135, o.getX());
	}
}
