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
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

import web.laf.lite.utils.ColorUtils;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SkyTimer;
import web.laf.lite.utils.SwingUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

/**
 * User: mgarin Date: 27.04.11 Time: 17:41
 */

public class ButtonUI extends BasicButtonUI implements ShapeProvider, SwingConstants, BorderMethods
{
    private Color topBgColor = Color.WHITE;
    private Color bottomBgColor = new Color ( 223, 223, 223 );
    private Color topSelectedBgColor = new Color ( 242, 242, 242 );
    private Color bottomSelectedBgColor = new Color ( 210, 210, 210 );
    private Color selectedForeground = Color.BLACK;
    private boolean rolloverShine = false;
    private Color shineColor = Color.WHITE;
    private boolean rolloverDarkBorderOnly = false;
    private int round = 2;
    private boolean drawShade = true;
    private boolean rolloverShadeOnly = false;
    private boolean showDisabledShade = false;
    private int shadeWidth = 2;
    private Insets margin = new Insets ( 0, 0, 0, 0 );
    private Color shadeColor = new Color ( 210, 210, 210 );
    private int innerShadeWidth = 2;
    private Color innerShadeColor = new Color ( 190, 190, 190 );
    private int leftRightSpacing = 4;
    private boolean shadeToggleIcon = false;
    private float shadeToggleIconTransparency = 0.5f;
    private boolean drawFocus = false;
    private boolean rolloverDecoratedOnly = false;
    private boolean animate = true;
    private boolean undecorated = false;
    private boolean moveIconOnPress = true;

    private boolean drawTop = true;
    private boolean drawLeft = true;
    private boolean drawBottom = true;
    private boolean drawRight = true;

    private boolean drawTopLine = false;
    private boolean drawLeftLine = false;
    private boolean drawBottomLine = false;
    private boolean drawRightLine = false;

    private Color transparentShineColor = new Color ( shineColor.getRed (), shineColor.getGreen (), shineColor.getBlue (), 0 );

    private boolean rollover = false;
    private float transparency = 0f;

    private Point mousePoint = null;
    private SkyTimer animator = null;
    private AbstractButton button = null;

