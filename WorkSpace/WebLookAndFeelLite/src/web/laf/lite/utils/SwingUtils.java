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

package web.laf.lite.utils;


import javax.swing.*;
import javax.swing.FocusManager;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

/**
 * This class provides a set of utilities to work with Swing components, their settings and events.
 *
 * @author Mikle Garin
 */

public final class SwingUtils
{
    /**
     * Client property key that identifies that component can handle enabled state changes.
     */
    public static final String HANDLES_ENABLE_STATE = "HANDLES_ENABLE_STATE";

    /**
     * System shortcut modifier.
     */
    private static Integer systemShortcutModifier = null;

    /**
     * Label for default system font retrieval.
     */
    private static JLabel label = null;

    /**
     * System font names array.
     */
    private static String[] fontNames;

    /**
     * System fonts array.
     */
    private static Font[] fonts;

    /**
     * Threads for smooth component scrolling.
     */
    private static Thread scrollThread1;
    private static Thread scrollThread2;

    /**
     * Most of applications use 10 or less fonts simultaneously.
     */
    private static final int STRONG_BEARING_CACHE_SIZE = 10;

    /**
     * Strong cache for the left and right side bearings for STRONG_BEARING_CACHE_SIZE most recently used fonts.
     */
    private static BearingCacheEntry[] strongBearingCache = new BearingCacheEntry[ STRONG_BEARING_CACHE_SIZE ];

    /**
     * Next index to insert an entry into the strong bearing cache.
     */
    private static int strongBearingCacheNextIndex = 0;

    /**
     * Soft cache for the left and right side bearings.
     */
    private static Set<SoftReference<BearingCacheEntry>> softBearingCache = new HashSet<SoftReference<BearingCacheEntry>> ();

    /**
     * Returns whether event involves left mouse button or not.
     *
     * @param e mouse event
     * @return true if event involves left mouse button, false otherwise
     */
    public static boolean isLeftMouseButton ( final MouseEvent e )
    {
        return ( e.getModifiers () & InputEvent.BUTTON1_MASK ) != 0;
    }

    /**
     * Returns whether event involves middle mouse button or not.
     *
     * @param e mouse event
     * @return true if event involves middle mouse button, false otherwise
     */
    public static boolean isMiddleMouseButton ( final MouseEvent e )
    {
        return ( e.getModifiers () & InputEvent.BUTTON2_MASK ) == InputEvent.BUTTON2_MASK;
    }

    /**
     * Returns whether event involves right mouse button or not.
     *
     * @param e mouse event
     * @return true if event involves right mouse button, false otherwise
     */
    public static boolean isRightMouseButton ( final MouseEvent e )
    {
        return ( e.getModifiers () & InputEvent.BUTTON3_MASK ) == InputEvent.BUTTON3_MASK;
    }

    /**
     * Packs all table rows to their preferred height.
     *
     * @param table table to process
     */
    public static void packRowHeights ( final JTable table )
    {
        for ( int row = 0; row < table.getRowCount (); row++ )
        {
            int maxHeight = 0;
            for ( int column = 0; column < table.getColumnCount (); column++ )
            {
                final TableCellRenderer cellRenderer = table.getCellRenderer ( row, column );
                final Object valueAt = table.getValueAt ( row, column );
                final Component renderer = cellRenderer.getTableCellRendererComponent ( table, valueAt, false, false, row, column );
                final int heightPreferable = renderer != null ? renderer.getPreferredSize ().height : 0;
                maxHeight = Math.max ( heightPreferable, maxHeight );
            }
            table.setRowHeight ( row, maxHeight );
        }
    }

    /**
     * Packs all table columns to their preferred width.
     *
     * @param table table to process
     */
    public static void packColumnWidths ( final JTable table )
    {
        packColumnWidths ( table, 2 );
    }

    /**
     * Packs all table columns to their preferred width.
     *
     * @param table  table to process
     * @param margin column side margin
     */
    public static void packColumnWidths ( final JTable table, final int margin )
    {
        for ( int i = 0; i < table.getColumnCount (); i++ )
        {
            packColumnWidth ( table, i, margin );
        }
    }

    /**
     * Packs table column at the specified index to its preferred width.
     *
     * @param table table to process
     * @param col   column index
     */
    public static void packColumnWidth ( final JTable table, final int col )
    {
        packColumnWidth ( table, col, 2 );
    }

    /**
     * Packs table column at the specified index to its preferred width.
     *
     * @param table  table to process
     * @param col    column index
     * @param margin column side margin
     */
    public static void packColumnWidth ( final JTable table, final int col, final int margin )
    {
        final DefaultTableColumnModel columnModel = ( DefaultTableColumnModel ) table.getColumnModel ();
        final TableColumn column = columnModel.getColumn ( col );
        int width;

        // Header renderer
        TableCellRenderer renderer = column.getHeaderRenderer ();
        if ( renderer == null )
        {
            renderer = table.getTableHeader ().getDefaultRenderer ();
        }

        // Header width
        Component comp = renderer.getTableCellRendererComponent ( table, column.getHeaderValue (), false, false, 0, 0 );
        width = comp.getPreferredSize ().width;

        // Cells width
        for ( int r = 0; r < table.getRowCount (); r++ )
        {
            renderer = table.getCellRenderer ( r, col );
            comp = renderer.getTableCellRendererComponent ( table, table.getValueAt ( r, col ), false, false, r, col );
            width = Math.max ( width, comp.getPreferredSize ().width );
        }

        // Margin
        width += 2 * margin;

        // Final values
        column.setPreferredWidth ( width );
        column.setWidth ( width );
    }

    /**
     * Returns whether the specifid mouse events triggers popup menu or not.
     * This method might act differently on different operating systems.
     *
     * @param e mouse event
     * @return true if the specifid mouse events triggers popup menu, false otherwise
     */
    public static boolean isPopupTrigger ( final MouseEvent e )
    {
        return e.isPopupTrigger () || SwingUtilities.isRightMouseButton ( e );
    }

    /**
     * Destroys container by destroying its childs structure and removing all listeners.
     *
     * @param container container to destroy
     */
    public static void destroyContainer ( final Container container )
    {
        for ( final Container toDestroy : collectAllContainers ( container ) )
        {
            toDestroy.removeAll ();
            toDestroy.setLayout ( null );

            for ( final MouseListener listener : toDestroy.getMouseListeners () )
            {
                toDestroy.removeMouseListener ( listener );
            }
            for ( final MouseMotionListener listener : toDestroy.getMouseMotionListeners () )
            {
                toDestroy.removeMouseMotionListener ( listener );
            }
            for ( final MouseWheelListener listener : toDestroy.getMouseWheelListeners () )
            {
                toDestroy.removeMouseWheelListener ( listener );
            }
            for ( final KeyListener listener : toDestroy.getKeyListeners () )
            {
                toDestroy.removeKeyListener ( listener );
            }
            for ( final ComponentListener listener : toDestroy.getComponentListeners () )
            {
                toDestroy.removeComponentListener ( listener );
            }
            for ( final ContainerListener listener : toDestroy.getContainerListeners () )
            {
                toDestroy.removeContainerListener ( listener );
            }
            if ( toDestroy instanceof JComponent )
            {
                final JComponent jComponent = ( JComponent ) toDestroy;
                for ( final AncestorListener listener : jComponent.getAncestorListeners () )
                {
                    jComponent.removeAncestorListener ( listener );
                }
            }
        }
    }

