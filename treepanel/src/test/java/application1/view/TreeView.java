package application1.view;

import static trees.panel.style.Alignment.*;
import static trees.panel.style.Orientation.*;
import static trees.panel.style.Size.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import application1.model.Root;
import application1.model.Sample;
import trees.panel.style.Alignment;
import trees.panel.style.Orientation;
import trees.panel.style.Shape;
import trees.panel.style.Style;
import trees.panel.TreePanel;
import trees.panel.TreePanel.NodeSelector;


@SuppressWarnings("serial")
public class TreeView extends JFrame implements Observer{
	
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
	
	private SpinnerNumberModel fontSizeModel;
	private JSpinner fontSizeSpinner;

	private String[] fonts;
	private JComboBox<String> fontFamilyBox;

	
	private Root root;
	private TreePanel<Root> treePanel;


	public TreeView(){
		super("Frame Template");
		
		// The Model
		
		Sample s = new application1.model.Sample();
		root = s.sample();
		root.add("123\nABCDEFGH\nxyz");
		
		
//		
//		root = new Root("XXXXXXXXXX\nyyyyyy\nzz");
//		root = new Root("B"); root.add("A"); root.add("C");		
//		root = new Root("M"); root.add("P");
//		root = new Root("M");
				
//		treePanel = new TreePanel<Root>(root);
		
//		treePanel = new TreePanel<Root>();

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
		
		int siblingSeparation = 40;
		int subtreeSeparation = 40;
		int levelSepartion = 60;
		
		Style style = new Style(siblingSeparation, subtreeSeparation, levelSepartion);
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
		
		
		int initFontSize = 12;
	    String initFontFamily = "Arial";
	    
		fontSizeModel = new SpinnerNumberModel(initFontSize, 1, 128, 1);
		fontSizeSpinner = new JSpinner(fontSizeModel);
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontFamilyBox = new JComboBox<>(fonts);
 		fontFamilyBox.setSelectedItem(initFontFamily);
		
		
		style.setShape(Shape.ROUNDED_RECTANGLE);
		style.setPointerBoxes(true);
		
//		style.setSize(FIXED(50, 30));
//		style.setSize(FIXED(60, 50));
//		style.setSize(VARIABLE());
//		style.setSize(MAX_VARIABLE(60, 50));
		style.setSize(MIN_VARIABLE(40, 30));
//		style.setSize(RESTRICTED_VARIABLE(40, 30, 60, 50));
		
		style.setFont(new Font(initFontFamily, 0, initFontSize));
		
//		style.setRootPointer("root");
		style.setPlaceHolder(false);
		
		treePanel = new TreePanel<>(style, root);
		
		treePanel.setNodeColor(Color.BLUE, root.getLeft());
		treePanel.setSubtreeColor(Color.RED, root.getRight());
		
		treePanel.addObserver(this);
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.

		panel.add(treePanel, BorderLayout.CENTER);
		treePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		
		JPanel controls = new JPanel(new GridLayout(4, 1));
		
		JPanel control1 = new JPanel(new WrapLayout());		
			control1.add(northRb);
			control1.add(southRb);
			control1.add(eastRb);
			control1.add(westRb);
			control1.add(clearButton);
			
			control1.add(new JLabel("Depth"));
			control1.add(depthSpinner);
			
			control1.add(addButton);
			
		controls.add(control1);
		
		JPanel control2 = new JPanel(new WrapLayout());		
			control2.add(leftRb);
			control2.add(htreeRb);
			control2.add(hrootRb);
			control2.add(rightRb);		
		controls.add(control2);

		JPanel control3 = new JPanel(new WrapLayout());		
			control3.add(topRb);
			control3.add(vtreeRb);
			control3.add(vrootRb);
			control3.add(bottomRb);		
		controls.add(control3);
		
		JPanel control4 = new JPanel(new WrapLayout());
			control4.add(fontFamilyBox);	
			control4.add(fontSizeSpinner);
		controls.add(control4);

		panel.add(controls, BorderLayout.SOUTH);
		
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
				root = null;
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
				String value = JOptionPane.showInputDialog(TreeView.this, "Neues Element");
					if(value != null && !value.trim().equals("")){
						if(root == null){
							root = new Root(value);
							treePanel.setTree(root);
						}else{							
							root.add(value);
							treePanel.repaint();
						}
					}
			}
			
		});
		
        fontSizeSpinner.getModel().addChangeListener(new ChangeListener() {           
            @Override public void stateChanged(ChangeEvent e) {
				updateFont();
            }
        });
        
        fontFamilyBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}
      	
        });
	}
	
	private void updateFont() {
		String fontFamilyName = fonts[fontFamilyBox.getSelectedIndex()];
		int fontSize = fontSizeModel.getNumber().intValue();
		Font f = new Font(fontFamilyName, 0, fontSize);
		treePanel.getStyle().setFont(f);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof NodeSelector){
			System.out.println("selected: " + arg);
		}
		
	}

	public static void main(String[] args) {
		new TreeView();	
	}
}
