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
import javax.swing.plaf.basic.BasicToolTipUI;

import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.util.Map;

/**
 * Custom UI for JTooltip component.
 *
 * @author Mikle Garin
 */

public class ToolTipUI extends BasicToolTipUI
{
    /**
     * Tooltip instance.
     */
    private JComponent tooltip = null;

    /**
     * Returns an instance of the WebToolTipUI for the specified component.
     * This tricky method is used by UIManager to create component UIs when needed.
     *
     * @param c component that will use UI instance
     * @return instance of the WebToolTipUI
     */
    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new ToolTipUI ();
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

        this.tooltip = c;

        // Default settings
        tooltip.setOpaque ( false );
        tooltip.setBackground ( Color.BLACK );
        tooltip.setForeground ( Color.WHITE );

        // Updating border
        updateBorder ( tooltip );
    }

    /**
     * Uninstalls UI from the specified component.
     *
     * @param c component with this UI
     */
    @Override
    public void uninstallUI ( JComponent c )
    {
        this.tooltip = null;

        super.uninstallUI ( c );
    }

    /**
     * Updates component border
     *
     * @param c component to process
     */
    private void updateBorder ( JComponent c )
    {
        c.setBorder ( LafUtils.createWebBorder (new Insets ( 3, 3, 3, 3 ) ) );
    }

    /**
     * Paints tooltip.
     *
     * @param g graphics
     * @param c component
     */
    @Override
    public void paint ( Graphics g, JComponent c )
    {
        Graphics2D g2d = ( Graphics2D ) g;

        Object aa = LafUtils.setupAntialias ( g2d );
        Composite oc = LafUtils.setupAlphaComposite ( g2d, 0.85f );

        g2d.setPaint ( c.getBackground () );
        g2d.fillRoundRect ( 0, 0, c.getWidth (), c.getHeight (), 2 * 2, 2 * 2 );

        LafUtils.restoreComposite ( g2d, oc );
        LafUtils.restoreAntialias ( g2d, aa );

        Map taa = SwingUtils.setupTextAntialias ( g2d );
        super.paint ( g, c );
        SwingUtils.restoreTextAntialias ( g2d, taa );
    }
}