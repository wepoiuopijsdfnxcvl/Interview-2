package web.laf.lite.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.ButtonPopup;
import web.laf.lite.popup.PopupWay;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.Register;
import web.laf.lite.widget.WebButtonGroup;
import web.laf.lite.widget.WebSwitch;

public class Main {
	public static JFrame frame;
	public static void main(String[] args) {
		Register.init("weblaflitedemo");
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	 try{
            		 UIManager.setLookAndFeel("web.laf.lite.ui.WebLookAndFeelLite");
                 }
                 catch ( Throwable e ){
                     e.printStackTrace ();
                 }
            	frame = new JFrame();
            	// Menubar
            	JMenuBar menubar = new JMenuBar();
            	JMenu menu1 = new JMenu("File");
            	menu1.add(new JMenuItem("New"));
            	menu1.add(new JMenuItem("Open"));
            	menu1.add(new JSeparator(JSeparator.HORIZONTAL));
            	menu1.add(new JMenuItem("Create"));
            	menu1.add(new JMenuItem("Read"));
            	menu1.add(new JMenuItem("Update"));
            	menu1.add(new JMenuItem("Delete"));
            	menubar.add(menu1);
            	menubar.add(new JMenu("Edit"));
            	menubar.add(new JMenu("About"));
            	menubar.add(new JMenu("Help"));
            	
            	// Toolbar
            	JToolBar toolbar = new JToolBar();
            	toolbar.add(new JButton("Tool1"));
            	toolbar.add(new JSeparator(JSeparator.VERTICAL));
            	toolbar.add(new JButton("Run"));
            	toolbar.add(new JButton("Stop"));
            	toolbar.add(new JSpinner());
            	initStyle(toolbar);
            	
            	//Statusbar
            	JPanel statusbar = new JPanel(new ToolbarLayout(5, ToolbarLayout.HORIZONTAL));
            	statusbar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            	statusbar.add(new JLabel("Tooltip"));
            	statusbar.add(new JLabel("Line 1"));
            	statusbar.add(new JLabel("        "));
            	statusbar.add(new JSeparator(JSeparator.VERTICAL));
            	statusbar.add(new JLabel("Soem Stuff"));
            	
            	//JTree
            	JTree tree = new JTree();
            	tree.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            	tree.setPreferredSize(new Dimension(200, -1));
            	
            	JTabbedPane tabs = new JTabbedPane();
            	tabs.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            	JPanel panel = new JPanel(new VerticalFlowLayout(35, 35));
            	panel.add(addText("ComboBox:", new JComboBox<String>(new String[]{"Apples", "Oranges", "Mangoes"})));
            	panel.add(addText("CheckBox:", new JCheckBox()));
            	panel.add(addText("RadioButton:", new JRadioButton()));
            	panel.add(addText("Spinner:" , new JSpinner()));
            	panel.add(addText("Slider:", new JSlider()));
            	panel.add(addText("ProgressBar:", new JProgressBar()));
            	panel.add(addText("Switch1:", new WebSwitch()));
            	panel.add(addText("Switch2:", new WebSwitch()));
            	panel.add(addText("Switch3:", new WebSwitch()));
            	panel.add(addText("Switch4:", new WebSwitch()));
            	tabs.addTab("Tab1", panel);
            	
            	JPanel panel2 = new JPanel(new VerticalFlowLayout(35, 35));
            	JTextField field = new JTextField();
            	UIUtils.setInputPrompt(field, "Search");
            	panel2.add(field );
            	panel2.add(new JTextArea(" This is some Text \n\n\n Something Something"));
            	panel2.add(new JList<String>(new String[]{"this", "is", "it"}));
            	
            	tabs.addTab("Tab2", panel2);
            	tabs.addTab("Tab3", new JTable(10, 10));
            	
            	frame.setJMenuBar(menubar);
            	frame.add(toolbar, BorderLayout.NORTH);
            	frame.add(tree, BorderLayout.WEST);
            	frame.add(tabs, BorderLayout.CENTER);
            	frame.add(statusbar, BorderLayout.SOUTH);
            	frame.pack();
       	     	frame.setVisible(true);
       	     	frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
	}
	
	public static void setLaf(final String lafName){
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            		try{
            			UIManager.setLookAndFeel(lafName);
            		}
            		catch ( Throwable e ){
            			e.printStackTrace ();
            		}
            		SwingUtilities.updateComponentTreeUI(frame.getRootPane());
            }
		});
	}
	
	static JPanel addText(String text, Component c){
		JPanel hoz = new JPanel(new HorizontalFlowLayout(35));
		hoz.add(new JLabel(text));
		hoz.add(c);
		return hoz;
	}
	
	static void initStyle(JToolBar bar){
		JButton laf = new JButton("LookAndFeel");
		bar.add(laf);
        final ButtonPopup wbp = new ButtonPopup(laf,PopupWay.downRight);
        JPanel lafStyles = new JPanel ( new VerticalFlowLayout (5, 5) );
        lafStyles.setOpaque(false);
        JLabel lafTitle = new JLabel("    LookAndFeel");
        lafTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lafStyles.add(UIUtils.setBoldFont(lafTitle));
        lafStyles.add(new JSeparator(SwingConstants.HORIZONTAL));
        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        LafButton[] lafButtons = new LafButton[lafInfo.length+1];
        lafButtons[0] =	createLafItem("Web", "web.laf.lite.ui.WebLookAndFeelLite");
        lafButtons[0].setSelected(true);
        int i = 1;
        for(UIManager.LookAndFeelInfo lafName: lafInfo){
        	lafButtons[i] = createLafItem(lafName.getName(), lafName.getClassName());
        	i++;
        }
        WebButtonGroup textGroup = new WebButtonGroup(WebButtonGroup.VERTICAL, true, lafButtons);
        textGroup.setButtonsDrawFocus(false);
        lafStyles.add(textGroup);
      
        JPanel popupContent = new JPanel(new HorizontalFlowLayout(20));
        popupContent.setOpaque(false);
        popupContent.add(lafStyles);
        wbp.setContent (popupContent);
	}
	
	static LafButton createLafItem(String title, String className){
		final LafButton lafButton = new LafButton(title, className);
		lafButton .addActionListener (new ActionListener (){
            public void actionPerformed (ActionEvent e){
                    setLaf(lafButton.getClassName());
           }
        });
		return lafButton;
	}
	
	final public static JButton createMenuButton(String title){
		JButton btn = new JButton(title);
		btn.setFocusable(false);
		btn.setOpaque(false);
	    UIUtils.setRolloverDecoratedOnly(btn, true);
	    UIUtils.setRound(btn, 0);
	    UIUtils.setLeftRightSpacing(btn, 5);
	    UIUtils.setShadeWidth(btn, 0);
	    UIUtils.setInnerShadeWidth(btn, 0);
		return btn;
	} 
	
	final public static JPanel createToolPanel(String text, String iconname,final ActionListener onClick){
		final JPanel pan = new JPanel(new VerticalFlowLayout(FlowLayout.CENTER, 0, 0));
		pan.setOpaque(false);
		UIUtils.setRound(pan, 0);
		UIUtils.setShadeWidth(pan, 0);
		JButton btn = createMenuButton(text);
		if(onClick != null)
			btn.addActionListener(onClick);
		pan.add(btn);
		return pan;
	}

}

final class LafButton extends JToggleButton{
	private static final long serialVersionUID = 1L;
	String className;
	LafButton(String text, String className){
		super(text);
		this.className = className;
		setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFocusable(false);
	}
	
	public String getClassName(){
		return className;
	}
}
