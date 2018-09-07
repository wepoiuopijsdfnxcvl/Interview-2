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

import web.laf.lite.layout.AlignLayout;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SkyTimer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This special popup layer is used to place modal-like popups atop of it.
 * It basically provides a semi-transparent layer that covers the whole window and leaves only modal popup in focus.
 *
 * @author Mikle Garin
 * @see PopupManager
 */

public class ShadeLayer extends PopupLayer
{
    /**
     * Whether modal shade should be animated or not.
     * It might cause serious lags in case it is used in a large window with lots of UI elements.
     */
    protected boolean animate = true;

    /**
     * Layer current opacity.
     */
    protected int opacity = 0;

    /**
     * Layer opacity animator.
     */
    protected SkyTimer animator;

    /**
     * Whether popup close attemps should be blocked or not.
     */
    protected boolean blockClose = false;

    /**
     * Constructs new shade layer.
     */
    public ShadeLayer ()
    {
        super ( new AlignLayout () );

        MouseAdapter mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( !blockClose )
                {
                    hideAllPopups ();
                }
            }
        };
        addMouseListener ( mouseAdapter );
        addMouseMotionListener ( mouseAdapter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPopup ( Popup popup )
    {
        showPopup ( popup, false, false );
    }

    /**
     * Displays the specified popup on this popup layer.
     *
     * @param popup popup to display
     * @param hfill whether popup should fill the whole available window width or not
     * @param vfill whether popup should fill the whole available window height or not
     */
    public void showPopup ( Popup popup, boolean hfill, boolean vfill )
    {
        // Updating layout settings
        LayoutManager layoutManager = getLayout ();
        if ( layoutManager instanceof AlignLayout )
        {
            AlignLayout layout = ( AlignLayout ) layoutManager;
            layout.setHfill ( hfill );
            layout.setVfill ( vfill );
        }

        // Adding popup
        removeAll ();
        add ( popup, SwingConstants.CENTER + AlignLayout.SEPARATOR + SwingConstants.CENTER, 0 );
        setBounds ( new Rectangle ( 0, 0, getParent ().getWidth (), getParent ().getHeight () ) );
        setVisible ( true );
        revalidate ();
        repaint ();
    }

    /**
     * Returns whether modal shade should be animated or not.
     *
     * @return true if modal shade should be animated, false otherwise
     */
    public boolean isAnimate ()
    {
        return animate;
    }

    /**
     * Sets whether modal shade should be animated or not.
     *
     * @param animate whether modal shade should be animated or not
     */
    public void setAnimate ( boolean animate )
    {
        this.animate = animate;
    }

    /**
     * Returns whether popup close attemps should be blocked or not.
     *
     * @return true if popup close attemps should be blocked, false otherwise
     */
    public boolean isBlockClose ()
    {
        return blockClose;
    }

    /**
     * Sets whether popup close attemps should be blocked or not.
     *
     * @param blockClose whether popup close attemps should be blocked or not
     */
    public void setBlockClose ( boolean blockClose )
    {
        this.blockClose = blockClose;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint ( Graphics g )
    {
        // todo Really bad workaround
        LafUtils.setupAlphaComposite ( ( Graphics2D ) g, ( float ) opacity / 100, opacity < 100 );
        super.paint ( g );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent ( Graphics g )
    {
        super.paintComponent ( g );

        Graphics2D g2d = ( Graphics2D ) g;
        Object old = LafUtils.setupAntialias ( g2d );

        Composite comp = LafUtils.setupAlphaComposite ( g2d, 0.8f );
        g2d.setPaint ( Color.LIGHT_GRAY );
        g2d.fillRect ( 0, 0, getWidth (), getHeight () );
        LafUtils.restoreComposite ( g2d, comp );

        LafUtils.restoreAntialias ( g2d, old );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible ( boolean visible )
    {
        super.setVisible ( visible );
        if ( visible )
        {
            if ( animator != null )
            {
                animator.stop ();
            }
            if ( animate )
            {
                opacity = 0;
                animator = new SkyTimer ( "ShadeLayer.fadeIn", 40, new ActionListener ()
                {
                    @Override
                    public void actionPerformed ( ActionEvent e )
                    {
                        if ( opacity < 100 )
                        {
                            opacity += 25;
                            ShadeLayer.this.repaint ();
                        }
                        else
                        {
                            animator.stop ();
                        }
                    }
                } );
                animator.start ();
            }
            else
            {
                opacity = 100;
                ShadeLayer.this.repaint ();
            }
        }
    }

    /**
     * Returns whether the specified point is within bounds of this popup layer or not.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return true if the specified point is within bounds of this popup layer, false otherwise
     */
    @Override
    public boolean contains ( int x, int y )
    {
        return normalContains ( x, y );
    }
}