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
import javax.swing.plaf.basic.BasicScrollPaneUI;

import web.laf.lite.focus.DefaultFocusTracker;
import web.laf.lite.focus.FocusManager;
import web.laf.lite.focus.FocusTracker;
import web.laf.lite.utils.LafUtils;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: mgarin Date: 29.04.11 Time: 15:34
 */

public class ScrollPaneUI extends BasicScrollPaneUI implements ShapeProvider
{
    private boolean drawBorder = true;
    private Color borderColor = Color.LIGHT_GRAY;
    private Color darkBorder = new Color ( 170, 170, 170 );

    private int round = 2;
    private int shadeWidth = 2;
    private Insets margin = new Insets(0,0,0,0);

    private boolean drawFocus = true;
    private boolean drawBackground = false;

    private SkyScrollPaneCorner corner;
    private PropertyChangeListener propertyChangeListener;

    /**
     * Scroll pane focus tracker.
     */
    protected FocusTracker focusTracker;

    private boolean focused = false;

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new ScrollPaneUI ();
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        scrollpane.setOpaque ( false );
        scrollpane.setBackground ( new Color ( 237, 237, 237 ) );

        // Border
        updateBorder ( scrollpane );

        // Styled scroll pane corner
        scrollpane.setCorner ( JScrollPane.LOWER_TRAILING_CORNER, getCornerComponent () );
        propertyChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                scrollpane.setCorner ( JScrollPane.LOWER_TRAILING_CORNER, getCornerComponent () );
            }
        };
        scrollpane.addPropertyChangeListener ( "componentOrientation", propertyChangeListener );

        // Focus tracker for the scroll pane content
        focusTracker = new DefaultFocusTracker ()
        {
            @Override
            public boolean isTrackingEnabled ()
            {
                return drawBorder && drawFocus;
            }

            @Override
            public void focusChanged ( boolean focused )
            {
                ScrollPaneUI.this.focused = focused;
                scrollpane.repaint ();
            }
        };
        FocusManager.addFocusTracker ( scrollpane, focusTracker );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        scrollpane.removePropertyChangeListener ( "componentOrientation", propertyChangeListener );
        scrollpane.remove ( getCornerComponent () );

        FocusManager.removeFocusTracker ( focusTracker );

        super.uninstallUI ( c );
    }

    private SkyScrollPaneCorner getCornerComponent ()
    {
        if ( corner == null )
        {
            corner = new SkyScrollPaneCorner ();
        }
        return corner;
    }

    @Override
    public Shape provideShape ()
    {
        return LafUtils.getWebBorderShape ( scrollpane, getShadeWidth (), getRound () );
    }

    private void updateBorder ( JComponent scrollPane )
    {
        if ( scrollPane != null )
        {
            if ( drawBorder )
            {
                scrollPane.setBorder ( BorderFactory
                        .createEmptyBorder ( shadeWidth + 1 + margin.top, shadeWidth + 1 + margin.left, shadeWidth + 1 + margin.bottom,
                                shadeWidth + 1 + margin.right ) );
            }
            else
            {
                scrollPane.setBorder ( BorderFactory.createEmptyBorder ( margin.top, margin.left, margin.bottom, margin.right ) );
            }
        }
    }

    public boolean isDrawBorder ()
    {
        return drawBorder;
    }

    public void setDrawBorder ( boolean drawBorder )
    {
        this.drawBorder = drawBorder;
        updateBorder ( scrollpane );
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
        updateBorder ( scrollpane );
    }

    public Insets getMargin ()
    {
        return margin;
    }

    public void setMargin ( Insets margin )
    {
        this.margin = margin;
        updateBorder ( scrollpane );
    }

    public boolean isDrawFocus ()
    {
        return drawFocus;
    }

    public void setDrawFocus ( boolean drawFocus )
    {
        this.drawFocus = drawFocus;
    }

    public boolean isDrawBackground ()
    {
        return drawBackground;
    }

    public void setDrawBackground ( boolean drawBackground )
    {
        this.drawBackground = drawBackground;
    }

    public Color getBorderColor ()
    {
        return borderColor;
    }

    public void setBorderColor ( Color borderColor )
    {
        this.borderColor = borderColor;
    }

    public Color getDarkBorder ()
    {
        return darkBorder;
    }

    public void setDarkBorder ( Color darkBorder )
    {
        this.darkBorder = darkBorder;
    }

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        if ( drawBorder )
        {
            // Border, background and shade
            LafUtils.drawWebStyle ( ( Graphics2D ) g, c, drawFocus && focused ? new Color ( 85, 142, 239 ) : new Color ( 210, 210, 210 ),
                    shadeWidth, round, drawBackground, false );
        }

        super.paint ( g, c );
    }
}

class SkyScrollPaneCorner extends JComponent
{
    // todo Move all paintings into a separate UI

    public SkyScrollPaneCorner ()
    {
        super ();
    }

    @Override
    protected void paintComponent ( Graphics g )
    {
        super.paintComponent ( g );

        int vBorder = getComponentOrientation ().isLeftToRight () ? 0 : getWidth () - 1;
        g.setColor ( new Color ( 245, 245, 245 ) );
        g.fillRect ( 0, 0, getWidth (), getHeight () );
        g.setColor ( new Color ( 230, 230, 230 ) );
        g.drawLine ( 0, 0, getWidth () - 1, 0 );
        g.drawLine ( vBorder, 0, vBorder, getHeight () - 1 );
    }
}