    private MouseAdapter mouseAdapter;
    private AncestorListener ancestorListener;

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new ButtonUI ();
    }

    @Override
    public void installUI ( final JComponent c )
    {
        super.installUI ( c );
        // Saving button to local variable
        button = ( AbstractButton ) c;

        // Default settings
        button.setFocusPainted ( false );
        button.setContentAreaFilled ( false );
        button.setBorderPainted ( false );
        button.setFocusable ( true );
        button.setOpaque ( false );

        // Updating border
        updateBorder ();

        // Rollover listener
        mouseAdapter = new MouseAdapter ()
        {
            @Override
            public void mouseEntered ( MouseEvent e )
            {
                rollover = true;
                button.getModel ().setRollover ( true );
                mousePoint = e.getPoint ();

                stopAnimator ();
                if (animate && ( rolloverShine || rolloverDecoratedOnly || rolloverShadeOnly ) )
                {
                    animator = new SkyTimer ( "SkyButtonUI.fadeInTimer", 20, new ActionListener ()
                    {
                        @Override
                        public void actionPerformed ( ActionEvent e )
                        {
                            transparency += 0.075f;
                            if ( transparency >= 1f )
                            {
                                transparency = 1f;
                                animator.stop ();
                            }
                            updateTransparentShineColor ();
                            if ( c.isEnabled () )
                            {
                                c.repaint ();
                            }
                        }
                    } );
                    animator.start ();
                }
                else
                {
                    transparency = 1f;
                    updateTransparentShineColor ();
                    c.repaint ();
                }

                refresh ( c );
            }

            @Override
            public void mouseExited ( MouseEvent e )
            {
                mousePoint = e.getPoint ();

                stopAnimator ();
                if (animate && ( rolloverShine || rolloverDecoratedOnly || rolloverShadeOnly ) )
                {
                    animator = new SkyTimer ( "SkyButtonUI.fadeOutTimer", 20, new ActionListener ()
                    {
                        @Override
                        public void actionPerformed ( ActionEvent e )
                        {
                            transparency -= 0.075f;
                            if ( transparency <= 0f )
                            {
                                rollover = false;
                                button.getModel ().setRollover ( false );
                                transparency = 0f;
                                mousePoint = null;
                                animator.stop ();
                            }
                            updateTransparentShineColor ();
                            if ( c.isEnabled () )
                            {
                                c.repaint ();
                            }
                        }
                    } );
                    animator.start ();
                }
                else
                {
                    rollover = false;
                    button.getModel ().setRollover ( false );
                    transparency = 0f;
                    mousePoint = null;
                    updateTransparentShineColor ();
                    c.repaint ();
                }

                refresh ( c );
            }

            @Override
            public void mouseReleased ( MouseEvent e )
            {
                // Fix for highlight stuck
                if ( !c.isShowing () || windowLostFocus () )
                {
                    mousePoint = null;
                    c.repaint ();
                }
            }

            private boolean windowLostFocus ()
            {
                Window wa = SwingUtils.getWindowAncestor ( c );
                return wa == null || !wa.isVisible () || !wa.isActive ();
            }

            private void stopAnimator ()
            {
                if ( animator != null )
                {
                    animator.stop ();
                }
            }

            @Override
            public void mouseDragged ( MouseEvent e )
            {
                mousePoint = e.getPoint ();
                if (rolloverShine )
                {
                    refresh ( c );
                }
            }

            @Override
            public void mouseMoved ( MouseEvent e )
            {
                mousePoint = e.getPoint ();
                if (rolloverShine )
                {
                    refresh ( c );
                }
            }

            private void refresh ( JComponent c )
            {
                if ( c.isEnabled () )
                {
                    if ( animator == null || !animator.isRunning () )
                    {
                        c.repaint ();
                    }
                }
            }
        };
        c.addMouseListener ( mouseAdapter );
        c.addMouseMotionListener ( mouseAdapter );

        // Ancestor listener
        ancestorListener = new AncestorAdapter ()
        {
            @Override
            public void ancestorRemoved ( AncestorEvent event )
            {
                rollover = false;
                button.getModel ().setRollover ( false );
                transparency = 0f;
                mousePoint = null;
                updateTransparentShineColor ();
                c.repaint ();
            }
        };
        c.addAncestorListener ( ancestorListener );
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        c.removeMouseListener ( mouseAdapter );
        c.removeMouseMotionListener ( mouseAdapter );
        c.removeAncestorListener ( ancestorListener );
        super.uninstallUI ( c );
    }

    @Override
    public Shape provideShape ()
    {
    	if (undecorated )
        {
            return SwingUtils.size ( button );
        }
        else
        {
            return getButtonShape ( button, true );
        }
    }

    public Color getTopBgColor ()
    {
        return topBgColor;
    }

    public void setTopBgColor ( Color topBgColor )
    {
        this.topBgColor = topBgColor;
    }

    public Color getBottomBgColor ()
    {
        return bottomBgColor;
    }

    public void setBottomBgColor ( Color bottomBgColor )
    {
        this.bottomBgColor = bottomBgColor;
    }

    public Color getTopSelectedBgColor ()
    {
        return topSelectedBgColor;
    }

    public void setTopSelectedBgColor ( Color topSelectedBgColor )
    {
        this.topSelectedBgColor = topSelectedBgColor;
    }

    public Color getBottomSelectedBgColor ()
    {
        return bottomSelectedBgColor;
    }

    public void setBottomSelectedBgColor ( Color bottomSelectedBgColor )
    {
        this.bottomSelectedBgColor = bottomSelectedBgColor;
    }

    public Color getSelectedForeground ()
    {
        return selectedForeground;
    }

    public void setSelectedForeground ( Color selectedForeground )
    {
        this.selectedForeground = selectedForeground;
    }

    public boolean isRolloverDarkBorderOnly ()
    {
        return rolloverDarkBorderOnly;
    }

    public void setRolloverDarkBorderOnly ( boolean rolloverDarkBorderOnly )
    {
        this.rolloverDarkBorderOnly = rolloverDarkBorderOnly;
    }

    public boolean isRolloverShine ()
    {
        return rolloverShine;
    }

    public void setRolloverShine ( boolean rolloverShine )
    {
        this.rolloverShine = rolloverShine;
    }

    public Color getShineColor ()
    {
        return shineColor;
    }

    public void setShineColor ( Color shineColor )
    {
        this.shineColor = shineColor;
        updateTransparentShineColor ();
    }

    private void updateTransparentShineColor ()
    {
        transparentShineColor = new Color ( shineColor.getRed (), shineColor.getGreen (), shineColor.getBlue (),
                Math.round ( transparency * shineColor.getAlpha () ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBorder ()
    {
        if ( button != null )
        {
            // Actual margin
            boolean ltr = button.getComponentOrientation ().isLeftToRight ();
            Insets m = new Insets ( margin.top, ( ltr ? margin.left : margin.right ) + leftRightSpacing, margin.bottom,
                    ( ltr ? margin.right : margin.left ) + leftRightSpacing );

            if ( !undecorated )
            {
                // Styling borders
                boolean actualDrawLeft = ltr ? drawLeft : drawRight;
                boolean actualDrawLeftLine = ltr ? drawLeftLine : drawRightLine;
                boolean actualDrawRight = ltr ? drawRight : drawLeft;
                boolean actualDrawRightLine = ltr ? drawRightLine : drawLeftLine;
                m.top += ( drawTop ? shadeWidth + 1 : ( drawTopLine ? 1 : 0 ) - 1 ) + innerShadeWidth;
                m.left += ( actualDrawLeft ? shadeWidth + 1 : ( actualDrawLeftLine ? 1 : 0 ) - 1 ) + innerShadeWidth;
                m.bottom += ( drawBottom ? shadeWidth + 1 : ( drawBottomLine ? 1 : 0 ) - 1 ) + innerShadeWidth;
                m.right += ( actualDrawRight ? shadeWidth + 1 : ( actualDrawRightLine ? 1 : 0 ) - 1 ) + innerShadeWidth;
            }

            // Installing border
            button.setBorder ( LafUtils.createWebBorder ( m ) );
        }
    }

    public boolean isDrawShade ()
    {
        return drawShade;
    }

    public void setDrawShade ( boolean drawShade )
    {
        this.drawShade = drawShade;
    }

    public boolean isRolloverShadeOnly ()
    {
        return rolloverShadeOnly;
    }

    public void setRolloverShadeOnly ( boolean rolloverShadeOnly )
    {
        this.rolloverShadeOnly = rolloverShadeOnly;
    }

    public boolean isShowDisabledShade ()
    {
        return showDisabledShade;
    }

    public void setShowDisabledShade ( boolean showDisabledShade )
    {
        this.showDisabledShade = showDisabledShade;
    }

    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ();
    }

    public Insets getMargin ()
    {
        return margin;
    }

    public void setMargin ( Insets margin )
    {
        this.margin = margin;
        updateBorder ();
    }

    public Color getShadeColor ()
    {
        return shadeColor;
    }

    public void setShadeColor ( Color shadeColor )
    {
        this.shadeColor = shadeColor;
    }

    public int getInnerShadeWidth ()
    {
        return innerShadeWidth;
    }

    public void setInnerShadeWidth ( int innerShadeWidth )
    {
        this.innerShadeWidth = innerShadeWidth;
        updateBorder ();
    }

    public Color getInnerShadeColor ()
    {
        return innerShadeColor;
    }

    public void setInnerShadeColor ( Color innerShadeColor )
    {
        this.innerShadeColor = innerShadeColor;
    }

    public int getLeftRightSpacing ()
    {
        return leftRightSpacing;
    }

    public void setLeftRightSpacing ( int leftRightSpacing )
    {
        this.leftRightSpacing = leftRightSpacing;
        updateBorder ();
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public boolean isShadeToggleIcon ()
    {
        return shadeToggleIcon;
    }

    public void setShadeToggleIcon ( boolean shadeToggleIcon )
    {
        this.shadeToggleIcon = shadeToggleIcon;
    }

    public float getShadeToggleIconTransparency ()
    {
        return shadeToggleIconTransparency;
    }

    public void setShadeToggleIconTransparency ( float shadeToggleIconTransparency )
    {
        this.shadeToggleIconTransparency = shadeToggleIconTransparency;
    }

    public boolean isUndecorated ()
    {
        return undecorated;
    }

    public void setUndecorated ( boolean undecorated )
    {
        this.undecorated = undecorated;
        updateBorder ();
    }

    public boolean isMoveIconOnPress ()
    {
        return moveIconOnPress;
    }

    public void setMoveIconOnPress ( boolean moveIconOnPress )
    {
        this.moveIconOnPress = moveIconOnPress;
    }

    public boolean isRolloverDecoratedOnly ()
    {
        return rolloverDecoratedOnly;
    }

    public void setRolloverDecoratedOnly ( boolean rolloverDecoratedOnly )
    {
        this.rolloverDecoratedOnly = rolloverDecoratedOnly;
    }

    public boolean isDrawFocus ()
    {
        return drawFocus;
    }

    public void setDrawFocus ( boolean drawFocus )
    {
        this.drawFocus = drawFocus;
    }

    public boolean isDrawBottom ()
    {
        return drawBottom;
    }

    public void setDrawBottom ( boolean drawBottom )
    {
        this.drawBottom = drawBottom;
        updateBorder ();
    }

    public boolean isDrawLeft ()
    {
        return drawLeft;
    }

    public void setDrawLeft ( boolean drawLeft )
    {
        this.drawLeft = drawLeft;
        updateBorder ();
    }

    public boolean isDrawRight ()
    {
        return drawRight;
    }

    public void setDrawRight ( boolean drawRight )
    {
        this.drawRight = drawRight;
        updateBorder ();
    }

    public boolean isDrawTop ()
    {
        return drawTop;
    }

    public void setDrawTop ( boolean drawTop )
    {
        this.drawTop = drawTop;
        updateBorder ();
    }

    public void setDrawSides ( boolean top, boolean left, boolean bottom, boolean right )
    {
        this.drawTop = top;
        this.drawLeft = left;
        this.drawBottom = bottom;
        this.drawRight = right;
        updateBorder ();
    }

    public boolean isDrawTopLine ()
    {
        return drawTopLine;
    }

    public void setDrawTopLine ( boolean drawTopLine )
    {
        this.drawTopLine = drawTopLine;
        updateBorder ();
    }

    public boolean isDrawLeftLine ()
    {
        return drawLeftLine;
    }

    public void setDrawLeftLine ( boolean drawLeftLine )
    {
        this.drawLeftLine = drawLeftLine;
        updateBorder ();
    }

    public boolean isDrawBottomLine ()
    {
        return drawBottomLine;
    }

    public void setDrawBottomLine ( boolean drawBottomLine )
    {
        this.drawBottomLine = drawBottomLine;
        updateBorder ();
    }

    public boolean isDrawRightLine ()
    {
        return drawRightLine;
    }

    public void setDrawRightLine ( boolean drawRightLine )
    {
        this.drawRightLine = drawRightLine;
        updateBorder ();
    }

    public void setDrawLines ( boolean top, boolean left, boolean bottom, boolean right )
    {
        this.drawTopLine = top;
        this.drawLeftLine = left;
        this.drawBottomLine = bottom;
        this.drawRightLine = right;
        updateBorder ();
    }

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        AbstractButton button = ( AbstractButton ) c;
        ButtonModel buttonModel = button.getModel ();

        Graphics2D g2d = ( Graphics2D ) g;
        Object aa = LafUtils.setupAntialias ( g2d );

            if ( !undecorated )
            {
                boolean pressed = buttonModel.isPressed () || buttonModel.isSelected ();

                Shape borderShape = getButtonShape ( button, true );

                if ( isDrawButton ( c, buttonModel ) )
                {
                    // Rollover decorated only transparency
                    boolean animatedTransparency = animate && rolloverDecoratedOnly && !pressed;
                    Composite oldComposite = LafUtils.setupAlphaComposite ( g2d, transparency, animatedTransparency );

                    // Shade
                    if ( /*( !pressed || isFocusActive ( c ) ) &&*/ drawShade && ( c.isEnabled () || showDisabledShade ) &&
                            ( !rolloverShadeOnly || rollover ) )
                    {
                        boolean setInner = !animatedTransparency && rolloverShadeOnly;
                        Composite oc = LafUtils.setupAlphaComposite ( g2d, transparency, setInner );
                        LafUtils.drawShade ( g2d, borderShape, getShadeColor ( c ), shadeWidth );
                        LafUtils.restoreComposite ( g2d, oc, setInner );
                    }

                    // Background
                    g2d.setPaint ( new GradientPaint ( 0, drawTop ? shadeWidth : 0, pressed ? topSelectedBgColor : topBgColor, 0,
                            button.getHeight () - ( drawBottom ? shadeWidth : 0 ), pressed ? bottomSelectedBgColor : bottomBgColor ) );
                    g2d.fill ( getButtonShape ( button, false ) );

                    // Cursor-following highlight
                    if ( rolloverShine && mousePoint != null && c.isEnabled () )
                    {
                        Shape oldClip = LafUtils.intersectClip ( g2d, borderShape );
                        g2d.setPaint ( new RadialGradientPaint ( mousePoint.x, c.getHeight (), c.getWidth (), new float[]{ 0f, 1f },
                                new Color[]{ transparentShineColor, new Color ( 255, 255, 255, 0 ) } ) );
                        g2d.fill ( borderShape );
                        LafUtils.restoreClip ( g2d, oldClip );
                    }

                    // Inner shade
                    if ( pressed )
                    {
                        LafUtils.drawShade ( g2d, borderShape, innerShadeColor, innerShadeWidth, borderShape );
                    }

                    // Border
                    g2d.setPaint ( c.isEnabled () ? ( rolloverDarkBorderOnly && rollover ? getBorderColor () :
                            ( !rolloverDarkBorderOnly ? Color.GRAY : new Color ( 170, 170, 170 ) ) ) :
                            	Color.LIGHT_GRAY );
                    g2d.draw ( borderShape );

                    // Changing line marks in case of RTL orientation
                    boolean ltr = button.getComponentOrientation ().isLeftToRight ();
                    boolean actualDrawLeft = ltr ? drawLeft : drawRight;
                    boolean actualDrawLeftLine = ltr ? drawLeftLine : drawRightLine;
                    boolean actualDrawRight = ltr ? drawRight : drawLeft;
                    boolean actualDrawRightLine = ltr ? drawRightLine : drawLeftLine;

                    // Side-border
                    g2d.setPaint ( c.isEnabled () ? Color.GRAY : Color.LIGHT_GRAY );
                    if ( drawTopLine )
                    {
                        int x = actualDrawLeft ? shadeWidth : 0;
                        g2d.drawLine ( x, 0, x + c.getWidth () - ( actualDrawLeft ? shadeWidth : 0 ) -
                                ( actualDrawRight ? shadeWidth + 1 : 0 ), 0 );
                    }
                    if ( drawBottomLine )
                    {
                        int x = actualDrawLeft ? shadeWidth : 0;
                        g2d.drawLine ( x, c.getHeight () - 1, x + c.getWidth () - ( actualDrawLeft ? shadeWidth : 0 ) -
                                ( actualDrawRight ? shadeWidth + 1 : 0 ), c.getHeight () - 1 );
                    }
                    if ( actualDrawLeftLine )
                    {
                        int y = drawTop ? shadeWidth : 0;
                        g2d.drawLine ( 0, y, 0, y + c.getHeight () - ( drawTop ? shadeWidth : 0 ) -
                                ( drawBottom ? shadeWidth + 1 : 0 ) );
                    }
                    if ( actualDrawRightLine )
                    {
                        int y = drawTop ? shadeWidth : 0;
                        g2d.drawLine ( c.getWidth () - 1, y, c.getWidth () - 1, y + c.getHeight () - ( drawTop ? shadeWidth : 0 ) -
                                ( drawBottom ? shadeWidth + 1 : 0 ) );
                    }

                    LafUtils.restoreComposite ( g2d, oldComposite, animatedTransparency );
                }
            }

        // Special text and icon translation effect on button press
        if ( buttonModel.isPressed () && moveIconOnPress )
        {
            g2d.translate ( 1, 1 );
        }

        LafUtils.restoreAntialias ( g2d, aa );

        // Default text and icon drawing
        Map hints = SwingUtils.setupTextAntialias ( g2d );
        super.paint ( g, c );
        SwingUtils.restoreTextAntialias ( g2d, hints );
    }

    private Color getShadeColor ( JComponent c )
    {
        return isFocusActive ( c ) ? new Color ( 85, 142, 239 ) : shadeColor;
    }

    private boolean isFocusActive ( JComponent c )
    {
        return c.isEnabled () && drawFocus && c.isFocusOwner ();
    }

    private boolean isDrawButton ( JComponent c, ButtonModel buttonModel )
    {
        return rolloverDecoratedOnly && rollover && c.isEnabled () ||
                animate && transparency > 0f && c.isEnabled () || !rolloverDecoratedOnly ||
                buttonModel.isSelected () || buttonModel.isPressed ();
    }

    @Override
    protected void paintText ( Graphics g, JComponent c, Rectangle textRect, String text )
    {
        AbstractButton b = ( AbstractButton ) c;
        ButtonModel model = b.getModel ();
        FontMetrics fm = SwingUtils.getFontMetrics ( c, g );
        int mnemonicIndex = b.getDisplayedMnemonicIndex ();

        // Drawing text
        if ( model.isEnabled () )
        {
            // Drawing normal text
            g.setColor ( model.isPressed () || model.isSelected () ? selectedForeground : b.getForeground () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x + getTextShiftOffset (),
                    textRect.y + fm.getAscent () + getTextShiftOffset () );
        }
        else
        {
            // Drawing disabled text
            g.setColor ( b.getBackground ().brighter () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent () );
            g.setColor ( b.getBackground ().darker () );
            SwingUtils.drawStringUnderlineCharAt ( g, text, mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent () - 1 );
        }
    }

    private Color getBorderColor ()
    {
        return ColorUtils.getIntermediateColor ( new Color ( 170, 170, 170 ), Color.GRAY, transparency );
    }

    @Override
    protected void paintIcon ( Graphics g, JComponent c, Rectangle iconRect )
    {
        AbstractButton button = ( AbstractButton ) c;
        ButtonModel buttonModel = button.getModel ();
        Graphics2D g2d = ( Graphics2D ) g;

        boolean shadeToggleIcon = this.shadeToggleIcon && button instanceof JToggleButton &&
                !buttonModel.isSelected ();
        Composite old = LafUtils.setupAlphaComposite ( g2d, shadeToggleIconTransparency, shadeToggleIcon );

        super.paintIcon ( g, c, iconRect );

        LafUtils.restoreComposite ( g2d, old, shadeToggleIcon );
    }

    protected Shape getButtonShape ( AbstractButton button, boolean border )
    {
        // Changing line marks in case of RTL orientation
        boolean ltr = button.getComponentOrientation ().isLeftToRight ();
        boolean actualDrawLeft = ltr ? drawLeft : drawRight;
        boolean actualDrawRight = ltr ? drawRight : drawLeft;

        // Determining border coordinates
        int x = actualDrawLeft ? shadeWidth : -shadeWidth - round - 1;
        int y = drawTop ? shadeWidth : -shadeWidth - round - 1;
        int maxX = actualDrawRight ? button.getWidth () - shadeWidth - 1 : button.getWidth () + shadeWidth + round;
        int maxY = drawBottom ? button.getHeight () - shadeWidth - 1 : button.getHeight () + shadeWidth + round;
        int width = maxX - x;
        int height = maxY - y;

        // Creating border shape
        if ( round > 0 )
        {
            return new RoundRectangle2D.Double ( x, y, width, height, round * 2 + ( border ? 0 : 1 ), round * 2 + ( border ? 0 : 1 ) );
        }
        else
        {
            return new Rectangle2D.Double ( x, y, width, height );
        }
    }

    @Override
    public Dimension getPreferredSize ( JComponent c )
    {
        Dimension ps = super.getPreferredSize ( c );
        return ps;
    }
}
