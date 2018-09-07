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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * User: mgarin Date: 13.09.12 Time: 20:20
 * <p/>
 * This class allows you to install (and uninstallcif needed) component updater to any JComponent quickly without any additional coding,
 * which allows you to add optimized rolling updates for the component from the added ActionListeners.
 */

public class UpdateTimer extends SkyTimer implements AncestorListener
{
    private JComponent component;

    public UpdateTimer ( JComponent component )
    {
        super ( 30 );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, long delay )
    {
        super ( delay );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, String name, long delay )
    {
        super ( name, delay );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, long delay, long initialDelay )
    {
        super ( delay, initialDelay );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, String name, long delay, long initialDelay )
    {
        super ( name, delay, initialDelay );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, long delay, ActionListener listener )
    {
        super ( delay, listener );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, String name, long delay, ActionListener listener )
    {
        super ( name, delay, listener );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, long delay, long initialDelay, ActionListener listener )
    {
        super ( delay, initialDelay, listener );
        initialize ( component );
    }

    public UpdateTimer ( JComponent component, String name, long delay, long initialDelay, ActionListener listener )
    {
        super ( name, delay, initialDelay, listener );
        initialize ( component );
    }

    private void initialize ( JComponent component )
    {
        this.component = component;
        component.addAncestorListener ( this );
    }

    public void uninstall ()
    {
        component.removeAncestorListener ( this );
    }

    public Component getComponent ()
    {
        return component;
    }

    @Override
    public void ancestorAdded ( AncestorEvent event )
    {
        start ();
    }

    @Override
    public void ancestorRemoved ( AncestorEvent event )
    {
        stop ();
    }

    @Override
    public void ancestorMoved ( AncestorEvent event )
    {
        //
    }

    /**
     * Installs component updater and ensures that it is the only installed
     */

    public static UpdateTimer install ( JComponent component )
    {
        uninstall ( component );
        return new UpdateTimer ( component );
    }

    public static UpdateTimer install ( JComponent component, long delay )
    {
        uninstall ( component );
        return new UpdateTimer ( component, delay );
    }

    public static UpdateTimer install ( JComponent component, String name, long delay )
    {
        uninstall ( component );
        return new UpdateTimer ( component, name, delay );
    }

    public static UpdateTimer install ( JComponent component, long delay, long initialDelay )
    {
        uninstall ( component );
        return new UpdateTimer ( component, delay, initialDelay );
    }

    public static UpdateTimer install ( JComponent component, String name, long delay, long initialDelay )
    {
        uninstall ( component );
        return new UpdateTimer ( component, name, delay, initialDelay );
    }

    public static UpdateTimer install ( JComponent component, long delay, ActionListener listener )
    {
        uninstall ( component );
        return new UpdateTimer ( component, delay, listener );
    }

    public static UpdateTimer install ( JComponent component, String name, long delay, ActionListener listener )
    {
        uninstall ( component );
        return new UpdateTimer ( component, name, delay, listener );
    }

    public static UpdateTimer install ( JComponent component, long delay, long initialDelay, ActionListener listener )
    {
        uninstall ( component );
        return new UpdateTimer ( component, delay, initialDelay, listener );
    }

    public static UpdateTimer install ( JComponent component, String name, long delay, long initialDelay, ActionListener listener )
    {
        uninstall ( component );
        return new UpdateTimer ( component, name, delay, initialDelay, listener );
    }

    /**
     * Uninstalls any existing component updater from component
     */

    public static void uninstall ( JComponent component )
    {
        for ( AncestorListener listener : component.getAncestorListeners () )
        {
            if ( listener instanceof UpdateTimer )
            {
                component.removeAncestorListener ( listener );
            }
        }
    }

    /**
     * Checks if component has any component updater installed
     */

    public static boolean isInstalled ( JComponent component )
    {
        for ( AncestorListener listener : component.getAncestorListeners () )
        {
            if ( listener instanceof UpdateTimer )
            {
                return true;
            }
        }
        return false;
    }
}