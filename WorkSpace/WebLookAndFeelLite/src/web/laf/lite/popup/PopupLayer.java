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

package web.laf.lite.popup;

import java.awt.*;

import javax.swing.JPanel;

import web.laf.lite.layout.MultiLayout;
import web.laf.lite.ui.ShapeProvider;

/**
 * This special container is used to place various custom WebLaF popups on it.
 * These lightweight popups are visible only within the window's root pane bounds.
 *
 * @author Mikle Garin
 * @see PopupManager
 * @see Popup
 */

public class PopupLayer extends JPanel
{
    /**
     * Constructs new popup layer.
     */
    public PopupLayer ()
    {
        this ( new MultiLayout () );
    }

    /**
     * Constructs new popup layer with the specified layout
     */
    public PopupLayer ( LayoutManager layoutManager )
    {
        super ( layoutManager );
        setOpaque ( false );
    }

    /**
     * Returns popup layer actual layout.
     *
     * @return popup layer actual layout
     */
    public MultiLayout getMultiLayout ()
    {
        return ( MultiLayout ) getLayout ();
    }

    /**
     * Adds layout manager to this popup layer.
     *
     * @param layoutManager layout manager to add
     */
    public void addLayoutManager ( LayoutManager layoutManager )
    {
        getMultiLayout ().addLayoutManager ( layoutManager );
    }

    /**
     * Removes layout manager from this glass pane.
     *
     * @param layoutManager layout manager to remove
     */
    public void removeLayoutManager ( LayoutManager layoutManager )
    {
        getMultiLayout ().removeLayoutManager ( layoutManager );
    }

    /**
     * Hides all popups visible on this popup layer.
     */
    public void hideAllPopups ()
    {
        // todo Call hidePopup on popup instead
        removeAll ();
        setVisible ( false );
    }

    /**
     * Displays the specified popup on this popup layer.
     *
     * @param popup popup to display
     */
    public void showPopup ( Popup popup )
    {
        // Adding popup
        add ( popup, 0 );
        setBounds ( new Rectangle ( 0, 0, getParent ().getWidth (), getParent ().getHeight () ) );
        setVisible ( true );
        revalidate ();
        repaint ();
    }

    /**
     * Hides specified popup displayed on this popup layer.
     *
     * @param popup popup to hide
     */
    public void hidePopup ( Popup popup )
    {
        // Ignore hide
        if ( !popup.isShowing () || popup.getParent () != PopupLayer.this )
        {
            return;
        }

        // Removing popup
        Rectangle bounds = popup.getBounds ();
        remove ( popup );
        revalidate ();
        repaint ( bounds );

        // Hiding layer if no popups left
        if ( getComponentCount () == 0 )
        {
            setVisible ( false );
        }
    }

    /**
     * Unlike default "contains" method this one returns true only if some of popups displayed on this layer contains the specified point.
     * Popup layer itself is not taken into account and doesn't absorb any mouse events because of that.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if some of popups displayed on this popup later contains the specified point, false otherwise
     */
    @Override
    public boolean contains ( int x, int y )
    {
        for ( Component child : getComponents () )
        {
            Point l = child.getLocation ();
            if ( child instanceof ShapeProvider )
            {
                Shape shape = ( ( ShapeProvider ) child ).provideShape ();
                if ( shape != null && shape.contains ( x - l.x, y - l.y ) )
                {
                    return true;
                }
            }
            else
            {
                if ( child.getBounds ().contains ( x, y ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether the specified point is within bounds of this popup layer or not.
     * This method returns default "contains" method result and might be used by some classes that would like to change layer's behavior.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if the specified point is within bounds of this popup layer, false otherwise
     */
    public boolean normalContains ( int x, int y )
    {
        return super.contains ( x, y );
    }
}