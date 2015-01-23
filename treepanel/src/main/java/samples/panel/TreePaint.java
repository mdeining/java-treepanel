package samples.panel;

import static trees.panel.style.Alignment.*;
import static trees.panel.style.Orientation.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import trees.panel.style.Alignment;
import trees.panel.style.Fixed;
import trees.panel.style.Orientation;
import trees.panel.style.Shape;
import trees.panel.style.Style;
import trees.panel.TreePanel;
import samples.tree.Root;
import samples.tree.Sample;


@SuppressWarnings("serial")
public class TreePaint extends JFrame {
	
	private static final int INITIAL_DEPTH = 10;

	private static final int WIDTH = 600, HEIGHT = 450;

	// Define functionals widgets here
	private JButton clearButton, addButton;
	private JSpinner depthSpinner;
	
	private ButtonGroup orientationGroup;
	private JRadioButton northRb, southRb, eastRb, westRb;
	private ButtonGroup verticalGroup;
	private JRadioButton leftRb, rightRb, htreeRb, hrootRb;
	private ButtonGroup horizontalGroup;
	private JRadioButton topRb, bottomRb, vtreeRb, vrootRb;
	
	private Root root;
	private TreePanel<Root> treePanel;


	public TreePaint(){
		super("Frame Template");
		
		// The Model
		
		Sample s = new samples.tree.Sample();
//		root = s.sample();
////		root.add("xxxxxxxxxxxxx\nyyyyyy\nzz");
//		
//		root = new Root("XXXXXXXXXX\nyyyyyy\nzz");
		root = new Root("B"); root.add("A"); root.add("C");
				
		treePanel = new TreePanel<Root>(root);

		// create instances of functional widgets here
		clearButton = new JButton("Clear");
		depthSpinner = new JSpinner();
		northRb = new JRadioButton("North");
		southRb = new JRadioButton("South");
		eastRb = new JRadioButton("East");
		westRb = new JRadioButton("West");
		
		leftRb = new JRadioButton("Left");
		rightRb = new JRadioButton("Right");
		htreeRb = new JRadioButton("Tree-Centered");
		hrootRb = new JRadioButton("Root-Centered");
		
		topRb = new JRadioButton("Top");
		bottomRb = new JRadioButton("Bottom");
		vtreeRb = new JRadioButton("Tree-Centered");
		vrootRb = new JRadioButton("Root-Centered");
		
		addButton = new JButton("Add");
		

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
		northRb.setSelected(true);
		
		Style style = treePanel.getStyle();
		verticalGroup = new ButtonGroup();
		leftRb.setSelected(true);
		style.setVerticalAlignment(TOP);
		verticalGroup.add(topRb);
		verticalGroup.add(vrootRb);
		verticalGroup.add(vtreeRb);
		verticalGroup.add(bottomRb);
		
		horizontalGroup = new ButtonGroup();
		topRb.setSelected(true);
		style.setHorizontalAlignment(LEFT);
		horizontalGroup.add(leftRb);
		horizontalGroup.add(hrootRb);
		horizontalGroup.add(htreeRb);
		horizontalGroup.add(rightRb);
		
		depthSpinner.setValue(INITIAL_DEPTH);
		
		
		style.setShape(Shape.ROUNDED_RECTANGLE);
		style.setPointerBoxes(true);
		style.setLevelSepartion(80);
		
		style.setSize(new Fixed(60, 40));
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.

		panel.add(treePanel, BorderLayout.CENTER);
		treePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		
		JPanel control = new JPanel(new GridLayout(3, 1));
		
		JPanel control1 = new JPanel(new WrapLayout());		
			control1.add(northRb);
			control1.add(southRb);
			control1.add(eastRb);
			control1.add(westRb);
			control1.add(clearButton);
			
			control1.add(new JLabel("Depth"));
			control1.add(depthSpinner);
			
			control1.add(addButton);
			
		control.add(control1);
		
		JPanel control2 = new JPanel(new WrapLayout());		
			control2.add(leftRb);
			control2.add(htreeRb);
			control2.add(hrootRb);
			control2.add(rightRb);		
		control.add(control2);

		JPanel control3 = new JPanel(new WrapLayout());		
			control3.add(topRb);
			control3.add(vtreeRb);
			control3.add(vrootRb);
			control3.add(bottomRb);		
		control.add(control3);

		panel.add(control, BorderLayout.SOUTH);
		
		return panel;
	}
	
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
		
		ActionListener rbListener = new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) { 
				Orientation orientation = null;
				
				if(northRb.isSelected())
					orientation = NORTH;
				else if(southRb.isSelected())
					orientation = SOUTH;
				else if(eastRb.isSelected())
					orientation = EAST;
				else if(westRb.isSelected())
					orientation = WEST;
				
				Style style = treePanel.getStyle();
				style.setOrientation(orientation);
			}			
		};
		
		northRb.addActionListener(rbListener);
		southRb.addActionListener(rbListener);
		eastRb.addActionListener(rbListener);
		westRb.addActionListener(rbListener);
		
				
		ActionListener horizontalRbListener = new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) {
				Alignment alignment = LEFT;
				if(leftRb.isSelected())
					alignment = LEFT;
				else if(htreeRb.isSelected())
					alignment = TREE_CENTER;
				else if(hrootRb.isSelected())
					alignment = ROOT_CENTER;
				else if(rightRb.isSelected())
					alignment = RIGHT;
	
				Style style = treePanel.getStyle();
				style.setHorizontalAlignment(alignment);
			}			
		};
		
		leftRb.addActionListener(horizontalRbListener);
		htreeRb.addActionListener(horizontalRbListener);
		hrootRb.addActionListener(horizontalRbListener);
		rightRb.addActionListener(horizontalRbListener);
		


		ActionListener verticalbListener = new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) { 
				Alignment alignment = TOP;
				if(topRb.isSelected())
					alignment = TOP;
				else if(vtreeRb.isSelected())
					alignment = TREE_CENTER;
				else if(vrootRb.isSelected())
					alignment = ROOT_CENTER;
				else if(bottomRb.isSelected())
					alignment = BOTTOM;
				
				Style style = treePanel.getStyle();
				style.setVerticalAlignment(alignment);
			}			
		};
		
		topRb.addActionListener(verticalbListener);
		vtreeRb.addActionListener(verticalbListener);
		vrootRb.addActionListener(verticalbListener);
		bottomRb.addActionListener(verticalbListener);
		

		clearButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				treePanel.clear();
				orientationGroup.clearSelection();
			}			
		});
		
		depthSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = (int)depthSpinner.getValue();

				Style style = treePanel.getStyle();
				style.setMaxDepth(value);
			}
			
		});
		
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String value = JOptionPane.showInputDialog(TreePaint.this, "Neues Element");
					if(value != null && !value.trim().equals("")){
						root.add(value);
						treePanel.setTree(root);
					}
			}
			
		});
		
	}
	

	public static void main(String[] args) {
		new TreePaint();	
	}
}
