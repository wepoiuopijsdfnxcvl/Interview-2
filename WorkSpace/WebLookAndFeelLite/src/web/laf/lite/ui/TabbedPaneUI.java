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
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * User: mgarin Date: 27.04.11 Time: 18:39
 */

public class TabbedPaneUI extends BasicTabbedPaneUI implements ShapeProvider, BorderMethods
{
    private Color selectedTopBg = Color.WHITE;
    private Color selectedBottomBg = new Color ( 237, 237, 237 );

    private Color topBg = new Color ( 227, 227, 227 );
    private Color bottomBg = new Color ( 208, 208, 208 );

    private Map<Integer, Color> selectedForegroundAt = new HashMap<Integer, Color> ();

    private int round = 2;
    private int shadeWidth = 2;

    private boolean rotateTabInsets = false;

    private Insets contentInsets = new Insets ( 0, 0, 0, 0 );
    private Insets tabInsets = new Insets ( 3, 4, 3, 4 );

    private int tabRunIndent = 0;
    private int tabOverlay = 1;
    private TabStretchType tabStretchType = TabStretchType.multiline;

    private FocusAdapter focusAdapter;
    //    private MouseAdapter mouseAdapter;
    //    private int rolloverTab = -1;

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new TabbedPaneUI ();
    }

    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );

        // Default settings
        tabPane.setBackground ( new Color ( 237, 237, 237 ) );

        // Updating border
        updateBorder ();

        // Focus updater
        focusAdapter = new FocusAdapter ()
        {
            @Override
            public void focusGained ( FocusEvent e )
            {
                tabPane.repaint ();
            }

            @Override
            public void focusLost ( FocusEvent e )
            {
                tabPane.repaint ();
            }
        };
        tabPane.addFocusListener ( focusAdapter );

        //        mouseAdapter = new MouseAdapter ()
        //        {
        //            public void mouseEntered ( MouseEvent e )
        //            {
        //                updateRolloverTab ( e );
        //            }
        //
        //            public void mouseExited ( MouseEvent e )
        //            {
        //                updateRolloverTab ( e );
        //            }
        //
        //            public void mouseMoved ( MouseEvent e )
        //            {
        //                updateRolloverTab ( e );
        //            }
        //
        //            public void mouseDragged ( MouseEvent e )
        //            {
        //                updateRolloverTab ( e );
        //            }
        //        };
        //        c.addMouseListener ( mouseAdapter );
        //        c.addMouseMotionListener ( mouseAdapter );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {

        if ( focusAdapter != null )
        {
            c.removeFocusListener ( focusAdapter );
        }
        //        if ( mouseAdapter != null )
        //        {
        //            c.removeMouseListener ( mouseAdapter );
        //            c.removeMouseMotionListener ( mouseAdapter );
        //        }

        super.uninstallUI ( c );
    }

    @Override
    public Shape provideShape ()
    {
        return LafUtils.getWebBorderShape ( tabPane, getShadeWidth (), getRound () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        if ( tabPane != null )
        {
            Insets bgInsets = getBackgroundInsets ( tabPane );
                // Attached style border
            tabPane.setBorder ( LafUtils.createWebBorder ( bgInsets ) );
        }
    }

    private Insets getBackgroundInsets ( JComponent c )
    {
        return  new Insets ( 0, 0, 0, 0 );
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

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public Insets getContentInsets ()
    {
        return contentInsets;
    }

    public void setContentInsets ( Insets contentInsets )
    {
        this.contentInsets = contentInsets;
    }

    public Insets getTabInsets ()
    {
        return tabInsets;
    }

    public void setTabInsets ( Insets tabInsets )
    {
        this.tabInsets = tabInsets;
    }

    public Color getSelectedTopBg ()
    {
        return selectedTopBg;
    }

    public void setSelectedTopBg ( Color selectedTopBg )
    {
        this.selectedTopBg = selectedTopBg;
    }

    public Color getSelectedBottomBg ()
    {
        return selectedBottomBg;
    }

    public void setSelectedBottomBg ( Color selectedBottomBg )
    {
        this.selectedBottomBg = selectedBottomBg;
    }

    public Color getTopBg ()
    {
        return topBg;
    }

    public void setTopBg ( Color topBg )
    {
        this.topBg = topBg;
    }

    public Color getBottomBg ()
    {
        return bottomBg;
    }

    public void setBottomBg ( Color bottomBg )
    {
        this.bottomBg = bottomBg;
    }

    public void setSelectedForegroundAt ( int tabIndex, Color foreground )
    {
        selectedForegroundAt.put ( tabIndex, foreground );
    }

    public Color getSelectedForegroundAt ( int tabIndex )
    {
        return selectedForegroundAt.get ( tabIndex );
    }

    //    private void updateRolloverTab ( MouseEvent e )
    //    {
    //        if ( tabPane != null )
    //        {
    //            int old = rolloverTab;
    //            rolloverTab = tabForCoordinate ( tabPane, e.getX (), e.getY () );
    //            if ( old != rolloverTab )
    //            {
    //                tabPane.repaint ();
    //            }
    //        }
    //    }

    public int getTabRunIndent ()
    {
        return tabRunIndent;
    }

    public void setTabRunIndent ( int tabRunIndent )
    {
        this.tabRunIndent = tabRunIndent;
    }

    public int getTabOverlay ()
    {
        return tabOverlay;
    }

    public void setTabOverlay ( int tabOverlay )
    {
        this.tabOverlay = tabOverlay;
    }

    public TabStretchType getTabStretchType ()
    {
        return tabStretchType;
    }

    public void setTabStretchType ( TabStretchType tabStretchType )
    {
        this.tabStretchType = tabStretchType;
    }

    @Override
    protected int getTabRunIndent ( int tabPlacement, int run )
    {
        return tabRunIndent;
    }

    @Override
    protected int getTabRunOverlay ( int tabPlacement )
    {
        return tabOverlay;
    }

    @Override
    protected boolean shouldPadTabRun ( int tabPlacement, int run )
    {
        return !tabStretchType.equals ( TabStretchType.never ) &&
                ( tabStretchType.equals ( TabStretchType.always ) || tabStretchType.equals ( TabStretchType.multiline ) && runCount > 1 );
    }

    @Override
    protected boolean shouldRotateTabRuns ( int tabPlacement )
    {
        // todo Requires style changes
        return true;
    }

    @Override
    protected Insets getContentBorderInsets ( int tabPlacement )
    {
            return new Insets ( 0, 0, 0, 0 );
    }

    @Override
    protected Insets getTabAreaInsets ( int tabPlacement )
    {
        Insets targetInsets = new Insets ( 0, 0, 0, 0 );
        rotateInsets (new Insets ( -1, -1, 0, 0 ), targetInsets, tabPlacement );
        return targetInsets;
    }

    @Override
    protected Insets getTabInsets ( int tabPlacement, int tabIndex )
    {
        Insets insets = SwingUtils.copy ( tabInsets );
        if ( tabIndex == 0 && tabPane.getSelectedIndex () == 0 )
        {
            // Fix for 1st element
            insets.left -= 1;
            insets.right += 1;
        }
        if ( rotateTabInsets )
        {
            Insets targetInsets = new Insets ( 0, 0, 0, 0 );
            rotateInsets ( insets, targetInsets, tabPlacement );
            return targetInsets;
        }
        else
        {
            return insets;
        }
    }

    @Override
    protected Insets getSelectedTabPadInsets ( int tabPlacement )
    {
        Insets targetInsets = new Insets ( 0, 0, 0, 0 );
        rotateInsets ( new Insets ( 0, 0, 0, 0 ),targetInsets, tabPlacement );
        return targetInsets;
    }

    @Override
    protected int getTabLabelShiftX ( int tabPlacement, int tabIndex, boolean isSelected )
    {
            return 0;
    }

    @Override
    protected int getTabLabelShiftY ( int tabPlacement, int tabIndex, boolean isSelected )
    {
       return 0;
    }

    @Override
    protected void paintTabBorder ( Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected )
    {
        // We don't need this one
    }

    @Override
    protected void paintTabBackground ( Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        Object aa = LafUtils.setupAntialias ( g2d );

        // Border shape
        GeneralPath borderShape = createTabShape ( TabShapeType.border, tabPlacement, x, y, w, h, isSelected );


        // Tab background
        GeneralPath bgShape = createTabShape ( TabShapeType.background, tabPlacement, x, y, w, h, isSelected );
        if ( isSelected )
        {
            Shape old = LafUtils.intersectClip ( g2d, bgShape );
            //bp.paint ( g2d, new Rectangle ( x, y, w, h ), tabPane );
            LafUtils.restoreClip ( g2d, old );
        }
        else
        {
            Point topPoint = getTopTabBgPoint ( tabPlacement, x, y, w, h );
            Point bottomPoint = getBottomTabBgPoint ( tabPlacement, x, y, w, h );
            if ( isSelected )
            {
                Color bg = tabPane.getBackgroundAt ( tabIndex );
                bg = bg != null ? bg : tabPane.getBackground ();
                g2d.setPaint ( new GradientPaint ( topPoint.x, topPoint.y, selectedTopBg, bottomPoint.x, bottomPoint.y, bg ) );
            }
            else
            {
                g2d.setPaint ( new GradientPaint ( topPoint.x, topPoint.y, topBg, bottomPoint.x, bottomPoint.y, bottomBg ) );
            }
            g2d.fill ( isSelected ? borderShape : bgShape );
        }

        // Tab border
        g2d.setPaint ( Color.GRAY );
        g2d.draw ( borderShape );

        // Tab focus
        //        else if ( drawFocus )
        //        {
        //            g2d.setPaint ( StyleConstants.fieldFocusColor );
        //            g2d.drawLine ( x, y + 1, x + w, y + 1 );
        //        }

        //todo Paint selected tab together with area

        LafUtils.restoreAntialias ( g2d, aa );
    }

    @Override
    protected void paintText ( Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
                               boolean isSelected )
    {
        g.setFont ( font );
        View v = getTextViewForTab ( tabIndex );
        if ( v != null )
        {
            // html
            v.paint ( g, textRect );
        }
        else
        {
            // plain text
            int mnemIndex = tabPane.getDisplayedMnemonicIndexAt ( tabIndex );

            if ( tabPane.isEnabled () && tabPane.isEnabledAt ( tabIndex ) )
            {
                Color fg = tabPane.getForegroundAt ( tabIndex );
                if ( isSelected && ( fg instanceof UIResource ) )
                {
                    if ( selectedForegroundAt.containsKey ( tabIndex ) )
                    {
                        fg = selectedForegroundAt.get ( tabIndex );
                    }
                    else
                    {
                        Color selectedFG = UIManager.getColor ( "TabbedPane.selectedForeground" );
                        if ( selectedFG != null )
                        {
                            fg = selectedFG;
                        }
                    }
                }
                g.setColor ( fg );
                SwingUtils.drawStringUnderlineCharAt ( g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent () );

            }
            else
            {
                // tab disabled
                g.setColor ( tabPane.getBackgroundAt ( tabIndex ).brighter () );
                SwingUtils.drawStringUnderlineCharAt ( g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent () );
                g.setColor ( tabPane.getBackgroundAt ( tabIndex ).darker () );
                SwingUtils.drawStringUnderlineCharAt ( g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent () - 1 );
            }
        }
    }

    private GeneralPath createTabShape ( TabShapeType tabShapeType, int tabPlacement, int x, int y, int w, int h, boolean isSelected )
    {
        // Fix for basic layouting of selected left-sided tab x coordinate
        Insets insets = tabPane.getInsets ();
        if ( isSelected )
        {
            // todo fix for other tabPlacement values aswell
            if ( tabPlacement == TOP && x == insets.left )
            {
                x = x - 1;
                w = w + 1;
            }
        }

        int actualRound = 0;
        GeneralPath bgShape = new GeneralPath ( GeneralPath.WIND_EVEN_ODD );
        if ( tabPlacement == JTabbedPane.TOP )
        {
            bgShape.moveTo ( x, y + h + getChange ( tabShapeType ) );
            bgShape.lineTo ( x, y + actualRound );
            bgShape.quadTo ( x, y, x + actualRound, y );
            bgShape.lineTo ( x + w - actualRound, y );
            bgShape.quadTo ( x + w, y, x + w, y + actualRound );
            bgShape.lineTo ( x + w, y + h + getChange ( tabShapeType ) );
        }
        else if ( tabPlacement == JTabbedPane.BOTTOM )
        {
            bgShape.moveTo ( x, y - getChange ( tabShapeType ) );
            bgShape.lineTo ( x, y + h - actualRound );
            bgShape.quadTo ( x, y + h, x + actualRound, y + h );
            bgShape.lineTo ( x + w - actualRound, y + h );
            bgShape.quadTo ( x + w, y + h, x + w, y + h - actualRound );
            bgShape.lineTo ( x + w, y - getChange ( tabShapeType ) );
        }
        else if ( tabPlacement == JTabbedPane.LEFT )
        {
            bgShape.moveTo ( x + w + getChange ( tabShapeType ), y );
            bgShape.lineTo ( x + actualRound, y );
            bgShape.quadTo ( x, y, x, y + actualRound );
            bgShape.lineTo ( x, y + h - actualRound );
            bgShape.quadTo ( x, y + h, x + actualRound, y + h );
            bgShape.lineTo ( x + w + getChange ( tabShapeType ), y + h );
        }
        else
        {
            bgShape.moveTo ( x - getChange ( tabShapeType ), y );
            bgShape.lineTo ( x + w - actualRound, y );
            bgShape.quadTo ( x + w, y, x + w, y + actualRound );
            bgShape.lineTo ( x + w, y + h - actualRound );
            bgShape.quadTo ( x + w, y + h, x + w - actualRound, y + h );
            bgShape.lineTo ( x - getChange ( tabShapeType ), y + h );
        }
        return bgShape;
    }

    private int getChange ( TabShapeType tabShapeType )
    {
        if ( tabShapeType.equals ( TabShapeType.shade ) )
        {
            return -( round > 0 ? round : 1 );
        }
        else if ( tabShapeType.equals ( TabShapeType.border ) )
        {
            return -1;
        }
        else if ( tabShapeType.equals ( TabShapeType.backgroundPainter ) )
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }

    private enum TabShapeType
    {
        shade,
        background,
        backgroundPainter,
        border
    }

    private Point getTopTabBgPoint ( int tabPlacement, int x, int y, int w, int h )
    {
        if ( tabPlacement == JTabbedPane.TOP )
        {
            return new Point ( x, y );
        }
        else if ( tabPlacement == JTabbedPane.BOTTOM )
        {
            return new Point ( x, y + h );
        }
        else if ( tabPlacement == JTabbedPane.LEFT )
        {
            return new Point ( x, y );
        }
        else
        {
            return new Point ( x + w, y );
        }
    }

    private Point getBottomTabBgPoint ( int tabPlacement, int x, int y, int w, int h )
    {
        if ( tabPlacement == JTabbedPane.TOP )
        {
            return new Point ( x, y + h - 4 );
        }
        else if ( tabPlacement == JTabbedPane.BOTTOM )
        {
            return new Point ( x, y + 4 );
        }
        else if ( tabPlacement == JTabbedPane.LEFT )
        {
            return new Point ( x + w - 4, y );
        }
        else
        {
            return new Point ( x + 4, y );
        }
    }

    @Override
    protected void paintContentBorder ( Graphics g, int tabPlacement, int selectedIndex )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        Object aa = LafUtils.setupAntialias ( g2d );

        int tabAreaSize = getTabAreaLength ( tabPlacement );

        Insets bi = tabPane.getInsets ();
        if ( tabPlacement == JTabbedPane.TOP || tabPlacement == JTabbedPane.BOTTOM )
        {
            bi.right += 1;
        }
        else
        {
            bi.bottom += 1;
        }

        // Selected tab bounds
        Rectangle selected = selectedIndex != -1 ? getTabBounds ( tabPane, selectedIndex ) : null;

        // Background shape
        Shape bs = createBackgroundShape ( tabPlacement, tabAreaSize, bi, selected );

        
            // Area background
                Color bg = selectedIndex != -1 ? tabPane.getBackgroundAt ( selectedIndex ) : null;
                g2d.setPaint ( bg != null ? bg : tabPane.getBackground () );
                g2d.fill ( bs );

            // todo draw for other tabPlacement values aswell
            // Area border
            g2d.setPaint ( Color.GRAY );
            if ( tabPlacement == JTabbedPane.TOP )
            {
                if ( selected != null )
                {
                    if ( bi.left < selected.x )
                    {
                        g2d.drawLine ( bi.left, bi.top + tabAreaSize, selected.x, bi.top + tabAreaSize );
                    }
                    if ( selected.x + selected.width < tabPane.getWidth () - bi.right )
                    {
                        g2d.drawLine ( selected.x + selected.width, bi.top + tabAreaSize, tabPane.getWidth () - bi.right,
                                bi.top + tabAreaSize );
                    }
                }
                else
                {
                    g2d.drawLine ( bi.left, bi.top + tabAreaSize, tabPane.getWidth () - bi.right, bi.top + tabAreaSize );
                }
            }
            //            else if ( tabPlacement == JTabbedPane.BOTTOM )
            //            {
            //                //
            //            }
            //            else if ( tabPlacement == JTabbedPane.LEFT )
            //            {
            //                //
            //            }
            //            else if ( tabPlacement == JTabbedPane.RIGHT )
            //            {
            //                //
            //            }

        LafUtils.restoreAntialias ( g2d, aa );
    }

    private int getTabAreaLength ( int tabPlacement )
    {
        return tabPlacement == JTabbedPane.TOP || tabPlacement == JTabbedPane.BOTTOM ?
                calculateTabAreaHeight ( tabPlacement, runCount, maxTabHeight ) - 1 :
                calculateTabAreaWidth ( tabPlacement, runCount, maxTabWidth ) - 1;
    }

    private Shape createBackgroundShape ( int tabPlacement, int tabAreaSize, Insets bi, Rectangle selected )
    {
      
            int x = bi.left + ( tabPlacement == JTabbedPane.LEFT ? tabAreaSize : 0 );
            int y = bi.top + ( tabPlacement == JTabbedPane.TOP ? tabAreaSize : 0 );
            int width = tabPane.getWidth () - bi.left - bi.right -
                    ( tabPlacement == JTabbedPane.LEFT || tabPlacement == JTabbedPane.RIGHT ? tabAreaSize : 0 );
            int height = tabPane.getHeight () - bi.top - bi.bottom -
                    ( tabPlacement == JTabbedPane.TOP || tabPlacement == JTabbedPane.BOTTOM ? tabAreaSize : 0 );
            return new Rectangle ( x, y, width + 1, height );
    }

    public Shape getContentClip ()
    {
        Shape clip = null;

        int tabPlacement = tabPane.getTabPlacement ();
        int tabAreaLength = getTabAreaLength ( tabPlacement );
        Insets insets = tabPane.getInsets ();

        if ( tabPlacement == JTabbedPane.TOP )
        {
            clip = new RoundRectangle2D.Double ( insets.left, insets.top + tabAreaLength, tabPane.getWidth () - insets.left - insets.right,
                    tabPane.getHeight () - insets.top - tabAreaLength - insets.bottom, round * 2, round * 2 );
        }
        else if ( tabPlacement == JTabbedPane.BOTTOM )
        {
            clip = new RoundRectangle2D.Double ( insets.left, insets.top, tabPane.getWidth () - insets.left - insets.right,
                    tabPane.getHeight () - insets.top - tabAreaLength - insets.bottom, round * 2, round * 2 );
        }
        else if ( tabPlacement == JTabbedPane.LEFT )
        {
            clip = new RoundRectangle2D.Double ( insets.left + tabAreaLength, insets.top,
                    tabPane.getWidth () - insets.left - tabAreaLength - insets.right, tabPane.getHeight () - insets.top - insets.bottom,
                    round * 2, round * 2 );
        }
        else if ( tabPlacement == JTabbedPane.RIGHT )
        {
            clip = new RoundRectangle2D.Double ( insets.left, insets.top, tabPane.getWidth () - insets.left - tabAreaLength - insets.right,
                    tabPane.getHeight () - insets.top - insets.bottom, round * 2, round * 2 );
        }

        return clip;
    }

    @Override
    protected void paintFocusIndicator ( Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect,
                                         Rectangle textRect, boolean isSelected )
    {
        // We don't need this one
    }

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        // Basic paintings
        Map hints = SwingUtils.setupTextAntialias ( g );
        super.paint ( g, c );
        SwingUtils.restoreTextAntialias ( g, hints );
    }

    //    protected void setRolloverTab ( int index )
    //    {
    //        super.setRolloverTab ( index );
    //
    //        // todo Animate rollover
    //    }

    //    public Dimension getPreferredSize ( JComponent c )
    //    {
    //        if ( tabPane.getTabPlacement () == JTabbedPane.TOP ||
    //                tabPane.getTabPlacement () == JTabbedPane.BOTTOM )
    //        {
    //            getTab
    //        }else {
    //
    //        }
    //    }

    //    protected LayoutManager createLayoutManager ()
    //    {
    //        return new TabbedPaneLayout ();
    //    }
    //
    //    public class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout
    //    {
    //
    //        public TabbedPaneLayout ()
    //        {
    //            WebTabbedPaneUI.this.super ();
    //        }
    //
    //        protected void normalizeTabRuns ( int tabPlacement, int tabCount, int start, int max )
    //        {
    //            // Only normalize the runs for top & bottom;  normalizing
    //            // doesn't look right for Metal's vertical tabs
    //            // because the last run isn't padded and it looks odd to have
    //            // fat tabs in the first vertical runs, but slimmer ones in the
    //            // last (this effect isn't noticeable for horizontal tabs).
    //            if ( tabPlacement == TOP || tabPlacement == BOTTOM )
    //            {
    //                super.normalizeTabRuns ( tabPlacement, tabCount, start, max );
    //            }
    //        }
    //
    //        // Don't rotate runs!
    //        protected void rotateTabRuns ( int tabPlacement, int selectedRun )
    //        {
    //        }
    //
    //        // Don't pad selected tab
    //        protected void padSelectedTab ( int tabPlacement, int selectedIndex )
    //        {
    //        }
    //    }

    //    public static void main ( String[] args )
    //    {
    //        new TestFrame ( new WebTabbedPane (  ){
    //            {
    //                getWebUI ().setTabbedPaneStyle ( TabbedPaneStyle.attached );
    //                addTab ( "Tab 1", new WebTabbedPane (  ){
    //                    {
    //                        setTabPlacement ( LEFT );
    //                        getWebUI ().setTabbedPaneStyle ( TabbedPaneStyle.attached );
    //                        addTab ( "Tab 1", new JLabel (  ) );
    //                        addTab ( "Tab 2", new JLabel (  ) );
    //                        addTab ( "Tab 3", new JLabel (  ) );
    //                        addTab ( "Tab 4", new JLabel (  ) );
    //                    }
    //                } );
    //                addTab ( "Tab 2",  new WebTabbedPane (  ){
    //                    {
    //                        setTabPlacement ( RIGHT );
    //                        getWebUI ().setTabbedPaneStyle ( TabbedPaneStyle.attached );
    //                        addTab ( "Tab 1", new JLabel (  ) );
    //                        addTab ( "Tab 2", new JLabel (  ) );
    //                        addTab ( "Tab 3", new JLabel (  ) );
    //                        addTab ( "Tab 4", new JLabel (  ) );
    //                    }
    //                } );
    //                addTab ( "Tab 3",  new WebTabbedPane (  ){
    //                    {
    //                        setTabPlacement ( BOTTOM );
    //                        getWebUI ().setTabbedPaneStyle ( TabbedPaneStyle.attached );
    //                        addTab ( "Tab 1", new JLabel (  ) );
    //                        addTab ( "Tab 2", new JLabel (  ) );
    //                        addTab ( "Tab 3", new JLabel (  ) );
    //                        addTab ( "Tab 4", new JLabel (  ) );
    //                    }
    //                } );
    //                addTab ( "Tab 4", new JLabel (  ) );
    //            }
    //        });
    //    }
}

enum TabStretchType
{
    never,
    multiline,
    always
}