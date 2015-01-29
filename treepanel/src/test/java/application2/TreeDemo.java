package application2;

import java.awt.Dimension;

import javax.swing.JFrame;

import trees.panel.TreePanel;
import trees.panel.style.Shape;
import trees.panel.style.Size;
import trees.panel.style.Style;

@SuppressWarnings("serial")
public class TreeDemo extends JFrame{
	
	public TreeDemo(){
		super("TreeDemo");
		
		Node root = this.sampleModel();
		Style style = new Style(20, 20, 40);
//		style.setSize(Size.FIXED(40, 24));
		style.setShape(Shape.ROUNDED_RECTANGLE);
		
		TreePanel<Node> panel = new TreePanel<>(style, root);
		
		this.setContentPane(panel);
		
		this.setPreferredSize(new Dimension(400, 300));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);
	}
	
	private Node sampleModel(){
		Node root = new Node("root");
		Node n1 = new Node("n1");
		Node n2 = new Node("n2");
		root.add(n1, n2);
		Node n11 = new Node("n1.1\n(first node)");
		Node n12 = new Node("n1.2");
		Node n13 = new Node("n1.3\n(last node)");
//		Node n13 = new Node("n1.3\n(last node) + extra text");
		n1.add(n11, n12, n13);
		Node n21 = new Node("n2.1");
		n2.add(n21);
		return root;
	}

	public static void main(String[] args) {
		new TreeDemo();
	}
}
