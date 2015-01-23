package trees.layout;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Arrays;

import trees.panel.style.Size;
import trees.panel.style.Style;

public class Label {
	
//	private static final Dimension MIN_DIMENSION = new Dimension(0, 0);
	private static final Dimension MAX_DIMENSION = new Dimension(1920, 1080);

	private static final String ETC = "...";
	
	private Node node = null;
	private String source = null;
	private Font font = null;
	
	private String[] lines;

	private int width, height;
	
	protected Label(Node node) {
		super();
		this.node = node;
	}

	protected void initialize() {
		this.source = null;
		this.font = null;
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
		String source = node.getLabel();
		FontMetrics metrics = style.getFontMetrics();
		if(source.equals(this.source) && font == metrics.getFont())
			return;

		this.source = source;
		this.font = metrics.getFont();
		
		Dimension max = this.getLabelMaximum(style);
		
		lines = source.split("\\n");
		int n = max.height / metrics.getHeight();
		if (n < lines.length)
			lines = Arrays.copyOfRange(lines, 0, n);
		
		int etcWidth = metrics.stringWidth(ETC);
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
			String adjusted = line.substring(0, i) + ETC;
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
		if(size.hasMinimum())
			return getLabelDimension(style, size.getMaximum());
		else
			return getLabelDimension(style, MAX_DIMENSION);
	}
	
	private Dimension getLabelDimension(Style style, Dimension dimension){
		int width = dimension.width - 2 * Style.MARGIN;
		int height = dimension.height - 2 * Style.MARGIN;
		if(style.hasVerticalOrientation() && style.hasPointerBoxes(node))
			width = width - Style.POINTER_BOX_HEIGHT;
		if(style.hasHorizontalOrientation() && style.hasPointerBoxes(node))
			height = height - Style.POINTER_BOX_HEIGHT;
		return new Dimension(width, height);
	}

}
