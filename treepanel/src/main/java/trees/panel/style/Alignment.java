package trees.panel.style;

/**
 * Possible alignment values for trees. 
 * 
 * @author Marcus Deininger
 *
 */
public enum Alignment {
	/**
	 * Tree has to be aligned on the left.
	 */
	LEFT, /**
	 * Tree has to be aligned on the right.
	 */
	RIGHT, /**
	 * Tree has to be aligned on the top.
	 */
	TOP, /**
	 * Tree has to be aligned on the bottom.
	 */
	BOTTOM, /**
	 * The root of the tree is put into center (either horizontal or vertical).
	 */
	ROOT_CENTER, /**
	 * The tree is put into center (either horizontal or vertical).
	 */
	TREE_CENTER
}