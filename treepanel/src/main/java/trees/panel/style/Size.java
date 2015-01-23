package trees.panel.style;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;

public abstract class Size {
	
	private static final String ETC = "...";
	
	public static Fixed FIXED(int width, int height){
		return new Fixed(width, height);
	}
	
	private Style style;

	public void setStyle(Style style) {
		this.style = style;
	}
	
	public abstract boolean hasMaximum();

	public abstract boolean hasMinimum();
	
	public abstract Dimension getMaximum();

	public abstract Dimension getMinimum();

	protected abstract int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label);
	
	protected abstract int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label);
//	
//	public abstract String[] getLabel(FontMetrics metrics, Rectangle area, String label);
	
//	protected String[] trimLabel(FontMetrics metrics, Rectangle area, String label){
//		int etcWidth = metrics.stringWidth(ETC);
//		int rows = area.height / metrics.getHeight();
//		
//		lines = source.split("\\n");
//		int n = area.height / metrics.getHeight();
//		if (n < lines.length)
//			lines = Arrays.copyOfRange(lines, 0, n);
//				
//		width = 0;
//		int[] stringWidth = new int[lines.length];
//		for(int i = 0; i < lines.length; i++){
//			line[i] = adjust()
//			stringWidth[i] = metrics.stringWidth(lines[i]);
//			if(stringWidth[i] > width)
//				width = stringWidth[i];
//		}
//		height = lines.length * metrics.getHeight();
//
//
////		String[] lines = label.split("\\n");
////		if(lines.length < rows)
////			rows = lines.length;
////		String[] trimmed = new String[rows];
////		
////		for(int i = 0; i < rows; i++){
////			if(metrics.stringWidth(lines[i]) <= area.width)
////				trimmed[i] = lines[i];
////			else if (etcWidth > area.width) // no space at all				
////				trimmed[i] = "";
////			else{ // add ETC
////				for(int k = lines[i].length() - 1; k >= 0; k--){
////					String line = lines[i].substring(0, k) + ETC;
////					if(metrics.stringWidth(line) <= area.width){ // fits
////						trimmed[i] = line;
////						break;
////					}
////				}
////			}
////		}
////		
////		return trimmed;
//	}
		
}
