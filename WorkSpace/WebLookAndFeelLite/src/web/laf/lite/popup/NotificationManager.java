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

import web.laf.lite.layout.NotificationsLayout;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * This manager allows you to display custom notification popups within the application.
 * You can also add custom actions, set their duration, modify popup styling and use some other advanced features.
 *
 * @author Mikle Garin
 * @see NotificationPopup
 * @see DisplayType
 */

public final class NotificationManager implements SwingConstants
{
    /**
     * Notifications display location.
     */
    private static int location = SOUTH_EAST;

    /**
     * Notifications display type.
     */
    private static DisplayType displayType = DisplayType.stack;

    /**
     * Notifications side margin.
     */
    private static Insets margin = new Insets ( 0, 0, 0, 0 );

    /**
     * Gap between notifications.
     */
    private static int gap = 10;

    /**
     * Whether popups should be cascaded or not.
     */
    private static boolean cascade = true;

    /**
     * Amount of cascaded in a row popups.
     */
    private static int cascadeAmount = 4;

    /**
     * Cached notification layouts.
     */
    private static Map<PopupLayer, NotificationsLayout> notificationsLayouts = new WeakHashMap<PopupLayer, NotificationsLayout> ();

    /**
     * Cached notifications.
     */
    private static Map<NotificationPopup, PopupLayer> notifications = new WeakHashMap<NotificationPopup, PopupLayer> ();

    /**
     * Returns notifications display location.
     *
     * @return notifications display location
     */
    public static int getLocation ()
    {
        return location;
    }

    /**
     * Sets notifications display location.
     *
     * @param location new notifications display location
     */
    public static void setLocation ( int location )
    {
        NotificationManager.location = location;
        updateNotificationLayouts ();
    }

    /**
     * Retyrns notification display type.
     *
     * @return notification display type
     */
    public static DisplayType getDisplayType ()
    {
        return displayType;
    }

    /**
     * Sets notification display type.
     *
     * @param displayType new notification display type
     */
    public static void setDisplayType ( final DisplayType displayType )
    {
        NotificationManager.displayType = displayType;
        updateNotificationLayouts ();
    }

    /**
     * Returns notifications side margin.
     *
     * @return notifications side margin
     */
    public static Insets getMargin ()
    {
        return margin;
    }

    /**
     * Sets notifications side margin.
     *
     * @param margin margin
     */
    public static void setMargin ( int margin )
    {
        setMargin ( margin, margin, margin, margin );
    }

    /**
     * Sets notifications side margin.
     *
     * @param top    top margin
     * @param left   left margin
     * @param bottom bottom margin
     * @param right  right margin
     */
    public static void setMargin ( int top, int left, int bottom, int right )
    {
        setMargin ( new Insets ( top, left, bottom, right ) );
    }

    /**
     * Sets notifications side margin.
     *
     * @param margin new notifications side margin
     */
    public static void setMargin ( Insets margin )
    {
        NotificationManager.margin = margin;
        updateNotificationLayouts ();
    }

    /**
     * Returns gap between notifications.
     *
     * @return gap between notifications
     */
    public static int getGap ()
    {
        return gap;
    }

    /**
     * Sets gap between notifications.
     *
     * @param gap new gap between notifications
     */
    public static void setGap ( final int gap )
    {
        NotificationManager.gap = gap;
        updateNotificationLayouts ();
    }

    /**
     * Returns whether popups should be cascaded or not.
     *
     * @return whether popups should be cascaded or not
     */
    public static boolean isCascade ()
    {
        return cascade;
    }

    /**
     * Sets whether popups should be cascaded or not.
     *
     * @param cascade whether popups should be cascaded or not
     */
    public static void setCascade ( final boolean cascade )
    {
        NotificationManager.cascade = cascade;
        updateNotificationLayouts ();
    }

    /**
     * Returns amount of cascaded in a row popups.
     *
     * @return amount of cascaded in a row popups
     */
    public static int getCascadeAmount ()
    {
        return cascadeAmount;
    }

    /**
     * Sets amount of cascaded in a row popups.
     *
     * @param cascadeAmount new amount of cascaded in a row popups
     */
    public static void setCascadeAmount ( int cascadeAmount )
    {
        NotificationManager.cascadeAmount = cascadeAmount;
        if ( NotificationManager.cascade )
        {
            updateNotificationLayouts ();
        }
    }

    /**
     * Optimized layout updates for all visible notifications.
     */
    public static void updateNotificationLayouts ()
    {
        final List<PopupLayer> layers = new ArrayList<PopupLayer> ();
        for ( Map.Entry<NotificationPopup, PopupLayer> entry : notifications.entrySet () )
        {
            final PopupLayer popupLayer = entry.getValue ();
            if ( !layers.contains ( popupLayer ) )
            {
                layers.add ( popupLayer );
                popupLayer.revalidate ();
            }
        }
    }

