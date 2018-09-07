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
import javax.swing.plaf.basic.BasicScrollBarUI;

import web.laf.lite.utils.LafUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: mgarin Date: 29.04.11 Time: 15:34
 */

public class ScrollBarUI extends BasicScrollBarUI
{
    public static final int LENGTH = 13;
    private boolean drawBorder = true;

    private MouseAdapter mouseAdapter;
    
    public void setDrawBorder(boolean value){
    	drawBorder = value;
    }

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new ScrollBarUI ();
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );
        // Default settings
        scrollbar.setUnitIncrement ( 4 );
        scrollbar.setUnitIncrement ( 16 );

        mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                scrollbar.repaint ();
            }
        };
        scrollbar.addMouseListener ( mouseAdapter );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        scrollbar.removeMouseListener ( mouseAdapter );

        super.uninstallUI ( c );
    }
    
    @Override
    public void paint ( Graphics g, JComponent c )
    {
        Object aa = LafUtils.disableAntialias ( g );
        super.paint ( g, c );
        LafUtils.restoreAntialias ( g, aa );
    }

    @Override
    protected void paintTrack ( Graphics g, JComponent c, Rectangle trackBounds )
    {
       if ( scrollbar.getOrientation () == JScrollBar.VERTICAL )
          Style.drawVerticalScrollTrack(g, trackBounds, scrollbar.getWidth (), scrollbar.getHeight (), drawBorder);
       else
    	   Style.drawHorizontalScrollTrack(g, trackBounds, scrollbar.getWidth (), scrollbar.getHeight (), drawBorder);
    }

    @Override
    protected void paintThumb ( Graphics g, JComponent c, Rectangle thumbBounds )
    {
        if ( scrollbar.getOrientation () == JScrollBar.VERTICAL )
        	Style.drawVerticalScrollBar(g, thumbRect, scrollbar.getWidth (), isDragging);
        else
        	Style.drawHorizontalScrollBar(g, thumbRect, scrollbar.getWidth (), isDragging);
    }

    @Override
    protected void installComponents ()
    {
        incrButton = new JButton ();
        incrButton.setPreferredSize ( new Dimension ( 0, 0 ) );
        decrButton = new JButton ();
        decrButton.setPreferredSize ( new Dimension ( 0, 0 ) );
    }

    @Override
    public Dimension getPreferredSize ( JComponent c )
    {
        Dimension preferredSize = super.getPreferredSize ( c );
        return ( scrollbar.getOrientation () == JScrollBar.VERTICAL ) ? new Dimension ( LENGTH, preferredSize.height ) :
                new Dimension ( preferredSize.width, LENGTH );
    }
}
