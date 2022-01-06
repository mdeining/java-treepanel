package trees.layout;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.util.Arrays;

import trees.style.Size;
import trees.style.Style;

/**
 * Helper class for encapsulating the data of the wrapped objects.
 * 
 * @author Marcus Deininger
 *
 */
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

	/**
	 * Factory method for wrapping a regular element.
	 * @param object - The object to be wrapped.
	 * @param cls - The class of the wrapped object.
	 * @param label - The original label of the object.
	 * @param style - The displaying style.
	 * @return an initialized regular element.
	 */
	public static ModelData newElement(Object object, Class<?> cls, String label, Style style){
		return new ModelData(object, cls, label, false, false, style);
	}
	
	/**
	 * Factory method for wrapping a duplicate element.
	 * @param object - The object to be wrapped.
	 * @param cls - The class of the wrapped object.
	 * @param label - The original label of the object.
	 * @param style - The displaying style.
	 * @return an initialized element, which is marked as duplicate.
	 */
	public static ModelData newDuplicate(Object object, Class<?> cls, String label, Style style){
		return new ModelData(object, cls, label, true, false, style);
	}
	
	/**
	 * Factory method for creating a placeholder element.
	 * The placeholder is not displayed but taken into account 
	 * for the layout.
	 * @param cls - The class of the wrapped object.
	 * @param style - The displaying style.
	 * @return an initialized element, which is marked as placeholder.
	 */
	public static ModelData newPlaceHolder(Class<?> cls, Style style){
		return new ModelData(null, cls, PLACEHOLDER_LABEL, false, true, style);
	}
	
	private ModelData(Object object, Class<?> cls, String label, boolean duplicate, boolean placeholder, Style style) {
		this.object = object;
		this.cls = cls;
		this.label = label;
		this.duplicate = duplicate;
		this.placeholder = placeholder;
		this.align(style);
	}
	
	/**
	 * Gets the original model object.
	 * @return the original model object.
	 */
	protected Object getModelObject(){
		return object;
	}

	/**
	 * Gets the original model class.
	 * @return the original model class.
	 */
	protected Class<?> getModelClass() {
		return cls;
	}

	/**
	 * Gets the original model label.
	 * @return the original model label.
	 */
	protected String getModelLabel() {
		return label;
	}

	/**
	 * Checks if the model was a duplicate. Actually this means,
	 * the tree is not really a tree but has circular references.
	 * These references are cut short here.
	 * @return true, if the model was a duplicate.
	 */
	protected boolean isDuplicate() {
		return duplicate;
	}

	/**
	 * Checks if the node is a placeholder.
	 * @return true if the node is a placeholder.
	 */
	protected boolean isPlaceholder() {
		return placeholder;
	}

	/**
	 * Gets the processed lines for the label. The lines
	 * are adjusted according to the layout style.
	 * So they will fit into the node box.
	 * @return the processed lines for the label.
	 */
	protected String[] getLines() {
		return lines;
	}

	/**
	 * Gets the size of the adjusted label 
	 * with respect to the current style.
	 * @return the size of the adjusted label.
	 */
	protected Dimension getLabelSize() {
		return new Dimension(width, height);
	}

	/**
	 * Aligns the label of the object according to the 
	 * given style. The label will fit into the drawing 
	 * box of the node. If it is too large characters
	 * or lines will be removed.
	 * @param style
	 */
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
			return getLabelDimension(style, style.getMaxDimension(size.getMaximum()));
		else // unrestricted
			return getLabelDimension(style, style.getMaxDimension());
	}
	
	private Dimension getLabelDimension(Style style, Dimension dimension){
		// remove margin and pointer boxes
		int width = dimension.width - 2 * Style.LABEL_MARGIN;
		int height = dimension.height - 2 * Style.LABEL_MARGIN;
		if(style.hasHorizontalOrientation() && style.hasPointerBoxes(cls))
			width = width - Style.POINTER_BOX_HEIGHT;
		if(style.hasVerticalOrientation() && style.hasPointerBoxes(cls))
			height = height - Style.POINTER_BOX_HEIGHT;
		return new Dimension(width, height);
	}
}
