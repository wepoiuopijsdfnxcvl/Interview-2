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
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import web.laf.lite.utils.LafUtils;

import java.awt.*;

/**
 * Custom UI for JSplitPane component.
 *
 * @author Mikle Garin
 */

public class SplitPaneUI extends BasicSplitPaneUI
{
    /**
     * Style settings.
     */
    private Color dragDividerColor = Color.LIGHT_GRAY;

    /**
     * Returns an instance of the WebSplitPaneUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebSplitPaneUI
     */
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new SplitPaneUI ();
    }

    /**
     * Installs UI in the specified component.
     *
     * @param c component for this UI
     */
    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        splitPane.setOpaque ( false );
        splitPane.setBorder ( null );
        splitPane.setDividerSize ( 2 );
    }

    /**
     * Returns dragged divider color.
     *
     * @return dragged divider color
     */
    public Color getDragDividerColor ()
    {
        return dragDividerColor;
    }

    /**
     * Sets dragged divider color.
     *
     * @param dragDividerColor dragged divider color
     */
    public void setDragDividerColor ( Color dragDividerColor )
    {
        this.dragDividerColor = dragDividerColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicSplitPaneDivider createDefaultDivider ()
    {
        return new BasicSplitPaneDivider ( this )
        {
            private final Border border = BorderFactory.createEmptyBorder ( 0, 0, 0, 0 );

            @Override
            public Border getBorder ()
            {
                return border;
            }

            @Override
            protected JButton createLeftOneTouchButton ()
            {
                JButton iconWebButton = new JButton(orientation == JSplitPane.HORIZONTAL_SPLIT ? 
                		new ImageIcon(SplitPaneUI.class.getResource("icons/left.png")) :  new ImageIcon(SplitPaneUI.class.getResource("icons/up.png")));
                iconWebButton.setBorder ( BorderFactory.createEmptyBorder ( 0, 0, 0, 0 ) );
                iconWebButton.setCursor ( Cursor.getDefaultCursor () );
                iconWebButton
                        .setPreferredSize ( orientation == JSplitPane.HORIZONTAL_SPLIT ? new Dimension ( 6, 7 ) : new Dimension ( 7, 6 ) );
                return iconWebButton;
            }

            @Override
            protected JButton createRightOneTouchButton ()
            {
                JButton iconWebButton = new JButton(orientation == JSplitPane.HORIZONTAL_SPLIT ? 
                		new ImageIcon(SplitPaneUI.class.getResource("icons/right.png")) : new ImageIcon(SplitPaneUI.class.getResource("icons/down.png")) );
                iconWebButton.setBorder ( BorderFactory.createEmptyBorder ( 0, 0, 0, 0 ) );
                iconWebButton.setCursor ( Cursor.getDefaultCursor () );
                iconWebButton
                        .setPreferredSize ( orientation == JSplitPane.HORIZONTAL_SPLIT ? new Dimension ( 6, 7 ) : new Dimension ( 7, 6 ) );
                return iconWebButton;
            }

            private Color color = new Color ( 158, 158, 158 );
            private Color transparent = new Color ( 0, 0, 0, 0 );
            private Color[] gradient = new Color[]{ transparent, color, color, transparent };

            @Override
            public void paint ( Graphics g )
            {
                Graphics2D g2d = ( Graphics2D ) g;
                Object aa = LafUtils.setupAntialias ( g2d );
               
                if ( orientation == JSplitPane.HORIZONTAL_SPLIT )
                {
                	Style.drawTopBorder(g, getWidth());
                	Style.drawBottomBorder(g, getWidth(), getHeight());
                }
                	 /*   int startY = getHeight () / 2 - 35;
                    int endY = getHeight () / 2 + 35;
                    g2d.setPaint ( new LinearGradientPaint ( 0, startY, 0, endY, new float[]{ 0f, 0.25f, 0.75f, 1f }, gradient ) );
                    for ( int i = startY; i < endY; i += 5 )
                    {
                        g2d.fillRect ( getWidth () / 2 - 1, i - 1, 2, 2 );
                    }
                }
                else
                {
                    int startX = getWidth () / 2 - 35;
                    int endX = getWidth () / 2 + 35;
                    g2d.setPaint ( new LinearGradientPaint ( startX, 0, endX, 0, new float[]{ 0f, 0.25f, 0.75f, 1f }, gradient ) );
                    for ( int i = startX; i < endX; i += 5 )
                    {
                        g2d.fillRect ( i - 1, getHeight () / 2 - 1, 2, 2 );
                    }
                }

                super.paint ( g );
				*/
                LafUtils.restoreAntialias ( g2d, aa );
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createDefaultNonContinuousLayoutDivider ()
    {
        return new Canvas ()
        {
            @Override
            public void paint ( Graphics g )
            {
                if ( !isContinuousLayout () && getLastDragLocation () != -1 )
                {
                    Dimension size = splitPane.getSize ();
                    g.setColor ( dragDividerColor );
                    if ( getOrientation () == JSplitPane.HORIZONTAL_SPLIT )
                    {
                        g.fillRect ( 0, 0, dividerSize - 1, size.height - 1 );
                    }
                    else
                    {
                        g.fillRect ( 0, 0, size.width - 1, dividerSize - 1 );
                    }
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finishedPaintingChildren ( JSplitPane jc, Graphics g )
    {
        if ( jc == splitPane && getLastDragLocation () != -1 && !isContinuousLayout () &&
                !draggingHW )
        {
            Dimension size = splitPane.getSize ();

            g.setColor ( dragDividerColor );
            if ( getOrientation () == JSplitPane.HORIZONTAL_SPLIT )
            {
                g.fillRect ( getLastDragLocation (), 0, dividerSize - 1, size.height - 1 );
            }
            else
            {
                g.fillRect ( 0, getLastDragLocation (), size.width - 1, dividerSize - 1 );
            }
        }
    }
}