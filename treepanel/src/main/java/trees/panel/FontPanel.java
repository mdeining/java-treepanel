package trees.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import trees.style.Style;

/**
 * This is a simple panel including a combo box for font selection 
 * and a spinner for font size selection. Possible fonts are
 * the so called core fonts (see <a href="en.wikipedia.org/wiki/Core_fonts_for_the_Web">
 * en.wikipedia.org/wiki/Core_fonts_for_the_Web</a>) plus <code>Default</code> which
 * references the Java default font. <p>
 * Only the overall font is changed, there is no way of a class-grained control. 
 * The Widget maintains the font/level separation-ratio, which means that the 
 * level separation is increased, when the font size is increased.
 * @author Marcus Deininger
 *
 */
@SuppressWarnings("serial")
public class FontPanel extends JPanel {
	
	// see: en.wikipedia.org/wiki/Core_fonts_for_the_Web
	// "Default" is an internally used java name
	public static final String[] CORE_FONTS = {"Default", "Arial", "Arial Black", "Comic Sans MS", 
		"Courier New", "Georgia", "Impact", "Times New Roman", "Trebuchet MS",
		"Verdana"};
	
	private Style style;
	private JComboBox<String> familyComboBox;
	private SpinnerNumberModel sizeModel;
	private boolean maintainLevelRatio;
	private double levelRatio;
	
	private List<ChangeListener> listeners	= new ArrayList<>();
	
	/**
	 * Creates a new font panel which can be used directly in a graphical user interface.
	 * By default, the level ratio will be maintained.
	 * @param style The style which will be changed by the widget.
	 */
	public FontPanel(Style style) {
		this(style, true);
	}

	/**
	 * Creates a new font panel which can be used directly in a graphical user interface.
	 * @param style The style which will be changed by the widget.
	 * @param maintainLevelRatio flag if the level ratio should be maintained.
	 */
	public FontPanel(Style style, boolean maintainLevelRatio) {
		super(new BorderLayout(0, 0));
		
		this.style = style;
		this.maintainLevelRatio = maintainLevelRatio;
		
		Font font = style.getFont();
		String fontFamilyName = font.getFamily();
		int fontSize = font.getSize();
		
		levelRatio = (double)style.getLevelSepartion() / style.getFontMetrics().getHeight();

		familyComboBox = new JComboBox<>(CORE_FONTS);
		this.add(familyComboBox);		
		JSpinner sizeSpinner = new JSpinner();
		sizeSpinner.setPreferredSize(new Dimension(50, 0));
		this.add(sizeSpinner, BorderLayout.EAST);
		
		// Initalization
		int selection = 0; // "Default"
		for(int i = 0; i < CORE_FONTS.length; i++)
			if(CORE_FONTS[i].equals(fontFamilyName)){
				selection = i;
				break;
			}		
		familyComboBox.setSelectedIndex(selection);
		
		sizeModel = (SpinnerNumberModel) sizeSpinner.getModel();
		sizeModel.setMinimum(1);
		sizeModel.setValue(fontSize);
		
		// Interaction
		
		familyComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateFont();
			}

		});	
		
		sizeModel.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				updateFont();
			}
		});
		
	}
	
	private void updateFont() {
		String fontFamilyName = CORE_FONTS[familyComboBox.getSelectedIndex()];
		int fontSize = (int) sizeModel.getValue();
		Font font = new Font(fontFamilyName, 0, fontSize);
		style.setFont(font);
		if(maintainLevelRatio){
			int levelSeparation = (int) (levelRatio * style.getFontMetrics().getHeight());
			style.setLevelSeparation(levelSeparation);
		}
		
		for(ChangeListener listener : listeners)
			listener.stateChanged(new ChangeEvent(font));
	}

	/**
	 * Adds a ChangeListener to the model's listener list. The ChangeListeners must 
	 * be notified when the models value changes.
	 * @param listener the ChangeListener to add
	 */
	public void addChangeListener(ChangeListener listener){
		listeners.add(listener);
	}

	/**
	 * Removes a ChangeListener from the model's listener list.
	 * @param listener the ChangeListener to remove
	 */
	public void removeChangeListener(ChangeListener listener){
		listeners.remove(listener);
	}

}
