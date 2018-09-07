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
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.*;

import web.laf.lite.utils.CollectionUtils;
import web.laf.lite.utils.GeometryUtils;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom UI for JTree component.
 *
 * @author Mikle Garin
 */

public class TreeUI extends BasicTreeUI
{
    /**
     * Expand and collapse control icons.
     */
    public static ImageIcon EXPAND_ICON = new ImageIcon(TreeUI.class.getResource("icons/expand.png"));
    public static ImageIcon COLLAPSE_ICON = new ImageIcon(TreeUI.class.getResource("icons/collapse.png"));

    /**
     * Style settings.
     */
    protected boolean autoExpandSelectedNode = false;
    protected int selectionRound = 2;
    protected int selectionShadeWidth = 2;
    protected boolean selectorEnabled = true;
    protected Color selectorColor = new Color ( 0, 0, 255, 50 );
    protected Color selectorBorderColor = Color.GRAY;
    protected int selectorRound = 2;
    protected BasicStroke selectorStroke = new BasicStroke ( 1, 
    		BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, new float[]{ 3f, 3f }, 0f );

    /**
     * Tree listeners.
     */
    protected PropertyChangeListener propertyChangeListener;
    protected TreeSelectionListener treeSelectionListener;
    protected TreeExpansionListener treeExpansionListener;
    protected MouseAdapter mouseAdapter;

    /**
     * Runtime variables.
     */
    protected int rolloverRow = -1;
    protected List<Integer> initialSelection = new ArrayList<Integer> ();
    protected Point selectionStart = null;
    protected Point selectionEnd = null;
    protected boolean ltr = true;
    
    private final ComponentListener componentListener = new ComponentAdapter() {
    	@Override
    	public void componentResized(ComponentEvent e) {
    		treeState.invalidateSizes();
    		tree.repaint();
    	};
    };

    /**
     * Returns an instance of the WebTreeUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebTreeUI
     */
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new TreeUI ();
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
        tree.addComponentListener(componentListener);
        // Default settings
        tree.setRowHeight ( -1 );
        //tree.setVisibleRowCount ( 10 );
        
        // Selection listener
        treeSelectionListener = new TreeSelectionListener ()
        {
            @Override
            public void valueChanged ( TreeSelectionEvent e )
            {
                // Optimized selection repaint
                repaintSelection ();

                // Tree expansion on selection
                if ( autoExpandSelectedNode && tree.getSelectionCount () > 0 )
                {
                    tree.expandPath ( tree.getSelectionPath () );
                }
            }
        };
        tree.addTreeSelectionListener ( treeSelectionListener );

        // Expansion listener
        treeExpansionListener = new TreeExpansionListener ()
        {
            @Override
            public void treeExpanded ( TreeExpansionEvent event )
            {
                repaintSelection ();
            }

            @Override
            public void treeCollapsed ( TreeExpansionEvent event )
            {
                repaintSelection ();
            }
        };
        tree.addTreeExpansionListener ( treeExpansionListener );

