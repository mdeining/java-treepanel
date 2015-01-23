package trees.panel.style;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Size {
	
	private static final String ETC = "...";
	
	public abstract int getWidth(String label);
	
	public abstract int getHeight(String label);
	
	public abstract String[] getLabel(Graphics g, Rectangle area, String label);
	
	protected String[] trimLabel(Graphics g, Rectangle area, String label){
		FontMetrics metrics = g.getFontMetrics();
		int etcWidth = metrics.stringWidth(ETC);
		int rows = area.height / metrics.getHeight();

		String[] lines = label.split("\\n");
		if(lines.length < rows)
			rows = lines.length;
		String[] trimmed = new String[rows];
		
		for(int i = 0; i < rows; i++){
			if(metrics.stringWidth(lines[i]) <= area.width)
				trimmed[i] = lines[i];
			else if (etcWidth > area.width) // no space at all				
				trimmed[i] = "";
			else{ // add ETC
				for(int k = lines[i].length() - 1; k >= 0; k--){
					String line = lines[i].substring(0, k) + ETC;
					if(metrics.stringWidth(line) <= area.width){ // fits
						trimmed[i] = line;
						break;
					}
				}
			}
		}
		
		return trimmed;
	}
		
}
