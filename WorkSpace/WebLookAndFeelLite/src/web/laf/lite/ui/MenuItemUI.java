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
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicMenuItemUI;
import javax.swing.text.View;

import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.MathUtils;
import web.laf.lite.utils.MenuUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * Custom UI for JMenuItem component.
 *
 * @author Mikle Garin
 */

public class MenuItemUI extends BasicMenuItemUI implements BorderMethods
{
    /**
     * Style settings.
     */
    protected Insets margin = new Insets(0,0,0,0);
    protected Color disabledFg = Color.LIGHT_GRAY;
    protected Color selectedTopBg = new Color ( 208, 208, 198 );
    protected Color selectedBottomBg = new Color ( 196, 196, 186 );
    protected Color acceleratorBg = new Color ( 255, 255, 255, 200 );
    protected Color acceleratorFg = new Color ( 90, 90, 90 );
    protected Color acceleratorDisabledFg = new Color ( 170, 170, 170 );
    protected int acceleratorGap = 15;
    protected boolean alignTextToMenuIcons = true;
    protected int iconAlignment = SwingConstants.CENTER;

    /**
     * Menu item listeners.
     */
    protected PropertyChangeListener propertyChangeListener;
    protected ChangeListener buttonModelChangeListener;

    /**
     * Returns an instance of the WebMenuItemUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebMenuItemUI
     */
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( final JComponent c )
    {
        return new MenuItemUI ();
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
        menuItem.setIconTextGap ( 7 );
        updateBorder ();
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
        // Uninstalling painter

        // Removing listeners
        propertyChangeListener = null;
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

                // Retrieving popup menu rounding
                int round = 0;
                final Container parent = menuItem.getParent ();
                if ( parent != null && parent instanceof JPopupMenu )
                {
                    final javax.swing.plaf.PopupMenuUI ui = ( ( JPopupMenu ) parent ).getUI ();
                    if ( ui instanceof PopupMenuUI )
                    {
                        round = ( ( PopupMenuUI ) ui ).getRound ();
                    }
                }

                // Styling borders
                m.top += 5;
                m.left += ( ltr ? 4 : 7 ) + round;
                m.bottom += 5;
                m.right += ( ltr ? 7 : 4 ) + round;

            // Installing border
            menuItem.setBorder ( LafUtils.createWebBorder ( m ) );
        }
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
     * Returns accelerator text background.
     *
     * @return accelerator text background
     */
    public Color getAcceleratorBg ()
    {
        return acceleratorBg;
    }

    /**
     * Sets accelerator text background.
     *
     * @param background new accelerator text background
     */
    public void setAcceleratorBg ( final Color background )
    {
        this.acceleratorBg = background;
    }

    /**
     * Returns accelerator foreground.
     *
     * @return accelerator foreground
     */
    public Color getAcceleratorFg ()
    {
        return acceleratorFg;
    }

    /**
     * Sets accelerator foreground.
     *
     * @param foreground new accelerator foreground
     */
    public void setAcceleratorFg ( final Color foreground )
    {
        this.acceleratorFg = foreground;
    }

    /**
     * Returns disabled accelerator foreground.
     *
     * @return disabled accelerator foreground
     */
    public Color getAcceleratorDisabledFg ()
    {
        return acceleratorDisabledFg;
    }

    /**
     * Sets disabled accelerator foreground.
     *
     * @param foreground new disabled accelerator foreground
     */
    public void setAcceleratorDisabledFg ( final Color foreground )
    {
        this.acceleratorDisabledFg = foreground;
    }

    /**
     * Returns gap between menu item icon/text and accelerator.
     *
     * @return gap between menu item icon/text and accelerator
     */
    public int getAcceleratorGap ()
    {
        return acceleratorGap;
    }

