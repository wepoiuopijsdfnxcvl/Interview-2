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
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.tree.TreeCellRenderer;

import web.laf.lite.utils.ColorUtils;
import web.laf.lite.utils.ImageUtils;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.ReflectUtils;
import web.laf.lite.utils.SkyTimer;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * Custom UI for JCheckBox component.
 *
 * @author Mikle Garin
 */

public class CheckBoxUI extends BasicCheckBoxUI implements ShapeProvider
{
    /**
     * Maximum border/background darkness.
     */
    public static final int MAX_DARKNESS = 5;

    /**
     * Animation updates delay in milliseconds.
     */
    public static final int UPDATE_DELAY = 40;

    /**
     * Background gradient fractions.
     */
    protected static final float[] fractions = { 0f, 1f };

    /**
     * Style settings.
     */
    protected Color borderColor = new Color ( 170, 170, 170 );
    protected Color darkBorderColor = Color.GRAY;
    protected Color disabledBorderColor = Color.LIGHT_GRAY;
    protected Color topBgColor = Color.WHITE;
    protected Color bottomBgColor = new Color ( 223, 223, 223 );
    protected Color topSelectedBgColor = new Color ( 242, 242, 242 );
    protected Color bottomSelectedBgColor = new Color ( 213, 213, 213 );
    protected int round = 2;
    protected int shadeWidth = 2;
    protected Insets margin = new Insets(0,0,0,0);
    protected boolean animated = true;
    protected boolean rolloverDarkBorderOnly = false;
    protected Stroke borderStroke = new BasicStroke ( 1.5f );
    protected int iconWidth = 16;
    protected int iconHeight = 16;

    /**
     * Icon background painting variables.
     */
    protected int bgDarkness = 0;
    protected boolean rollover;
    protected SkyTimer bgTimer;

    /**
     * Check icon painting variables.
     */
    protected CheckIcon checkIcon;
    protected boolean checking;
    protected SkyTimer checkTimer;

    /**
     * Last displayed icon rect.
     */
    protected Rectangle iconRect;

    /**
     * Checkbox to which this UI is applied.
     */
    protected JCheckBox checkBox = null;

    /**
     * Checkbox listeners.
     */
    protected PropertyChangeListener enabledStateListener;
    protected MouseAdapter mouseAdapter;
    protected ItemListener itemListener;

    /**
     * Returns an instance of the WebCheckBoxUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebCheckBoxUI
     */
    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( final JComponent c )
    {
        return new CheckBoxUI ();
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

        // Saving checkbox to local variable
        checkBox = ( JCheckBox ) c;

        // Initial check state
        checkIcon = createCheckStateIcon ();
        checkIcon.setEnabled ( checkBox.isEnabled () );
        checkIcon.setState ( checkBox.isSelected () ? CheckState.checked : CheckState.unchecked );

        // Default settings
        checkBox.setOpaque ( false );
        checkBox.setIcon ( createIcon () );
        setAnimated ( isAnimatedByDefault () );
        updateBorder ();

        // Checkbox state change listeners
        installEnabledStateListeners ();
        installRolloverListeners ();
        installStateChangeListeners ();
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( final JComponent c )
    {
        uninstallEnabledStateListeners ();
        uninstallRolloverListeners ();
        uninstallStateChangeListeners ();

        checkBox.setIcon ( null );
        checkIcon = null;
        checkBox = null;

        super.uninstallUI ( c );
    }

    /**
     * Creates check state icon.
     *
     * @return check state icon
     */
    protected CheckIcon createCheckStateIcon ()
    {
        return new SimpleCheckIcon ();
    }

    /**
     * Installs enabled state listeners.
     */
    protected void installEnabledStateListeners ()
    {
        enabledStateListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( final PropertyChangeEvent evt )
            {
                checkIcon.setEnabled ( checkBox.isEnabled () );
            }
        };
        checkBox.addPropertyChangeListener ( "enabled", enabledStateListener );
    }

    /**
     * Uninstalls enabled state listeners.
     */
    protected void uninstallEnabledStateListeners ()
    {
        checkBox.removePropertyChangeListener ( "enabled", enabledStateListener );
    }

