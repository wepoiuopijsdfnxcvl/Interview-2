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
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;

import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * User: mgarin Date: 17.08.11 Time: 23:01
 */

public class TextAreaUI extends BasicTextAreaUI implements BorderMethods
{

    private FocusListener focusListener;
    private PropertyChangeListener marginChangeListener;

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new TextAreaUI ();
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        final JTextComponent textComponent = getComponent ();

        // Default settings
        textComponent.setOpaque ( true );
        textComponent.setBackground ( Color.WHITE );
        textComponent.setSelectionColor ( new Color ( 210, 210, 210 ) );
        textComponent.setForeground ( Color.BLACK );
        textComponent.setSelectedTextColor ( Color.BLACK );
        textComponent.setCaretColor ( Color.GRAY );
        textComponent.setMargin ( new Insets ( 2, 2, 2, 2 ) );

        // Updating border
        updateBorder ();

        focusListener = new FocusListener ()
        {
            @Override
            public void focusLost ( FocusEvent e )
            {
                textComponent.repaint ();
            }

            @Override
            public void focusGained ( FocusEvent e )
            {
                textComponent.repaint ();
            }
        };
        textComponent.addFocusListener ( focusListener );

        marginChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                updateBorder ();
            }
        };
        textComponent.addPropertyChangeListener ("margin", marginChangeListener );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        final JTextComponent component = getComponent ();


        component.removeFocusListener ( focusListener );
        component.removePropertyChangeListener ( "margin", marginChangeListener );

        super.uninstallUI ( c );
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        JTextComponent component = getComponent ();
        if ( component != null )
        {
            // Actual margin
            Insets margin = component.getMargin ();
            boolean ltr = component.getComponentOrientation ().isLeftToRight ();
            Insets m = margin == null ? new Insets ( 0, 0, 0, 0 ) :
                    new Insets ( margin.top, ( ltr ? margin.left : margin.right ), margin.bottom, ( ltr ? margin.right : margin.left ) );

            // Installing border
            component.setBorder ( LafUtils.createWebBorder ( m ) );
        }
    }

    @Override
    protected void paintBackground ( Graphics g )
    {
        //
    }

    @Override
    protected void paintSafely ( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        JTextComponent c = getComponent ();

        if ( c.isOpaque ())
        {
            // Paint default background
            g2d.setPaint ( c.getBackground () );
            g2d.fillRect ( 0, 0, c.getWidth (), c.getHeight () );
        }

        Map hints = SwingUtils.setupTextAntialias ( g2d );
        super.paintSafely ( g );
        SwingUtils.restoreTextAntialias ( g2d, hints );
    }

    @Override
    public Dimension getPreferredSize ( JComponent c )
    {
        Dimension ps = super.getPreferredSize ( c );
        return ps;
    }
}