    /**
     * Sets gap between menu icon/text and accelerator.
     *
     * @param gap new gap between menu icon/text and accelerator
     */
    public void setAcceleratorGap ( final int gap )
    {
        this.acceleratorGap = gap;
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
     * Paints menu item decoration.
     *
     * @param g graphics context
     * @param c menu item component
     */
    @Override
    public void paint ( final Graphics g, final JComponent c )
    {
        final Graphics2D g2d = ( Graphics2D ) g;
        final Object aa = LafUtils.setupAntialias ( g2d );

        final JMenuItem menuItem = ( JMenuItem ) c;
        final boolean ltr = menuItem.getComponentOrientation ().isLeftToRight ();
        final int w = menuItem.getWidth ();
        final int h = menuItem.getHeight ();
        final Insets bi = menuItem.getInsets ();
        final int y = bi.top;
        final int ih = h - bi.top - bi.bottom;
        final ButtonModel model = menuItem.getModel ();
        final boolean selected = menuItem.isEnabled () && model.isArmed ();

        // Painting background and icon
        final int iconPlaceholderWidth = MenuUtils.getIconPlaceholderWidth ( menuItem, alignTextToMenuIcons );
        final int gap = iconPlaceholderWidth > 0 ? menuItem.getIconTextGap () : 0;
        int x = ltr ? bi.left : w - bi.right - iconPlaceholderWidth;
        paintBackground ( g2d, menuItem, x, y, iconPlaceholderWidth, ih, selected, ltr );
        paintIcon ( g2d, menuItem, x, y, iconPlaceholderWidth, ih, selected, ltr );
        x += ltr ? ( iconPlaceholderWidth + gap ) : -gap;

        // Painting text and accelerator
        final String text = menuItem.getText ();
        final boolean hasText = text != null && text.length () > 0;
        final String accText = MenuUtils.getAcceleratorText ( menuItem );
        final boolean hasAccelerator = accText != null;
        if ( hasText || hasAccelerator )
        {
            final Map hints = SwingUtils.setupTextAntialias ( g2d );
            final Font oldFont = LafUtils.setupFont ( g, menuItem.getFont () );

            // Painting text
            if ( hasText )
            {
                final FontMetrics fm = menuItem.getFontMetrics ( menuItem.getFont () );
                final View html = ( View ) menuItem.getClientProperty ( BasicHTML.propertyKey );
                final int tw = html != null ? ( int ) html.getPreferredSpan ( View.X_AXIS ) : fm.stringWidth ( text );

                x -= ltr ? 0 : tw;
                paintText ( g2d, menuItem, fm, x, y, tw, ih, selected, ltr );
            }

            // Painting accelerator text
            if ( hasAccelerator )
            {
                final FontMetrics afm = menuItem.getFontMetrics ( acceleratorFont );
                final int aw = afm.stringWidth ( accText );

                x = ltr ? w - bi.right - aw : bi.left;
                paintAcceleratorText ( g2d, menuItem, accText, afm, x, y, aw, ih, selected, ltr );
            }

            LafUtils.restoreFont ( g, oldFont );
            SwingUtils.restoreTextAntialias ( g2d, hints );
        }

        LafUtils.restoreAntialias ( g2d, aa );
    }

    /**
     * Paints menu item background.
     *
     * @param g2d      graphics context
     * @param menuItem menu item
     * @param x        icon placeholder X coordinate
     * @param y        icon placeholder Y coordinate
     * @param w        icon placeholder width
     * @param h        icon placeholder height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintBackground ( final Graphics2D g2d, final JMenuItem menuItem, final int x, final int y, final int w, final int h,
                                     final boolean selected, final boolean ltr )
    {
            if ( selected )
            {
                g2d.setPaint ( new GradientPaint ( 0, 0, selectedTopBg, 0, menuItem.getHeight (), selectedBottomBg ) );
                g2d.fillRect ( 0, 0, menuItem.getWidth (), menuItem.getHeight () );
            }
    }

    /**
     * Paints menu item icon.
     *
     * @param g2d      graphics context
     * @param menuItem menu item
     * @param x        icon placeholder X coordinate
     * @param y        icon placeholder Y coordinate
     * @param w        icon placeholder width
     * @param h        icon placeholder height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintIcon ( final Graphics2D g2d, final JMenuItem menuItem, final int x, final int y, final int w, final int h,
                               final boolean selected, final boolean ltr )
    {
        final boolean enabled = menuItem.isEnabled ();
        final Icon icon = menuItem.isSelected () && menuItem.getSelectedIcon () != null ?
                ( enabled ? menuItem.getSelectedIcon () : menuItem.getDisabledSelectedIcon () ) :
                ( enabled ? menuItem.getIcon () : menuItem.getDisabledIcon () );
        if ( icon != null )
        {
            final boolean left = ltr ? ( iconAlignment == SwingConstants.LEFT || iconAlignment == SwingConstants.LEADING ) :
                    ( iconAlignment == SwingConstants.RIGHT || iconAlignment == SwingConstants.TRAILING );
            final boolean center = iconAlignment == SwingConstants.CENTER;
            final int iconX = left ? x : center ? x + w / 2 - icon.getIconWidth () / 2 : x + w - icon.getIconWidth ();
            icon.paintIcon ( menuItem, g2d, iconX, y + h / 2 - icon.getIconHeight () / 2 );
        }
    }

    /**
     * Paints menu item text.
     *
     * @param g2d      graphics context
     * @param menuItem menu item
     * @param fm       text font metrics
     * @param x        text X coordinate
     * @param y        text rectangle Y coordinate
     * @param w        text width
     * @param h        text rectangle height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintText ( final Graphics2D g2d, final JMenuItem menuItem, final FontMetrics fm, final int x, final int y, final int w,
                               final int h, final boolean selected, final boolean ltr )
    {
        g2d.setPaint ( menuItem.isEnabled () ? menuItem.getForeground () : disabledFg );

        final View html = ( View ) menuItem.getClientProperty ( BasicHTML.propertyKey );
        if ( html != null )
        {
            html.paint ( g2d, new Rectangle ( x, y, w, h ) );
        }
        else
        {
            final int mnem = -1; //: menuItem.getDisplayedMnemonicIndex ();
            SwingUtils.drawStringUnderlineCharAt ( g2d, menuItem.getText (), mnem, x, y + h / 2 + LafUtils.getTextCenterShearY ( fm ) );
        }
    }

    /**
     * Paints menu item accelerator text.
     *
     * @param g2d      graphics context
     * @param menuItem menu item
     * @param accText  accelerator text
     * @param fm       accelerator text font metrics
     * @param x        accelerator text X coordinate
     * @param y        accelerator text rectangle Y coordinate
     * @param w        accelerator text width
     * @param h        accelerator text rectangle height
     * @param selected whether menu item is selected or not
     * @param ltr      whether menu item has left-to-right orientation or not
     */
    @SuppressWarnings ("UnusedParameters")
    protected void paintAcceleratorText ( final Graphics2D g2d, final JMenuItem menuItem, final String accText, final FontMetrics fm,
                                          final int x, final int y, final int w, final int h, final boolean selected, final boolean ltr )
    {
        if ( selected && acceleratorBg != null )
        {
            final int th = fm.getHeight ();
            g2d.setPaint ( acceleratorBg );
            g2d.fillRoundRect ( x - 3, y + h / 2 - th / 2, w + 6, th, 4, 4 );
        }

        g2d.setPaint ( menuItem.isEnabled () ? acceleratorFg : acceleratorDisabledFg );
        g2d.drawString ( accText, x, y + h / 2 + LafUtils.getTextCenterShearY ( fm ) );
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
        final JMenuItem menuItem = ( JMenuItem ) c;
        final Insets bi = menuItem.getInsets ();
        final FontMetrics fm = menuItem.getFontMetrics ( menuItem.getFont () );
        final FontMetrics afm = menuItem.getFontMetrics ( acceleratorFont );

        // Icon
        final int iconPlaceholderWidth = MenuUtils.getIconPlaceholderWidth ( menuItem, alignTextToMenuIcons );

        // Text
        final View html = ( View ) menuItem.getClientProperty ( BasicHTML.propertyKey );
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
            final String text = menuItem.getText ();
            textWidth = text != null && text.length () > 0 ? fm.stringWidth ( text ) : 0;
            textHeight = fm.getHeight ();
        }

        // Icon-Text gap
        final int gap = textWidth > 0 && iconPlaceholderWidth > 0 ? menuItem.getIconTextGap () : 0;

        // Acceleration text and its gap
        final String accelerationText = MenuUtils.getAcceleratorText ( menuItem );
        final int accWidth = accelerationText != null ? acceleratorGap + afm.stringWidth ( accelerationText ) : 0;

        // Content height
        final int iconHeight = menuItem.getIcon () != null ? menuItem.getIcon ().getIconHeight () : 0;
        final int contentHeight = MathUtils.max ( iconHeight, textHeight, afm.getHeight () );

        return new Dimension ( bi.left + iconPlaceholderWidth + gap + textWidth + accWidth + bi.right, bi.top + contentHeight + bi.bottom );
    }
}