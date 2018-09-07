package web.laf.lite.ui;

import java.awt.Color;

import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import javax.swing.plaf.basic.BasicColorChooserUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;
import javax.swing.plaf.basic.BasicDesktopPaneUI;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.basic.BasicPopupMenuSeparatorUI;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;
import javax.swing.plaf.basic.BasicRootPaneUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.plaf.basic.BasicViewportUI;

import sun.swing.SwingLazyValue;

public class WebLookAndFeelLite extends BasicLookAndFeel {
	private static final long serialVersionUID = 1L;
	
	public static final String version = "0.35";
	
	// Label
	public static String labelUI = LabelUI.class.getCanonicalName ();
    public static String toolTipUI = ToolTipUI.class.getCanonicalName ();

    // Button
    public static String buttonUI = ButtonUI.class.getCanonicalName ();
    public static String toggleButtonUI = ToggleButtonUI.class.getCanonicalName ();
    public static String checkBoxUI = CheckBoxUI.class.getCanonicalName ();
    public static String radioButtonUI =  RadioButtonUI.class.getCanonicalName ();

    // Menu
    public static String menuBarUI =  MenuBarUI.class.getCanonicalName ();
    public static String menuUI =  MenuUI.class.getCanonicalName ();
    public static String popupMenuUI =  PopupMenuUI.class.getCanonicalName ();
    public static String menuItemUI =  MenuItemUI.class.getCanonicalName ();
    public static String checkBoxMenuItemUI =  BasicCheckBoxMenuItemUI.class.getCanonicalName ();
    public static String radioButtonMenuItemUI =  BasicRadioButtonMenuItemUI.class.getCanonicalName ();
    public static String popupMenuSeparatorUI =  BasicPopupMenuSeparatorUI.class.getCanonicalName ();

    // Separator
    public static String separatorUI =  SeparatorUI.class.getCanonicalName ();

    // Scroll
    public static String scrollBarUI =  ScrollBarUI.class.getCanonicalName ();
    public static String scrollPaneUI =  ScrollPaneUI.class.getCanonicalName ();

    // Text
    public static String textFieldUI =  TextFieldUI.class.getCanonicalName ();
    public static String passwordFieldUI =  PasswordFieldUI.class.getCanonicalName ();
    public static String formattedTextFieldUI =  FormattedTextFieldUI.class.getCanonicalName ();
    public static String textAreaUI =  TextAreaUI.class.getCanonicalName ();
    public static String editorPaneUI =  EditorPaneUI.class.getCanonicalName ();
    public static String textPaneUI =  TextPaneUI.class.getCanonicalName ();

    // Toolbar
    public static String toolBarUI =  ToolBarUI.class.getCanonicalName ();
    public static String toolBarSeparatorUI =  SeparatorUI.class.getCanonicalName ();

    // Table
    public static String tableUI =  BasicTableUI.class.getCanonicalName ();
    public static String tableHeaderUI =  TableHeaderUI.class.getCanonicalName ();

    // Chooser
    public static String colorChooserUI =  BasicColorChooserUI.class.getCanonicalName ();
    public static String fileChooserUI =  BasicFileChooserUI.class.getCanonicalName ();

    // Container
    public static String panelUI =  PanelUI.class.getCanonicalName ();
    public static String viewportUI =  BasicViewportUI.class.getCanonicalName ();
    public static String rootPaneUI =  BasicRootPaneUI.class.getCanonicalName ();
    public static String tabbedPaneUI =  TabbedPaneUI.class.getCanonicalName ();
    public static String splitPaneUI =  SplitPaneUI.class.getCanonicalName ();

    // Complex components
    public static String progressBarUI =  ProgressBarUI.class.getCanonicalName ();
    public static String sliderUI =  SliderUI.class.getCanonicalName ();
    public static String spinnerUI =  SpinnerUI.class.getCanonicalName ();
    public static String treeUI =  TreeUI.class.getCanonicalName ();
    public static String listUI =  ListUI.class.getCanonicalName ();
    public static String comboBoxUI =  ComboBoxUI.class.getCanonicalName ();

    // Desktop pane
    public static String desktopPaneUI =  BasicDesktopPaneUI.class.getCanonicalName ();
    public static String descktopIconUI =  BasicDesktopIconUI.class.getCanonicalName ();
    public static String internalFrameUI =  BasicInternalFrameUI.class.getCanonicalName ();

    // Option pane
    public static String optionPaneUI =  OptionPaneUI.class.getCanonicalName ();
    
	

	@Override
	public String getDescription() {
		return "SkyLookAndFeel";
	}

	@Override
	public String getID() {
		return "SkyLookAndFeel";
	}

