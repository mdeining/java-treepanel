package samples.general;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class IntersectRectangles1 extends JFrame {
	
	private static final int WIDTH = 800, HEIGHT = 600;

	// Define functionals widgets here
	private JTextField textField;
	private JButton button;

	public IntersectRectangles1(){
		super("Frame Template");
		
		// create instances of functional widgets here
		textField = new JTextField();
		button = new JButton();

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
	
	private JPanel drawPanel(){
		return new JPanel(){
			
			{ this.setBackground(Color.WHITE); }

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				double factor = 1 - Math.sqrt(2) / 2;
				int dia = 50;
				int offset = (int)(dia/2 * factor) + 1;
				
				int x = 10, y = 20, w = 200, h = 100;
				
				g.drawRoundRect(x, y, w, h, dia, dia);

//				g.drawRect(x, y, w, h);
//				g.drawOval(x, y, dia, dia);
//				
//				g.drawOval(x + w - dia, y, dia, dia);
//				g.drawOval(x, y + h - dia, dia, dia);
//				g.drawOval(x + w - dia, y + h - dia, dia, dia);
			
				
				
				g.drawLine(x + offset, y + offset, x + w - offset, y + h - offset);
				g.drawLine(x + offset, y + h - offset, x + w - offset, y + offset);
				
				
			}						
		};
	}
	
	private void initializeWidgets() {
		// initialize functional widgets here
		textField.setColumns(20);
		button.setText("Press");
	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.
		
		panel.add(drawPanel(), BorderLayout.CENTER);
		
		JPanel control = new JPanel(new WrapLayout());
		JLabel label = new JLabel("This is a template for creating frames in a uniform way.");
		control.add(label);
		
		control.add(textField);
		control.add(button);
		panel.add(control, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(textField.getText());
			}
			
		});
	}
	
	public static void main(String[] args) {
		new IntersectRectangles1();	
	}
}
