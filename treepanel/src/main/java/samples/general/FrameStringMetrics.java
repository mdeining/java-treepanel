package samples.general;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FrameStringMetrics extends JFrame {
	
	private static final int WIDTH = 800, HEIGHT = 600;

	// Define functionals widgets here
	private JTextField textField;
	private JButton button;

	public FrameStringMetrics(){
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
				
//				g.setFont(new Font("Helvetica", Font.PLAIN, 60));
//				
//				int x = 10, y = 20, w = 200, h = 100;
//				
//				g.drawRect(x, y, w, h);
//
//				
//				FontMetrics fm = g.getFontMetrics();
//				String str = "XXXX";
//				g.drawString(str, x, y + fm.getAscent());
//				g.drawLine(x, y + fm.getHeight() - fm.getDescent(), x + w, y + fm.getHeight() - fm.getDescent());
//				
//				
//				Graphics2D g2 = (Graphics2D) g;
				
		        // must be called before getTextBounds()
		        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

		        Graphics2D g2 = (Graphics2D) g;

			        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			                RenderingHints.VALUE_ANTIALIAS_ON);


			        String str = "Mxxxxxxy";
			        float x = 140, y = 128;

			        Rectangle bounds = getStringBounds(g2, str, x, y);

			        g2.setColor(Color.red);
			        g2.drawString(str, x, y);

			        g2.setColor(Color.blue);
			        g2.draw(bounds);

			        g2.dispose();
			}						
		};
	}
	
    private Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
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
		new FrameStringMetrics();	
	}
}
