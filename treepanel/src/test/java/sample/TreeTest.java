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
		alg.POSITIONTREE(o);
		
		assertEquals(0, a.PRELIM, 0);	assertEquals(0, a.MODIFIER, 0);	assertEquals(0, a.XCOORD, 0);
		assertEquals(0, b.PRELIM, 0);	assertEquals(0, b.MODIFIER, 0);	assertEquals(3, b.XCOORD, 0);
		assertEquals(6, c.PRELIM, 0);	assertEquals(0, c.MODIFIER, 0);	assertEquals(9, c.XCOORD, 0);
		assertEquals(6, d.PRELIM, 0);	assertEquals(3, d.MODIFIER, 0);	assertEquals(6, d.XCOORD, 0);
		assertEquals(3, e.PRELIM, 0);	assertEquals(0, e.MODIFIER, 0);	assertEquals(3, e.XCOORD, 0);
		assertEquals(13.5, f.PRELIM, 0);	assertEquals(4.5, f.MODIFIER, 0);	assertEquals(13.5, f.XCOORD, 0);
		assertEquals(0, g.PRELIM, 0);	assertEquals(0, g.MODIFIER, 0);	assertEquals(21, g.XCOORD, 0);
		assertEquals(0, h.PRELIM, 0);	assertEquals(0, h.MODIFIER, 0);	assertEquals(15, h.XCOORD, 0);
		assertEquals(6, i.PRELIM, 0);	assertEquals(0, i.MODIFIER, 0);	assertEquals(21, i.XCOORD, 0);
		assertEquals(12, j.PRELIM, 0);	assertEquals(0, j.MODIFIER, 0);	assertEquals(27, j.XCOORD, 0);
		assertEquals(18, k.PRELIM, 0);	assertEquals(0, k.MODIFIER, 0);	assertEquals(33, k.XCOORD, 0);
		assertEquals(24, l.PRELIM, 0);	assertEquals(0, l.MODIFIER, 0);	assertEquals(39, l.XCOORD, 0);
		assertEquals(6, m.PRELIM, 0);	assertEquals(-6, m.MODIFIER, 0);	assertEquals(27, m.XCOORD, 0);
		assertEquals(24, n.PRELIM, 0);	assertEquals(21, n.MODIFIER, 0);	assertEquals(24, n.XCOORD, 0);
		assertEquals(13.5, o.PRELIM, 0);	assertEquals(0, o.MODIFIER, 0);	assertEquals(13.5, o.XCOORD, 0);

		
	}

}
