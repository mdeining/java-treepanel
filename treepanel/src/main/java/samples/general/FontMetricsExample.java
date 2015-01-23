package samples.general;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class FontMetricsExample extends JFrame {
	
    static final int MARGIN = 10;
    
    public FontMetricsExample() {
        super(FontMetricsExample.class.getSimpleName());

        JPanel panel = new JPanel(new BorderLayout());
        JPanel fontPanel = new JPanel(new BorderLayout());
        final JTextPane textSource = new JTextPane();
        textSource.setText("ABCDEFGHIJKLMNOPQRSTUVWXYZƒ÷‹¡\n"
                +"abcdefghijklmnopqrstuvwxyz‰ˆ¸ﬂ\n"
                +"0123456789!@#$%^&*()[]{}Ä");
        final SpinnerNumberModel fontSizeModel = new SpinnerNumberModel(18, 1, 128, 1);
        final String fonts[] =  GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        final JComboBox<String> fontFamilyBox = new JComboBox<>(fonts);
        fontFamilyBox.setSelectedItem("Arial");

		final JPanel textPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				String fontFamilyName = fonts[fontFamilyBox.getSelectedIndex()];
				int fontSize = fontSizeModel.getNumber().intValue();
				Font f = new Font(fontFamilyName, 0, fontSize);
				g.setFont(f);
				FontMetrics fm = g.getFontMetrics();

				int w = 0;
				String[] lines = textSource.getText().split("\n");
				for (String line : lines) {
					int stringWidth = fm.stringWidth(line);
					if (w < stringWidth)
						w = stringWidth;
				}
				int h = lines.length * fm.getHeight();
				g.setColor(Color.YELLOW);
				g.fillRect(MARGIN, MARGIN, w, h);
				g.setColor(Color.BLACK);


				int[] yOffsets = { -fm.getAscent(), 0, fm.getDescent() };
				Color[] yColors = { Color.RED, Color.BLUE, Color.GREEN };

				int x0 = MARGIN;
				int y0 = MARGIN + fm.getAscent();

				System.out.println(fm.getAscent());

				for (int i = 0; i < lines.length; ++i) {
					String s = lines[i];
					g.drawString(s, x0, y0);
					int width = fm.stringWidth(s);
					int x1 = x0 + width;
					for (int k = 0; k < yOffsets.length; k++) {
						int y = y0 + yOffsets[k];
						g.setColor(yColors[k]);
						g.drawLine(x0, y, x1, y);
					}
					g.setColor(Color.BLACK);
					y0 = y0 + fm.getHeight();

				}
			}
		};
        
        final JSpinner fontSizeSpinner = new JSpinner(fontSizeModel);
        fontSizeSpinner.getModel().addChangeListener(new ChangeListener() {           
            @Override public void stateChanged(ChangeEvent e) {
                textPanel.repaint();
            }
        });
        
        textPanel.setMinimumSize(new Dimension(200,100));
        textPanel.setPreferredSize(new Dimension(400,150));
        ActionListener repainter = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                textPanel.repaint();
            }           
        };
        textSource.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e) {
                textPanel.repaint();             
            }
            @Override public void insertUpdate(DocumentEvent e) {}
            @Override public void removeUpdate(DocumentEvent e) {}
        });
        fontFamilyBox.addActionListener(repainter);

        fontPanel.add(fontFamilyBox, BorderLayout.CENTER);
        fontPanel.add(fontSizeSpinner, BorderLayout.EAST);
        fontPanel.add(textSource, BorderLayout.SOUTH);
        panel.add(fontPanel, BorderLayout.NORTH);
        panel.add(textPanel, BorderLayout.CENTER);       
        setContentPane(panel);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new FontMetricsExample().setVisible(true);
    }
}