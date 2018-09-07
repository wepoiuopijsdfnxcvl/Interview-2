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
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.text.JTextComponent;

import web.laf.lite.layout.TextComponentLayout;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * User: mgarin Date: 16.05.11 Time: 20:37
 */

public class PasswordFieldUI extends BasicPasswordFieldUI implements ShapeProvider, SwingConstants, BorderMethods
{
    private boolean drawBorder = true;
    private boolean drawFocus = true;
    private int round = 2;
    private boolean drawShade = true;
    private int shadeWidth = 2;
    private boolean drawBackground = true;
    private boolean webColored = false;
    private Insets fieldMargin = new Insets ( 2, 2, 2, 2 );
    private String inputPrompt = null;
    private Font inputPromptFont = null;
    private Color inputPromptForeground = new Color ( 160, 160, 160 );
    private int inputPromptPosition = LEADING;
    private boolean hideInputPromptOnFocus = true;

    private JPasswordField passwordField;
    private JComponent leadingComponent = null;
    private JComponent trailingComponent = null;

    private boolean inputPromptSet = false;

    private FocusListener focusListener;
    private PropertyChangeListener accessibleChangeListener;
    private PropertyChangeListener marginChangeListener;
    private ComponentAdapter componentResizeListener;

    public static ComponentUI createUI ( JComponent c )
    {
        return new PasswordFieldUI ( ( JPasswordField ) c );
    }

    public PasswordFieldUI ( final JPasswordField passwordField )
    {
        this ( passwordField, true );
    }

