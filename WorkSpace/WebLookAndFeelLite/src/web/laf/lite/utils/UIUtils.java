package web.laf.lite.utils;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import web.laf.lite.ui.ButtonUI;
import web.laf.lite.ui.CheckBoxUI;
import web.laf.lite.ui.ComboBoxUI;
import web.laf.lite.ui.LabelUI;
import web.laf.lite.ui.ListUI;
import web.laf.lite.ui.PanelUI;
import web.laf.lite.ui.ScrollBarUI;
import web.laf.lite.ui.ScrollPaneUI;
import web.laf.lite.ui.TextFieldUI;
import web.laf.lite.ui.ToggleButtonUI;
import web.laf.lite.ui.SpinnerUI;

public class UIUtils {

	public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
		BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
		return dest; 
	}

	/*******************************************************************************************************
	 * JPanel Related UI Methods
	 *******************************************************************************************************/
	public static void setDrawFocus(JPanel panel, boolean value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setDrawFocus(value);
		}
	}

	public static void setUndecorated(JPanel panel, boolean value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setUndecorated(value);
		}
	}

	public static void setWebColored(JPanel panel, boolean value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setWebColored(value);
		}
	}

	public static void setRound(JPanel panel, int value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setRound(value);
		}
	}

	public static int getRound(JPanel panel){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			return ui.getRound();
		}
		else
			return 0;
	}

	public static void setShadeWidth(JPanel panel, int value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setMargin(JPanel panel, Insets value){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setMargin(value);
		}
	}

	public static void setDrawSides(JPanel panel, boolean top, boolean left, boolean bot, boolean right){
		if(panel.getUI() instanceof PanelUI){
			PanelUI ui = (PanelUI) panel.getUI();
			ui.setDrawSides(top, left, bot, right);
		}
	}


	/*******************************************************************************************************
	 * 		JLabel Related UI Methods
	 *******************************************************************************************************/
	public static void setDrawShade(JLabel label, boolean value){
		if(label.getUI() instanceof LabelUI){
			LabelUI ui = (LabelUI) label.getUI();
			ui.setDrawShade(value);
		}
	}

	public static void setMargin(JLabel label, Insets value){
		if(label.getUI() instanceof LabelUI){
			LabelUI ui = (LabelUI) label.getUI();
			ui.setMargin(value);
		}
	}



	/*******************************************************************************************************
	 * 		JButton Related UI Methods
	 *******************************************************************************************************/
	public static void setUndecorated(JButton button, boolean value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setUndecorated(value);
		}
	}

	public static void setDrawFocus(JButton button, boolean value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setDrawFocus(value);
		}
	}

	public static void setRolloverDecoratedOnly(JButton button, boolean value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setRolloverDecoratedOnly(value);
		}
	}

	public static void setMoveIconOnPress(JButton button, boolean value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setMoveIconOnPress(value);
		}
	}

	public static void setRound(JButton button, int value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setRound(value);
		}
	}

	public static void setShadeWidth(JButton button, int value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setInnerShadeWidth(JButton button, int value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setInnerShadeWidth(value);
		}
	}

	public static void setLeftRightSpacing(JButton button, int value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setLeftRightSpacing(value);
		}
	}

	public static void setMargin(JButton button, Insets value){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setMargin(value);
		}
	}

	public static void setDrawSides(JButton button, boolean top, boolean left, boolean bot, boolean right){
		if(button.getUI() instanceof ButtonUI){
			ButtonUI ui = (ButtonUI) button.getUI();
			ui.setDrawSides(top, left, bot, right);
		}
	}

	/*******************************************************************************************************
	 * 		JToggleButton Related UI Methods
	 *******************************************************************************************************/	
	public static void setLeftRightSpacing(JToggleButton button, int value){
		if(button.getUI() instanceof ToggleButtonUI){
			ToggleButtonUI ui = (ToggleButtonUI) button.getUI();
			ui.setLeftRightSpacing(value);
		}
	}

	public static void setShadeWidth(JToggleButton button, int value){
		if(button.getUI() instanceof ToggleButtonUI){
			ToggleButtonUI ui = (ToggleButtonUI) button.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setMargin(JToggleButton button, Insets value){
		if(button.getUI() instanceof ToggleButtonUI){
			ToggleButtonUI ui = (ToggleButtonUI) button.getUI();
			ui.setMargin(value);
		}
	}

	public static void setUndecorated(JToggleButton button, boolean value){
		if(button.getUI() instanceof ToggleButtonUI){
			ToggleButtonUI ui = (ToggleButtonUI) button.getUI();
			ui.setUndecorated(value);
		}
	}


	/*******************************************************************************************************
	 * 		JScrollPane Related UI Methods
	 *******************************************************************************************************/
	public static void setDrawBorder(JScrollPane scroll, boolean value){
		if(scroll.getUI() instanceof ScrollPaneUI){
			ScrollPaneUI ui = (ScrollPaneUI) scroll.getUI();
			ui.setDrawBorder(value);
		}
	}

	public static void setDrawBorder(JScrollBar scrollbar, boolean value){
		if(scrollbar.getUI() instanceof ScrollBarUI){
			ScrollBarUI ui = (ScrollBarUI) scrollbar.getUI();
			ui.setDrawBorder(value);
		}
	}

	/*******************************************************************************************************
	 * 		JList Related UI Methods
	 *******************************************************************************************************/
	public static void setHighlightRolloverCell(JList list, boolean value){
		if(list.getUI() instanceof ListUI){
			ListUI ui = (ListUI) list.getUI();
			ui.setHighlightRolloverCell(value);
		}
	}

	public static void setDecorateSelection(JList list, boolean value){
		if(list.getUI() instanceof ListUI){
			ListUI ui = (ListUI) list.getUI();
			ui.setDecorateSelection(value);
		}
	}

/*******************************************************************************************************
* 		JTextField Related UI Methods
*******************************************************************************************************/
	public static void setRound(JTextField text, int value){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setRound(value);
		}
	}

	public static void setShadeWidth(JTextField text, int value){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setDrawFocus(JTextField text, boolean value){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setDrawFocus(value);
		}
	}

	public static void setAlwaysDrawFocus(JTextField text, boolean value){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setAlwaysDrawFocus(value);
		}
	}

	public static void setTrailingComponent(JTextField text, JComponent trailingComponent){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setTrailingComponent(trailingComponent);
		}
	}

	public static void setLeadingComponent(JTextField text, JComponent leadingComponent){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setLeadingComponent(leadingComponent);
		}
	}

	public static void setInputPrompt(JTextField text, String value){
		if(text.getUI() instanceof TextFieldUI){
			TextFieldUI ui = (TextFieldUI) text.getUI();
			ui.setInputPrompt(value);
		}
	}

