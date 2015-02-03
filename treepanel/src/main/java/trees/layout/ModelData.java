package trees.layout;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.Arrays;

import trees.panel.style.Size;
import trees.panel.style.Style;

public class ModelData {
	
	private static final String PLACEHOLDER_LABEL = "";
	// stable
	private Object object; // the original source
	private Class<?> cls;
	private String label;
	private boolean duplicate;
	private boolean placeholder;
		
	// style-dependent
	private String[] lines;
	private int width, height;

	public static ModelData newElement(Object object, Class<?> cls, String label, Style style){
		return new ModelData(object, cls, label, false, false, style);
	}
	
	public static ModelData newDuplicate(Object object, Class<?> cls, String label, Style style){
		return new ModelData(object, cls, label, true, false, style);
	}
	
	public static ModelData newPlaceHolder(Class<?> cls, Style style){
		return new ModelData(null, cls, PLACEHOLDER_LABEL, false, true, style);
	}
	
	private ModelData(Object object, Class<?> cls, String label, boolean duplicate, boolean placeholder, Style style) {
		this.object = object;
		this.cls = cls;
		this.label = label;
		this.duplicate = duplicate;		
		this.align(style);
	}
	
	protected Object getModelObject(){
		return object;
	}

	protected Class<?> getModelClass() {
		return cls;
	}

	protected String getModelLabel() {
		return label;
	}

	protected boolean isDuplicate() {
		return duplicate;
	}

	protected boolean isPlaceholder() {
		return placeholder;
	}

	protected String[] getLines() {
		return lines;
	}

	protected Dimension getLabelSize() {
		return new Dimension(width, height);
	}

	public void align(Style style) {
		FontMetrics metrics = style.getFontMetrics(cls);		
		Dimension max = this.getLabelMaximum(style);
		
		lines = label.split("\\n");
		int n = max.height / metrics.getHeight();
		if (n < lines.length)
			lines = Arrays.copyOfRange(lines, 0, n);
		
		int etcWidth = metrics.stringWidth(Style.ETC);
		width = 0;
		for(int i = 0; i < lines.length; i++){
			lines[i] = adjustLine(metrics, etcWidth, max.width, lines[i]);
			int currentWidth = metrics.stringWidth(lines[i]);
			if(currentWidth > width)
				width = currentWidth;
		}
		height = lines.length * metrics.getHeight();
	}
	
	private String adjustLine(FontMetrics metrics, int etcWidth, int maxWidth, String line) {
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

	private Dimension getLabelMaximum(Style style){
		Size size = style.getSize(cls);
		if(size.hasMaximum())
			return getLabelDimension(style, size.getMaximum());
		else // unrestricted
			return getLabelDimension(style, Style.MAX_DIMENSION);
	}
	
	private Dimension getLabelDimension(Style style, Dimension dimension){
		// remove margin and pointer boxes
		int width = dimension.width - 2 * Style.LABEL_MARGIN;
		int height = dimension.height - 2 * Style.LABEL_MARGIN;
		if(style.hasVerticalOrientation() && style.hasPointerBoxes(cls))
			width = width - Style.POINTER_BOX_HEIGHT;
		if(style.hasHorizontalOrientation() && style.hasPointerBoxes(cls))
			height = height - Style.POINTER_BOX_HEIGHT;
		return new Dimension(width, height);
	}
}
