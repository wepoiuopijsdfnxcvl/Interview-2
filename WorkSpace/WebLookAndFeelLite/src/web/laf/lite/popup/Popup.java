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

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import web.laf.lite.focus.DefaultFocusTracker;
import web.laf.lite.focus.FocusManager;
import web.laf.lite.ui.AncestorAdapter;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is base popup class which offers basic popups functionality and contains all features needed to create great-looking popups within
 * the window root pane bounds.
 *
 * @author Mikle Garin
 * @see PopupManager
 * @see PopupLayer
 */

public class Popup extends JPanel {
	private static final long serialVersionUID = 1L;
	protected boolean closeOnFocusLoss = false;
    protected boolean requestFocusOnShow = true;
    protected Component defaultFocusComponent = null;
    protected List<Component> focusableChilds = new ArrayList<Component> ();

    protected Component lastComponent = null;
    protected AncestorListener lastListener = null;

    protected boolean focused = false;
    protected float fade = 0;

    public Popup ()
    {
    	super(new BorderLayout());
        initializePopup ();
    }

    /**
     * Initializes various popup settings.
     */
    protected void initializePopup ()
    {
        setOpaque ( false );
        // Listener to determine popup appearance and disappearance
        addAncestorListener ( new AncestorAdapter ()
        {
            @Override
            public void ancestorAdded ( AncestorEvent event )
            {
                if ( requestFocusOnShow )
                {
                    if ( defaultFocusComponent != null )
                    {
                        defaultFocusComponent.requestFocusInWindow ();
                    }
                    else
                    {
                        Popup.this.transferFocus ();
                    }
                }
                fade = 1;
            }

            @Override
            public void ancestorRemoved ( AncestorEvent event )
            {
            }
        } );

        // Focus tracking
        FocusManager.addFocusTracker ( this, new DefaultFocusTracker ( true )
        {
            @Override
            public boolean isTrackingEnabled ()
            {
                return Popup.this.isShowing ();
            }

            @Override
            public void focusChanged ( boolean focused )
            {
                Popup.this.focusChanged ( focused );
            }
        } );
    }

    /**
     * Called when this popup recieve or lose focus.
     * You can your own behavior for focus change by overriding this method.
     *
     * @param focused whether popup has focus or not
     */
    protected void focusChanged ( boolean focused )
    {
        // todo Replace with MultiFocusTracker (for multiply components)
        if ( Popup.this.isShowing () && !focused && !isChildFocused () && closeOnFocusLoss )
        {
            hidePopup ();
        }
    }

    public boolean isCloseOnFocusLoss ()
    {
        return closeOnFocusLoss;
    }

    public void setCloseOnFocusLoss ( boolean closeOnFocusLoss )
    {
        this.closeOnFocusLoss = closeOnFocusLoss;
    }

    public boolean isRequestFocusOnShow ()
    {
        return requestFocusOnShow;
    }

    public void setRequestFocusOnShow ( boolean requestFocusOnShow )
    {
        this.requestFocusOnShow = requestFocusOnShow;
    }

    public Component getDefaultFocusComponent ()
    {
        return defaultFocusComponent;
    }

    public void setDefaultFocusComponent ( Component defaultFocusComponent )
    {
        this.defaultFocusComponent = defaultFocusComponent;
    }

    /**
     * Focusable components which will not force popup to close
     */

    public List<Component> getFocusableChilds ()
    {
        return focusableChilds;
    }

    public void addFocusableChild ( Component child )
    {
        focusableChilds.add ( child );
    }

    public void removeFocusableChild ( Component child )
    {
        focusableChilds.remove ( child );
    }