        // Mouse events adapter
        mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                // Only left mouse button events
                if ( SwingUtilities.isLeftMouseButton ( e ) )
                {
                    // Check that mouse did not hit actuall tree cell
                    if ( getRowForPoint ( e.getPoint (), false ) == -1 )
                    {
                        if ( isSelectorAvailable () )
                        {
                            // Avoiding selection start when pressed on tree expand handle
                            TreePath path = getClosestPathForLocation ( tree, e.getX (), e.getY () );
                            if ( !isLocationInExpandControl ( path, e.getX (), e.getY () ) )
                            {
                                // Selection
                                selectionStart = e.getPoint ();
                                selectionEnd = selectionStart;

                                // Initial tree selection
                                initialSelection = getSelectionRowsList ();

                                // Updating selection
                                validateSelection ( e );

                                // Repainting selection on the tree
                                repaintSelector ();
                            }
                        }
                        else if ( true )
                        {
                            // Avoiding selection start when pressed on tree expand handle
                            TreePath path = getClosestPathForLocation ( tree, e.getX (), e.getY () );
                            if ( !isLocationInExpandControl ( path, e.getX (), e.getY () ) )
                            {
                                if ( tree.getSelectionModel ().getSelectionMode () == TreeSelectionModel.SINGLE_TREE_SELECTION )
                                {
                                    // Selection
                                    tree.setSelectionRow ( getRowForPoint ( e.getPoint (), true ) );
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseDragged ( MouseEvent e )
            {
                if ( isSelectorAvailable () && selectionStart != null )
                {
                    // Selection
                    selectionEnd = e.getPoint ();

                    // Updating selection
                    validateSelection ( e );

                    // Repainting selection on the tree
                    repaintSelector ();

                    if ( !tree.getVisibleRect ().contains ( e.getPoint () ) )
                    {
                        tree.scrollRectToVisible ( new Rectangle ( e.getPoint (), new Dimension ( 0, 0 ) ) );
                    }
                }
            }

            @Override
            public void mouseReleased ( MouseEvent e )
            {
                if ( isSelectorAvailable () && selectionStart != null )
                {
                    // Saving selection rect to repaint
                    // Rectangle fr = GeometryUtils.getContainingRect ( selectionStart, selectionEnd );

                    // Selection
                    selectionStart = null;
                    selectionEnd = null;

                    // Repainting selection on the tree
                    repaintSelector ( /*fr*/ );
                }
            }

            private void validateSelection ( MouseEvent e )
            {
                // todo Possibly optimize selection? - modify it instead of overwriting each time

                // Selection rect
                Rectangle selection = GeometryUtils.getContainingRect ( selectionStart, selectionEnd );

                // Compute new selection
                List<Integer> newSelection = new ArrayList<Integer> ();
                if ( SwingUtils.isShift ( e ) )
                {

                    for ( int row = 0; row < tree.getRowCount (); row++ )
                    {
                        if ( getRowBounds ( row ).intersects ( selection ) && !initialSelection.contains ( row ) )
                        {
                            newSelection.add ( row );
                        }
                    }
                    for ( int row : initialSelection )
                    {
                        newSelection.add ( row );
                    }
                }
                else if ( SwingUtils.isCtrl ( e ) )
                {
                    List<Integer> excludedRows = new ArrayList<Integer> ();
                    for ( int row = 0; row < tree.getRowCount (); row++ )
                    {
                        if ( getRowBounds ( row ).intersects ( selection ) )
                        {
                            if ( initialSelection.contains ( row ) )
                            {
                                excludedRows.add ( row );
                            }
                            else
                            {
                                newSelection.add ( row );
                            }
                        }
                    }
                    for ( int row : initialSelection )
                    {
                        if ( !excludedRows.contains ( row ) )
                        {
                            newSelection.add ( row );
                        }
                    }
                }
                else
                {
                    for ( int row = 0; row < tree.getRowCount (); row++ )
                    {
                        if ( getRowBounds ( row ).intersects ( selection ) )
                        {
                            newSelection.add ( row );
                        }
                    }
                }

                // Change selection if it is not the same as before
                if ( !CollectionUtils.areEqual ( getSelectionRowsList (), newSelection ) )
                {
                    if ( newSelection.size () > 0 )
                    {
                        tree.setSelectionRows ( CollectionUtils.toArray ( newSelection ) );
                    }
                    else
                    {
                        tree.clearSelection ();
                    }
                }
            }

            private List<Integer> getSelectionRowsList ()
            {
                List<Integer> selection = new ArrayList<Integer> ();
                final int[] selectionRows = tree.getSelectionRows ();
                if ( selectionRows != null )
                {
                    for ( int row : selectionRows )
                    {
                        selection.add ( row );
                    }
                }
                return selection;
            }

            private void repaintSelector ()
            {
                //                // Calculating selector pervious and current rects
                //                final Rectangle sb1 = GeometryUtils.getContainingRect ( selectionStart, selectionPrevEnd );
                //                final Rectangle sb2 = GeometryUtils.getContainingRect ( selectionStart, selectionEnd );
                //
                //                // Repainting final rect
                //                repaintSelector ( GeometryUtils.getContainingRect ( sb1, sb2 ) );

                // Replaced with full repaint due to strange tree lines painting bug
                tree.repaint ();
            }

            //            private void repaintSelector ( Rectangle fr )
            //            {
            //                //                // Expanding width and height to fully cover the selector
            //                //                fr.x -= 1;
            //                //                fr.y -= 1;
            //                //                fr.width += 2;
            //                //                fr.height += 2;
            //                //
            //                //                // Repainting selector area
            //                //                tree.repaint ( fr );
            //
            //                // Replaced with full repaint due to strange tree lines painting bug
            //                tree.repaint ();
            //            }

            @Override
            public void mouseEntered ( MouseEvent e )
            {
                updateMouseover ( e );
            }

            @Override
            public void mouseExited ( MouseEvent e )
            {
                clearMouseover ();
            }

            @Override
            public void mouseMoved ( MouseEvent e )
            {
                updateMouseover ( e );
            }

            private void updateMouseover ( MouseEvent e )
            {
            }

            private void clearMouseover ()
            {
                int oldRollover = rolloverRow;
                rolloverRow = -1;
                updateRow ( oldRollover );
            }

            private void updateRow ( int row )
            {
                if ( row != -1 )
                {
                    Rectangle rowBounds = getFullRowBounds ( row );
                    if ( rowBounds != null )
                    {
                        tree.repaint ( rowBounds );
                    }
                }
            }
        };
        tree.addMouseListener ( mouseAdapter );
        tree.addMouseMotionListener ( mouseAdapter );
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( JComponent c )
    {
        tree.removeTreeSelectionListener ( treeSelectionListener );
        tree.removeTreeExpansionListener ( treeExpansionListener );
        tree.removeMouseListener ( mouseAdapter );
        tree.removeMouseMotionListener ( mouseAdapter );
        tree.removeComponentListener(componentListener);
        super.uninstallUI ( c );
    }

    /**
     * Returns whether selector is available for current tree or not.
     *
     * @return true if selector is available for current tree, false otherwise
     */
    public boolean isSelectorAvailable ()
    {
        return selectorEnabled && tree != null && tree.isEnabled () &&
                tree.getSelectionModel ().getSelectionMode () != TreeSelectionModel.SINGLE_TREE_SELECTION;
    }


    /**
     * Returns row index for specified point on the tree.
     * This method takes selection style into account.
     *
     * @param point point on the tree
     * @return row index for specified point on the tree
     */
    public int getRowForPoint ( Point point )
    {
        return getRowForPoint ( point, true );
    }

    /**
     * Returns row index for specified point on the tree.
     *
     * @param point        point on the tree
     * @param countFullRow whether take the whole row into account or just node renderer rect
     * @return row index for specified point on the tree
     */
    public int getRowForPoint ( Point point, boolean countFullRow )
    {
        if ( tree != null )
        {
            for ( int row = 0; row < tree.getRowCount (); row++ )
            {
                Rectangle bounds = getRowBounds ( row, countFullRow );
                if ( bounds.contains ( point ) )
                {
                    return row;
                }
            }
        }
        return -1;
    }

    /**
     * Returns row bounds by its index.
     * This method takes selection style into account.
     *
     * @param row row index
     * @return row bounds by its index
     */
    public Rectangle getRowBounds ( int row )
    {
        return getRowBounds ( row, true );
    }

    /**
     * Returns row bounds by its index.
     *
     * @param row          row index
     * @param countFullRow whether take the whole row into account or just node renderer rect
     * @return row bounds by its index
     */
    public Rectangle getRowBounds ( int row, boolean countFullRow )
    {
        return countFullRow ? getFullRowBounds ( row ) : tree.getRowBounds ( row );
    }

    /**
     * Returns full row bounds by its index.
     *
     * @param row row index
     * @return full row bounds by its index
     */
    public Rectangle getFullRowBounds ( int row )
    {
        Rectangle b = tree.getRowBounds ( row );
        if ( b != null )
        {
            b.x = 0;
            b.width = tree.getWidth ();
        }
        return b;
    }
    
    @Override
    protected AbstractLayoutCache.NodeDimensions createNodeDimensions() {
        return new NodeDimensionsHandler() {
            @Override
            public Rectangle getNodeDimensions(Object value, int row, int depth, boolean expanded,
                    Rectangle size) {
                Rectangle dimensions = super.getNodeDimensions(value, row, depth, expanded, size);
                dimensions.width = tree.getWidth() - getRowX(row, depth);
                return dimensions;
            }
        };
    }
    
    @Override
    protected void paintHorizontalPartOfLeg ( Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row,
                                            boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf ){
    }

    @Override
    protected void paintHorizontalLine ( Graphics g, JComponent c, int y, int left, int right ){
    }

    @Override
    protected void paintVerticalPartOfLeg ( Graphics g, Rectangle clipBounds, Insets insets, TreePath path ){
    }
    
    @Override
    protected void paintVerticalLine ( Graphics g, JComponent c, int x, int top, int bottom ){
    }

    /**
     * Returns tree structure expanded node icon.
     *
     * @return tree structure expanded node icon
     */
    @Override
    public Icon getExpandedIcon ()
    {
        return COLLAPSE_ICON;
    }

    /**
     * Returns tree structure collapsed node icon.
     *
     * @return tree structure collapsed node icon
     */
    @Override
    public Icon getCollapsedIcon ()
    {
        return EXPAND_ICON;
    }

    /**
     * Paints centered icon.
     *
     * @param c    component
     * @param g    graphics
     * @param icon icon
     * @param x    x coordinate
     * @param y    y coordinate
     */
    @Override
    protected void drawCentered ( Component c, Graphics g, Icon icon, int x, int y )
    {
        icon.paintIcon ( c, g, findCenteredX ( x, icon.getIconWidth () ), y - icon.getIconHeight () / 2 );
    }

    /**
     * Returns centered x coordinate for the icon.
     *
     * @param x         x coordinate
     * @param iconWidth icon width
     * @return centered x coordinate
     */
    protected int findCenteredX ( int x, int iconWidth )
    {
        return ltr ? x + 2 - ( int ) Math.ceil ( iconWidth / 2.0 ) : x - 2 - ( int ) Math.floor ( iconWidth / 2.0 ) - 3;
    }

    /**
     * Repaints all rectangles containing tree selections.
     * This method is optimized to repaint only those area which are actually has selection in them.
     */
    protected void repaintSelection ()
    {
        if ( tree.getSelectionCount () > 0 )
        {
            for ( Rectangle rect : getSelectionRects () )
            {
                tree.repaint ( rect );
            }
        }
    }

    /**
     * Returns list of tree selections bounds.
     * This method takes selection style into account.
     *
     * @return list of tree selections bounds
     */
    protected List<Rectangle> getSelectionRects ()
    {
        List<Rectangle> selections = new ArrayList<Rectangle> ();

        // Checking that selection exists
        int[] rows = tree.getSelectionRows ();
        if ( rows == null )
        {
            return selections;
        }

        // Sorting selected rows
        Arrays.sort ( rows );

        // Calculating selection rects
        Rectangle maxRect = null;
        int lastRow = -1;
        for ( int row : rows )
        {
                if ( lastRow != -1 && lastRow + 1 != row )
                {
                    // Save determined group
                    selections.add ( maxRect );

                    // Reset counting
                    maxRect = null;
                    lastRow = -1;
                }
                if ( lastRow == -1 || lastRow + 1 == row )
                {
                    // Required bounds
                    Rectangle b = tree.getRowBounds ( row );
                    if ( true )
                    {
                        b.x = 0;
                        b.width = tree.getWidth ();
                    }

                    // Increase rect
                    maxRect = lastRow == -1 ? b : GeometryUtils.getContainingRect ( maxRect, b );

                    // Remember last row
                    lastRow = row;
                }
        }
        if ( maxRect != null )
        {
            selections.add ( maxRect );
        }
        return selections;
    }

    
    @Override
    public void paint ( Graphics g, JComponent c )
    {
    	Style.drawLightHorizontalBar(g, c.getWidth(), c.getHeight());
        // Cells selection
        if ( tree.getSelectionCount () > 0 )
        {
            // Draw final selections
            List<Rectangle> selections = getSelectionRects ();
            for ( Rectangle rect : selections )
            {
            	Graphics2D g2d = ( Graphics2D ) g;
                // Rectangle shapeBounds = rect.getBounds ();
                // Style.drawTreeSelection(g, shapeBounds.y + shapeBounds.height, rect);
                LafUtils.drawCustomWebBorder(g2d, tree, rect,new Color(0x4580c8), 0, true, true);
            }
        }
    	super.paint ( g, c );
        // Multiselector
        if ( isSelectorAvailable () && selectionStart != null && selectionEnd != null )
        {
            Graphics2D g2d = ( Graphics2D ) g;
            LafUtils.setupAntialias ( g2d );
            Object aa = LafUtils.setupAntialias ( g2d );
            Stroke os = LafUtils.setupStroke ( g2d, selectorStroke );

            Rectangle sb = GeometryUtils.getContainingRect ( selectionStart, selectionEnd );
            
           // g2d.setPaint ( selectorColor );
            //g2d.fill ( getSelectionShape ( sb, true ) );

            //g2d.setPaint ( selectorBorderColor );
            //g2d.draw ( getSelectionShape ( sb, false ) );
            LafUtils.restoreStroke ( g2d, os );
            LafUtils.restoreAntialias ( g2d, aa );
        }
    }
    
    Color topLineColor = new Color(0x4580c8);
    Color topColor = new Color(0x5d94d6);
    Color bottomColor = new Color(0x1956ad);

    /**
     * Returns selection shape for specified selection bounds.
     *
     * @param sb   selection bounds
     * @param fill should fill the whole cell
     * @return selection shape for specified selection bounds
     */
    protected Shape getSelectionShape ( Rectangle sb, boolean fill )
    {
        int shear = fill ? 1 : 0;
        if ( selectorRound > 0 )
        {
            return new RoundRectangle2D.Double ( sb.x + shear, sb.y + shear, sb.width - shear, sb.height - shear, selectorRound * 2,
                    selectorRound * 2 );
        }
        else
        {
            return new Rectangle2D.Double ( sb.x + shear, sb.y + shear, sb.width - shear, sb.height - shear );
        }
    }
}