    /**
     * Installs rollover listeners.
     */
    protected void installRolloverListeners ()
    {
        // Background fade animation
        bgTimer = new SkyTimer ( "WebCheckBoxUI.bgUpdater", UPDATE_DELAY, new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                if ( rollover && bgDarkness < MAX_DARKNESS )
                {
                    bgDarkness++;
                    checkBox.repaint ();
                }
                else if ( !rollover && bgDarkness > 0 )
                {
                    bgDarkness--;
                    checkBox.repaint ();
                }
                else
                {
                    bgTimer.stop ();
                }
            }
        } );
        mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mouseEntered ( final MouseEvent e )
            {
                rollover = true;
                if ( isAnimated () && checkBox.isEnabled () )
                {
                    bgTimer.start ();
                }
                else
                {
                    bgDarkness = MAX_DARKNESS;
                    checkBox.repaint ();
                }
            }

            @Override
            public void mouseExited ( final MouseEvent e )
            {
                rollover = false;
                if ( isAnimated () && checkBox.isEnabled () )
                {
                    bgTimer.start ();
                }
                else
                {
                    bgDarkness = 0;
                    checkBox.repaint ();
                }
            }
        };
        checkBox.addMouseListener ( mouseAdapter );
    }

    /**
     * Uninstalls rollover listeners.
     */
    protected void uninstallRolloverListeners ()
    {
        checkBox.removeMouseListener ( mouseAdapter );
    }

    /**
     * Installs state change listeners.
     */
    protected void installStateChangeListeners ()
    {
        // Selection changes animation
        checkTimer = new SkyTimer ( "WebCheckBoxUI.iconUpdater", UPDATE_DELAY, new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                if ( checkIcon.isTransitionCompleted () )
                {
                    checkIcon.finishTransition ();
                    checkTimer.stop ();
                }
                else
                {
                    checkIcon.doStep ();
                    checkBox.repaint ();
                }
            }
        } );
        itemListener = new ItemListener ()
        {
            @Override
            public void itemStateChanged ( final ItemEvent e )
            {
                performStateChanged ();
            }
        };
        checkBox.addItemListener ( itemListener );
    }

    /**
     * Performs visual state change.
     * In case animation is possible and enabled state change will be animated.
     */
    protected void performStateChanged ()
    {
        if ( isAnimationAllowed () && isAnimated () && checkBox.isEnabled () )
        {
            checkIcon.setNextState ( checkBox.isSelected () ? CheckState.checked : CheckState.unchecked );
            checkTimer.start ();
        }
        else
        {
            checkTimer.stop ();
            checkIcon.setState ( checkBox.isSelected () ? CheckState.checked : CheckState.unchecked );
            checkBox.repaint ();
        }
    }

    /**
     * Uninstalls state change listeners.
     */
    protected void uninstallStateChangeListeners ()
    {
        checkBox.removeItemListener ( itemListener );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape provideShape ()
    {
        return LafUtils.getWebBorderShape ( checkBox, getShadeWidth (), getRound () );
    }

    /**
     * Returns whether checkbox should be animated by default or not.
     *
     * @return true if checkbox should be animated by default, false otherwise
     */
    protected boolean isAnimatedByDefault ()
    {
        // Workaround for Jide tristate checkbox
        return !ReflectUtils.containsInClassOrSuperclassName ( checkBox.getClass (), "com.jidesoft.swing.TristateCheckBox" );
    }

    /**
     * Returns whether checkbox can be animated or not.
     *
     * @return true if checkbox can be animated, false otherwise
     */
    protected boolean isAnimationAllowed ()
    {
        final Container parent = checkBox.getParent ();
        if ( parent == null )
        {
            return true;
        }
        else
        {
            // Workaround for Jide checkbox list and tree
            final Class parentClass = parent.getClass ();
            return !ReflectUtils.containsInClassOrSuperclassName ( parentClass, "com.jidesoft.swing.CheckBoxList" ) &&
                    !ReflectUtils.containsInClassOrSuperclassName ( parentClass, "com.jidesoft.swing.CheckBoxTreeCellRenderer" );
        }
    }

    /**
     * Updates custom UI border.
     */
    protected void updateBorder ()
    {
        // Actual margin
        final boolean ltr = checkBox.getComponentOrientation ().isLeftToRight ();
        final Insets m = new Insets ( margin.top, ltr ? margin.left : margin.right, margin.bottom, ltr ? margin.right : margin.left );

        // Installing border
        checkBox.setBorder ( LafUtils.createWebBorder ( m ) );
    }

    /**
     * Returns component margin.
     *
     * @return component margin
     */
    public Insets getMargin ()
    {
        return margin;
    }

    /**
     * Sets component margin.
     *
     * @param margin component margin
     */
    public void setMargin ( final Insets margin )
    {
        this.margin = margin;
        updateBorder ();
    }

    /**
     * Returns whether checkbox is animated or not.
     *
     * @return true if checkbox is animated, false otherwise
     */
    public boolean isAnimated ()
    {
        return animated && ( checkBox == null || checkBox.getParent () == null ||
                !(  checkBox.getParent () instanceof TreeCellRenderer ) );
    }

    /**
     * Sets whether checkbox is animated or not.
     *
     * @param animated whether checkbox is animated or not
     */
    public void setAnimated ( boolean animated )
    {
        this.animated = animated;
    }

    /**
     * Returns whether should draw dark border only on rollover or not.
     *
     * @return true if should draw dark border only on rollover, false otherwise
     */
    public boolean isRolloverDarkBorderOnly ()
    {
        return rolloverDarkBorderOnly;
    }

    /**
     * Sets whether should draw dark border only on rollover or not.
     *
     * @param rolloverDarkBorderOnly whether should draw dark border only on rollover or not
     */
    public void setRolloverDarkBorderOnly ( final boolean rolloverDarkBorderOnly )
    {
        this.rolloverDarkBorderOnly = rolloverDarkBorderOnly;
    }

    /**
     * Returns border color.
     *
     * @return border color
     */
    public Color getBorderColor ()
    {
        return borderColor;
    }

    /**
     * Sets border color.
     *
     * @param borderColor border color
     */
    public void setBorderColor ( final Color borderColor )
    {
        this.borderColor = borderColor;
    }

    /**
     * Returns dark border color.
     *
     * @return dark border color
     */
    public Color getDarkBorderColor ()
    {
        return darkBorderColor;
    }

    /**
     * Sets dark border color.
     *
     * @param darkBorderColor dark border color
     */
    public void setDarkBorderColor ( final Color darkBorderColor )
    {
        this.darkBorderColor = darkBorderColor;
    }

    /**
     * Returns disabled border color.
     *
     * @return disabled border color
     */
    public Color getDisabledBorderColor ()
    {
        return disabledBorderColor;
    }

    /**
     * Sets disabled border color.
     *
     * @param disabledBorderColor disabled border color
     */
    public void setDisabledBorderColor ( final Color disabledBorderColor )
    {
        this.disabledBorderColor = disabledBorderColor;
    }

    /**
     * Returns top background color.
     *
     * @return top background color
     */
    public Color getTopBgColor ()
    {
        return topBgColor;
    }

    /**
     * Sets top background color.
     *
     * @param topBgColor top background color
     */
    public void setTopBgColor ( final Color topBgColor )
    {
        this.topBgColor = topBgColor;
    }

    /**
     * Returns bottom background color.
     *
     * @return bottom background color
     */
    public Color getBottomBgColor ()
    {
        return bottomBgColor;
    }

    /**
     * Sets bottom background color.
     *
     * @param bottomBgColor bottom background color
     */
    public void setBottomBgColor ( final Color bottomBgColor )
    {
        this.bottomBgColor = bottomBgColor;
    }

    /**
     * Returns top selected background color.
     *
     * @return top selected background color
     */
    public Color getTopSelectedBgColor ()
    {
        return topSelectedBgColor;
    }

    /**
     * Sets top selected background color.
     *
     * @param topSelectedBgColor top selected background color
     */
    public void setTopSelectedBgColor ( final Color topSelectedBgColor )
    {
        this.topSelectedBgColor = topSelectedBgColor;
    }

    /**
     * Returns bottom selected background color.
     *
     * @return bottom selected background color
     */
    public Color getBottomSelectedBgColor ()
    {
        return bottomSelectedBgColor;
    }

    /**
     * Sets bottom selected background color.
     *
     * @param bottomSelectedBgColor bottom selected background color
     */
    public void setBottomSelectedBgColor ( final Color bottomSelectedBgColor )
    {
        this.bottomSelectedBgColor = bottomSelectedBgColor;
    }

    /**
     * Returns border rounding.
     *
     * @return border rounding
     */
    public int getRound ()
    {
        return round;
    }

    /**
     * Sets border rounding.
     *
     * @param round border rounding
     */
    public void setRound ( final int round )
    {
        this.round = round;
    }

    /**
     * Returns border shade width.
     *
     * @return border shade width
     */
    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    /**
     * Sets border shade width.
     *
     * @param shadeWidth border shade width
     */
    public void setShadeWidth ( final int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
    }

    /**
     * Returns icon width.
     *
     * @return icon width
     */
    public int getIconWidth ()
    {
        return iconWidth;
    }

    /**
     * Sets icon width.
     *
     * @param iconWidth icon width
     */
    public void setIconWidth ( final int iconWidth )
    {
        this.iconWidth = iconWidth;
    }

    /**
     * Returns icon height.
     *
     * @return icon height
     */
    public int getIconHeight ()
    {
        return iconHeight;
    }

    /**
     * Sets icon height.
     *
     * @param iconHeight icon height
     */
    public void setIconHeight ( final int iconHeight )
    {
        this.iconHeight = iconHeight;
    }

    /**
     * Creates and returns checkbox icon.
     *
     * @return checkbox icon
     */
    protected Icon createIcon ()
    {
        return new CheckBoxIcon ();
    }

    /**
     * Returns background colors.
     *
     * @return background colors
     */
    protected Color[] getBgColors ()
    {
        if ( checkBox.isEnabled () )
        {
            float darkness = getBgDarkness ();
            if ( darkness < 1f )
            {
                return new Color[]{ ColorUtils.getIntermediateColor ( topBgColor, topSelectedBgColor, darkness ),
                        ColorUtils.getIntermediateColor ( bottomBgColor, bottomSelectedBgColor, darkness ) };
            }
            else
            {
                return new Color[]{ topSelectedBgColor, bottomSelectedBgColor };
            }
        }
        else
        {
            return new Color[]{ topBgColor, bottomBgColor };
        }
    }

    /**
     * Returns background darkness.
     *
     * @return background darkness
     */
    protected float getBgDarkness ()
    {
        return ( float ) bgDarkness / MAX_DARKNESS;
    }

    /**
     * Returns icon bounds.
     *
     * @return icon bounds
     */
    public Rectangle getIconRect ()
    {
        return iconRect != null ? new Rectangle ( iconRect ) : new Rectangle ();
    }

    /**
     * Paints checkbox.
     *
     * @param g graphics context
     * @param c painted component
     */
    @Override
    public synchronized void paint ( final Graphics g, final JComponent c )
    {
        final Map hints = SwingUtils.setupTextAntialias ( g );
        super.paint ( g, c );
        SwingUtils.restoreTextAntialias ( g, hints );
    }

    /**
     * Paints checkbox icon.
     *
     * @param g2d 2D graphics context
     * @param x   icon location X coordinate
     * @param y   icon location Y coordinate
     */
    protected void paintIcon ( final Graphics2D g2d, final int x, final int y )
    {
        // Icon background
        paintIconBackground ( g2d, x, y );

        // Check icon
        paintCheckIcon ( g2d, x, y );
    }

    /**
     * Paints checkbox icon background.
     *
     * @param g2d 2D graphics context
     * @param x   icon location X coordinate
     * @param y   icon location Y coordinate
     */
    protected void paintIconBackground ( final Graphics2D g2d, final int x, final int y )
    {
        final boolean enabled = checkBox.isEnabled ();

        // Button size and shape
        final Rectangle iconRect =
                new Rectangle ( x + shadeWidth, y + shadeWidth, iconWidth - shadeWidth * 2 - 1, iconHeight - shadeWidth * 2 - 1 );
        final RoundRectangle2D shape =
                new RoundRectangle2D.Double ( iconRect.x, iconRect.y, iconRect.width, iconRect.height, round * 2, round * 2 );

        // Shade
        if ( enabled )
        {
            final Color shadeColor = checkBox.isFocusOwner () ? new Color ( 85, 142, 239 ) : new Color ( 210, 210, 210 );
            LafUtils.drawShade ( g2d, shape, shadeColor, shadeWidth );
        }

        // Background
        final int radius = Math.round ( ( float ) Math.sqrt ( iconRect.width * iconRect.width / 2 ) );
        final int cx = iconRect.x + iconRect.width / 2;
        final int cy = iconRect.y + iconRect.height / 2;
        g2d.setPaint ( new RadialGradientPaint ( cx, cy, radius, fractions, getBgColors () ) );
        g2d.fill ( shape );

        // Border
        final Stroke os = LafUtils.setupStroke ( g2d, borderStroke );
        g2d.setPaint ( enabled ?
                ( rolloverDarkBorderOnly ? ColorUtils.getIntermediateColor ( borderColor, darkBorderColor, getBgDarkness () ) :
                        darkBorderColor ) : disabledBorderColor );
        g2d.draw ( shape );
        LafUtils.restoreStroke ( g2d, os );
    }

    /**
     * Paints checkbox check icon.
     *
     * @param g2d 2D graphics context
     * @param x   icon location X coordinate
     * @param y   icon location Y coordinate
     */
    protected void paintCheckIcon ( final Graphics2D g2d, final int x, final int y )
    {
        checkIcon.paintIcon ( checkBox, g2d, x, y, iconWidth, iconHeight );
    }

    /**
     * Paints checkbox text.
     *
     * @param g        graphics context
     * @param b        abstract button
     * @param textRect text bounds
     * @param text     text to be painted
     */
    @Override
    protected void paintText ( final Graphics g, final AbstractButton b, final Rectangle textRect, final String text )
    {
        final ButtonModel model = b.getModel ();
        final FontMetrics fm = SwingUtils.getFontMetrics ( b, g );
        final int mnemonicIndex = b.getDisplayedMnemonicIndex ();

        // Drawing text
        if ( model.isEnabled () )
        {
            // Normal text
            g.setColor ( b.getForeground () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x + getTextShiftOffset (),
                    textRect.y + fm.getAscent () + getTextShiftOffset () );
        }
        else
        {
            // Disabled text
            g.setColor ( b.getBackground ().brighter () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent () );
            g.setColor ( b.getBackground ().darker () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent () - 1 );
        }
    }

    /**
     * Custom icon for tristate checkbox.
     */
    protected class CheckBoxIcon implements Icon
    {
        /**
         * {@inheritDoc}
         */
        @Override
        public void paintIcon ( final Component c, final Graphics g, final int x, final int y )
        {
            // Updating actual icon rect
            iconRect = new Rectangle ( x, y, iconWidth, iconHeight );

            // Painting checkbox icon
            final Graphics2D g2d = ( Graphics2D ) g;
            final Object aa = LafUtils.setupAntialias ( g2d );
            CheckBoxUI.this.paintIcon ( g2d, x, y );
            LafUtils.restoreAntialias ( g2d, aa );
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconWidth ()
        {
            return CheckBoxUI.this.getIconWidth ();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIconHeight ()
        {
            return CheckBoxUI.this.getIconHeight ();
        }
    }
}

abstract class CheckIcon
{
    /**
     * Whether should paint enabled check icon or not.
     */
    protected boolean enabled = true;

    /**
     * Currently active state.
     */
    protected CheckState state = CheckState.unchecked;

    /**
     * Next active state.
     * Not null only while transition is in progress.
     */
    protected CheckState nextState = null;

    /**
     * Returns whether should paint enabled check icon or not.
     *
     * @return true if should paint enabled check icon, false otherwise
     */
    public boolean isEnabled ()
    {
        return enabled;
    }

    /**
     * Sets whether should paint enabled check icon or not.
     *
     * @param enabled whether should paint enabled check icon or not
     */
    public void setEnabled ( final boolean enabled )
    {
        this.enabled = enabled;
    }

    /**
     * Returns currently active state.
     *
     * @return currently active state
     */
    public CheckState getState ()
    {
        return state;
    }

    /**
     * Sets currently active check state.
     *
     * @param state new active check state
     */
    public void setState ( final CheckState state )
    {
        this.state = state;
        this.nextState = null;
        resetStep ();
    }

    /**
     * Returns next active state.
     *
     * @return next active state
     */
    public CheckState getNextState ()
    {
        return nextState;
    }

    /**
     * Sets next active state.
     *
     * @param nextState next active state
     */
    public void setNextState ( CheckState nextState )
    {
        this.state = this.nextState != null ? this.nextState : this.state;
        this.nextState = nextState;
    }

    /**
     * Displays next step toward the next active state.
     */
    public abstract void doStep ();

    /**
     * Resets steps according to currently set state and next state.
     */
    public abstract void resetStep ();

    /**
     * Returns whether current transition has reached its end or not.
     *
     * @return true if current transition has reached its end, false otherwise
     */
    public abstract boolean isTransitionCompleted ();

    /**
     * Finishes transition.
     */
    public abstract void finishTransition ();

    /**
     * Returns check icon width.
     *
     * @return check icon width
     */
    public abstract int getIconWidth ();

    /**
     * Returns check icon height.
     *
     * @return check icon height
     */
    public abstract int getIconHeight ();

    /**
     * Paints check icon in the specified bounds.
     *
     * @param c   component to paint check icon onto
     * @param g2d graphics context
     * @param x   icon X coordinate
     * @param y   icon Y coordinate
     * @param w   icon width
     * @param h   icon height
     */
    public abstract void paintIcon ( Component c, Graphics2D g2d, int x, int y, int w, int h );
}

class SimpleCheckIcon extends CheckIcon
{
    /**
     * Check icons for all states.
     */
    public static ArrayList<ImageIcon> CHECK_STATES = new ArrayList<ImageIcon> ();
    public static ArrayList<ImageIcon> DISABLED_CHECK_STATES = new ArrayList<ImageIcon> ();

    /**
     * Check icons initialization.
     */
    static
    {
        for ( int i = 1; i <= 4; i++ )
        {
            final ImageIcon icon = new ImageIcon(CheckBoxUI.class.getResource ( "icons/ck" + i + ".png" ));
            CHECK_STATES.add ( icon );
            DISABLED_CHECK_STATES.add ( ImageUtils.getDisabledCopy ( "WebCheckBox.disabled.check." + i, icon ) );
        }
    }

    /**
     * Current step.
     */
    protected int step = -1;

    /**
     * {@inheritDoc}
     */
    @Override
    public void doStep ()
    {
        step = nextState == CheckState.checked ? step + 1 : step - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetStep ()
    {
        step = state == CheckState.checked ? 3 : -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTransitionCompleted ()
    {
        return nextState == CheckState.unchecked && step == -1 || nextState == CheckState.checked && step == 3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishTransition ()
    {
        this.state = nextState;
        this.nextState = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconWidth ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIconHeight ()
    {
        return 16;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintIcon ( final Component c, final Graphics2D g2d, final int x, final int y, final int w, final int h )
    {
        if ( step > -1 )
        {
            final ImageIcon icon = enabled ? CHECK_STATES.get ( step ) : DISABLED_CHECK_STATES.get ( step );
            g2d.drawImage ( icon.getImage (), x + w / 2 - getIconWidth () / 2, y + h / 2 - getIconHeight () / 2, null );
        }
    }
}

enum CheckState
{
    /**
     * When checkbox is not checked.
     */
    unchecked,

    /**
     * When checkbox is checked.
     */
    checked,

    /**
     * When checkbox is partially checked. Shortly - mixed state.
     * This is a third custom checkbox state specially for WebTristateCheckBox.
     */
    mixed
}