    public boolean isChildFocused ()
    {
        for ( Component child : focusableChilds )
        {
            if ( SwingUtils.hasFocusOwner ( child ) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Popup display methods
     */

    public void showAsPopupMenu ( final Component component )
    {
        // Detrmining component position inside window
        final Rectangle cb = SwingUtils.getBoundsInWindow ( component );
        final Dimension rps = SwingUtils.getRootPane ( component ).getSize ();
        final Dimension ps = Popup.this.getPreferredSize ();
        //        Painter bp = getPainter ();
        //        Insets bm = bp != null ? bp.getMargin ( this ) : new Insets ( 0, 0, 0, 0 );

        // Choosing display way
        final int x;
        if ( cb.x + ps.width < rps.width || rps.width - cb.x - ps.width > cb.x )
        {
            // Expanding popup to the right side
            x = 0;
        }
        else
        {
            // Expanding popup to the left side
            x = cb.width - ps.width;
        }
        final int y;
        if ( cb.y + cb.height + ps.height < rps.height || rps.height - cb.y - cb.height - ps.height > cb.y )
        {
            // Displaying popup below the component
            y = cb.height;
        }
        else
        {
            // Displaying popup above the component
            y = -ps.height;
        }

        showPopup ( component, x, y );
    }

    public void showPopup ( Component component )
    {
        PopupManager.showPopup ( component, this, requestFocusOnShow );
        clearComponentAncestorListener ();
    }

    public void showPopup ( Component component, Point location )
    {
        showPopup ( component, location.x, location.y );
    }

    public void showPopup ( Component component, int x, int y )
    {
        final Dimension ps = Popup.this.getPreferredSize ();
        showPopup ( component, x, y, ps.width, ps.height );
    }

    public void showPopup ( Component component, Rectangle bounds )
    {
        showPopup ( component, bounds.x, bounds.y, bounds.width, bounds.height );
    }

    public void showPopup ( Component component, int x, int y, int width, int height )
    {
        updatePopupBounds ( component, x, y, width, height );
        PopupManager.showPopup ( component, this, requestFocusOnShow );
        updateComponentAncestorListener ( component, x, y, width, height );
    }

    protected void updatePopupBounds ( Component component, int x, int y, int width, int height )
    {
        // Updating popup bounds with component-relative values
        if ( component.isShowing () )
        {
            final Rectangle cb = SwingUtils.getBoundsInWindow ( component );
            setBounds ( cb.x + x, cb.y + y, width, height );
        }
    }

    protected void updateComponentAncestorListener ( final Component component, final int x, final int y, final int width,
                                                     final int height )
    {
        clearComponentAncestorListener ();

        lastComponent = component;
        if ( component instanceof JComponent )
        {
            lastListener = new AncestorAdapter ()
            {
                @Override
                public void ancestorMoved ( AncestorEvent event )
                {
                    updatePopupBounds ( component, x, y, width, height );
                }
            };
            ( ( JComponent ) lastComponent ).addAncestorListener ( lastListener );
        }
    }

    protected void clearComponentAncestorListener ()
    {
        if ( lastComponent != null && lastListener != null && lastComponent instanceof JComponent )
        {
            ( ( JComponent ) lastComponent ).removeAncestorListener ( lastListener );
        }
    }

    /**
     * Modal popup display methods
     */

    public void showPopupAsModal ( Component component )
    {
        showPopupAsModal ( component, false, false );
    }

    public void showPopupAsModal ( Component component, boolean hfill, boolean vfill )
    {
        PopupManager.showModalPopup ( component, this, hfill, vfill );
    }

    /**
     * Popup hide methods
     */

    public void hidePopup ()
    {
       hidePopupImpl ();
    }

    protected void hidePopupImpl ()
    {
        clearComponentAncestorListener ();

        final PopupLayer layer = ( PopupLayer ) this.getParent ();
        if ( layer != null )
        {
            layer.hidePopup ( this );
        }
    }

    /**
     * Popup pack method
     */

    public void packPopup ()
    {
        setSize ( getPreferredSize () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent ( Graphics g )
    {
        // Fade animation and transparency
        if ( fade < 1f )
        {
            LafUtils.setupAlphaComposite ( ( Graphics2D ) g, fade );
        }
        super.paintComponent ( g );
    }
}