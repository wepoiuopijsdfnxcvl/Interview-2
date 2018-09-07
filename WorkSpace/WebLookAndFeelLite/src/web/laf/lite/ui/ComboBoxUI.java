/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package web.laf.lite.ui;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import web.laf.lite.layout.AbstractLayoutManager;
import web.laf.lite.utils.ImageUtils;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;
import web.laf.lite.utils.UIUtils;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * User: mgarin Date: 01.06.11 Time: 14:36
 */

public class ComboBoxUI extends BasicComboBoxUI implements ShapeProvider
{
    private ImageIcon expandIcon = new ImageIcon(ComboBoxUI.class.getResource("icons/arrow.png"));
    private ImageIcon collapseIcon = ImageUtils.rotateImage180 ( expandIcon );
    private int iconSpacing = 3;
    private boolean drawBorder = true;
    private int round = 2;
    private int shadeWidth = 2;
    private boolean drawFocus = true;
    private boolean mouseWheelScrollingEnabled = true;
    
    private JButton arrow = null;

    
    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( final JComponent c )
    {
        return new ComboBoxUI ();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        final JComboBox comboBox = ( JComboBox ) c;

        // Default settings
        comboBox.setFocusable ( true );
        comboBox.setOpaque ( false );

        // Updating border
        updateBorder ();
        comboBox.setRenderer ( new ComboBoxCellRenderer ( comboBox ) );
    }


    @Override
    public void uninstallUI ( JComponent c )
    {
        arrow = null;
        super.uninstallUI ( c );
    }

    public void setEditorColumns ( int columns )
    {
        if ( editor instanceof JTextField )
        {
            ( ( JTextField ) editor ).setColumns ( columns );
        }
    }

    private void updateBorder ()
    {
        if ( drawBorder )
        {
            comboBox.setBorder ( BorderFactory.createEmptyBorder ( shadeWidth + 1, shadeWidth + 1, shadeWidth + 1, shadeWidth + 1 ) );
        }
        else
        {
            comboBox.setBorder ( null );
        }
    }

  
    @Override
    protected void installComponents ()
    {
        comboBox.setLayout ( createLayoutManager () );

        arrowButton = createArrowButton ();
        comboBox.add ( arrowButton, "1,0" );
        if ( arrowButton != null )
        {
            configureArrowButton ();
        }

        if ( comboBox.isEditable () )
        {
            addEditor ();
        }

        comboBox.add ( currentValuePane, "0,0" );
    }

   
    @Override
    protected ComboBoxEditor createEditor ()
    {
        final ComboBoxEditor editor = super.createEditor ();
        Component e = editor.getEditorComponent ();
        e.addFocusListener ( new FocusAdapter ()
        {
            @Override
            public void focusGained ( FocusEvent e )
            {
                comboBox.repaint ();
            }

            @Override
            public void focusLost ( FocusEvent e )
            {
                comboBox.repaint ();
            }
        } );
        if ( e instanceof JComponent )
        {
            ( ( JComponent ) e ).setOpaque ( false );
        }
        if ( e instanceof JTextField )
        {
            JTextField textField = ( JTextField ) e;
            textField.setMargin ( new Insets ( 0, 1, 0, 1 ) );
        }
        return editor;
    }


    @Override
    protected JButton createArrowButton ()
    {
        arrow = new JButton ();
        UIUtils.setDrawSides(arrow, false, true, false, false);
        UIUtils.setShadeWidth(arrow, 0);
        UIUtils.setRound(arrow, 0);
        UIUtils.setDrawFocus (arrow,  false );
        UIUtils.setMoveIconOnPress (arrow , false );
        arrow.setName ( "ComboBox.arrowButton" );
        arrow.setIcon ( expandIcon );
        UIUtils.setLeftRightSpacing (arrow , iconSpacing );
        return arrow;
    }


    @Override
    public void configureArrowButton ()
    {
        super.configureArrowButton ();
        if ( arrowButton != null )
        {
            arrowButton.setFocusable ( false );
        }
    }

  
    @Override
    protected ComboPopup createPopup ()
    {
        return new BasicComboPopup ( comboBox )
        {
            @Override
            protected JScrollPane createScroller ()
            {
                final JScrollPane scroll = super.createScroller ();
                scroll.setOpaque ( false );
                scroll.getViewport ().setOpaque ( false );
                
                UIUtils.setDrawBorder ( scroll, false );
                UIUtils.setDrawBorder ( scroll.getVerticalScrollBar (), false );

                return scroll;
            }

            @Override
            protected JList createList ()
            {
                final JList list = super.createList ();
                //list.setOpaque ( false );
                UIUtils.setHighlightRolloverCell (list, false );
                UIUtils.setDecorateSelection (list, false );
                return list;
            }

            @Override
            protected void configurePopup ()
            {
                super.configurePopup ();

                // Button updater
                addPopupMenuListener ( new PopupMenuListener ()
                {
        
                    @Override
                    public void popupMenuWillBecomeVisible ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( collapseIcon );

                        // Fix for combobox repaint on popup opening
                        comboBox.repaint ();
                    }

         
                    @Override
                    public void popupMenuWillBecomeInvisible ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( expandIcon );
                    }

            
                    @Override
                    public void popupMenuCanceled ( final PopupMenuEvent e )
                    {
                        arrow.setIcon ( expandIcon );
                    }
                } );
            }