    public PasswordFieldUI ( final JPasswordField passwordField, boolean drawBorder )
    {
        super ();
        this.drawBorder = drawBorder;
        this.passwordField = passwordField;
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        passwordField.putClientProperty ( SwingUtils.HANDLES_ENABLE_STATE, true );
        passwordField.setFocusable ( true );
        passwordField.setOpaque ( false );
        passwordField.setMargin ( new Insets ( 0, 0, 0, 0 ) );
        passwordField.setBackground ( Color.WHITE );
        passwordField.setSelectionColor ( new Color ( 210, 210, 210 ) );
        passwordField.setForeground ( Color.BLACK );
        passwordField.setSelectedTextColor ( Color.BLACK );
        passwordField.setCaretColor ( Color.GRAY );
        passwordField.setLayout ( new TextComponentLayout ( passwordField ) );

        // Updating border
        updateBorder ();

        focusListener = new FocusListener ()
        {
            @Override
            public void focusLost ( FocusEvent e )
            {
                passwordField.repaint ();
            }

            @Override
            public void focusGained ( FocusEvent e )
            {
                passwordField.repaint ();
            }
        };
        passwordField.addFocusListener ( focusListener );

        accessibleChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                updateInnerComponents ();
            }
        };
        passwordField.addPropertyChangeListener ( "enabled", accessibleChangeListener );

        marginChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                updateBorder ();
            }
        };
        passwordField.addPropertyChangeListener ( "margin", marginChangeListener );

        componentResizeListener = new ComponentAdapter ()
        {
            @Override
            public void componentResized ( ComponentEvent e )
            {
                updateBorder ();
            }
        };
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        passwordField.putClientProperty ( SwingUtils.HANDLES_ENABLE_STATE, null );

        passwordField.removeFocusListener ( focusListener );
        passwordField.removePropertyChangeListener ( "enabled", accessibleChangeListener );
        passwordField.removePropertyChangeListener ( "margin", marginChangeListener );

        cleanupLeadingComponent ();
        cleanupTrailingComponent ();
        passwordField.setLayout ( null );

        super.uninstallUI ( c );
    }

    @Override
    public Shape provideShape ()
    {
        if ( drawBorder )
        {
            return LafUtils.getWebBorderShape ( passwordField, shadeWidth, round );
        }
        else
        {
            return SwingUtils.size ( passwordField );
        }
    }

    private void updateInnerComponents ()
    {
        if ( leadingComponent != null )
        {
            leadingComponent.setEnabled ( passwordField.isEnabled () );
        }
        if ( trailingComponent != null )
        {
            trailingComponent.setEnabled ( passwordField.isEnabled () );
        }
    }

    public JComponent getLeadingComponent ()
    {
        return leadingComponent;
    }

    public void setLeadingComponent ( JComponent leadingComponent )
    {
        if ( this.leadingComponent == leadingComponent )
        {
            return;
        }

        // Removing old leading component
        cleanupLeadingComponent ();

        // New leading component
        if ( leadingComponent != null )
        {
            this.leadingComponent = leadingComponent;

            // Registering resize listener
            this.leadingComponent.addComponentListener ( componentResizeListener );

            // Adding component
            passwordField.add ( leadingComponent, TextComponentLayout.LEADING );

            // Updating components state
            updateInnerComponents ();
        }

        // Updating layout
        passwordField.revalidate ();

        // Updating border
        updateBorder ();
    }

    private void cleanupLeadingComponent ()
    {
        if ( this.leadingComponent != null )
        {
            this.leadingComponent.removeComponentListener ( componentResizeListener );
            passwordField.remove ( this.leadingComponent );
            this.leadingComponent = null;
        }
    }

    public JComponent getTrailingComponent ()
    {
        return trailingComponent;
    }

    public void setTrailingComponent ( JComponent trailingComponent )
    {
        if ( this.trailingComponent == trailingComponent )
        {
            return;
        }

        // Removing old trailing component
        cleanupTrailingComponent ();

        // New trailing component
        if ( trailingComponent != null )
        {
            this.trailingComponent = trailingComponent;

            // Registering resize listener
            this.trailingComponent.addComponentListener ( componentResizeListener );

            // Adding component
            passwordField.add ( trailingComponent, TextComponentLayout.TRAILING );

            // Updating components state
            updateInnerComponents ();
        }

        // Updating layout
        passwordField.revalidate ();

        // Updating border
        updateBorder ();
    }

    private void cleanupTrailingComponent ()
    {
        if ( this.trailingComponent != null )
        {
            this.trailingComponent.removeComponentListener ( componentResizeListener );
            passwordField.remove ( this.trailingComponent );
            this.trailingComponent = null;
        }
    }

    public void setFieldMargin ( Insets margin )
    {
        this.fieldMargin = margin;
        updateBorder ();
    }

    public Insets getFieldMargin ()
    {
        return fieldMargin;
    }

    public String getInputPrompt ()
    {
        return inputPrompt;
    }

    public void setInputPrompt ( String inputPrompt )
    {
        this.inputPrompt = inputPrompt;
        this.inputPromptSet = inputPrompt != null && !inputPrompt.trim ().equals ( "" );
        updateInputPromptView ();
    }

    public Font getInputPromptFont ()
    {
        return inputPromptFont;
    }

    public void setInputPromptFont ( Font inputPromptFont )
    {
        this.inputPromptFont = inputPromptFont;
        updateInputPromptView ();
    }

    public Color getInputPromptForeground ()
    {
        return inputPromptForeground;
    }

    public void setInputPromptForeground ( Color inputPromptForeground )
    {
        this.inputPromptForeground = inputPromptForeground;
        updateInputPromptView ();
    }

    public int getInputPromptPosition ()
    {
        return inputPromptPosition;
    }

    public void setInputPromptPosition ( int inputPromptPosition )
    {
        this.inputPromptPosition = inputPromptPosition;
        updateInputPromptView ();
    }

    public boolean isHideInputPromptOnFocus ()
    {
        return hideInputPromptOnFocus;
    }

    public void setHideInputPromptOnFocus ( boolean hideInputPromptOnFocus )
    {
        this.hideInputPromptOnFocus = hideInputPromptOnFocus;
        updateInputPromptView ();
    }

    public boolean isDrawShade ()
    {
        return drawShade;
    }

    public void setDrawShade ( boolean drawShade )
    {
        this.drawShade = drawShade;
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

    public boolean isDrawBackground ()
    {
        return drawBackground;
    }

    public void setDrawBackground ( boolean drawBackground )
    {
        this.drawBackground = drawBackground;
        updateView ();
    }

    public boolean isWebColored ()
    {
        return webColored;
    }

    public void setWebColored ( boolean webColored )
    {
        this.webColored = webColored;
        updateView ();
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
        updateView ();
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
        updateView ();
    }

    private void updateInputPromptView ()
    {
        if ( isInputPromptVisible ( getComponent () ) )
        {
            updateView ();
        }
    }

    private boolean isInputPromptVisible ( JTextComponent c )
    {
        return inputPromptSet && c.isEditable () && c.isEnabled () && ( !hideInputPromptOnFocus || !c.isFocusOwner () ) &&
                c.getText ().equals ( "" );
    }

    private void updateView ()
    {
        if ( passwordField != null )
        {
            passwordField.repaint ();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        if ( passwordField != null )
        {
            // Style border
            Insets m;
            if ( drawBorder )
            {
                m = new Insets ( shadeWidth + 1, shadeWidth + 1, shadeWidth + 1, shadeWidth + 1 );
            }
            else
            {
                m = new Insets ( 0, 0, 0, 0 );
            }

            // Taking margins into account
            boolean ltr = passwordField.getComponentOrientation ().isLeftToRight ();
            Insets margin = passwordField.getMargin ();
            if ( margin != null )
            {
                m.top += margin.top;
                m.left += ltr ? margin.left : margin.right;
                m.bottom += margin.bottom;
                m.right += ltr ? margin.right : margin.left;
            }
            if ( fieldMargin != null )
            {
                m.top += fieldMargin.top;
                m.left += ltr ? fieldMargin.left : fieldMargin.right;
                m.bottom += fieldMargin.bottom;
                m.right += ltr ? fieldMargin.right : fieldMargin.left;
            }

            // Adding component sizes into border
            Component lc = ltr ? leadingComponent : trailingComponent;
            Component tc = ltr ? trailingComponent : leadingComponent;
            if ( lc != null )
            {
                m.left += lc.getPreferredSize ().width;
            }
            if ( tc != null )
            {
                m.right += tc.getPreferredSize ().width;
            }

            // Final border
            passwordField.setBorder ( LafUtils.createWebBorder ( m ) );
        }
    }

    @Override
    protected void paintSafely ( Graphics g )
    {
        JTextComponent c = getComponent ();
        Graphics2D g2d = ( Graphics2D ) g;

        if ( c.isOpaque ())
        {
            // Paint default background
            g.setColor ( c.getBackground () );
            g.fillRect ( 0, 0, c.getWidth (), c.getHeight () );
        }

        if (drawBorder )
        {
            Object aa = LafUtils.setupAntialias ( g2d );
            if ( drawBorder )
            {
                // Border, background and shade
                final Color shadeColor;
                if ( drawShade )
                {
                    shadeColor = drawFocus && c.isFocusOwner () ? new Color ( 85, 142, 239 ) :  new Color ( 210, 210, 210 );
                }
                else
                {
                    shadeColor = null;
                }
                LafUtils.drawWebStyle ( g2d, c, shadeColor, shadeWidth, round, drawBackground, webColored );
            }
            LafUtils.restoreAntialias ( g2d, aa );
        }

        Map hints = SwingUtils.setupTextAntialias ( g2d );
        super.paintSafely ( g );
        if ( isInputPromptVisible ( c ) )
        {
            boolean ltr = c.getComponentOrientation ().isLeftToRight ();
            Rectangle b = getVisibleEditorRect ();
            Shape oc = LafUtils.intersectClip ( g2d, b );
            g2d.setFont ( inputPromptFont != null ? inputPromptFont : c.getFont () );
            g2d.setPaint ( inputPromptForeground != null ? inputPromptForeground : c.getForeground () );

            FontMetrics fm = g2d.getFontMetrics ();
            int x;
            if ( inputPromptPosition == CENTER )
            {
                x = b.x + b.width / 2 - fm.stringWidth ( inputPrompt ) / 2;
            }
            else if ( ltr && inputPromptPosition == LEADING || !ltr && inputPromptPosition == TRAILING )
            {
                x = b.x;
            }
            else
            {
                x = b.x + b.width - fm.stringWidth ( inputPrompt );
            }
            g2d.drawString ( inputPrompt, x, getBaseline ( c, c.getWidth (), c.getHeight () ) );

            g2d.setClip ( oc );
        }
        SwingUtils.restoreTextAntialias ( g2d, hints );
    }

    @Override
    protected void paintBackground ( Graphics g )
    {
        // Do not paint anything here
    }

    @Override
    public Dimension getPreferredSize ( JComponent c )
    {
        Dimension ps = super.getPreferredSize ( c );

        // Fix for Swing bug with pointless scrolling when field's default preferred size is already reached
        ps.width += 1;

        // Height might be changed due to inner components
        if ( leadingComponent != null || trailingComponent != null )
        {
            Dimension lps = c.getLayout ().preferredLayoutSize ( c );
            ps.height = Math.max ( ps.height, lps.height );
        }

        // Background painter preferred size

        return ps;
    }
}