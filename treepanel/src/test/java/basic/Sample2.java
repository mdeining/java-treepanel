package basic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import trees.panel.TreePanel;
import trees.panel.style.Shape;
import trees.panel.style.Size;
import trees.panel.style.Style;

public class Sample2 {
	
	public static void main(String[] args) {
				
		Node root = new Node("root", 
				new Node("n1",
						new Node("n1.1\n(first node)"),
						new Node("n1.2"),
						new Node("n1.3\n(last node) + extra text")), 
				new Node("n2", 
						new Node("n2.1")),
				new Node("n3"));

		final String about = "This is the layout example as implemented for:\n" + 
				"abego TreeLayout (code.google.com/p/treelayout)\n";

		Style style = new Style(20, 20, 45, Size.VARIABLE());
		style.setShape(Shape.ROUNDED_RECTANGLE);
		TreePanel<Node> treePanel = new TreePanel<Node>(style, root);

		final JFrame frame = new JFrame("Sample 2");
		frame.add(treePanel);		

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Help");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("About ...");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, about,
					    "About this Sample", JOptionPane.PLAIN_MESSAGE);			
			}		
		});
		menu.add(menuItem);	
		frame.setJMenuBar(menuBar);

		frame.setPreferredSize(new Dimension(400, 250));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);		
	}
}
