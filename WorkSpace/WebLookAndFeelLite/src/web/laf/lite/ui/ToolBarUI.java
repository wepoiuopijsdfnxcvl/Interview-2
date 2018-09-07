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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarUI;

import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: mgarin Date: 17.08.11 Time: 23:06
 */

public class ToolBarUI extends BasicToolBarUI implements ShapeProvider, BorderMethods
{
    public static final int gripperSpace = 5;

    private Color topBgColor = Color.WHITE;
    private Color bottomBgColor = new Color ( 229, 233, 238 );
    private Color borderColor = new Color ( 139, 144, 151 );
    private Color disabledBorderColor = Color.LIGHT_GRAY;

    private boolean undecorated = false;
    private int round = 6;
    private int shadeWidth = 2;
    private Insets margin = new Insets ( 1, 1, 1, 1 );
    private int spacing = 2;
    private ToolbarStyle toolbarStyle = ToolbarStyle.standalone;

    private AncestorListener ancestorListener;
    private PropertyChangeListener propertyChangeListener;
    private PropertyChangeListener componentOrientationListener;

    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( JComponent c )
    {
        return new ToolBarUI ();
    }

    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );

        // Default settings
        toolBar.setOpaque ( false );

        // Updating border and layout
        updateBorder ();
        updateLayout ( toolBar );

        // Border and layout update listeners
        ancestorListener = new AncestorAdapter ()
        {
            @Override
            public void ancestorAdded ( AncestorEvent event )
            {
                updateBorder ();
                updateLayout ( toolBar );
            }
        };
        toolBar.addAncestorListener ( ancestorListener );
        propertyChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                updateBorder ();
                updateLayout ( toolBar );
            }
        };
        toolBar.addPropertyChangeListener ( "floatable", propertyChangeListener );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        c.removeAncestorListener ( ancestorListener );
        c.removePropertyChangeListener ( "floatable", propertyChangeListener ); 

        super.uninstallUI ( c );
    }

    @Override
    public Shape provideShape ()
    {
        if (undecorated )
        {
            return SwingUtils.size ( toolBar );
        }
        else
        {
            return LafUtils.getWebBorderShape ( toolBar, getShadeWidth (), getRound () );
        }
    }

    public boolean isUndecorated ()
    {
        return undecorated;
    }

    public void setUndecorated ( boolean undecorated )
    {
        this.undecorated = undecorated;

        // Updating border
        updateBorder ();
    }

    public int getRound ()
    {
        if ( undecorated )
        {
            return 0;
        }
        else
        {
            return round;
        }
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public Color getTopBgColor ()
    {
        return topBgColor;
    }

    public void setTopBgColor ( Color topBgColor )
    {
        this.topBgColor = topBgColor;
    }

    public Color getBottomBgColor ()
    {
        return bottomBgColor;
    }

    public void setBottomBgColor ( Color bottomBgColor )
    {
        this.bottomBgColor = bottomBgColor;
    }

    public Color getBorderColor ()
    {
        return borderColor;
    }

    public void setBorderColor ( Color borderColor )
    {
        this.borderColor = borderColor;
    }

    public Color getDisabledBorderColor ()
    {
        return disabledBorderColor;
    }

    public void setDisabledBorderColor ( Color disabledBorderColor )
    {
        this.disabledBorderColor = disabledBorderColor;
    }

    public int getShadeWidth ()
    {
        if ( undecorated )
        {
            return 0;
        }
        else
        {
            return shadeWidth;
        }
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ();
    }

    public Insets getMargin ()
    {
        return margin;
    }

    public void setMargin ( Insets margin )
    {
        this.margin = margin;
        updateBorder ();
    }

    public ToolbarStyle getToolbarStyle ()
    {
        return toolbarStyle;
    }

    public void setToolbarStyle ( ToolbarStyle toolbarStyle )
    {
        this.toolbarStyle = toolbarStyle;
        updateBorder ();
    }

    public int getSpacing ()
    {
        return spacing;
    }

    public void setSpacing ( int spacing )
    {
        this.spacing = spacing;
        updateLayout ( toolBar );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        if ( toolBar != null )
        {
            if ( !undecorated )
            {
                // Web-style insets
                int gripperSpacing = toolBar.isFloatable () ? gripperSpace : 0;
                boolean horizontal = toolBar.getOrientation () == JToolBar.HORIZONTAL;
                boolean ltr = toolBar.getComponentOrientation ().isLeftToRight ();
                if ( toolbarStyle.equals ( ToolbarStyle.standalone ) )
                {
                    if ( isFloating () )
                    {
                        toolBar.setBorder ( LafUtils.createWebBorder ( margin.top + ( !horizontal ? gripperSpacing : 0 ),
                                margin.left + ( horizontal && ltr ? gripperSpacing : 0 ), margin.bottom,
                                margin.right + ( horizontal && !ltr ? gripperSpacing : 0 ) ) );
                    }
                    else
                    {
                        toolBar.setBorder ( LafUtils.createWebBorder ( margin.top + 1 + shadeWidth + ( !horizontal ? gripperSpacing : 0 ),
                                margin.left + 1 + shadeWidth +
                                        ( horizontal && ltr ? gripperSpacing : 0 ), margin.bottom + 1 + shadeWidth,
                                margin.right + 1 + shadeWidth +
                                        ( horizontal && !ltr ? gripperSpacing : 0 ) ) );
                    }
                }
                else
                {
                    if ( isFloating () )
                    {
                        toolBar.setBorder ( LafUtils.createWebBorder ( margin.top + ( !horizontal ? gripperSpacing : 0 ),
                                margin.left + ( horizontal && ltr ? gripperSpacing : 0 ), margin.bottom,
                                margin.right + ( horizontal && !ltr ? gripperSpacing : 0 ) ) );
                    }
                    else
                    {
                        toolBar.setBorder ( LafUtils.createWebBorder ( margin.top + ( !horizontal ? gripperSpacing : 0 ),
                                margin.left + ( horizontal && ltr ? gripperSpacing : 0 ) +
                                        ( !horizontal && !ltr ? 1 : 0 ), margin.bottom + ( horizontal ? 1 : 0 ),
                                margin.right + ( horizontal && !ltr ? gripperSpacing : 0 ) +
                                        ( !horizontal && ltr ? 1 : 0 ) ) );
                    }
                }
            }
            else
            {
                // Empty insets
                toolBar.setBorder ( LafUtils.createWebBorder ( margin.top, margin.left, margin.bottom, margin.right ) );
            }
        }
    }

    private void updateLayout ( JComponent c )
    {
        ToolbarLayout layout = new ToolbarLayout ( spacing, toolBar.getOrientation () );
        if ( c.getLayout () instanceof ToolbarLayout )
        {
            ToolbarLayout old = ( ToolbarLayout ) c.getLayout ();
            layout.setConstraints ( old.getConstraints () );
        }
        c.setLayout ( layout );
    }

    private Color color = new Color ( 158, 158, 158 );
    private Color transparent = new Color ( 0, 0, 0, 0 );
    private Color[] gradient = new Color[]{ transparent, color, color, transparent };
    private float[] fractions = { 0f, 0.33f, 0.66f, 1f };

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        if ( !undecorated )
        {
            Graphics2D g2d = ( Graphics2D ) g;
            Object aa = LafUtils.setupAntialias ( g2d );

            boolean horizontal = toolBar.getOrientation () == JToolBar.HORIZONTAL;
            boolean ltr = c.getComponentOrientation ().isLeftToRight ();

            // Painting border and background
            if ( isFloating () )
            {
                if ( horizontal )
                {
                    g2d.setPaint ( new GradientPaint ( 0, c.getHeight () / 2, topBgColor, 0, c.getHeight (), bottomBgColor ) );
                }
                else
                {
                    g2d.setPaint ( new GradientPaint ( c.getWidth () / 2, 0, topBgColor, c.getWidth (), 0, bottomBgColor ) );
                }
                g2d.fillRect ( 0, 0, c.getWidth (), c.getHeight () );
            }
            else
            {
                if ( toolbarStyle.equals ( ToolbarStyle.standalone ) )
                {
                    RoundRectangle2D rr = new RoundRectangle2D.Double ( shadeWidth, shadeWidth, c.getWidth () - shadeWidth * 2 - 1,
                            c.getHeight () - shadeWidth * 2 - 1, round, round );

                    if ( c.isEnabled () )
                    {
                        LafUtils.drawShade ( g2d, rr, new Color ( 210, 210, 210 ), shadeWidth );
                    }

                    if ( horizontal )
                    {
                        g2d.setPaint ( new GradientPaint ( 0, c.getHeight () / 2, topBgColor, 0, c.getHeight (), bottomBgColor ) );
                    }
                    else
                    {
                        g2d.setPaint ( new GradientPaint ( c.getWidth () / 2, 0, topBgColor, c.getWidth (), 0, bottomBgColor ) );
                    }
                    g2d.fill ( rr );

                    g2d.setPaint ( c.isEnabled () ? borderColor : disabledBorderColor );
                    g2d.draw ( rr );
                }
                else
                {
                    if ( horizontal )
                    {
                        g2d.setPaint ( new GradientPaint ( 0, c.getHeight () / 2, topBgColor, 0, c.getHeight (), bottomBgColor ) );
                    }
                    else
                    {
                        g2d.setPaint ( new GradientPaint ( c.getWidth () / 2, 0, topBgColor, c.getWidth (), 0, bottomBgColor ) );
                    }
                    g2d.fillRect ( 0, 0, c.getWidth (), c.getHeight () );

                    g2d.setPaint ( c.isEnabled () ? borderColor : disabledBorderColor );
                    if ( horizontal )
                    {
                        g2d.drawLine ( 0, c.getHeight () - 1, c.getWidth () - 1, c.getHeight () - 1 );
                    }
                    else
                    {
                        if ( ltr )
                        {
                            g2d.drawLine ( c.getWidth () - 1, 0, c.getWidth () - 1, c.getHeight () - 1 );
                        }
                        else
                        {
                            g2d.drawLine ( 0, 0, 0, c.getHeight () - 1 );
                        }
                    }
                }
            }

            // Painting gripper
            if ( toolBar.isFloatable () )
            {
                if ( toolBar.getOrientation () == JToolBar.HORIZONTAL )
                {
                    int gradY = shadeWidth + 1;
                    int gradEndY = c.getHeight () - shadeWidth - 2;
                    if ( gradEndY > gradY )
                    {
                        g2d.setPaint ( new LinearGradientPaint ( 0, gradY, 0, gradEndY, fractions, gradient ) );

                        // Determining gripper X coordinate
                        int x = toolbarStyle.equals ( ToolbarStyle.standalone ) ?
                                shadeWidth + 1 + margin.left + ( isFloating () ? -1 : 1 ) : margin.left + gripperSpace / 2 - 1;
                        if ( !ltr )
                        {
                            x = c.getWidth () - x - 2;
                        }

                        // Painting gripper
                        for ( int i = c.getHeight () / 2 - 3; i >= gradY; i -= 4 )
                        {
                            g2d.fillRect ( x, i, 2, 2 );
                        }
                        for ( int i = c.getHeight () / 2 + 1; i + 2 <= gradEndY; i += 4 )
                        {
                            g2d.fillRect ( x, i, 2, 2 );
                        }
                    }
                }
                else
                {
                    int gradX = shadeWidth + 1;
                    int gradEndX = c.getWidth () - shadeWidth - 2;
                    if ( gradEndX > gradX )
                    {
                        g2d.setPaint ( new LinearGradientPaint ( gradX, 0, gradEndX, 0, fractions, gradient ) );

                        // Determining gripper Y coordinate
                        int y = toolbarStyle.equals ( ToolbarStyle.standalone ) ? shadeWidth + 1 + margin.top + ( isFloating () ? -1 : 1 ) :
                                margin.top + gripperSpace / 2 - 1;

                        // Painting gripper
                        for ( int i = c.getWidth () / 2 - 3; i >= gradX; i -= 4 )
                        {
                            g2d.fillRect ( i, y, 2, 2 );
                        }
                        for ( int i = c.getWidth () / 2 + 1; i + 2 <= gradEndX; i += 4 )
                        {
                            g2d.fillRect ( i, y, 2, 2 );
                        }
                    }
                }
            }

            LafUtils.restoreAntialias ( g2d, aa );
        }
    }

    @Override
    public Dimension getPreferredSize ( JComponent c )
    {
        Dimension ps = c.getLayout () != null ? c.getLayout ().preferredLayoutSize ( c ) : null;
        return ps;
    }

    @Override
    protected RootPaneContainer createFloatingWindow ( JToolBar toolbar )
    {
        class ToolBarDialog extends JDialog
        {
            public ToolBarDialog ( Frame owner, String title, boolean modal )
            {
                super ( owner, title, modal );
            }

            public ToolBarDialog ( Dialog owner, String title, boolean modal )
            {
                super ( owner, title, modal );
            }

            // Override createRootPane() to automatically resize
            // the frame when contents change
            @Override
            protected JRootPane createRootPane ()
            {
                JRootPane rootPane = new JRootPane ()
                {
                    private boolean packing = false;

                    @Override
                    public void validate ()
                    {
                        super.validate ();
                        if ( !packing )
                        {
                            packing = true;
                            pack ();
                            packing = false;
                        }
                    }
                };
                rootPane.setOpaque ( true );
                return rootPane;
            }
        }

        ToolBarDialog dialog;
        Window window = SwingUtils.getWindowAncestor ( toolbar );
        if ( window instanceof Frame )
        {
            dialog = new ToolBarDialog ( ( Frame ) window, toolbar.getName (), false );
        }
        else if ( window instanceof Dialog )
        {
            dialog = new ToolBarDialog ( ( Dialog ) window, toolbar.getName (), false );
        }
        else
        {
            dialog = new ToolBarDialog ( ( Frame ) null, toolbar.getName (), false );
        }

        dialog.getRootPane ().setName ( "ToolBar.FloatingWindow" );
        dialog.setTitle ( toolbar.getName () );
        dialog.setResizable ( false );
        WindowListener wl = createFrameListener ();
        dialog.addWindowListener ( wl );
        //        dialog.setUndecorated ( true );
        return dialog;
    }

    //    protected void paintDragWindow ( Graphics g )
    //    {
    //        super.paintDragWindow ( g );
    //        System.out.println (
    //                "can fock now: " + dragWindow.getBorderColor ().equals ( dockingBorderColor ) );
    //        System.out.println (
    //                "horizontal: " + ( dragWindow.getOrientation () == WebToolBar.HORIZONTAL ) );
    //        toolBar.printAll ( g );
    //    }

    @Override
    protected DragWindow createDragWindow ( JToolBar toolbar )
    {
        DragWindow dragWindow = super.createDragWindow ( toolbar );
        return dragWindow;
    }
}

enum ToolbarStyle
{
    standalone,
    attached
}
