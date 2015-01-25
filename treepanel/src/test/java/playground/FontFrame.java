package playground;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class FontFrame extends JFrame {
	
	private static final int WIDTH = 400, HEIGHT = 300;

	// Define functionals widgets here
	private JTextField textField;
	private JButton button;
	
	private TextPanel textPanel;

	private SpinnerNumberModel fontSizeModel;
	private JSpinner fontSizeSpinner;

	private String[] fonts;
	private JComboBox<String> fontFamilyBox;

	public FontFrame(){
		super("Frame Template");
		
		// create instances of functional widgets here
		textField = new JTextField();
		button = new JButton();
		textPanel = new TextPanel();
		
		fontSizeSpinner = null;
		fontFamilyBox = null;

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
	
	private class TextPanel extends JPanel{
		
		public TextPanel(){ 
			this.setBackground(Color.WHITE); 
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Font font = this.getFont();
			FontMetrics fm = this.getFontMetrics(font);
			
			int margin = 10;
			String text = textField.getText();
			
			int x = margin;
			int y = margin + fm.getAscent();
			
			g.drawString(text, x, y);
		}						
	}
	
	private void initializeWidgets() {
		// initialize functional widgets here
		textField.setColumns(20);
		button.setText("Press");
		
		fontSizeModel = new SpinnerNumberModel(18, 1, 128, 1);
		fontSizeSpinner = new JSpinner(fontSizeModel);
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontFamilyBox = new JComboBox<>(fonts);
        fontFamilyBox.setSelectedItem("Arial");

	}
	
	private JPanel createWidgetLayout() {
		JPanel panel = new JPanel(new BorderLayout());
		// create the layout here - if needed define supporting widgets like labels, etc.
		
		panel.add(textPanel, BorderLayout.CENTER);
		
		JPanel controls = new JPanel(new GridLayout(2, 1));
		
			JPanel control1 = new JPanel(new WrapLayout());
			JLabel label = new JLabel("Text");
			control1.add(label);		
			control1.add(textField);
			control1.add(button);
			controls.add(control1);
			
			JPanel control2 = new JPanel(new WrapLayout());
			control2.add(fontFamilyBox);	
			control2.add(fontSizeSpinner);
			controls.add(control2);
		
		panel.add(controls, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void createWidgetInteraction() {
		// add Listeners, etc. here
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
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
		textPanel.setFont(f); // repaints the component
//		textPanel.repaint();
	}

	public static void main(String[] args) {
		new FontFrame();	
	}
}
