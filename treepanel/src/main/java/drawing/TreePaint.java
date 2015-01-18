package drawing;
import static layout.Orientation.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import layout.Settings;
import layout.LayoutAlgorithm;
import layout.Node;
import sample.TreeFactory;

@SuppressWarnings("serial")
public class TreePaint extends JFrame {
	
	private static final int WIDTH = 600, HEIGHT = 450;

	// Define functionals widgets here
	private JButton clearButton;
	
	private JRadioButton northRb, southRb, eastRb, westRb;
	
	private TreePanel treePanel;

	private ButtonGroup orientationGroup;

	public TreePaint(){
		super("Frame Template");
		
		// create instances of functional widgets here
		clearButton = new JButton("Clear");
		northRb = new JRadioButton("North");
		southRb = new JRadioButton("South");
		eastRb = new JRadioButton("East");
		westRb = new JRadioButton("West");
		
		treePanel = new TreePanel();

		initializeWidgets();
		JPanel panel = createWidgetLayout();		
		createWidgetInteraction();
	
		this.setContentPane(panel);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setResizable(false);
		this.setLocationByPlatform(true);
		this.pack();
		this.setVisible(true);
	}
	
	private void initializeWidgets() {
		// initialize functional widgets here
		orientationGroup = new ButtonGroup();
		orientationGroup.add(northRb);
		orientationGroup.add(southRb);
		orientationGroup.add(eastRb);
		orientationGroup.add(westRb);
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.

		panel.add(treePanel, BorderLayout.CENTER);
		treePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		
		JPanel control = new JPanel(new WrapLayout());	
		
		control.add(northRb);
		control.add(southRb);
		control.add(eastRb);
		control.add(westRb);
		control.add(clearButton);
		
		panel.add(control, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void sample(){
		TreeFactory factory = new TreeFactory();
		Node root = factory.sample1();
		Settings s = null;
		
		LayoutAlgorithm alg = new LayoutAlgorithm();
		int maxDepth = 2;
		if(northRb.isSelected())
			s = new Settings(maxDepth, 40, 40, 40, NORTH);
		else if(southRb.isSelected())
			s = new Settings(maxDepth, 40, 40, 40, SOUTH);
		else if(eastRb.isSelected())
			s = new Settings(maxDepth, 40, 40, 40, EAST);
		else if(westRb.isSelected())
			s = new Settings(maxDepth, 40, 40, 40, WEST);
		alg.positionTree(s, root);
		
		treePanel.paint(s, root);

	}
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
		
		ActionListener rbListener = new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) { sample(); }			
		};
		
		northRb.addActionListener(rbListener);
		southRb.addActionListener(rbListener);
		eastRb.addActionListener(rbListener);
		westRb.addActionListener(rbListener);

		clearButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				treePanel.clear();
				orientationGroup.clearSelection();
			}			
		});
	}
	
	public static void main(String[] args) {
		new TreePaint();	
	}
}
