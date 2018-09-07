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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicMenuUI;
import javax.swing.text.View;

import web.laf.lite.utils.ColorUtils;
import web.laf.lite.utils.ImageUtils;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.MathUtils;
import web.laf.lite.utils.MenuUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * Custom UI for JMenu component.
 *
 * @author Mikle Garin
 */

public class MenuUI extends BasicMenuUI implements BorderMethods
{
    /**
     * Used icons.
     */
	public static final ImageIcon arrowRightIcon = new ImageIcon ( MenuUI.class.getResource ( "icons/right.png" ) );
    public static final ImageIcon arrowLeftIcon = new ImageIcon ( MenuUI.class.getResource ( "icons/left.png" ) );

    /**
     * Style settings.
     */
    protected int round = 2;
    protected int shadeWidth = 2;
    protected Insets margin = new Insets(0,0,0,0);
    protected Color disabledFg = Color.LIGHT_GRAY;
    protected Color selectedTopBg = new Color ( 208, 208, 198 );
    protected Color selectedBottomBg = new Color ( 196, 196, 186 );
    protected int arrowGap = 15;
    protected boolean alignTextToMenuIcons = true;
    protected int iconAlignment = SwingConstants.CENTER;

    /**
     * Menu listeners.
     */
    protected MouseAdapter mouseAdapter;
    protected MenuItemChangeListener buttonModelChangeListener;

    /**
     * Runtime variables.
     */
    protected boolean mouseover = false;

    /**
     * Returns an instance of the WebMenuUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebMenuUI
     */
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( final JComponent c )
    {
        return new MenuUI ();
    }

