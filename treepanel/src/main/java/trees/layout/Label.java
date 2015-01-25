package trees.layout;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.Arrays;

import trees.panel.style.Size;
import trees.panel.style.Style;

public class Label {
	
private Node node = null;
	private String source = null;
	
	private String[] lines;

	private int width, height;
	
	protected Label(Node node) {
		super();
		this.node = node;
	}

	protected void initialize() {
		this.source = null;
	}

	public String[] getLines() {
		return lines;
	}
	
	public Dimension getDimension(){
		return new Dimension(width, height);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	protected void adjust(Style style){
		if(source != null)
			return;

		FontMetrics metrics = style.getFontMetrics();
		source = node.getLabel();
		
		Dimension max = this.getLabelMaximum(style);
		
		lines = source.split("\\n");
		int n = max.height / metrics.getHeight();
		if (n < lines.length)
			lines = Arrays.copyOfRange(lines, 0, n);
		
		int etcWidth = metrics.stringWidth(Style.ETC);
		width = 0;
		for(int i = 0; i < lines.length; i++){
			lines[i] = adjust(metrics, etcWidth, max.width, lines[i]);
			int currentWidth = metrics.stringWidth(lines[i]);
			if(currentWidth > width)
				width = currentWidth;
		}
		height = lines.length * metrics.getHeight();
	}
	
	private String adjust(FontMetrics metrics, int etcWidth, int maxWidth, String line) {
		if(metrics.stringWidth(line) <= maxWidth) // everything fits
			return line;
		if (etcWidth > maxWidth) // no space at all				
			return "";
		
		// add ETC until it fits
		for(int i = line.length() - 1; i >= 0; i--){
			String adjusted = line.substring(0, i) + Style.ETC;
			if(metrics.stringWidth(adjusted) <= maxWidth) // fits
				return adjusted;
		}
		
		return null; // should never be reached
	}

//	private Dimension getLabelMinimum(Style style){
//		Size size = style.getSize(node);
//		if(size.hasMinimum())
//			return getLabelDimension(style, size.getMinimum());
//		else
//			return getLabelDimension(style, MIN_DIMENSION);
//	}
	
	private Dimension getLabelMaximum(Style style){
		Size size = style.getSize(node);
		if(size.hasMaximum())
			return getLabelDimension(style, size.getMaximum());
		else
			return getLabelDimension(style, Style.MAX_DIMENSION);
	}
	
	private Dimension getLabelDimension(Style style, Dimension dimension){
		int width = dimension.width - 2 * Style.LABEL_MARGIN;
		int height = dimension.height - 2 * Style.LABEL_MARGIN;
		if(style.hasVerticalOrientation() && style.hasPointerBoxes(node))
			width = width - Style.POINTER_BOX_HEIGHT;
		if(style.hasHorizontalOrientation() && style.hasPointerBoxes(node))
			height = height - Style.POINTER_BOX_HEIGHT;
		return new Dimension(width, height);
	}

}
