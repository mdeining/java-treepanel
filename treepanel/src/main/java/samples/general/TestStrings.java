package samples.general;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class TestStrings {

	public static void main(String[] args) {
		String label = "abcxxxxxxxxxxxx\ndef ghi 0123456789\niii\nmmm\nxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		System.out.println(label);
		String[] lines = label.split("\\n");
		System.out.println(Arrays.toString(lines));
		
		Font font = UIManager.getDefaults().getFont("Panel.font");
		JPanel comp = new JPanel();
		FontMetrics fm = comp.getFontMetrics(font);
				
		System.out.println(font + " " + fm + " " + fm.getHeight());		
		System.out.println();
		
		for(String line : lines)
			System.out.println(line + " " + fm.stringWidth(line) + "pt");	
		System.out.println("\n=========================================\n");
		
		int width = 60, height = 40;
		
		final String ETC = "...";
		int etcWidth = fm.stringWidth(ETC);

		int visibleLines = height / fm.getHeight();
		System.out.println("visibleLines " + visibleLines);
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < visibleLines; i++){
			int stringWidth = fm.stringWidth(lines[i]);
			System.out.println(lines[i] + " " + stringWidth);
			String line = null;
			if(stringWidth <= width)
				line = lines[i];
			else if (etcWidth > width) // no space at all				
				line = "";
			else{ // add ...
				for(int k = lines[i].length() - 1; k >= 0; k--){
					String str = lines[i].substring(0, k) + ETC;
					System.out.println("Test '" + str + "' " + fm.stringWidth(str));
					if(fm.stringWidth(str) <= width){ // fits
						System.out.println("ok");
						line = str;
						break;
					}
				//...
				}
			}
			sb.append(line);
			sb.append('\n');
		}
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		
		System.out.println("final text");
		System.out.println("================");
		System.out.println(sb.toString());
		System.out.println("================");
			
			
	}

}
