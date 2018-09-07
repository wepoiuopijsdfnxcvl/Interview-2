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

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.utils.CollectionUtils;
import web.laf.lite.utils.SkyTimer;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom popup used to display notifications within the application windows.
 *
 * @author Mikle Garin
 * @see NotificationManager
 * @see NotificationStyle
 * @see NotificationIcon
 * @see NotificationOption
 * @see WebPopup
 */

public class NotificationPopup extends Popup
{
    /**
     * Notification popup listeners.
     */
    protected List<NotificationListener> listeners = new ArrayList<NotificationListener> ( 1 );

    /**
     * Whether notification can be closed by simple click on popup or not.
     */
    protected boolean clickToClose = true;

    /**
     * Whether notification popup should be closed when some option is selected by user or not.
     * You can disable this and provide your own behavior for options selection through NotificationListener.
     */
    protected boolean closeOnOptionSelection = true;

    /**
     * Notification display duration.
     * If set to zero notification will be visible until closed by user.
     */
    protected long displayTime = 0;

    /**
     * Delayed hide timer.
     */
    protected SkyTimer displayTimer;

    /**
     * Whether notification is accepted or not.
     */
    protected boolean accepted;

    /**
     * Notification icon.
     */
    protected Icon icon;

    /**
     * Notification content.
     */
    protected Component content;

    /**
     * Notification options.
     */
    protected List<NotificationOption> options;

    /**
     * Notification visual components.
     */
    protected JLabel iconImage;
    protected JPanel contentPanel;
    protected JPanel optionsPanel;

    /**
     * Constructs new notification popup with the specified painter.
     *
     * @param stylePainter popup style painter
     */
    public NotificationPopup ()
    {
        //super ( stylePainter );
        initializeNotificationPopup ();
        //Painter painter = new NinePatchIconPainter ( NotificationStyle.class.getResource ( "icons/styles/" + this + ".9.png" ) );
    }