    /**
     * Installs UI in the specified component.
     *
     * @param c component for this UI
     */
    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );

        // Default settings
        menuItem.setOpaque ( false );
        menuItem.setBackground ( selectedBottomBg );
        menuItem.setIconTextGap ( 7 );
        updateBorder ();

        // Rollover listener
        mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mouseEntered ( final MouseEvent e )
            {
                mouseover = true;
                menuItem.repaint ();
            }

            @Override
            public void mouseExited ( final MouseEvent e )
            {
                mouseover = false;
                menuItem.repaint ();
            }
        };
        menuItem.addMouseListener ( mouseAdapter );

        // Button model change listener
        buttonModelChangeListener = new MenuItemChangeListener ( menuItem );
        menuItem.getModel ().addChangeListener ( buttonModelChangeListener );
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( final JComponent c )
    {
        menuItem.removeMouseListener ( mouseAdapter );
        mouseAdapter = null;
        menuItem.getModel ().removeChangeListener ( buttonModelChangeListener );
        buttonModelChangeListener = null;

        // Restoring basic settings
        menuItem.setOpaque ( true );

        super.uninstallUI ( c );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        if ( menuItem != null )
        {
            // Actual margin
            final boolean ltr = menuItem.getComponentOrientation ().isLeftToRight ();
            final Insets m = new Insets ( margin.top, ltr ? margin.left : margin.right, margin.bottom, ltr ? margin.right : margin.left );

                // Styling borders
                final Container parent = menuItem.getParent ();
                if ( parent != null && parent instanceof JPopupMenu )
                {
                    // Retrieving popup menu rounding
                    int round = 0;
                    final javax.swing.plaf.PopupMenuUI ui = ( ( JPopupMenu ) parent ).getUI ();
                    if ( ui instanceof PopupMenuUI )
                    {
                        round = ( ( PopupMenuUI ) ui ).getRound ();
                    }

                    // Combining margin
                    m.top += 5;
                    m.left += ( ltr ? 4 : 7 ) + round;
                    m.bottom += 5;
                    m.right += ( ltr ? 7 : 4 ) + round;
                }
                else
                {
                    m.top += shadeWidth + 3;
                    m.left += shadeWidth + 3 + 2;
                    m.bottom += shadeWidth + 3;
                    m.right += shadeWidth + 3 + 2;
                }

            // Installing border
            menuItem.setBorder ( LafUtils.createWebBorder ( m ) );
        }
    }

    /**
     * Returns top-level menu border rounding.
     *
     * @return top-level menu border rounding
     */
    public int getRound ()
    {
        return round;
    }

    /**
     * Sets top-level menu border rounding.
     *
     * @param round new top-level menu border rounding
     */
    public void setRound ( final int round )
    {
        this.round = round;
    }

    /**
     * Returns top-level menu shade width.
     *
     * @return top-level menu shade width
     */
    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    /**
     * Sets top-level menu shade width.
     *
     * @param shadeWidth new top-level menu shade width
     */
    public void setShadeWidth ( final int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
    }

    /**
     * Returns menu item margin.
     *
     * @return menu item margin
     */
    public Insets getMargin ()
    {
        return margin;
    }

    /**
     * Sets menu item margin.
     *
     * @param margin new menu item margin
     */
    public void setMargin ( final Insets margin )
    {
        this.margin = margin;
        updateBorder ();
    }

    /**
     * Returns disabled menu item foreground.
     *
     * @return disabled menu item foreground
     */
    public Color getDisabledFg ()
    {
        return disabledFg;
    }

    /**
     * Sets disabled menu item foreground.
     *
     * @param foreground new disabled menu item foreground
     */
    public void setDisabledFg ( final Color foreground )
    {
        this.disabledFg = foreground;
    }

    /**
     * Returns top background color for selected item.
     *
     * @return top background color for selected item
     */
    public Color getSelectedTopBg ()
    {
        return selectedTopBg;
    }

    /**
     * Sets top background color for selected item.
     *
     * @param background new top background color for selected item
     */
    public void setSelectedTopBg ( final Color background )
    {
        this.selectedTopBg = background;
    }

    /**
     * Returns bottom background color for selected item.
     *
     * @return bottom background color for selected item
     */
    public Color getSelectedBottomBg ()
    {
        return selectedBottomBg;
    }

    /**
     * Sets bottom background color for selected item.
     *
     * @param background new bottom background color for selected item
     */
    public void setSelectedBottomBg ( final Color background )
    {
        this.selectedBottomBg = background;
    }

    /**
     * Returns gap between menu icon/text and submenu arrow.
     *
     * @return gap between menu icon/text and submenu arrow
     */
    public int getArrowGap ()
    {
        return arrowGap;
    }

    /**
     * Sets gap between menu icon/text and submenu arrow.
     *
     * @param gap new gap between menu icon/text and submenu arrow
     */
    public void setArrowGap ( final int gap )
    {
        this.arrowGap = gap;
    }

    /**
     * Returns whether should align all item texts to a single vertical line within single popup menu or not.
     *
     * @return true if should align all item texts to a single vertical line within single popup menu, false otherwise
     */
    public boolean isAlignTextToMenuIcons ()
    {
        return alignTextToMenuIcons;
    }

    /**
     * Sets whether should align all item texts to a single vertical line within single popup menu or not.
     *
     * @param align whether should align all item texts to a single vertical line within single popup menu or not
     */
    public void setAlignTextToMenuIcons ( final boolean align )
    {
        this.alignTextToMenuIcons = align;
    }

    /**
     * Returns icon alignment.
     *
     * @return icon alignment
     */
    public int getIconAlignment ()
    {
        return iconAlignment;
    }

    /**
     * Sets icon alignment
     *
     * @param alignment new icon alignment
     */
    public void setIconAlignment ( final int alignment )
    {
        this.iconAlignment = alignment;
    }

    /**
     * Returns paint used to fill north popup menu corner when this component is first in the menu.
     *
     * @return paint used to fill north popup menu corner when this component is first in the menu
     */
    public Paint getNorthCornerFill ()
    {
        return selectedTopBg;
    }

    /**
     * Returns paint used to fill south popup menu corner when this component is last in the menu.
     *
     * @return paint used to fill south popup menu corner when this component is last in the menu
     */
    public Paint getSouthCornerFill ()
    {
        return selectedTopBg;
    }

    /**
     * Paints menu decoration.
     *
     * @param g graphics context
     * @param c menu component
     */
    @Override
    public void paint ( final Graphics g, final JComponent c )
    {
        final Graphics2D g2d = ( Graphics2D ) g;
        final Object aa = LafUtils.setupAntialias ( g2d );

        final JMenu menu = ( JMenu ) c;
        final boolean ltr = menu.getComponentOrientation ().isLeftToRight ();
        final int w = menu.getWidth ();
        final int h = menu.getHeight ();
        final Insets bi = menu.getInsets ();
        final int y = bi.top;
        final int ih = h - bi.top - bi.bottom;

        // Painting background
        final ButtonModel model = menu.getModel ();
        final boolean selected = menu.isEnabled () && ( model.isArmed () || model.isSelected () );
        paintBackground ( g2d, menu, selected, ltr );

        // Painting icon
        final int iconPlaceholderWidth = MenuUtils.getIconPlaceholderWidth ( menu, alignTextToMenuIcons );
        final int gap = iconPlaceholderWidth > 0 ? menu.getIconTextGap () : 0;
        int x = ltr ? bi.left : w - bi.right - iconPlaceholderWidth;
        paintIcon ( g2d, menu, x, y, iconPlaceholderWidth, ih, selected, ltr );
        x += ltr ? ( iconPlaceholderWidth + gap ) : -gap;

        // Painting text
        final String text = menu.getText ();
        if ( text != null && text.length () > 0 )
        {
            final Map hints = SwingUtils.setupTextAntialias ( g2d );
            final Font oldFont = LafUtils.setupFont ( g, menu.getFont () );

            final FontMetrics fm = menu.getFontMetrics ( menu.getFont () );
            final View html = ( View ) menu.getClientProperty ( BasicHTML.propertyKey );
            final int tw = html != null ? ( int ) html.getPreferredSpan ( View.X_AXIS ) : fm.stringWidth ( text );

            x -= ltr ? 0 : tw;
            paintText ( g2d, menu, fm, x, y, tw, ih, selected, ltr );

            LafUtils.restoreFont ( g, oldFont );
            SwingUtils.restoreTextAntialias ( g2d, hints );
        }

        // Painting sub-menu arrow icon
        final Icon arrowIcon = getArrowIcon ( menu );
        if ( arrowIcon != null )
        {
            final Composite oc = LafUtils.setupAlphaComposite ( g2d, 0.4f, selected );
            arrowIcon.paintIcon ( menu, g2d, ltr ? w - bi.right - arrowIcon.getIconWidth () : bi.left,
                    y + ih / 2 - arrowIcon.getIconHeight () / 2 );
            LafUtils.restoreComposite ( g2d, oc, selected );
        }

        LafUtils.restoreAntialias ( g2d, aa );
    }

    /**
     * Paints menu item background.
     *
     * @param g2d      graphics context
     * @param menu     menu
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintBackground ( final Graphics2D g2d, final JMenu menu, final boolean selected, final boolean ltr )
    {

            if ( menu.getParent () instanceof JPopupMenu )
            {
                if ( selected )
                {
                    g2d.setPaint ( new GradientPaint ( 0, 0, selectedTopBg, 0, menu.getHeight (), selectedBottomBg ) );
                    g2d.fillRect ( 0, 0, menu.getWidth (), menu.getHeight () );
                }
            }
            else
            {
                if ( menu.isEnabled () && ( selected || mouseover ) )
                {
                    LafUtils.drawWebStyle ( g2d, menu, new Color ( 210, 210, 210 ), shadeWidth, round, menu.isEnabled (),
                            !selected && mouseover, selected ? ColorUtils.getIntermediateColor ( new Color ( 170, 170, 170 ), Color.GRAY, 0.5f ) : new Color ( 170, 170, 170 ) );
                }
            }
    }

    /**
     * Paints menu item icon.
     *
     * @param g2d      graphics context
     * @param menu     menu
     * @param x        icon placeholder X coordinate
     * @param y        icon placeholder Y coordinate
     * @param w        icon placeholder width
     * @param h        icon placeholder height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintIcon ( final Graphics2D g2d, final JMenu menu, final int x, final int y, final int w, final int h,
                               final boolean selected, final boolean ltr )
    {
        final Icon icon = menu.isEnabled () ? menu.getIcon () : menu.getDisabledIcon ();
        if ( icon != null )
        {
            final boolean left = ltr ? ( iconAlignment == SwingConstants.LEFT || iconAlignment == SwingConstants.LEADING ) :
                    ( iconAlignment == SwingConstants.RIGHT || iconAlignment == SwingConstants.TRAILING );
            final boolean center = iconAlignment == SwingConstants.CENTER;
            final int iconX = left ? x : center ? x + w / 2 - icon.getIconWidth () / 2 : x + w - icon.getIconWidth ();
            icon.paintIcon ( menu, g2d, iconX, y + h / 2 - icon.getIconHeight () / 2 );
        }
    }

    /**
     * Paints menu item text.
     *
     * @param g2d      graphics context
     * @param menu     menu
     * @param fm       text font metrics
     * @param x        text X coordinate
     * @param y        text rectangle Y coordinate
     * @param w        text width
     * @param h        text rectangle height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintText ( final Graphics2D g2d, final JMenu menu, final FontMetrics fm, final int x, final int y, final int w,
                               final int h, final boolean selected, final boolean ltr )
    {
        g2d.setPaint ( menu.isEnabled () ? menu.getForeground () : disabledFg );

        final View html = ( View ) menu.getClientProperty ( BasicHTML.propertyKey );
        if ( html != null )
        {
            html.paint ( g2d, new Rectangle ( x, y, w, h ) );
        }
        else
        {
            final int mnem =  -1;// : menu.getDisplayedMnemonicIndex ();
            SwingUtils.drawStringUnderlineCharAt ( g2d, menu.getText (), mnem, x, y + h / 2 + LafUtils.getTextCenterShearY ( fm ) );
        }
    }

    /**
     * Returns arrow icon displayed when sub-menu is available.
     *
     * @return arrow icon displayed when sub-menu is available
     */
    protected Icon getArrowIcon ( final JMenu menu )
    {
        if ( menu.getParent () instanceof JPopupMenu )
        {
            final boolean ltr = menu.getComponentOrientation ().isLeftToRight ();
            if ( menu.isEnabled () )
            {
                return ltr ? arrowRightIcon : arrowLeftIcon;
            }
            else
            {
                return ltr ? ImageUtils.getDisabledCopy ( "Menu.arrowRightIcon", arrowRightIcon ) :
                        ImageUtils.getDisabledCopy ( "Menu.arrowLeftIcon", arrowLeftIcon );
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns menu item preferred size.
     *
     * @param c menu item component
     * @return menu item preferred size
     */
    @Override
    public Dimension getPreferredSize ( final JComponent c )
    {
        final JMenu menu = ( JMenu ) c;
        final Insets bi = menu.getInsets ();
        final FontMetrics fm = menu.getFontMetrics ( menu.getFont () );
        final FontMetrics afm = menu.getFontMetrics ( acceleratorFont );

        // Icon
        final int iconPlaceholderWidth = MenuUtils.getIconPlaceholderWidth ( menu, alignTextToMenuIcons );

        // Text
        final View html = ( View ) menu.getClientProperty ( BasicHTML.propertyKey );
        final int textWidth;
        final int textHeight;
        if ( html != null )
        {
            // Text is HTML
            textWidth = ( int ) html.getPreferredSpan ( View.X_AXIS );
            textHeight = ( int ) html.getPreferredSpan ( View.Y_AXIS );
        }
        else
        {
            // Text isn't HTML
            final String text = menu.getText ();
            textWidth = text != null && text.length () > 0 ? fm.stringWidth ( text ) : 0;
            textHeight = fm.getHeight ();
        }

        // Icon-Text gap
        final int gap = textWidth > 0 && iconPlaceholderWidth > 0 ? menu.getIconTextGap () : 0;

        // Sub-menu arrow icon
        final Icon subMenuArrowIcon = getArrowIcon ( menu );
        final int arrowWidth = subMenuArrowIcon != null ? arrowGap + subMenuArrowIcon.getIconWidth () : 0;

        // Content height
        final int iconHeight = menu.getIcon () != null ? menu.getIcon ().getIconHeight () : 0;
        final int contentHeight = MathUtils.max ( iconHeight, textHeight, afm.getHeight () );

        return new Dimension ( bi.left + iconPlaceholderWidth + gap + textWidth + arrowWidth + bi.right,
                bi.top + contentHeight + bi.bottom );
    }
}

/**
 * Special menu item change listener required to update popup menu decoration properly.
 *
 * @author Mikle Garin
 */

class MenuItemChangeListener implements ChangeListener
{
    /**
     * Listened menu item.
     */
    protected JMenuItem menuItem;

    /**
     * Constructs new menu item change listener.
     *
     * @param menuItem menu item to listen
     */
    public MenuItemChangeListener ( final JMenuItem menuItem )
    {
        super ();
        this.menuItem = menuItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stateChanged ( final ChangeEvent e )
    {
        final Container parent = menuItem.getParent ();
        if ( parent instanceof JPopupMenu )
        {
            final JPopupMenu popupMenu = ( JPopupMenu ) parent;
            if ( popupMenu.getUI () instanceof PopupMenuUI )
            {
                final PopupMenuUI ui = ( PopupMenuUI ) popupMenu.getUI ();
                /*if ( ui.getPopupMenuStyle () == PopupMenuStyle.dropdown )
                {
                    // Checking whether this item state change affect the corner
                    final int zOrder = popupMenu.getComponentZOrder ( menuItem );
                    if ( ui.getCornerSide () == SwingConstants.NORTH && zOrder == 0 )
                    {
                        // Repainting only corner bounds
                        popupMenu.repaint ( 0, 0, popupMenu.getWidth (), menuItem.getBounds ().y );
                    }
                    else if ( ui.getCornerSide () == SwingConstants.SOUTH && zOrder == popupMenu.getComponentCount () - 1 )
                    {
                        // Repainting only corner bounds
                        final Rectangle itemBounds = menuItem.getBounds ();
                        final int y = itemBounds.y + itemBounds.height;
                        popupMenu.repaint ( 0, y, popupMenu.getWidth (), popupMenu.getHeight () - y );
                    }
                }*/
            }
        }
    }
}