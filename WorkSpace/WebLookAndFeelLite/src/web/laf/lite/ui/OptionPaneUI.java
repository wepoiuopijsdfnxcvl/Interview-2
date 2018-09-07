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
import javax.swing.plaf.basic.BasicOptionPaneUI;

import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

/**
 * User: mgarin Date: 17.08.11 Time: 22:46
 */

public class OptionPaneUI extends BasicOptionPaneUI
{

    @SuppressWarnings ( "UnusedParameters" )
    public static ComponentUI createUI ( JComponent c )
    {
        return new OptionPaneUI ();
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        optionPane.setOpaque ( false );
        optionPane.setBackground (new Color ( 237, 237, 237 ));

        // Updating border
        optionPane.setBorder ( BorderFactory.createEmptyBorder ( 15, 15, 15, 15 ) );
    }

    @Override
    protected Container createMessageArea ()
    {
        Container messageArea = super.createMessageArea ();
        SwingUtils.setOpaqueRecursively ( messageArea, false );
        return messageArea;
    }

    /**
     * Modified buttons creation method
     */

    @Override
    protected void addButtonComponents ( Container container, Object[] buttons, int initialIndex )
    {
        if ( container instanceof JComponent )
        {
            ( ( JComponent ) container ).setOpaque ( false );
        }
        if ( buttons != null && buttons.length > 0 )
        {
            boolean sizeButtonsToSame = getSizeButtonsToSameWidth ();
            boolean createdAll = true;
            int numButtons = buttons.length;
            JButton[] createdButtons = null;
            int maxWidth = 0;

            if ( sizeButtonsToSame )
            {
                createdButtons = new JButton[ numButtons ];
            }

            for ( int counter = 0; counter < numButtons; counter++ )
            {
                Object button = buttons[ counter ];
                Component newComponent;

                if ( button instanceof Component )
                {
                    createdAll = false;
                    newComponent = ( Component ) button;
                    container.add ( newComponent );
                    hasCustomComponents = true;

                }
                else
                {
                    JButton aButton;
                    if ( button instanceof Icon )
                    {
                        aButton = new JButton ( ( Icon ) button );
                    }
                    else
                    {
                        aButton = new JButton ( button.toString () );
                    }

                    aButton.setName ( "OptionPane.button" );
                    aButton.setMultiClickThreshhold ( UIManager.getInt ( "OptionPane.buttonClickThreshhold" ) );
                    configureButton ( aButton );

                    container.add ( aButton );

                    ActionListener buttonListener = createButtonActionListener ( counter );
                    if ( buttonListener != null )
                    {
                        aButton.addActionListener ( buttonListener );
                    }
                    newComponent = aButton;
                }
                if ( sizeButtonsToSame && createdAll && newComponent instanceof JButton )
                {
                    createdButtons[ counter ] = ( JButton ) newComponent;
                    maxWidth = Math.max ( maxWidth, newComponent.getMinimumSize ().width );
                }
                if ( counter == initialIndex )
                {
                    initialFocusComponent = newComponent;
                    if ( initialFocusComponent instanceof JButton )
                    {
                        JButton defaultB = ( JButton ) initialFocusComponent;
                        defaultB.addHierarchyListener ( new HierarchyListener ()
                        {
                            @Override
                            public void hierarchyChanged ( HierarchyEvent e )
                            {
                                if ( ( e.getChangeFlags () & HierarchyEvent.PARENT_CHANGED ) != 0 )
                                {
                                    JButton defaultButton = ( JButton ) e.getComponent ();
                                    JRootPane root = SwingUtilities.getRootPane ( defaultButton );
                                    if ( root != null )
                                    {
                                        root.setDefaultButton ( defaultButton );
                                    }
                                }
                            }
                        } );
                    }
                }
            }
            ( ( ButtonAreaLayout ) container.getLayout () ).setSyncAllWidths ( ( sizeButtonsToSame && createdAll ) );
        }
    }

    /**
     * Modified dialog buttons
     */

    @Override
    protected Object[] getButtons ()
    {
        if ( optionPane != null )
        {
            Object[] suppliedOptions = optionPane.getOptions ();

            if ( suppliedOptions == null )
            {
                JButton[] defaultOptions;
                int type = optionPane.getOptionType ();
                if ( type == JOptionPane.YES_NO_OPTION )
                {
                    defaultOptions = new JButton[ 2 ];

                    defaultOptions[ 0 ] = new JButton ( "Yes" );
                    defaultOptions[ 1 ] = new JButton ( "No" );
 
                }
                else if ( type == JOptionPane.YES_NO_CANCEL_OPTION )
                {
                    defaultOptions = new JButton[ 3 ];
                    defaultOptions[ 0 ] = new JButton ( "Yes" );
                    defaultOptions[ 1 ] = new JButton ( "No" );
                    defaultOptions[ 2 ] = new JButton ( "Cancel" );
                }
                else if ( type == JOptionPane.OK_CANCEL_OPTION )
                {
                    defaultOptions = new JButton[ 2 ];
                    defaultOptions[ 0 ] = new JButton ( "Ok" );
                    defaultOptions[ 1 ] = new JButton ( "Cancel" );
                }
                else
                {
                    defaultOptions = new JButton[ 1 ];
                    defaultOptions[ 0 ] = new JButton ( "Ok" );
                }

                int count = 0;
                for ( JButton button : defaultOptions )
                {
                    configureButton ( button );
                    button.addActionListener ( createButtonActionListener ( count ) );
                    count++;
                }

                return defaultOptions;

            }
            return suppliedOptions;
        }
        return null;
    }

    /**
     * Default option pane button settings
     */

    private void configureButton ( JButton button )
    {
        //button.setLeftRightSpacing ( 10 );
        button.setMinimumSize ( new Dimension ( 70, 0 ) );
        //button.setRolloverShine ( false );

        Font buttonFont = UIManager.getFont ( "OptionPane.buttonFont" );
        if ( buttonFont != null )
        {
            button.setFont ( buttonFont );
        }
    }

    /**
     * Modified dialogs side icons
     */
}