/*******************************************************************************************************
* 		JSpinner Related UI Methods
*******************************************************************************************************/
	public static void setRound(JSpinner spinner, int value){
		if(spinner.getUI() instanceof SpinnerUI){
			SpinnerUI ui = (SpinnerUI) spinner.getUI();
			ui.setRound(value);
		}
	}

	public static void setShadeWidth(JSpinner spinner, int value){
		if(spinner.getUI() instanceof SpinnerUI){
			SpinnerUI ui = (SpinnerUI) spinner.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setDrawFocus(JSpinner spinner, boolean value){
		if(spinner.getUI() instanceof SpinnerUI){
			SpinnerUI ui = (SpinnerUI) spinner.getUI();
			ui.setDrawFocus(value);
		}
	}

	public static void setDrawBorder(JSpinner spinner, boolean value){
		if(spinner.getUI() instanceof SpinnerUI){
			SpinnerUI ui = (SpinnerUI) spinner.getUI();
			ui.setDrawBorder(value);
		}
	}

/*******************************************************************************************************
* 		JComboBox Related UI Methods
*******************************************************************************************************/
	public static void setRound(JComboBox<?> comboBox, int value){
		if(comboBox.getUI() instanceof ComboBoxUI){
			ComboBoxUI ui = (ComboBoxUI) comboBox.getUI();
			ui.setRound(value);
		}
	}

	public static void setShadeWidth(JComboBox<?> comboBox, int value){
		if(comboBox.getUI() instanceof ComboBoxUI){
			ComboBoxUI ui = (ComboBoxUI) comboBox.getUI();
			ui.setShadeWidth(value);
		}
	}

	public static void setDrawFocus(JComboBox<?> comboBox, boolean value){
		if(comboBox.getUI() instanceof ComboBoxUI){
			ComboBoxUI ui = (ComboBoxUI) comboBox.getUI();
			ui.setDrawFocus(value);
		}
	}

	public static void setDrawBorder(JComboBox<?> comboBox, boolean value){
		if(comboBox.getUI() instanceof ComboBoxUI){
			ComboBoxUI ui = (ComboBoxUI) comboBox.getUI();
			ui.setDrawBorder(value);
		}
	}

/*******************************************************************************************************
* 		JCheckBox Related UI Methods
*******************************************************************************************************/	
	public static void setRound(JCheckBox checkBox, int value){
		if(checkBox.getUI() instanceof CheckBoxUI){
			CheckBoxUI ui = (CheckBoxUI) checkBox.getUI();
			ui.setRound(value);
		}
	}

	public static void setShadeWidth(JCheckBox checkBox, int value){
		if(checkBox.getUI() instanceof CheckBoxUI){
			CheckBoxUI ui = (CheckBoxUI) checkBox.getUI();
			ui.setShadeWidth(value);
		}
	}
	
/*******************************************************************************************************
* 		Font Related UI Methods
*******************************************************************************************************/
	public static <C extends Component> C setBoldFont ( final C component )
	{
		return setBoldFont ( component, true );
	}

	public static <C extends Component> C setBoldFont ( final C component, final boolean apply )
	{
		if ( apply && component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( Font.BOLD ) );
		}
		return component;
	}

	public static boolean isItalicFont ( final Component component )
	{
		return component != null && component.getFont () != null && component.getFont ().isItalic ();
	}

	public static <C extends Component> C setItalicFont ( final C component )
	{
		return setItalicFont ( component, true );
	}

	public static <C extends Component> C setItalicFont ( final C component, final boolean apply )
	{
		if ( apply && component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( Font.ITALIC ) );
		}
		return component;
	}

	public static <C extends Component> C setFontSize ( final C component, final int fontSize )
	{
		if ( component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( ( float ) fontSize ) );
		}
		return component;
	}

	public static <C extends Component> C changeFontSize ( final C component, final int change )
	{
		if ( component != null && component.getFont () != null )
		{
			final Font font = component.getFont ();
			component.setFont ( font.deriveFont ( ( float ) font.getSize () + change ) );
		}
		return component;
	}

	public static int getFontSize ( final Component component )
	{
		if ( component != null && component.getFont () != null )
		{
			return component.getFont ().getSize ();
		}
		return -1;
	}

	public static <C extends Component> C setFontStyle ( final C component, final boolean bold, final boolean italic )
	{
		final int style = bold && italic ? Font.BOLD | Font.ITALIC : ( bold ? Font.BOLD : ( italic ? Font.ITALIC : Font.PLAIN ) );
		return setFontStyle ( component, style );
	}

	public static <C extends Component> C setFontStyle ( final C component, final int style )
	{
		if ( component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( style ) );
		}
		return component;
	}

	public static <C extends Component> C setFontSizeAndStyle ( final C component, final int fontSize, final boolean bold,
			final boolean italic )
	{
		final int style = bold && italic ? Font.BOLD | Font.ITALIC : ( bold ? Font.BOLD : ( italic ? Font.ITALIC : Font.PLAIN ) );
		return setFontSizeAndStyle ( component, fontSize, style );
	}

	public static <C extends Component> C setFontSizeAndStyle ( final C component, final int fontSize, final int style )
	{
		if ( component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( style, ( float ) fontSize ) );
		}
		return component;
	}

	public static <C extends Component> C setFontName ( final C component, final String fontName )
	{
		if ( component != null && component.getFont () != null )
		{
			final Font oldFont = component.getFont ();
			component.setFont ( new Font ( fontName, oldFont.getStyle (), oldFont.getSize () ) );
		}
		return component;
	}

	public static String getFontName ( final Component component )
	{
		if ( component != null && component.getFont () != null )
		{
			return component.getFont ().getFontName ();
		}
		return null;
	}

	public static boolean isBoldFont ( final Component component )
	{
		return component != null && component.getFont () != null && component.getFont ().isBold ();
	}

	public static <C extends Component> C setPlainFont ( final C component, final boolean apply )
	{
		if ( apply && component != null && component.getFont () != null )
		{
			component.setFont ( component.getFont ().deriveFont ( Font.PLAIN ) );
		}
		return component;
	}
}