    /**
     * Returns list of all sub-containers for this container.
     *
     * @param container container to process
     * @return list of all sub-containers
     */
    public static List<Container> collectAllContainers ( final Container container )
    {
        return collectAllContainers ( container, new ArrayList<Container> () );
    }

    /**
     * Returns list of all sub-containers for this container.
     *
     * @param container  container to process
     * @param containers list to collect sub-containers into
     * @return list of all sub-containers
     */
    public static List<Container> collectAllContainers ( final Container container, final List<Container> containers )
    {
        containers.add ( container );
        for ( final Component component : container.getComponents () )
        {
            if ( component instanceof Container )
            {
                collectAllContainers ( ( Container ) component, containers );
            }
        }
        return containers;
    }

    /**
     * Returns top component inside the specified container component at the specified point.
     *
     * @param component container component to process
     * @param x         X coordinate
     * @param y         Y coordinate
     * @return top component inside the specified container component at the specified point
     */
    public static Component getTopComponentAt ( final Component component, final int x, final int y )
    {
        final Component child = component.getComponentAt ( x, y );
        if ( child == component || !( child instanceof Container ) )
        {
            return component;
        }
        else
        {
            final Rectangle b = child.getBounds ();
            return getTopComponentAt ( child, x - b.x, y - b.y );
        }
    }

    /**
     * Displays the specified frame as modal to the owner frame.
     * Note that this method returns only after the modal frame is closed.
     * <p/>
     * This method is a Swing hack and not recommended for real use.
     * Still it might be useful for some specific cases.
     *
     * @param frame frame to display as modal
     * @param owner owner frame
     */
    public static void showAsModal ( final Frame frame, final Frame owner )
    {
        frame.addWindowListener ( new WindowAdapter ()
        {
            @Override
            public void windowOpened ( final WindowEvent e )
            {
                owner.setEnabled ( false );
            }

            @Override
            public void windowClosed ( final WindowEvent e )
            {
                owner.setEnabled ( true );
                frame.removeWindowListener ( this );
            }
        } );

        owner.addWindowListener ( new WindowAdapter ()
        {
            @Override
            public void windowActivated ( final WindowEvent e )
            {
                if ( frame.isShowing () )
                {
                    frame.setExtendedState ( JFrame.NORMAL );
                    frame.toFront ();
                }
                else
                {
                    owner.removeWindowListener ( this );
                }
            }
        } );

        frame.setVisible ( true );
        try
        {
            new EventPump ( frame ).start ();
        }
        catch ( final Throwable throwable )
        {
            throw new RuntimeException ( throwable );
        }
    }

    /**
     * Groups all buttons inside this container and returns created button group.
     *
     * @param container container to process
     * @return created button group
     */
    public static ButtonGroup groupButtons ( final Container container )
    {
        return groupButtons ( container, false );
    }

    /**
     * Groups all buttons inside this container and all subcontainers if requested and returns created button group.
     *
     * @param container container to process
     * @param recursive whether to check all subcontainers or not
     * @return created button group
     */
    public static ButtonGroup groupButtons ( final Container container, final boolean recursive )
    {
        final ButtonGroup buttonGroup = new ButtonGroup ();
        groupButtons ( container, recursive, buttonGroup );
        return buttonGroup;
    }

    /**
     * Groups all buttons inside this container and all subcontainers if requested and returns created button group.
     *
     * @param container   container to process
     * @param recursive   whether to check all subcontainers or not
     * @param buttonGroup button group
     */
    public static void groupButtons ( final Container container, final boolean recursive, final ButtonGroup buttonGroup )
    {
        for ( final Component component : container.getComponents () )
        {
            if ( component instanceof AbstractButton )
            {
                buttonGroup.add ( ( AbstractButton ) component );
            }
            if ( recursive )
            {
                if ( component instanceof Container )
                {
                    groupButtons ( container, true );
                }
            }
        }
    }

    /**
     * Groups specified buttons and returns created button group.
     *
     * @param buttons buttons to group
     * @return created button group
     */
    public static ButtonGroup groupButtons ( final AbstractButton... buttons )
    {
        final ButtonGroup buttonGroup = new ButtonGroup ();
        groupButtons ( buttonGroup, buttons );
        return buttonGroup;
    }

    /**
     * Groups buttons in the specified button group.
     *
     * @param buttonGroup button group
     * @param buttons     buttons to group
     */
    public static void groupButtons ( final ButtonGroup buttonGroup, final AbstractButton... buttons )
    {
        for ( final AbstractButton button : buttons )
        {
            buttonGroup.add ( button );
        }
    }



    

    /**
     * Returns maximum component width.
     *
     * @param components components to process
     * @return maximum component width
     */
    public static int maxWidth ( final Component... components )
    {
        int max = 0;
        for ( final Component component : components )
        {
            max = Math.max ( max, component.getPreferredSize ().width );
        }
        return max;
    }

    /**
     * Returns maximum component height.
     *
     * @param components components to process
     * @return maximum component height
     */
    public static int maxHeight ( final Component... components )
    {
        int max = 0;
        for ( final Component component : components )
        {
            max = Math.max ( max, component.getPreferredSize ().height );
        }
        return max;
    }

    /**
     * Returns window ancestor for specified component or null if it doesn't exist.
     *
     * @param component component to process
     * @return window ancestor for specified component or null if it doesn't exist
     */
    public static Window getWindowAncestor ( final Component component )
    {
        if ( component == null )
        {
            return null;
        }
        if ( component instanceof Window )
        {
            return ( Window ) component;
        }
        for ( Container p = component.getParent (); p != null; p = p.getParent () )
        {
            if ( p instanceof Window )
            {
                return ( Window ) p;
            }
        }
        return null;
    }

    /**
     * Returns root pane for the specified component or null if it doesn't exist.
     *
     * @param component component to look under
     * @return root pane for the specified component or null if it doesn't exist
     */
    public static JRootPane getRootPane ( final Component component )
    {
        if ( component instanceof JFrame )
        {
            return ( ( JFrame ) component ).getRootPane ();
        }
        else if ( component instanceof JDialog )
        {
            return ( ( JDialog ) component ).getRootPane ();
        }
        else if ( component instanceof JWindow )
        {
            return ( ( JWindow ) component ).getRootPane ();
        }
        else if ( component instanceof JApplet )
        {
            return ( ( JApplet ) component ).getRootPane ();
        }
        else if ( component instanceof JRootPane )
        {
            return ( JRootPane ) component;
        }
        else
        {
            for ( Container p = component.getParent (); p != null; p = p.getParent () )
            {
                if ( p instanceof JRootPane )
                {
                    return ( JRootPane ) p;
                }
            }
        }
        return null;
    }

    /**
     * Returns layered pane for the specified component or null if it doesn't exist.
     *
     * @param component component to look under
     * @return layered pane for the specified component or null if it doesn't exist
     */
    public static JLayeredPane getLayeredPane ( final Component component )
    {
        final JRootPane rootPane = getRootPane ( component );
        return rootPane != null ? rootPane.getLayeredPane () : null;
    }

    /**
     * Returns glass pane for the specified component or null if it doesn't exist.
     *
     * @param component component to look under
     * @return glass pane for the specified component or null if it doesn't exist
     */
    public static Component getGlassPane ( final Component component )
    {
        final JRootPane rootPane = getRootPane ( component );
        return rootPane != null ? rootPane.getGlassPane () : null;
    }

    /**
     * Sets an empty border for the specified component.
     *
     * @param component component to modify
     * @param border    component border
     * @param <C>       component type
     * @return modified component
     */
    public static <C extends JComponent> C setBorder ( final C component, final int border )
    {
        return setBorder ( component, LafUtils.createWebBorder ( border ) );
    }

