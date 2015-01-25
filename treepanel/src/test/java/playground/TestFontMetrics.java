package playground;

import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

public class TestFontMetrics {

	public static void main(String[] args) {
		JPanel panel = new JPanel();
		Font font = panel.getFont();
		FontMetrics metrics = panel.getFontMetrics(font);
		System.out.println(metrics);
		
		JPanel panel2 = new JPanel(){};
		Font font2 = panel2.getFont();
		FontMetrics metrics2 = panel2.getFontMetrics(font2);
		System.out.println(metrics2);
		System.out.println(metrics == metrics2);

	}

}