    /**
     * Initializes various notification popup settings.
     */
    protected void initializeNotificationPopup ()
    {
        setLayout ( new BorderLayout ( 15, 5 ) );
        //setAnimated ( true );

        iconImage = new JLabel ();
        add ( new AlignPanel ( iconImage, SwingConstants.CENTER, SwingConstants.TOP ), BorderLayout.WEST );
        updateIcon ();

        contentPanel = new JPanel (new BorderLayout());
        contentPanel.setOpaque ( false );
        add ( new AlignPanel ( contentPanel, SwingConstants.CENTER, SwingConstants.CENTER ), BorderLayout.CENTER );
        updateContent ();

        optionsPanel = new JPanel ( new HorizontalFlowLayout ( 4, false ) );
        optionsPanel.setOpaque ( false );
        add ( new AlignPanel ( optionsPanel, SwingConstants.RIGHT, SwingConstants.CENTER ), BorderLayout.SOUTH );
        updateOptions ();

        addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( clickToClose )
                {
                    if ( SwingUtils.isLeftMouseButton ( e ) )
                    {
                        acceptAndHide ();
                    }
                    else
                    {
                        hidePopup ();
                    }
                }
            }
        } );
        /*addPopupListener ( new PopupAdapter ()
        {
            @Override
            public void popupWillBeOpened ()
            {
                accepted = false;
            }

            @Override
            public void popupOpened ()
            {
                startDelayedClose ();
            }

            @Override
            public void popupWillBeClosed ()
            {
                stopDelayedClose ();
                if ( accepted )
                {
                    fireAccepted ();
                }
                else
                {
                    fireClosed ();
                }
            }
        } );*/
    }

    /**
     * Starts delayed close timer.
     */
    private void startDelayedClose ()
    {
        if ( displayTime > 0 && ( displayTimer == null || !displayTimer.isRunning () ) )
        {
            displayTimer = SkyTimer.delay ( displayTime, new ActionListener ()
            {
                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    hidePopup ();
                }
            } );
        }
    }

    /**
     * Stops delayed close timer.
     */
    private void stopDelayedClose ()
    {
        if ( displayTimer != null && displayTimer.isRunning () )
        {
            displayTimer.stop ();
        }
    }

    /**
     * Returns notification icon.
     *
     * @return notification icon
     */
    public Icon getIcon ()
    {
        return icon;
    }

    /**
     * Sets notification icon.
     *
     * @param icon new notification icon
     */
    public void setIcon ( Icon icon )
    {
        this.icon = icon;
        updateIcon ();
    }

    /**
     * Updates visible notification icon.
     */
    protected void updateIcon ()
    {
        iconImage.setIcon ( icon );
        revalidate ();
    }

    /**
     * Returns notification content.
     *
     * @return notification content
     */
    public Component getContent ()
    {
        return content;
    }

    /**
     * Sets notification content.
     *
     * @param content new notification content
     */
    public void setContent ( Component content )
    {
        this.content = content;
        updateContent ();
    }

    /**
     * Sets notification text content.
     *
     * @param content new notification text content
     */
    public void setContent ( String content )
    {
        setContent ( new JLabel ( content ) );
    }

    /**
     * Updates visible notification content.
     */
    protected void updateContent ()
    {
        contentPanel.removeAll ();
        if ( content != null )
        {
            contentPanel.add ( content );
        }
        contentPanel.revalidate ();
    }

    /**
     * Returns notification options list.
     *
     * @return notification options list
     */
    public List<NotificationOption> getOptions ()
    {
        return options;
    }

    /**
     * Sets notification options.
     *
     * @param options new notification options
     */
    public void setOptions ( NotificationOption... options )
    {
        setOptions ( Arrays.asList ( options ) );
    }

    /**
     * Sets notification options list.
     *
     * @param options new notification options list
     */
    public void setOptions ( List<NotificationOption> options )
    {
        this.options = options;
        updateOptions ();
    }

    /**
     * Updates visible notification options.
     */
    protected void updateOptions ()
    {
        if ( options != null && options.size () > 0 )
        {
            for ( final NotificationOption option : options )
            {
                JButton optionButton = new JButton ( "" );
                optionButton.addActionListener ( new ActionListener ()
                {
                    @Override
                    public void actionPerformed ( ActionEvent e )
                    {
                        fireOptionSelected ( option );
                        if ( closeOnOptionSelection )
                        {
                            acceptAndHide ();
                        }
                    }
                } );
                optionsPanel.add ( optionButton );
            }
        }
        else
        {
            optionsPanel.removeAll ();
        }
        revalidate ();
    }

    /**
     * Accepts notification and closes popup.
     */
    public void acceptAndHide ()
    {
        accepted = true;
        hidePopup ();
    }

    /**
     * Returns whether notification can be closed by simple click on popup or not.
     *
     * @return true if notification can be closed by simple click on popup, false otherwise
     */
    public boolean isClickToClose ()
    {
        return clickToClose;
    }

    /**
     * Sets whether notification can be closed by simple click on popup or not.
     *
     * @param clickToClose whether notification can be closed by simple click on popup or not
     */
    public void setClickToClose ( final boolean clickToClose )
    {
        this.clickToClose = clickToClose;
    }

    /**
     * Whether notification popup should be closed when some option is selected by user or not.
     *
     * @return true if notification popup should be closed when some option is selected by user, false otherwise
     */
    public boolean isCloseOnOptionSelection ()
    {
        return closeOnOptionSelection;
    }

    /**
     * Returns whether notification popup should be closed when some option is selected by user or not.
     *
     * @param closeOnOptionSelection whether notification popup should be closed when some option is selected by user or not
     */
    public void setCloseOnOptionSelection ( boolean closeOnOptionSelection )
    {
        this.closeOnOptionSelection = closeOnOptionSelection;
    }

    /**
     * Returns notification display time.
     *
     * @return notification display time
     */
    public long getDisplayTime ()
    {
        return displayTime;
    }

    /**
     * Sets notification display time.
     *
     * @param displayTime new notification display time
     */
    public void setDisplayTime ( long displayTime )
    {
        this.displayTime = displayTime;
        if ( isShowing () )
        {
            if ( displayTime > 0 )
            {
                startDelayedClose ();
            }
            else
            {
                stopDelayedClose ();
            }
        }
    }

    /**
     * Adds directory chooser listener.
     *
     * @param listener directory chooser listener to add
     */
    public void addNotificationListener ( final NotificationListener listener )
    {
        listeners.add ( listener );
    }

    /**
     * Removes directory chooser listener.
     *
     * @param listener directory chooser listener to remove
     */
    public void removeNotificationListener ( final NotificationListener listener )
    {
        listeners.remove ( listener );
    }

    /**
     * Fires when notification options is selected.
     */
    public void fireOptionSelected ( NotificationOption option )
    {
        for ( NotificationListener listener : CollectionUtils.copy ( listeners ) )
        {
            listener.optionSelected ( option );
        }
    }

    /**
     * Fires when notification accepted and closed.
     */
    public void fireAccepted ()
    {
        for ( NotificationListener listener : CollectionUtils.copy ( listeners ) )
        {
            listener.accepted ();
        }
    }

    /**
     * Fires when notification simply closed.
     */
    public void fireClosed ()
    {
        for ( NotificationListener listener : CollectionUtils.copy ( listeners ) )
        {
            listener.closed ();
        }
    }
}