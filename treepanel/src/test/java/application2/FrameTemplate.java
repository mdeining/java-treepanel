package application2;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import trees.panel.TreePanel;
import trees.panel.style.Shape;
import trees.panel.style.Style;
import static trees.panel.style.Size.*;

@SuppressWarnings("serial")
public class FrameTemplate extends JFrame {
	
	private static final int WIDTH = 400, HEIGHT = 300;

	// Define functionals widgets here
	
	private TreePanel<Node> treePanel;
	private Node root;

	public FrameTemplate(){
		super("Frame Template");
		
		// create instances of functional widgets here
		root = this.sampleModel();
		treePanel = new TreePanel<>(root);

		initializeWidgets();
		JPanel panel = createWidgetLayout();		
		createWidgetInteraction();
	
		this.setContentPane(panel);
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
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
		n1.add(n11, n12, n13);
		Node n21 = new Node("n2");
		n2.add(n21);
		return root;
	}

	private void initializeWidgets() {
		// initialize functional widgets here
		Style style = treePanel.getStyle();
//		style.setSize(VARIABLE());
//		style.setSize(FIXED(60, 40));
//		style.setSize(MIN_VARIABLE(60, 40));
		style.setSize(MIN_VARIABLE(30, 20));
		style.setShape(Shape.ROUNDED_RECTANGLE);
		style.setLevelSepartion(60);
		style.setPointerBoxes(true);
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new GridLayout(1, 1));
		// create the layout here - if needed define supporting widgets like labels, etc.
		panel.add(treePanel);
		return panel;
	}
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
	}
	
	public static void main(String[] args) {
		new FrameTemplate();	
	}
}
