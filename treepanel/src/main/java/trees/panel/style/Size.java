package trees.panel.style;

import java.awt.Dimension;

import trees.panel.style.sizes.Fixed;
import trees.panel.style.sizes.MaxVariable;
import trees.panel.style.sizes.MinVariable;
import trees.panel.style.sizes.RestrictedVariable;
import trees.panel.style.sizes.Variable;

public abstract class Size {
	
	public static Fixed FIXED(int width, int height){
		return new Fixed(width, height);
	}
	
	public static Variable VARIABLE(){
		return new Variable();
	}
	
	public static MaxVariable MAX_VARIABLE(int maxWidth, int maxHeight){
		return new MaxVariable(maxWidth, maxHeight);
	}
	
	public static MinVariable MIN_VARIABLE(int minWidth, int minHeight){
		return new MinVariable(minWidth, minHeight);
	}
	
	public static RestrictedVariable RESTRICTED_VARIABLE(int minWidth, int minHeight, int maxWidth, int maxHeight){
		return new RestrictedVariable(minWidth, minHeight, maxWidth, maxHeight);
	}
	
	public abstract boolean isFixed();
	
	public boolean isVariable(){
		return !this.isFixed();
	}
	
	public abstract boolean hasMaximum();

	public abstract boolean hasMinimum();
	
	public abstract Dimension getMaximum();

	public abstract Dimension getMinimum();

	protected abstract int getWidth(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label);
	
	protected abstract int getHeight(boolean hasVerticalOrientation, boolean hasPointerBoxes, Dimension label);
}