    /**
     * Sets an empty border for the specified component.
     *
     * @param component component to modify
     * @param top       component top border
     * @param left      component left border
     * @param bottom    component bottom border
     * @param right     component right border
     * @param <C>       component type
     * @return modified component
     */
    public static <C extends JComponent> C setBorder ( final C component, final int top, final int left, final int bottom, final int right )
    {
        return setBorder ( component, LafUtils.createWebBorder ( top, left, bottom, right ) );
    }

    /**
     * Sets border for the specified component.
     *
     * @param component component to modify
     * @param border    component border
     * @param <C>       component type
     * @return modified component
     */
    public static <C extends JComponent> C setBorder ( final C component, final Border border )
    {
        component.setBorder ( border );
        return component;
    }

    /**
     * Returns whether component font is plain or not.
     *
     * @param component component to process
     * @return true if component font is plain, false otherwise
     */
    public static boolean isPlainFont ( final Component component )
    {
        return !( component != null && component.getFont () != null ) || component.getFont ().isPlain ();
    }

    /**
     * Changes font to plain for the specified component.
     *
     * @param component component to modify
     * @param <C>       component type
     * @return modified component
     */
    public static <C extends Component> C setPlainFont ( final C component )
    {
        return UIUtils.setPlainFont ( component, true );
    }

    

    /**
     * Returns dimension copy.
     *
     * @param dimension dimension to copy
     * @return dimension copy
     */
    public static Dimension copy ( final Dimension dimension )
    {
        return new Dimension ( dimension );
    }

    /**
     * Returns point copy.
     *
     * @param point point to copy
     * @return point copy
     */
    public static Point copy ( final Point point )
    {
        return new Point ( point );
    }

    /**
     * Returns rectangle copy.
     *
     * @param rectangle rectangle to copy
     * @return rectangle copy
     */
    public static Rectangle copy ( final Rectangle rectangle )
    {
        return new Rectangle ( rectangle );
    }

    /**
     * Returns insets copy.
     *
     * @param insets insets to copy
     * @return insets copy
     */
    public static Insets copy ( final Insets insets )
    {
        return new Insets ( insets.top, insets.left, insets.bottom, insets.right );
    }

    /**
     * Returns color copy.
     *
     * @param color color to copy
     * @return color copy
     */
    public static Color copy ( final Color color )
    {
        return new Color ( color.getRed (), color.getGreen (), color.getBlue (), color.getAlpha () );
    }

    /**
     * Returns component size represented as a rectangle with zero X and Y coordinates.
     *
     * @param component component to process
     * @return component size rectangle
     */
    public static Rectangle size ( final Component component )
    {
        return new Rectangle ( 0, 0, component.getWidth (), component.getHeight () );
    }

