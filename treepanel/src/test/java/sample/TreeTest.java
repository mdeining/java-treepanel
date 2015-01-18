package sample;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import layout.LayoutAlgorithm;
import layout.NodeClass;

public class TreeTest {
	
	NodeClass a, b, c, d, e, f, g, h, i, j, k, l, m, n, o;
	
	@Before
	public void sample(){
		a = new NodeClass("A");
		b = new NodeClass("B");
		c = new NodeClass("C");
		d = new NodeClass("D");
		e = new NodeClass("E");
		f = new NodeClass("F");
		g = new NodeClass("G");
		h = new NodeClass("H");
		i = new NodeClass("I");
		j = new NodeClass("J");
		k = new NodeClass("K");
		l = new NodeClass("L");
		m = new NodeClass("M");
		n = new NodeClass("N");
		o = new NodeClass("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);		
	}
	
	@Test
	public void test1(){
		LayoutAlgorithm alg = new LayoutAlgorithm();
		alg.POSITIONTREE(o, 100, 100);
		
		assertEquals(0, a.PRELIM, 0);	assertEquals(0, a.MODIFIER, 0);	assertEquals(0, a.XCOORD, 0);
		assertEquals(0, b.PRELIM, 0);	assertEquals(0, b.MODIFIER, 0);	assertEquals(30, b.XCOORD, 0);
		assertEquals(60, c.PRELIM, 0);	assertEquals(0, c.MODIFIER, 0);	assertEquals(90, c.XCOORD, 0);
		assertEquals(60, d.PRELIM, 0);	assertEquals(30, d.MODIFIER, 0);	assertEquals(60, d.XCOORD, 0);
		assertEquals(30, e.PRELIM, 0);	assertEquals(0, e.MODIFIER, 0);	assertEquals(30, e.XCOORD, 0);
		assertEquals(135, f.PRELIM, 0);	assertEquals(45, f.MODIFIER, 0);	assertEquals(135, f.XCOORD, 0);
		assertEquals(0, g.PRELIM, 0);	assertEquals(0, g.MODIFIER, 0);	assertEquals(210, g.XCOORD, 0);
		assertEquals(0, h.PRELIM, 0);	assertEquals(0, h.MODIFIER, 0);	assertEquals(150, h.XCOORD, 0);
		assertEquals(60, i.PRELIM, 0);	assertEquals(0, i.MODIFIER, 0);	assertEquals(210, i.XCOORD, 0);
		assertEquals(120, j.PRELIM, 0);	assertEquals(0, j.MODIFIER, 0);	assertEquals(270, j.XCOORD, 0);
		assertEquals(180, k.PRELIM, 0);	assertEquals(0, k.MODIFIER, 0);	assertEquals(330, k.XCOORD, 0);
		assertEquals(240, l.PRELIM, 0);	assertEquals(0, l.MODIFIER, 0);	assertEquals(390, l.XCOORD, 0);
		assertEquals(60, m.PRELIM, 0);	assertEquals(-60, m.MODIFIER, 0);	assertEquals(270, m.XCOORD, 0);
		assertEquals(240, n.PRELIM, 0);	assertEquals(210, n.MODIFIER, 0);	assertEquals(240, n.XCOORD, 0);
		assertEquals(135, o.PRELIM, 0);	assertEquals(0, o.MODIFIER, 0);	assertEquals(135, o.XCOORD, 0);

		
	}

}