    /**
     * Hides all visible notifications.
     */
    public static void hideAllNotifications ()
    {
        for ( Map.Entry<NotificationPopup, PopupLayer> entry : notifications.entrySet () )
        {
            entry.getKey ().hidePopup ();
        }
    }

    /**
     * Returns displayed notification.
     *
     * @param content notification text content
     * @param icon    notification icon
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final String content, final Icon icon )
    {
        return showNotification ( SwingUtils.getActiveWindow (), new JLabel ( content ), icon );
    }
    
    public static NotificationPopup showNotification ( final String content)
    {
        return showNotification ( SwingUtils.getActiveWindow (), new JLabel ( content ), null);
    }


    /**
     * Returns displayed notification.
     *
     * @param showFor component used to determine window where notification should be displayed
     * @param content notification text content
     * @param icon    notification icon
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component showFor, final String content, final Icon icon )
    {
        return showNotification ( showFor, new JLabel ( content ), icon );
    }

    /**
     * Returns displayed notification.
     *
     * @param content notification content
     * @param icon    notification icon
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component content, final Icon icon )
    {
        return showNotification ( SwingUtils.getActiveWindow (), content, icon );
    }

    /**
     * Returns displayed notification.
     *
     * @param showFor component used to determine window where notification should be displayed
     * @param content notification content
     * @param icon    notification icon
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component showFor, final Component content, final Icon icon )
    {
        final NotificationPopup notificationPopup = new NotificationPopup ();
        notificationPopup.setIcon ( icon );
        notificationPopup.setContent ( content );
        return showNotification ( showFor, notificationPopup );
    }

    /**
     * Returns displayed notification.
     *
     * @param content notification text content
     * @param icon    notification icon
     * @param options notification selectable options
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final String content, final Icon icon, NotificationOption... options )
    {
        return showNotification ( SwingUtils.getActiveWindow (), new JLabel ( content ), icon, options );
    }

    /**
     * Returns displayed notification.
     *
     * @param showFor component used to determine window where notification should be displayed
     * @param content notification text content
     * @param icon    notification icon
     * @param options notification selectable options
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component showFor, final String content, final Icon icon,
                                                          NotificationOption... options )
    {
        return showNotification ( showFor, new JLabel ( content ), icon, options );
    }

    /**
     * Returns displayed notification.
     *
     * @param content notification content
     * @param icon    notification icon
     * @param options notification selectable options
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component content, final Icon icon, NotificationOption... options )
    {
        return showNotification ( SwingUtils.getActiveWindow (), content, icon, options );
    }

    /**
     * Returns displayed notification.
     *
     * @param showFor component used to determine window where notification should be displayed
     * @param content notification content
     * @param icon    notification icon
     * @param options notification selectable options
     * @return displayed notification
     */
    public static NotificationPopup showNotification ( final Component showFor, final Component content, final Icon icon,
                                                          NotificationOption... options )
    {
        final NotificationPopup notificationPopup = new NotificationPopup ();
        notificationPopup.setIcon ( icon );
        notificationPopup.setContent ( content );
        notificationPopup.setOptions ( options );
        return showNotification ( showFor, notificationPopup );
    }

    /**
     * Displays notification on popup layer.
     *
     * @param notification notification to display
     */
    public static NotificationPopup showNotification ( final NotificationPopup notification )
    {
        return showNotification ( SwingUtils.getActiveWindow (), notification );
    }

    /**
     * Displays notification on popup layer.
     *
     * @param showFor      component used to determine window where notification should be displayed
     * @param notification notification to display
     */
    public static NotificationPopup showNotification ( final Component showFor, final NotificationPopup notification )
    {
        // Adding custom layout into notifications
        final PopupLayer popupLayer = PopupManager.getPopupLayer ( showFor );
        if ( !notificationsLayouts.containsKey ( popupLayer ) )
        {
            final NotificationsLayout layout = new NotificationsLayout ();
            popupLayer.addLayoutManager ( layout );
            notificationsLayouts.put ( popupLayer, layout );
        }

        // Notifications caching
        notifications.put ( notification, popupLayer );
        /*notification.addPopupListener ( new PopupAdapter ()
        {
            @Override
            public void popupWillBeClosed ()
            {
                notifications.remove ( notification );
            }
        } );*/

        // Displaying popup
        notification.showPopup ( showFor );

        return notification;
    }
}

/**
 * This enumeration represents available predefined notification display types.
 *
 * @author Mikle Garin
 */

enum DisplayType
{
    /**
     * Display all active notification together, one after another.
     */
    flow,

    /**
     * Display notification stacked together.
     */
    stack
}