package tests.layout;

import static org.junit.Assert.assertEquals;
import trees.acessing.WrappedNode;
import trees.layout.Child;
import trees.layout.LayoutAlgorithm;
import trees.layout.Node;
import trees.layout.Root;
import trees.panel.style.Orientation;
import trees.panel.style.Style;
import trees.panel.style.StyleFactory;
import static trees.panel.style.Size.*;

import org.junit.Before;
import org.junit.Test;

public class TestLayoutAlgorithm {
	
	private Node a, b, c, d, e, f, g, h, i, j, k, l, m, n;
	private Root o, root;
	private LayoutAlgorithm algorithm;
	private Style style;
	
	private static class Stub {
		private String label;

		public Stub(String label) {
			this.label = label;
		}

		@Override public String toString() {
			return label;
		}		
	}
	
	private Child newChild(String label){
		Stub stub = new Stub(label);
		WrappedNode wn = new WrappedNode(stub);
		Child child = new Child(wn);
		return child;
	}
	
	private Root newRoot(String label){
		Stub stub = new Stub(label);
		WrappedNode wn = new WrappedNode(stub);
		Root root = new Root(wn);
		return root;
	}
	
	@Before
	public void setUp(){
		a = newChild("A");
		b = newChild("B");
		c = newChild("C");
		d = newChild("D");
		e = newChild("E");
		f = newChild("F");
		g = newChild("G");
		h = newChild("H");
		i = newChild("I");
		j = newChild("J");
		k = newChild("K");
		l = newChild("L");
		m = newChild("M");
		n = newChild("N");
		o = newRoot("O");
		o.add(e, f, n);
		e.add(a, d);
		d.add(b, c);
		n.add(g, m);
		m.add(h, i, j, k, l);
		
		root = o;
		
		algorithm = new LayoutAlgorithm();
		style = StyleFactory.getPlainStyle();
		
		style.setOrientation(Orientation.NORTH);
		style.setSize(FIXED(20, 20));
		style.setLevelSepartion(40);
		style.setSiblingSeparation(40);
		style.setSubtreeSeparation(40);
		style.setMaxDepth(10);
	}
	
	@Test
	public void test1(){
		
		algorithm.positionTree(style, root);
		root.printPostOrder();
				
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