    /**
     * Returns maximum insets combined from the specified ones.
     *
     * @param insets1 first insets
     * @param insets2 second insets
     * @return maximum insets
     */
    public static Insets max ( final Insets insets1, final Insets insets2 )
    {
        if ( insets1 != null && insets2 != null )
        {
            return new Insets ( Math.max ( insets1.top, insets2.top ), Math.max ( insets1.left, insets2.left ),
                    Math.max ( insets1.bottom, insets2.bottom ), Math.max ( insets1.right, insets2.right ) );
        }
        else if ( insets1 != null )
        {
            return insets1;
        }
        else if ( insets2 != null )
        {
            return insets2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns minimum insets combined from the specified ones.
     *
     * @param insets1 first insets
     * @param insets2 second insets
     * @return minimum insets
     */
    public static Insets min ( final Insets insets1, final Insets insets2 )
    {
        if ( insets1 != null && insets2 != null )
        {
            return new Insets ( Math.min ( insets1.top, insets2.top ), Math.min ( insets1.left, insets2.left ),
                    Math.min ( insets1.bottom, insets2.bottom ), Math.min ( insets1.right, insets2.right ) );
        }
        else if ( insets1 != null )
        {
            return insets1;
        }
        else if ( insets2 != null )
        {
            return insets2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns maximum dimension combined from specified components dimensions.
     *
     * @param component1 first component
     * @param component2 second component
     * @return maximum dimension
     */
    public static Dimension max ( final Component component1, final Component component2 )
    {
        return max ( component1.getPreferredSize (), component2.getPreferredSize () );
    }

    /**
     * Returns maximum dimension combined from specified ones.
     *
     * @param dimension1 first dimension
     * @param dimension2 second dimension
     * @return maximum dimension
     */
    public static Dimension max ( final Dimension dimension1, final Dimension dimension2 )
    {
        if ( dimension1 == null && dimension2 == null )
        {
            return null;
        }
        else if ( dimension1 == null )
        {
            return dimension2;
        }
        else if ( dimension2 == null )
        {
            return dimension1;
        }
        else
        {
            return new Dimension ( Math.max ( dimension1.width, dimension2.width ), Math.max ( dimension1.height, dimension2.height ) );
        }
    }

    /**
     * Returns minimum dimension combined from specified components dimensions.
     *
     * @param component1 first component
     * @param component2 second component
     * @return minimum dimension
     */
    public static Dimension min ( final Component component1, final Component component2 )
    {
        return min ( component1.getPreferredSize (), component2.getPreferredSize () );
    }

    /**
     * Returns minimum dimension combined from specified ones.
     *
     * @param dimension1 first dimension
     * @param dimension2 second dimension
     * @return minimum dimension
     */
    public static Dimension min ( final Dimension dimension1, final Dimension dimension2 )
    {
        if ( dimension1 == null || dimension2 == null )
        {
            return null;
        }
        else
        {
            return new Dimension ( Math.min ( dimension1.width, dimension2.width ), Math.min ( dimension1.height, dimension2.height ) );
        }
    }

    /**
     * Sets opaque state of component and all of its children.
     *
     * @param component component to modify
     * @param opaque    whether opaque state or not
     */
    public static void setOpaqueRecursively ( final Component component, final boolean opaque )
    {
        setOpaqueRecursively ( component, opaque, false );
    }

    /**
     * Sets opaque state of component and all of its children.
     *
     * @param component  component to modify
     * @param opaque     whether opaque state or not
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setOpaqueRecursively ( final Component component, final boolean opaque, final boolean childsOnly )
    {
        if ( component instanceof JComponent )
        {
            final JComponent jComponent = ( JComponent ) component;
            if ( !childsOnly )
            {
                jComponent.setOpaque ( opaque );
            }
        }
        if ( component instanceof Container )
        {
            for ( final Component child : ( ( Container ) component ).getComponents () )
            {
                setOpaqueRecursively ( child, opaque, false );
            }
        }
    }

    /**
     * Sets double buffered state of component and all of its children.
     *
     * @param component      component to modify
     * @param doubleBuffered whether use double buffering or not
     */
    public static void setDoubleBufferedRecursively ( final Component component, final boolean doubleBuffered )
    {
        setDoubleBufferedRecursively ( component, doubleBuffered, false );
    }

    /**
     * Sets double buffered state of component and all of its children.
     *
     * @param component      component to modify
     * @param doubleBuffered whether use double buffering or not
     * @param childsOnly     whether exclude component from changes or not
     */
    public static void setDoubleBufferedRecursively ( final Component component, final boolean doubleBuffered, final boolean childsOnly )
    {
        if ( component instanceof JComponent )
        {
            final JComponent jComponent = ( JComponent ) component;
            if ( !childsOnly )
            {
                jComponent.setDoubleBuffered ( doubleBuffered );
            }
        }
        if ( component instanceof Container )
        {
            for ( final Component child : ( ( Container ) component ).getComponents () )
            {
                setDoubleBufferedRecursively ( child, doubleBuffered, false );
            }
        }
    }

    /**
     * Sets enabled state of component and all of its children.
     *
     * @param component component to modify
     * @param enabled   whether component is enabled or not
     */
    public static void setEnabledRecursively ( final Component component, final boolean enabled )
    {
        setEnabledRecursively ( component, enabled, false );
    }

    /**
     * Sets enabled state of component and all of its children.
     *
     * @param component  component to modify
     * @param enabled    whether component is enabled or not
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setEnabledRecursively ( final Component component, final boolean enabled, final boolean childsOnly )
    {
        if ( !childsOnly )
        {
            component.setEnabled ( enabled );
        }
        if ( component instanceof Container )
        {
            if ( component instanceof JComponent )
            {
                final Object co = ( ( JComponent ) component ).getClientProperty ( HANDLES_ENABLE_STATE );
                if ( co != null && ( Boolean ) co && !childsOnly )
                {
                    return;
                }
            }
            for ( final Component child : ( ( Container ) component ).getComponents () )
            {
                setEnabledRecursively ( child, enabled, false );
            }
        }
    }

    /**
     * Sets focusable state of component and all of its children.
     *
     * @param component component to modify
     * @param focusable whether component is focusable or not
     */
    public static void setFocusableRecursively ( final JComponent component, final boolean focusable )
    {
        setFocusableRecursively ( component, focusable, false );
    }

    /**
     * Sets focusable state of component and all of its children.
     *
     * @param component  component to modify
     * @param focusable  whether component is focusable or not
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setFocusableRecursively ( final JComponent component, final boolean focusable, final boolean childsOnly )
    {
        if ( !childsOnly )
        {
            component.setFocusable ( focusable );
        }
        for ( int i = 0; i < component.getComponentCount (); i++ )
        {
            if ( component.getComponent ( i ) instanceof JComponent )
            {
                setFocusableRecursively ( ( JComponent ) component.getComponent ( i ), focusable, false );
            }
        }
    }

    /**
     * Sets background color of component and all of its children.
     *
     * @param component component to modify
     * @param bg        new background color
     */
    public static void setBackgroundRecursively ( final Component component, final Color bg )
    {
        setBackgroundRecursively ( component, bg, false );
    }

    /**
     * Sets background color of component and all of its children.
     *
     * @param component  component to modify
     * @param bg         new background color
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setBackgroundRecursively ( final Component component, final Color bg, final boolean childsOnly )
    {
        if ( !childsOnly )
        {
            component.setBackground ( bg );
        }
        if ( component instanceof Container )
        {
            for ( final Component child : ( ( Container ) component ).getComponents () )
            {
                setBackgroundRecursively ( child, bg, false );
            }
        }
    }

    /**
     * Sets foreground color of component and all of its children.
     *
     * @param component  component to modify
     * @param foreground new foreground color
     */
    public static void setForegroundRecursively ( final JComponent component, final Color foreground )
    {
        setForegroundRecursively ( component, foreground, false );
    }

    /**
     * Sets foreground color of component and all of its children.
     *
     * @param component  component to modify
     * @param foreground new foreground color
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setForegroundRecursively ( final JComponent component, final Color foreground, final boolean childsOnly )
    {
        if ( !childsOnly )
        {
            component.setForeground ( foreground );
        }
        for ( int i = 0; i < component.getComponentCount (); i++ )
        {
            if ( component.getComponent ( i ) instanceof JComponent )
            {
                setForegroundRecursively ( ( JComponent ) component.getComponent ( i ), foreground, false );
            }
        }
    }

    /**
     * Sets font of component and all of its children.
     *
     * @param component component to modify
     * @param font      new font
     */
    public static void setFontRecursively ( final JComponent component, final Font font )
    {
        setFontRecursively ( component, font, false );
    }

    /**
     * Sets font of component and all of its children.
     *
     * @param component  component to modify
     * @param font       new font
     * @param childsOnly whether exclude component from changes or not
     */
    public static void setFontRecursively ( final JComponent component, final Font font, final boolean childsOnly )
    {
        if ( !childsOnly )
        {
            component.setFont ( font );
        }
        for ( int i = 0; i < component.getComponentCount (); i++ )
        {
            if ( component.getComponent ( i ) instanceof JComponent )
            {
                setFontRecursively ( ( JComponent ) component.getComponent ( i ), font, false );
            }
        }
    }

    /**
     * Returns component snapshot image.
     * Component must be showing to render properly using this method.
     *
     * @param content component for snapshot
     * @return component snapshot image
     */
    public static BufferedImage createComponentSnapshot ( final Component content )
    {
        return createComponentSnapshot ( content, content.getWidth (), content.getHeight () );
    }

    /**
     * Returns component snapshot image of specified size.
     * Component must be showing to render properly using this method.
     *
     * @param content component for snapshot
     * @param width   snapshot image width
     * @param height  snapshot image height
     * @return component snapshot image
     */
    public static BufferedImage createComponentSnapshot ( final Component content, final int width, final int height )
    {
        final BufferedImage bi = ImageUtils.createCompatibleImage ( width, height, Transparency.TRANSLUCENT );
        if ( content != null )
        {
            final Graphics2D g2d = bi.createGraphics ();
            content.setSize ( width, height );
            content.paintAll ( g2d );
            g2d.dispose ();
        }
        return bi;
    }
    /**
     * Returns active application window.
     *
     * @return active application window
     */
    public static Window getActiveWindow ()
    {
        final Window[] windows = Window.getWindows ();
        Window window = null;
        for ( final Window w : windows )
        {
            if ( w.isVisible () && w.isActive () && w.isFocused () )
            {
                window = w;
                break;
            }
        }
        return window;
    }

    /**
     * Returns whether specified event contains shortcut modifier or not.
     *
     * @param event event to process
     * @return true if specified event contains shortcut modifier, false otherwise
     */
    public static boolean isShortcut ( final InputEvent event )
    {
        return ( event.getModifiers () & getSystemShortcutModifier () ) != 0;
    }

    /**
     * Returns system shortcut modifier.
     *
     * @return system shortcut modifier
     */
    public static int getSystemShortcutModifier ()
    {
        if ( systemShortcutModifier == null )
        {
            systemShortcutModifier = Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask ();
        }
        return systemShortcutModifier;
    }

    /**
     * Returns readable form of hotkey triggered by specified key event.
     *
     * @param keyEvent key event to process
     * @return readable form of hotkey triggered by specified key event
     */
    public static String hotkeyToString ( final KeyEvent keyEvent )
    {
        return hotkeyToString ( isCtrl ( keyEvent ), isAlt ( keyEvent ), isShift ( keyEvent ), keyEvent.getKeyCode () );
    }

    /**
     * Returns readable form of specified key stroke.
     *
     * @param keyStroke key stroke to process
     * @return readable form of specified key stroke
     */
    public static String hotkeyToString ( final KeyStroke keyStroke )
    {
        return hotkeyToString ( isCtrl ( keyStroke.getModifiers () ), isAlt ( keyStroke.getModifiers () ),
                isShift ( keyStroke.getModifiers () ), keyStroke.getKeyCode () );
    }

    /**
     * Returns readable form for specified hotkey.
     *
     * @param isCtrl  whether hotkey requires CTRL modifier or not
     * @param isAlt   whether hotkey requires ALT modifier or not
     * @param isShift whether hotkey requires SHIFT modifier or not
     * @param keyCode key code for hotkey
     * @return readable form for specified hotkey
     */
    public static String hotkeyToString ( final boolean isCtrl, final boolean isAlt, final boolean isShift, final Integer keyCode )
    {
        final StringBuilder text = new StringBuilder ( "" );
        text.append ( isCtrl ? KeyEvent.getKeyModifiersText ( SwingUtils.getSystemShortcutModifier () ) +
                ( isAlt || isShift || keyCode != null ? "+" : "" ) : "" );
        text.append ( isAlt ? KeyEvent.getKeyModifiersText ( Event.ALT_MASK ) + ( isShift || keyCode != null ? "+" : "" ) : "" );
        text.append ( isShift ? KeyEvent.getKeyModifiersText ( Event.SHIFT_MASK ) + ( keyCode != null ? "+" : "" ) : "" );
        text.append ( keyCode != null ? KeyEvent.getKeyText ( keyCode ) : "" );
        return text.toString ();
    }

    /**
     * Returns whether CTRL modifier is triggered by the specified event or not.
     *
     * @param event event to process
     * @return true if CTRL modifier is triggered by the specified event, false otherwise
     */
    public static boolean isCtrl ( final InputEvent event )
    {
        return isCtrl ( event.getModifiers () );
    }

    /**
     * Returns whether CTRL modifier is triggered by the specified modifiers or not.
     *
     * @param modifiers modifiers to process
     * @return true if CTRL modifier is triggered by the specified modifiers, false otherwise
     */
    public static boolean isCtrl ( final int modifiers )
    {
        return ( modifiers & InputEvent.CTRL_MASK ) != 0;
    }

    /**
     * Returns whether ALT modifier is triggered by the specified event or not.
     *
     * @param event event to process
     * @return true if ALT modifier is triggered by the specified event, false otherwise
     */
    public static boolean isAlt ( final InputEvent event )
    {
        return isAlt ( event.getModifiers () );
    }

    /**
     * Returns whether ALT modifier is triggered by the specified modifiers or not.
     *
     * @param modifiers modifiers to process
     * @return true if ALT modifier is triggered by the specified modifiers, false otherwise
     */
    public static boolean isAlt ( final int modifiers )
    {
        return ( modifiers & InputEvent.ALT_MASK ) != 0;
    }

    /**
     * Returns whether SHIFT modifier is triggered by the specified event or not.
     *
     * @param event event to process
     * @return true if SHIFT modifier is triggered by the specified event, false otherwise
     */
    public static boolean isShift ( final InputEvent event )
    {
        return isShift ( event.getModifiers () );
    }

    /**
     * Returns whether SHIFT modifier is triggered by the specified modifiers or not.
     *
     * @param modifiers modifiers to process
     * @return true if SHIFT modifier is triggered by the specified modifiers, false otherwise
     */
    public static boolean isShift ( final int modifiers )
    {
        return ( modifiers & InputEvent.SHIFT_MASK ) != 0;
    }
    /**
     * Returns default label font.
     * This method might be used as a hack with other L&Fs to retrieve system default font for simple text.
     *
     * @return default label font
     */
    public static Font getDefaultLabelFont ()
    {
        if ( label == null )
        {
            label = new JLabel ();
        }
        return label.getFont ();
    }

    /**
     * Returns scroll pane for specified component if exists, null otherwise.
     *
     * @param component component to process
     * @return scroll pane for specified component if exists, null otherwise
     */
    public static JScrollPane getScrollPane ( final Component component )
    {
        if ( component != null && component.getParent () != null && component.getParent () instanceof JViewport &&
                component.getParent ().getParent () != null && component.getParent ().getParent () instanceof JScrollPane )
        {
            return ( JScrollPane ) component.getParent ().getParent ();
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns first focusable component found in the container.
     *
     * @param container container to process
     * @return first focusable component found in the container
     */
    public static Component findFocusableComponent ( final Container container )
    {
        final FocusTraversalPolicy focusTraversalPolicy = container.getFocusTraversalPolicy ();
        if ( focusTraversalPolicy != null )
        {
            return focusTraversalPolicy.getFirstComponent ( container );
        }
        else
        {
            for ( final Component component : container.getComponents () )
            {
                if ( component.isFocusable () )
                {
                    return component;
                }
                if ( component instanceof Container )
                {
                    final Component focusable = findFocusableComponent ( ( Container ) component );
                    if ( focusable != null )
                    {
                        return focusable;
                    }
                }
            }
            return null;
        }
    }

    /**
     * Returns component bounds on screen.
     *
     * @param component component to process
     * @return component bounds on screen
     */
    public static Rectangle getBoundsOnScreen ( final Component component )
    {
        return new Rectangle ( component.getLocationOnScreen (), component.getSize () );
    }

    /**
     * Returns component bounds inside its window.
     * This will return component bounds relative to window root pane location, not the window location.
     *
     * @param component component to process
     * @return component bounds inside its window
     */
    public static Rectangle getBoundsInWindow ( final Component component )
    {
        return component instanceof Window || component instanceof JApplet ? getRootPane ( component ).getBounds () :
                getRelativeBounds ( component, getRootPane ( component ) );
    }

    /**
     * Returns component bounds relative to another component.
     *
     * @param component  component to process
     * @param relativeTo component relative to which bounds will be returned
     * @return component bounds relative to another component
     */
    public static Rectangle getRelativeBounds ( final Component component, final Component relativeTo )
    {
        return new Rectangle ( getRelativeLocation ( component, relativeTo ), component.getSize () );
    }

    /**
     * Returns component location relative to another component.
     *
     * @param component  component to process
     * @param relativeTo component relative to which location will be returned
     * @return component location relative to another component
     */
    public static Point getRelativeLocation ( final Component component, final Component relativeTo )
    {
        final Point los = component.getLocationOnScreen ();
        final Point rt = relativeTo.getLocationOnScreen ();
        return new Point ( los.x - rt.x, los.y - rt.y );
    }

    /**
     * Returns whether specified components have the same ancestor or not.
     *
     * @param component1 first component
     * @param component2 second component
     * @return true if specified components have the same ancestor, false otherwise
     */
    public static boolean isSameAncestor ( final Component component1, final Component component2 )
    {
        return getWindowAncestor ( component1 ) == getWindowAncestor ( component2 );
    }

    /**
     * Makes all specified component sizes equal.
     *
     * @param components components to modify
     */
    public static void equalizeComponentsSize ( final Component... components )
    {
        final Dimension maxSize = new Dimension ( 0, 0 );
        for ( final Component c : components )
        {
            if ( c != null )
            {
                final Dimension ps = c.getPreferredSize ();
                maxSize.width = Math.max ( maxSize.width, ps.width );
                maxSize.height = Math.max ( maxSize.height, ps.height );
            }
        }
        for ( final Component c : components )
        {
            if ( c != null )
            {
                c.setPreferredSize ( maxSize );
            }
        }
    }

    /**
     * Makes all specified component widths equal.
     *
     * @param components components to modify
     */
    public static void equalizeComponentsWidths ( final Component... components )
    {
        int maxWidth = 0;
        for ( final Component c : components )
        {
            if ( c != null )
            {
                maxWidth = Math.max ( maxWidth, c.getPreferredSize ().width );
            }
        }
        for ( final Component c : components )
        {
            if ( c != null )
            {
                c.setPreferredSize ( new Dimension ( maxWidth, c.getPreferredSize ().height ) );
            }
        }
    }

    /**
     * Makes all specified component heights equal.
     *
     * @param components components to modify
     */
    public static void equalizeComponentsHeights ( final Component... components )
    {
        int maxHeight = 0;
        for ( final Component c : components )
        {
            if ( c != null )
            {
                maxHeight = Math.max ( maxHeight, c.getPreferredSize ().height );
            }
        }
        for ( final Component c : components )
        {
            if ( c != null )
            {
                c.setPreferredSize ( new Dimension ( c.getPreferredSize ().width, maxHeight ) );
            }
        }
    }

    /**
     * Returns whether the first component or any of its children are equal to second component or not.
     *
     * @param component1 first component to compare
     * @param component2 second component to compare
     * @return true if the first component or any of its children are equal to second component, false otherwise
     */
    public static boolean isEqualOrChild ( final Component component1, final Component component2 )
    {
        if ( component1 == component2 )
        {
            return true;
        }
        else
        {
            if ( component1 instanceof Container )
            {
                for ( final Component c : ( ( Container ) component1 ).getComponents () )
                {
                    if ( isEqualOrChild ( c, component2 ) )
                    {
                        return true;
                    }
                }
                return false;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Returns whether component or any of its children has focus or not.
     *
     * @param component component to process
     * @return true if component or any of its children has focus, false otherwise
     */
    public static boolean hasFocusOwner ( final Component component )
    {
        final Component focusOwner = FocusManager.getCurrentManager ().getFocusOwner ();
        return component == focusOwner || component instanceof Container && ( ( Container ) component ).isAncestorOf ( focusOwner );
    }

    /**
     * Returns whether atleast one of child components within the specified container is focusable or not.
     *
     * @param container container to process
     * @return true if atleast one of child components within the specified container is focusable, false otherwise
     */
    public static boolean hasFocusableComponent ( final Container container )
    {
        for ( final Component component : container.getComponents () )
        {
            if ( component.isFocusable () )
            {
                return true;
            }
            else if ( component instanceof Container )
            {
                if ( hasFocusableComponent ( ( Container ) component ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns system font names array.
     *
     * @return system font names array
     */
    public static String[] getFontNames ()
    {
        if ( fontNames == null )
        {
            fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment ().getAvailableFontFamilyNames ();
        }
        return fontNames;
    }

    /**
     * Returns system fonts array.
     *
     * @return system fonts array
     */
    public static Font[] getFonts ()
    {
        if ( fonts == null )
        {
            fonts = createFonts ( getFontNames () );
        }
        return fonts;
    }

    /**
     * Creates fonts by their font-names
     */

    /**
     * Returns an array of fonts created using specified array of font names.
     *
     * @param fontNames array of font names
     * @return an array of fonts
     */
    public static Font[] createFonts ( final String[] fontNames )
    {
        final Font[] fonts = new Font[ fontNames.length ];
        for ( int i = 0; i < fontNames.length; i++ )
        {
            fonts[ i ] = new Font ( fontNames[ i ], Font.PLAIN, 13 );
        }
        return fonts;
    }

    /**
     * Delays an invoke later call.
     *
     * @param delay    delay time in milliseconds
     * @param runnable runnable
     */
    public static void delayInvokeLater ( final long delay, final Runnable runnable )
    {
        new Thread ( new Runnable ()
        {
            @Override
            public void run ()
            {
                ThreadUtils.sleepSafely ( delay );
                SwingUtilities.invokeLater ( runnable );
            }
        } ).start ();
    }

    /**
     * Improved invoke later call.
     *
     * @param runnable runnable
     */
    public static void invokeLater ( final Runnable runnable )
    {
        if ( SwingUtilities.isEventDispatchThread () )
        {
            runnable.run ();
        }
        else
        {
            SwingUtilities.invokeLater ( runnable );
        }
    }    
    /**
     * Improved invoke and wait call.
     *
     * @param runnable runnable
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public static void invokeAndWait ( final Runnable runnable ) throws InterruptedException, InvocationTargetException
    {
        if ( SwingUtilities.isEventDispatchThread () )
        {
            runnable.run ();
        }
        else
        {
            SwingUtilities.invokeAndWait ( runnable );
        }
    }

    /**
     * Safe invoke and wait call that doesn't throw exceptions.
     *
     * @param runnable runnable
     */
    public static void invokeAndWaitSafely ( final Runnable runnable )
    {
        try
        {
            invokeAndWait ( runnable );
        }
        catch ( final Throwable e )
        {
            //
        }
    }

    /**
     * Returns insets converted into RTL orientation.
     *
     * @param insets insets to convert
     * @return insets converted into RTL orientation
     */
    public static Insets toRTL ( final Insets insets )
    {
        return new Insets ( insets.top, insets.right, insets.bottom, insets.left );
    }

    /**
     * Returns mouse point relative to specified component.
     *
     * @param component component to process
     * @return mouse point relative to specified component
     */
    public static Point getMousePoint ( final Component component )
    {
        final Point p = MouseInfo.getPointerInfo ().getLocation ();
        final Point los = component.getLocationOnScreen ();
        return new Point ( p.x - los.x, p.y - los.y );
    }

    /**
     * Scrolls scroll pane visible area smoothly to destination values.
     *
     * @param scrollPane scroll pane to scroll through
     * @param xValue     horiontal scroll bar value
     * @param yValue     vertical scroll bar value
     */
    public static void scrollSmoothly ( final JScrollPane scrollPane, int xValue, int yValue )
    {
        // todo 1. Use single thread to scroll through
        // todo 2. Make this method multiply usage possible
        // todo 3. Use timer instead of thread

        final JScrollBar hor = scrollPane.getHorizontalScrollBar ();
        final JScrollBar ver = scrollPane.getVerticalScrollBar ();

        final Dimension viewportSize = scrollPane.getViewport ().getSize ();
        xValue = xValue > hor.getMaximum () - viewportSize.width ? hor.getMaximum () - viewportSize.width : xValue;
        yValue = yValue > ver.getMaximum () - viewportSize.height ? ver.getMaximum () - viewportSize.height : yValue;
        final int x = xValue < 0 ? 0 : xValue;
        final int y = yValue < 0 ? 0 : yValue;


        final int xSign = hor.getValue () > x ? -1 : 1;
        final int ySign = ver.getValue () > y ? -1 : 1;

        new Thread ( new Runnable ()
        {
            @Override
            public void run ()
            {
                scrollThread1 = Thread.currentThread ();
                int lastValue = hor.getValue ();
                while ( lastValue != x )
                {
                    if ( scrollThread1 != Thread.currentThread () )
                    {
                        Thread.currentThread ().interrupt ();
                    }
                    if ( lastValue != x )
                    {
                        final int value = lastValue + xSign * Math.max ( Math.abs ( lastValue - x ) / 4, 1 );
                        lastValue = value;
                        SwingUtilities.invokeLater ( new Runnable ()
                        {
                            @Override
                            public void run ()
                            {
                                hor.setValue ( value );
                            }
                        } );
                        if ( xSign < 0 && value == hor.getMinimum () || xSign > 0 && value == hor.getMaximum () )
                        {
                            break;
                        }
                    }
                    ThreadUtils.sleepSafely ( 25 );
                }
            }
        } ).start ();
        new Thread ( new Runnable ()
        {
            @Override
            public void run ()
            {
                scrollThread2 = Thread.currentThread ();
                int lastValue = ver.getValue ();
                while ( lastValue != y )
                {
                    if ( scrollThread2 != Thread.currentThread () )
                    {
                        Thread.currentThread ().interrupt ();
                    }
                    if ( lastValue != y )
                    {
                        final int value = lastValue + ySign * Math.max ( Math.abs ( lastValue - y ) / 4, 1 );
                        lastValue = value;
                        SwingUtilities.invokeLater ( new Runnable ()
                        {
                            @Override
                            public void run ()
                            {
                                ver.setValue ( value );
                            }
                        } );
                        if ( ySign < 0 && value == ver.getMinimum () || ySign > 0 && value == ver.getMaximum () )
                        {
                            break;
                        }
                    }
                    ThreadUtils.sleepSafely ( 25 );
                }
            }
        } ).start ();
    }

    /**
     * Paints string with underlined character at the specified index.
     *
     * @param g               graphics context
     * @param text            painted text
     * @param underlinedIndex underlined character index
     * @param x               text X coordinate
     * @param y               text Y coordinate
     */
    public static void drawStringUnderlineCharAt ( final Graphics g, final String text, final int underlinedIndex, final int x,
                                                   final int y )
    {
        // Painting string
        drawString ( g, text, x, y );

        // Painting character underline
        if ( underlinedIndex >= 0 && underlinedIndex < text.length () )
        {
            final FontMetrics fm = g.getFontMetrics ();
            g.fillRect ( x + fm.stringWidth ( text.substring ( 0, underlinedIndex ) ), y + fm.getDescent () - 1,
                    fm.charWidth ( text.charAt ( underlinedIndex ) ), 1 );
        }
    }

    /**
     * Paints string.
     *
     * @param g    graphics context
     * @param text painted text
     * @param x    text X coordinate
     * @param y    text Y coordinate
     */
    public static void drawString ( final Graphics g, final String text, final int x, final int y )
    {
        g.drawString ( text, x, y );
    }

    /**
     * Installs text antialiasing hints into specified graphics context.
     *
     * @param g graphics context
     * @return old text antialiasing hints
     */
    public static Map setupTextAntialias ( final Graphics g )
    {
        return setupTextAntialias ( ( Graphics2D ) g );
    }

    /**
     * Installs text antialiasing hints into specified graphics context.
     *
     * @param g2d graphics context
     * @return old text antialiasing hints
     */
    public static Map setupTextAntialias ( final Graphics2D g2d )
    {
        return setupTextAntialias ( g2d, new RenderingHints 
        		( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON ));
    }

    /**
     * Installs text antialiasing hints into specified graphics context.
     *
     * @param g     graphics context
     * @param hints text antialiasing hints
     * @return old text antialiasing hints
     */
    public static Map setupTextAntialias ( final Graphics g, final Map hints )
    {
        return setupTextAntialias ( ( Graphics2D ) g, hints );
    }

    /**
     * Installs text antialiasing hints into specified graphics context.
     *
     * @param g2d   graphics context
     * @param hints text antialiasing hints
     * @return old text antialiasing hints
     */
    public static Map setupTextAntialias ( final Graphics2D g2d, final Map hints )
    {
        if ( hints != null )
        {
            final Map oldHints = getRenderingHints ( g2d, hints, null );
            g2d.addRenderingHints ( hints );
            return oldHints;
        }
        else
        {
            return null;
        }
    }

    /**
     * Restores text antialiasing hints into specified graphics context
     *
     * @param g     graphics context
     * @param hints old text antialiasing hints
     */
    public static void restoreTextAntialias ( final Graphics g, final Map hints )
    {
        restoreTextAntialias ( ( Graphics2D ) g, hints );
    }

    /**
     * Restores text antialiasing hints into specified graphics context
     *
     * @param g2d   graphics context
     * @param hints old text antialiasing hints
     */
    public static void restoreTextAntialias ( final Graphics2D g2d, final Map hints )
    {
        if ( hints != null )
        {
            g2d.addRenderingHints ( hints );
        }
    }

    /**
     * Returns map with rendering hints from a Graphics instance.
     *
     * @param g2d         Graphics instance
     * @param hintsToSave map of RenderingHint key-values
     * @param savedHints  map to save hints into
     * @return map with rendering hints from a Graphics instance
     */
    private static Map getRenderingHints ( final Graphics2D g2d, final Map hintsToSave, Map savedHints )
    {
        if ( savedHints == null )
        {
            savedHints = new RenderingHints ( null );
        }
        else
        {
            savedHints.clear ();
        }
        if ( hintsToSave == null || hintsToSave.size () == 0 )
        {
            return savedHints;
        }
        final Set objects = hintsToSave.keySet ();
        for ( Object o : objects )
        {
            final RenderingHints.Key key = ( RenderingHints.Key ) o;
            final Object value = g2d.getRenderingHint ( key );
            if ( value != null )
            {
                savedHints.put ( key, value );
            }
        }
        return savedHints;
    }

    /**
     * Returns the FontMetrics for the current Font of the passed in Graphics.
     * This method is used when a Graphics is available, typically when painting.
     * If a Graphics is not available the JComponent method of the same name should be used.
     * <p/>
     * This does not necessarily return the FontMetrics from the Graphics.
     *
     * @param c JComponent requesting FontMetrics, may be null
     * @param g Graphics Graphics
     */
    public static FontMetrics getFontMetrics ( final JComponent c, final Graphics g )
    {
        return getFontMetrics ( c, g, g.getFont () );
    }

    /**
     * Returns the FontMetrics for the specified Font.
     * This method is used when a Graphics is available, typically when painting.
     * If a Graphics is not available the JComponent method of the same name should be used.
     * <p/>
     * This does not necessarily return the FontMetrics from the Graphics.
     *
     * @param c    JComponent requesting FontMetrics, may be null
     * @param g    Graphics Graphics
     * @param font Font to get FontMetrics for
     */
    public static FontMetrics getFontMetrics ( final JComponent c, final Graphics g, final Font font )
    {
        if ( c != null )
        {
            return c.getFontMetrics ( font );
        }
        else
        {
            return g.getFontMetrics ( font );
        }
    }

    /**
     * Returns the width of the passed in String.
     * If the passed String is null, returns zero.
     *
     * @param fm     FontMetrics used to measure the String width
     * @param string String to get the width of
     */
    public static int stringWidth ( final FontMetrics fm, final String string )
    {
        if ( string == null || string.equals ( "" ) )
        {
            return 0;
        }
        return fm.stringWidth ( string );
    }

    /**
     * Returns the left side bearing of the first character of string.
     * The left side bearing is calculated from the passed in FontMetrics.
     *
     * @param c      JComponent that will display the string
     * @param fm     FontMetrics used to measure the String width
     * @param string String to get the left side bearing for
     */
    public static int getLeftSideBearing ( final JComponent c, final FontMetrics fm, final String string )
    {
        if ( ( string == null ) || ( string.length () == 0 ) )
        {
            return 0;
        }
        return getLeftSideBearing ( c, fm, string.charAt ( 0 ) );
    }

    /**
     * Returns the left side bearing of the specified character.
     * The left side bearing is calculated from the passed in FontMetrics.
     *
     * @param c         JComponent that will display the string
     * @param fm        FontMetrics used to measure the String width
     * @param firstChar Character to get the left side bearing for
     */
    public static int getLeftSideBearing ( final JComponent c, final FontMetrics fm, final char firstChar )
    {
        return getBearing ( c, fm, firstChar, true );
    }

    /**
     * Returns the right side bearing of the last character of string.
     * The right side bearing is calculated from the passed in FontMetrics.
     *
     * @param c      JComponent that will display the string
     * @param fm     FontMetrics used to measure the String width
     * @param string String to get the right side bearing for
     */
    public static int getRightSideBearing ( final JComponent c, final FontMetrics fm, final String string )
    {
        if ( ( string == null ) || ( string.length () == 0 ) )
        {
            return 0;
        }
        return getRightSideBearing ( c, fm, string.charAt ( string.length () - 1 ) );
    }

    /**
     * Returns the right side bearing of the specified character.
     * The right side bearing is calculated from the passed in FontMetrics.
     *
     * @param c        JComponent that will display the string
     * @param fm       FontMetrics used to measure the String width
     * @param lastChar Character to get the right side bearing for
     */
    public static int getRightSideBearing ( final JComponent c, final FontMetrics fm, final char lastChar )
    {
        return getBearing ( c, fm, lastChar, false );
    }

    /**
     * Returns the left and right side bearing for a character.
     * Strongly caches bearings for STRONG_BEARING_CACHE_SIZE most recently used Fonts and softly caches as many as GC allows.
     *
     * @param comp          JComponent that will display the string
     * @param fm            FontMetrics used to measure the String width
     * @param c             Character to get the right side bearing for
     * @param isLeftBearing whether left bearing is calculated or not
     * @return the left and right side bearing for a character
     */
    private static int getBearing ( final JComponent comp, FontMetrics fm, final char c, final boolean isLeftBearing )
    {
        if ( fm == null )
        {
            if ( comp == null )
            {
                return 0;
            }
            else
            {
                fm = comp.getFontMetrics ( comp.getFont () );
            }
        }
        synchronized ( SwingUtils.class )
        {
            final BearingCacheEntry searchKey = new BearingCacheEntry ( fm );
            BearingCacheEntry entry = null;
            // See if we already have an entry in the strong cache
            for ( final BearingCacheEntry cacheEntry : strongBearingCache )
            {
                if ( searchKey.equals ( cacheEntry ) )
                {
                    entry = cacheEntry;
                    break;
                }
            }
            // See if we already have an entry in the soft cache
            if ( entry == null )
            {
                final Iterator<SoftReference<BearingCacheEntry>> iter = softBearingCache.iterator ();
                while ( iter.hasNext () )
                {
                    final BearingCacheEntry cacheEntry = iter.next ().get ();
                    if ( cacheEntry == null )
                    {
                        // Remove discarded soft reference from the cache
                        iter.remove ();
                        continue;
                    }
                    if ( searchKey.equals ( cacheEntry ) )
                    {
                        entry = cacheEntry;
                        putEntryInStrongCache ( entry );
                        break;
                    }
                }
            }
            if ( entry == null )
            {
                // No entry, add it
                entry = searchKey;
                cacheEntry ( entry );
            }
            return ( isLeftBearing ) ? entry.getLeftSideBearing ( c ) : entry.getRightSideBearing ( c );
        }
    }

    /**
     * Adds enty into cache.
     *
     * @param entry bearing cache entry
     */
    private synchronized static void cacheEntry ( final BearingCacheEntry entry )
    {
        // Move the oldest entry from the strong cache into the soft cache
        final BearingCacheEntry oldestEntry = strongBearingCache[ strongBearingCacheNextIndex ];
        if ( oldestEntry != null )
        {
            softBearingCache.add ( new SoftReference<BearingCacheEntry> ( oldestEntry ) );
        }
        // Put entry in the strong cache
        putEntryInStrongCache ( entry );
    }

    /**
     * Adds enty into strong cache.
     *
     * @param entry bearing cache entry
     */
    private synchronized static void putEntryInStrongCache ( final BearingCacheEntry entry )
    {
        strongBearingCache[ strongBearingCacheNextIndex ] = entry;
        strongBearingCacheNextIndex = ( strongBearingCacheNextIndex + 1 ) % STRONG_BEARING_CACHE_SIZE;
    }

    /**
     * BearingCacheEntry is used to cache left and right character bearings for a particular Font and FontRenderContext.
     */
    private static class BearingCacheEntry
    {
        private FontMetrics fontMetrics;
        private Font font;
        private FontRenderContext frc;
        private Map<Character, Short> cache;
        private static final char[] oneChar = new char[ 1 ];

        public BearingCacheEntry ( final FontMetrics fontMetrics )
        {
            this.fontMetrics = fontMetrics;
            this.font = fontMetrics.getFont ();
            this.frc = fontMetrics.getFontRenderContext ();
            this.cache = new HashMap<Character, Short> ();
            assert ( font != null && frc != null );
        }

        public int getLeftSideBearing ( final char aChar )
        {
            Short bearing = cache.get ( aChar );
            if ( bearing == null )
            {
                bearing = calcBearing ( aChar );
                cache.put ( aChar, bearing );
            }
            return ( ( 0xFF00 & bearing ) >>> 8 ) - 127;
        }

        public int getRightSideBearing ( final char aChar )
        {
            Short bearing = cache.get ( aChar );
            if ( bearing == null )
            {
                bearing = calcBearing ( aChar );
                cache.put ( aChar, bearing );
            }
            return ( 0xFF & bearing ) - 127;
        }

        /* Calculates left and right side bearings for a character.
         * Makes an assumption that bearing is a value between -127 and +127.
         * Stores LSB and RSB as single two-byte number (short):
         * LSB is the high byte, RSB is the low byte.
         */
        private short calcBearing ( final char aChar )
        {
            oneChar[ 0 ] = aChar;
            final GlyphVector gv = font.createGlyphVector ( frc, oneChar );
            final Rectangle pixelBounds = gv.getGlyphPixelBounds ( 0, frc, 0f, 0f );

            // Get bearings
            int lsb = pixelBounds.x;
            int rsb = pixelBounds.width - fontMetrics.charWidth ( aChar );

            /*
             * HRGB/HBGR LCD glyph images will always have a pixel
             * on the left and a pixel on the right
             * used in colour fringe reduction.
             * Text rendering positions this correctly but here
             * we are using the glyph image to adjust that position
             * so must account for it.
             */
            if ( lsb < 0 )
            {
                final Object aaHint = frc.getAntiAliasingHint ();
                if ( aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB || aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR )
                {
                    lsb++;
                }
            }
            if ( rsb > 0 )
            {
                final Object aaHint = frc.getAntiAliasingHint ();
                if ( aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB || aaHint == RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR )
                {
                    rsb--;
                }
            }

            // Make sure that LSB and RSB are valid (see 6472972)
            if ( lsb < -127 || lsb > 127 )
            {
                lsb = 0;
            }
            if ( rsb < -127 || rsb > 127 )
            {
                rsb = 0;
            }

            final int bearing = ( ( lsb + 127 ) << 8 ) + ( rsb + 127 );
            return ( short ) bearing;
        }

        /**
         * {@inheritDoc}
         */
        public boolean equals ( final Object entry )
        {
            if ( entry == this )
            {
                return true;
            }
            if ( !( entry instanceof BearingCacheEntry ) )
            {
                return false;
            }
            final BearingCacheEntry oEntry = ( BearingCacheEntry ) entry;
            return ( font.equals ( oEntry.font ) && frc.equals ( oEntry.frc ) );
        }

        /**
         * {@inheritDoc}
         */
        public int hashCode ()
        {
            int result = 17;
            if ( font != null )
            {
                result = 37 * result + font.hashCode ();
            }
            if ( frc != null )
            {
                result = 37 * result + frc.hashCode ();
            }
            return result;
        }
    }
}