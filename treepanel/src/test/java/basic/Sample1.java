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
import trees.panel.style.Size;
import trees.panel.style.Style;

public class Sample1 {
	
	public static void main(String[] args) {
				
		Node root = new Node("O", 
				new Node("E",
						new Node("A"),
						new Node("D",
								new Node("B"),
								new Node("C"))), 
				new Node("F"),
				new Node("N",
						new Node("G"),
						new Node("M",
								new Node("H"),
								new Node("I"),
								new Node("J"),
								new Node("K"),
								new Node("L")
						)));
		
		final String about = "This is the layout example as described in the paper:\n" + 
				"Walker, JQ II: A node-positioning algorithm for general trees.\n" + 
				"Software-Practice and Experience 1990; 20(7):685-705.";

		Style style = new Style(40, 40, 50, Size.FIXED(20, 25));
		TreePanel<Node> treePanel = new TreePanel<Node>(style, root);

		final JFrame frame = new JFrame("Sample 1");
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
		
		frame.setPreferredSize(new Dimension(450, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.pack();
		frame.setVisible(true);		
	}
}
