package trees.panel.style.sizes;

import java.awt.Dimension;

import trees.panel.style.Size;

/**
 * Variable size with no upper and lower bounds. The nodes with
 * this size may have all different sizes. When choosing this 
 * size type, the label will be displayed completely,
 * the node size is adjusted to the label (and not vice versa).
 * 
 * @author Marcus Deininger
 *
 */
public class Variable extends Size {
	
	/**
	 * Initializing the size with no upper and lower bounds.
	 * For convenience you may use the {@link trees.panel.style.Size#VARIABLE() factory method of Size} instead
	 */
	public Variable() {
		super();
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#hasMaximum()
	 */
	@Override
	public boolean hasMaximum() {
		return false;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#hasMinimum()
	 */
	@Override
	public boolean hasMinimum() {
		return false;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#getMaximum()
	 */
	@Override
	public Dimension getMaximum() {
		return null;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#getMinimum()
	 */
	@Override
	public Dimension getMinimum() {
		return null;
	}

	/* (non-Javadoc)
	 * @see trees.panel.style.Size#isFixed()
	 */
	@Override
	public boolean isFixed() {
		return false;
	}
}
