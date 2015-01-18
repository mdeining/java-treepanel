package drawing;
import static layout.LayoutAlgorithm.Orientation.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

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
		Node apex = factory.sample1();
		
		LayoutAlgorithm alg = new LayoutAlgorithm();
		if(northRb.isSelected())
			alg.rootOrientation = NORTH;
		else if(southRb.isSelected())
			alg.rootOrientation = SOUTH;
		else if(eastRb.isSelected())
			alg.rootOrientation = EAST;
		else if(westRb.isSelected())
			alg.rootOrientation = WEST;
		alg.positionTree(apex);
		
		treePanel.paint(apex, alg.rootOrientation);

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
				treePanel.paint((Node)null);
				orientationGroup.clearSelection();
			}			
		});
	}
	
	public static void main(String[] args) {
		new TreePaint();	
	}
}
