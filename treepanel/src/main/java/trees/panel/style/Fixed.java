package trees.panel.style;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Fixed extends Size {
	
	private int width, height;

	public Fixed(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	@Override
	public int getWidth(String label) {
		// Ignore Label
		return width;
	}

	@Override
	public int getHeight(String label) {
		// Ignore Label
		return height;
	}

	@Override
	public String[] getLabel(Graphics g, Rectangle area, String label) {
		return trimLabel(g, area, label);
	}
	
}
