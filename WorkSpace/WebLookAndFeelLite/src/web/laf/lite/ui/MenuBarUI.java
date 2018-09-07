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
import javax.swing.plaf.basic.BasicMenuBarUI;

import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.utils.LafUtils;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * User: mgarin Date: 15.08.11 Time: 20:24
 */

public class MenuBarUI extends BasicMenuBarUI implements ShapeProvider
{
    private boolean undecorated = false;
    private int round = 4;
    private int shadeWidth = 0;
    private MenuBarStyle menuBarStyle = MenuBarStyle.attached;
    private Color borderColor = new Color ( 170, 170, 170 );

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new MenuBarUI ();
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        menuBar.setLayout ( new ToolbarLayout ( 0 ) );
        menuBar.setOpaque ( false );

        // Updating border
        updateBorder ( menuBar );
    }

    @Override
    public Shape provideShape ()
    {
        return LafUtils.getWebBorderShape ( menuBar, getShadeWidth (), getRound () );
    }

    private void updateBorder ( final JComponent c )
    {
        final Insets insets;
        if ( !undecorated )
        {
            if ( menuBarStyle.equals ( MenuBarStyle.attached ) )
            {
                insets = new Insets ( 0, 0, 1 + shadeWidth, 0 );
            }
            else
            {
                insets = new Insets ( 1 + shadeWidth, 1 + shadeWidth, 1 + shadeWidth, 1 + shadeWidth );
            }
        }
        else
        {
            insets = new Insets ( 0, 0, 0, 0 );
        }
        c.setBorder ( LafUtils.createWebBorder ( insets ) );
    }

    public boolean isUndecorated ()
    {
        return undecorated;
    }

    public void setUndecorated ( boolean undecorated )
    {
        this.undecorated = undecorated;
        updateBorder ( menuBar );
    }

    public MenuBarStyle getMenuBarStyle ()
    {
        return menuBarStyle;
    }

    public void setMenuBarStyle ( MenuBarStyle menuBarStyle )
    {
        this.menuBarStyle = menuBarStyle;
        updateBorder ( menuBar );
    }

    public Color getBorderColor ()
    {
        return borderColor;
    }

    public void setBorderColor ( Color borderColor )
    {
        this.borderColor = borderColor;
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
        updateBorder ( menuBar );
    }

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        if ( !undecorated )
        {
            Graphics2D g2d = ( Graphics2D ) g;

            if ( menuBarStyle == MenuBarStyle.attached )
            {
                Shape border = new Line2D.Double ( 0, c.getHeight () - 1 - shadeWidth, c.getWidth () - 1, c.getHeight () - 1 - shadeWidth );

                LafUtils.drawShade ( g2d, border, new Color ( 210, 210, 210 ), shadeWidth );

                g2d.setPaint ( new GradientPaint ( 0, 0, Color.WHITE, 0, c.getHeight (), new Color ( 235, 235, 235 ) ) );
                g2d.fillRect ( 0, 0, c.getWidth (), c.getHeight () - shadeWidth );

                g2d.setPaint ( borderColor );
                g2d.draw ( border );
            }
            else
            {
                LafUtils.drawWebStyle ( g2d, c, new Color ( 210, 210, 210 ), shadeWidth, round, true, true, borderColor );
            }
        }
    }
}

enum MenuBarStyle
{
    /**
     * Standalone menubar.
     * May be used to display menubar separately from frame borders or elements.
     */
    standalone,

    /**
     * Attached menubar.
     * Usually used as the default style for window menubar.
     */
    attached
}