	@Override
	public String getName() {
		return "SkyLookAndFeel";
	}

	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}
	
	/**
     * Initializes  BasicLookAndFeel UI classes.
     *
     * @param table UIDefaults table
     */
    @Override
    protected void initClassDefaults ( final UIDefaults table )
    {
    	 // Label
        table.put ( "LabelUI", labelUI );
        table.put ( "ToolTipUI", toolTipUI );

        // Button
        table.put ( "ButtonUI", buttonUI );
        table.put ( "ToggleButtonUI", toggleButtonUI );
        table.put ( "CheckBoxUI", checkBoxUI );
        table.put ( "RadioButtonUI", radioButtonUI );

        // Menu
        table.put ( "MenuBarUI", menuBarUI );
        table.put ( "MenuUI", menuUI );
        table.put ( "PopupMenuUI", popupMenuUI );
        table.put ( "MenuItemUI", menuItemUI );
        table.put ( "CheckBoxMenuItemUI", checkBoxMenuItemUI );
        table.put ( "RadioButtonMenuItemUI", radioButtonMenuItemUI );
        table.put ( "PopupMenuSeparatorUI", popupMenuSeparatorUI );

        // Separator
        table.put ( "SeparatorUI", separatorUI );

        // Scroll
        table.put ( "ScrollBarUI", scrollBarUI );
        table.put ( "ScrollPaneUI", scrollPaneUI );

        // Text
        table.put ( "TextFieldUI", textFieldUI );
        table.put ( "PasswordFieldUI", passwordFieldUI );
        table.put ( "FormattedTextFieldUI", formattedTextFieldUI );
        table.put ( "TextAreaUI", textAreaUI );
        table.put ( "EditorPaneUI", editorPaneUI );
        table.put ( "TextPaneUI", textPaneUI );

        // Toolbar
        table.put ( "ToolBarUI", toolBarUI );
        table.put ( "ToolBarSeparatorUI", toolBarSeparatorUI );

        // Table
        table.put ( "TableUI", tableUI );
        table.put ( "TableHeaderUI", tableHeaderUI );

        // Chooser
        table.put ( "ColorChooserUI", colorChooserUI );
        table.put ( "FileChooserUI", fileChooserUI );

        // Container
        table.put ( "PanelUI", panelUI );
        table.put ( "ViewportUI", viewportUI );
        table.put ( "RootPaneUI", rootPaneUI );
        table.put ( "TabbedPaneUI", tabbedPaneUI );
        table.put ( "SplitPaneUI", splitPaneUI );

        // Complex components
        table.put ( "ProgressBarUI", progressBarUI );
        table.put ( "SliderUI", sliderUI );
        table.put ( "SpinnerUI", spinnerUI );
        table.put ( "TreeUI", treeUI );
        table.put ( "ListUI", listUI );
        table.put ( "ComboBoxUI", comboBoxUI );

        // Desktop pane
        table.put ( "DesktopPaneUI", desktopPaneUI );
        table.put ( "DesktopIconUI", descktopIconUI );
        table.put ( "InternalFrameUI", internalFrameUI );

        // Option pane
        table.put ( "OptionPaneUI", optionPaneUI );
    }
    
    /**
     * Initializes WebLookAndFeel defaults (like default renderers, component borders and such).
     * This method will be called only in case WebLookAndFeel is installed through UIManager as current application LookAndFeel.
     *
     * @param table UI defaults table
     */
    @SuppressWarnings ( "UnnecessaryBoxing" )
    @Override
    protected void initComponentDefaults ( final UIDefaults table )
    {
        super.initComponentDefaults ( table );

        // Fonts
        //initializeFonts ( table );

        // JLabels
        final Color controlText = table.getColor ( "controlText" );
        table.put ( "Label.foreground", controlText );
        table.put ( "Label.disabledForeground", Color.gray );

        // JTextFields
        final Object textComponentBorder =
                new SwingLazyValue ( "javax.swing.plaf.BorderUIResource.LineBorderUIResource", new Object[]{ Color.gray } );
        table.put ( "TextField.border", textComponentBorder );

        // JTextAreas
        table.put ( "TextArea.border", textComponentBorder );

        // JEditorPanes
        table.put ( "EditorPane.border", textComponentBorder );

        // JTextPanes
        table.put ( "TextPane.border", textComponentBorder );

        // Option pane
        table.put ( "OptionPane.messageAreaBorder",
                new SwingLazyValue ( "javax.swing.plaf.BorderUIResource$EmptyBorderUIResource", new Object[]{ 0, 0, 5, 0 } ) );

        // HTML image icons

        // Scroll bars minimum size
       // table.put ( "ScrollBar.minimumThumbSize", new Dimension ( WebScrollBarStyle.minThumbWidth, WebScrollBarStyle.minThumbHeight ) );
       // table.put ( "ScrollBar.width", new Integer ( 10 ) );

        // Tree icons

        // JMenu expand spacing
        // Up-down menu expand
        table.put ( "Menu.menuPopupOffsetX", new Integer ( 0 ) );
        table.put ( "Menu.menuPopupOffsetY", new Integer ( 0 ) );
        // Left-right menu expand
        table.put ( "Menu.submenuPopupOffsetX", new Integer ( 0 ) );
        table.put ( "Menu.submenuPopupOffsetY", new Integer ( 0 ) );

        // JViewport
        table.put ( "Viewport.background", Color.white );


        // Default list renderer
        // List selection foreground
       // table.put ( "List.selectionForeground", WebListStyle.foreground );

        // Combobox selection foregrounds
        //table.put ( "ComboBox.selectionForeground", Color.black );
        // Combobox non-square arrow
        //table.put ( "ComboBox.squareButton", false );
        
        // WebComboBox actions
        table.put ( "ComboBox.ancestorInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "ESCAPE", "hidePopup", "PAGE_UP", "pageUpPassThrough", "PAGE_DOWN", "pageDownPassThrough", "HOME",
                        "homePassThrough", "END", "endPassThrough", "DOWN", "selectNext", "KP_DOWN", "selectNext", "alt DOWN",
                        "togglePopup", "alt KP_DOWN", "togglePopup", "alt UP", "togglePopup", "alt KP_UP", "togglePopup", "SPACE",
                        "spacePopup", "ENTER", "enterPressed", "UP", "selectPrevious", "KP_UP", "selectPrevious" } ) );

        // WebFileChooser actions
        table.put ( "FileChooser.ancestorInputMap", new UIDefaults.LazyInputMap (
                new Object[]{ "ESCAPE", "cancelSelection", "F2", "editFileName", "F5", "refresh", "BACK_SPACE", "Go Up", "ENTER",
                        "approveSelection", "ctrl ENTER", "approveSelection" } ) );
    }
}