            @Override
            public void show ()
            {
                comboBox.firePopupMenuWillBecomeVisible ();

                setListSelection ( comboBox.getSelectedIndex () );

                final Point location = getPopupLocation ();
                show ( comboBox, location.x, location.y );
            }

            private void setListSelection ( int selectedIndex )
            {
                if ( selectedIndex == -1 )
                {
                    list.clearSelection ();
                }
                else
                {
                    list.setSelectedIndex ( selectedIndex );
                    list.ensureIndexIsVisible ( selectedIndex );
                }
            }

            private Point getPopupLocation ()
            {
                final Dimension comboSize = comboBox.getSize ();
                comboSize.setSize ( comboSize.width - 2, getPopupHeightForRowCount ( comboBox.getMaximumRowCount () ) );
                final Rectangle popupBounds = computePopupBounds ( 0, comboBox.getBounds ().height, comboSize.width, comboSize.height );

                final Dimension scrollSize = popupBounds.getSize ();
                scroller.setMaximumSize ( scrollSize );
                scroller.setPreferredSize ( scrollSize );
                scroller.setMinimumSize ( scrollSize );
                list.revalidate ();

                return popupBounds.getLocation ();
            }
        };
    }
    
   

    public boolean isComboboxCellEditor ()
    {
        if ( comboBox != null )
        {
            final Object cellEditor = comboBox.getClientProperty ( "JComboBox.isTableCellEditor" );
            return cellEditor != null && ( Boolean ) cellEditor;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Shape provideShape ()
    {
        if ( drawBorder )
        {
            return LafUtils.getWebBorderShape ( comboBox, shadeWidth, round );
        }
        else
        {
            return SwingUtils.size ( comboBox );
        }
    }

    public ImageIcon getExpandIcon ()
    {
        return expandIcon;
    }

    public void setExpandIcon ( ImageIcon expandIcon )
    {
        this.expandIcon = expandIcon;
        if ( arrow != null && !isPopupVisible ( comboBox ) )
        {
            arrow.setIcon ( expandIcon );
        }
    }

    public ImageIcon getCollapseIcon ()
    {
        return collapseIcon;
    }

    public void setCollapseIcon ( ImageIcon collapseIcon )
    {
        this.collapseIcon = collapseIcon;
        if ( arrow != null && isPopupVisible ( comboBox ) )
        {
            arrow.setIcon ( collapseIcon );
        }
    }

    public int getIconSpacing ()
    {
        return iconSpacing;
    }

    public void setIconSpacing ( int iconSpacing )
    {
        this.iconSpacing = iconSpacing;
        if ( arrow != null )
        {
            UIUtils.setLeftRightSpacing ( arrow, iconSpacing );
        }
    }

    public boolean isDrawBorder ()
    {
        return drawBorder;
    }

    public void setDrawBorder ( boolean drawBorder )
    {
        this.drawBorder = drawBorder;
        updateBorder ();
    }

    public boolean isDrawFocus ()
    {
        return drawFocus;
    }

    public void setDrawFocus ( boolean drawFocus )
    {
        this.drawFocus = drawFocus;
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ();
    }

    public boolean isMouseWheelScrollingEnabled ()
    {
        return mouseWheelScrollingEnabled;
    }

    public void setMouseWheelScrollingEnabled ( boolean enabled )
    {
        this.mouseWheelScrollingEnabled = enabled;
    }

   
    @Override
    public void paint ( Graphics g, JComponent c )
    {
        hasFocus = comboBox.hasFocus ();
        Rectangle r = rectangleForCurrentValue ();

        // Background
        paintCurrentValueBackground ( g, r, hasFocus );

        // Selected uneditable value
        if ( !comboBox.isEditable () )
        {
            paintCurrentValue ( g, r, hasFocus );
        }
    }

    
    @Override
    public void paintCurrentValueBackground ( Graphics g, Rectangle bounds, boolean hasFocus )
    {
        Graphics2D g2d = ( Graphics2D ) g;

        if ( drawBorder )
        {
            // Border and background
            comboBox.setBackground ( new Color ( 223, 220, 213 ) );
            LafUtils.drawWebStyle ( g2d, comboBox,
                    drawFocus && SwingUtils.hasFocusOwner ( comboBox ) ? new Color ( 85, 142, 239 ) : new Color ( 210, 210, 210 ),
                    shadeWidth, round, true, !isPopupVisible ( comboBox ) );
        }
        else
        {
            // Simple background
            boolean pressed = isPopupVisible ( comboBox );
            Rectangle cb = SwingUtils.size ( comboBox );
            g2d.setPaint ( new GradientPaint ( 0, shadeWidth, pressed ? new Color ( 242, 242, 242 ) : Color.WHITE, 0,
                    comboBox.getHeight () - shadeWidth, pressed ? new Color ( 213, 213, 213 ) : new Color ( 223, 223, 223 ) ) );
            g2d.fillRect ( cb.x, cb.y, cb.width, cb.height );
        }

        // Separator line
        if ( comboBox.isEditable () )
        {
            boolean ltr = comboBox.getComponentOrientation ().isLeftToRight ();
            Insets insets = comboBox.getInsets ();
            int lx = ltr ? comboBox.getWidth () - insets.right - arrow.getWidth () - 1 : insets.left + arrow.getWidth ();

            g2d.setPaint ( comboBox.isEnabled () ? new Color ( 170, 170, 170 ) : Color.LIGHT_GRAY );
            g2d.drawLine ( lx, insets.top + 1, lx, comboBox.getHeight () - insets.bottom - 2 );
        }
    }

    
    @Override
    public void paintCurrentValue ( Graphics g, Rectangle bounds, boolean hasFocus )
    {
        ListCellRenderer renderer = comboBox.getRenderer ();
        Component c;

        if ( hasFocus && !isPopupVisible ( comboBox ) )
        {
            c = renderer.getListCellRendererComponent ( listBox, comboBox.getSelectedItem (), -1, true, false );
        }
        else
        {
            c = renderer.getListCellRendererComponent ( listBox, comboBox.getSelectedItem (), -1, false, false );
            c.setBackground ( UIManager.getColor ( "ComboBox.background" ) );
        }
        c.setFont ( comboBox.getFont () );

        if ( comboBox.isEnabled () )
        {
            c.setForeground ( comboBox.getForeground () );
            c.setBackground ( comboBox.getBackground () );
        }
        else
        {
            c.setForeground ( UIManager.getColor ( "ComboBox.disabledForeground" ) );
            c.setBackground ( UIManager.getColor ( "ComboBox.disabledBackground" ) );
        }

        boolean shouldValidate = false;
        if ( c instanceof JPanel )
        {
            shouldValidate = true;
        }

        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.width;
        int h = bounds.height;

        currentValuePane.paintComponent ( g, c, comboBox, x, y, w, h, shouldValidate );
    }

   
    @Override
    protected LayoutManager createLayoutManager ()
    {
        return new ComboBoxLayout ();
    }


    private class ComboBoxLayout extends AbstractLayoutManager
    {

        @Override
        public Dimension preferredLayoutSize ( Container parent )
        {
            return parent.getPreferredSize ();
        }


        @Override
        public Dimension minimumLayoutSize ( Container parent )
        {
            return parent.getMinimumSize ();
        }


        @Override
        public void layoutContainer ( Container parent )
        {
            JComboBox cb = ( JComboBox ) parent;
            int width = cb.getWidth ();
            int height = cb.getHeight ();

            if ( arrowButton != null )
            {
                Insets insets = getInsets ();
                int buttonHeight = height - ( insets.top + insets.bottom );
                int buttonWidth = arrowButton.getPreferredSize ().width;
                if ( cb.getComponentOrientation ().isLeftToRight () )
                {
                    arrowButton.setBounds ( width - ( insets.right + buttonWidth ), insets.top, buttonWidth, buttonHeight );
                }
                else
                {
                    arrowButton.setBounds ( insets.left, insets.top, buttonWidth, buttonHeight );
                }
            }

            if ( editor != null )
            {
                editor.setBounds ( rectangleForCurrentValue () );
            }
        }
    }
}

class ComboBoxCellRenderer extends DefaultListCellRenderer
{
    protected Component component;
    protected JLabel label;
    private int index = 0;
    private boolean select = false;
    private JList list;

    public ComboBoxCellRenderer ( Component component )
    {
        super ();
        this.component = component;
        this.label = new JLabel(){
            @Override
            public void paint ( final Graphics g)
            {
               Graphics2D g2d = ( Graphics2D ) g;
               if (select && index != -1)
               {
            	   g2d.setColor(list.getSelectionBackground ());
                   g2d.fillRect ( 0, 0, getWidth (), getHeight () );
               }
               super.paint(g);
            }
        };
    }


    @Override
    public Component getListCellRendererComponent ( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus )
    {
    	this.list = list;
    	this.index = index;
    	select = isSelected;
        label.setEnabled ( component.isEnabled () );
        label.setFont ( component.getFont () );
        label.setForeground ( isSelected ? list.getSelectionForeground () : list.getForeground () );
        label.setComponentOrientation ( list.getComponentOrientation () );

        if ( value instanceof Icon )
        {
            label.setIcon ( ( Icon ) value );
            label.setText ( "" );
        }
        else
        {
            label.setIcon ( null );
            if(value instanceof String)
            	label.setText (value.toString());
           // else if(value instanceof Integer)
           // 	renderer.setText (""+(int)value);
        }

        return label;
    }
}