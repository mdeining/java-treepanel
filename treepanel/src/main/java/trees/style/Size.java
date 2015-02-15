package trees.style;

import java.awt.Dimension;

import trees.style.sizes.Fixed;
import trees.style.sizes.MaxVariable;
import trees.style.sizes.MinVariable;
import trees.style.sizes.RestrictedVariable;
import trees.style.sizes.Variable;

/**
 * Possible size values for nodes. Basically sizes may be fixed or variable 
 * with possible upper and lower bounds. For a fixed size the node label is 
 * adjusted to the size. For a variable size the node is adjusted to the label.
 * 
 * @author Marcus Deininger
 *
 */
public abstract class Size {
	
	/**
	 * Factory method for creating a fixed size.
	 * @param width Width of the node (as pixels).
	 * @param height Height of the node (as pixels).
	 * @return A new Fixed-size-object
	 */
	public static Fixed FIXED(int width, int height){
		return new Fixed(width, height);
	}
	
	/**
	 * Factory method for creating a variable size.
	 * @return A new Variable-size-object
	 */
	public static Variable VARIABLE(){
		return new Variable();
	}
	
	/**
	 * Factory method for creating a variable size with upper bounds.
	 * @param maxWidth Maximum width of the node (as pixels).
	 * @param maxHeight Maximum height of the node (as pixels).
	 * @return A new MaxVariable-size-object
	 */
	public static MaxVariable MAX_VARIABLE(int maxWidth, int maxHeight){
		return new MaxVariable(maxWidth, maxHeight);
	}
	
	/**
	 * Factory method for creating a variable size with lower bounds.
	 * @param minWidth Minimum width of the node (as pixels).
	 * @param minHeight Minimum height of the node (as pixels).
	 * @return A new MinVariable-size-object
	 */
	public static MinVariable MIN_VARIABLE(int minWidth, int minHeight){
		return new MinVariable(minWidth, minHeight);
	}
	
	/**
	 * Factory method for creating a variable size with upper and lower bounds.
	 * @param minWidth Minimum width of the node (as pixels).
	 * @param minHeight Minimum height of the node (as pixels).
	 * @param maxWidth Maximum width of the node (as pixels).
	 * @param maxHeight Maximum height of the node (as pixels).
	 * @return A new RestrictedVariable-size-object
	 */
	public static RestrictedVariable RESTRICTED_VARIABLE(int minWidth, int minHeight, int maxWidth, int maxHeight){
		return new RestrictedVariable(minWidth, minHeight, maxWidth, maxHeight);
	}
	
	/**
	 * Checks if the size is fixed.
	 * @return true, if the size is fixed.
	 */
	public abstract boolean isFixed();
	
	/**
	 * Checks if the size is variable.
	 * @return true, if the size is variable.
	 */
	public boolean isVariable(){
		return !this.isFixed();
	}
	
	/**
	 * Checks if the size has upper bounds.
	 * @return true, if the size has upper bounds.
	 */
	public abstract boolean hasMaximum();

	/**
	 * Checks if the size has lower bounds.
	 * @return true, if the size has lower bounds.
	 */
	public abstract boolean hasMinimum();
	
	/**
	 * Returns the maximum dimension of the size object.
	 * If there is no maximum <code>null</code> is returned.
	 * @return the maximum dimension of the size object.
	 */
	public abstract Dimension getMaximum();

	/**
	 * Returns the minimum dimension of the size object.
	 * If there is no minimum <code>null</code> is returned.
	 * @return the minimum dimension of the size object.
	 */
	public abstract Dimension getMinimum();

}
