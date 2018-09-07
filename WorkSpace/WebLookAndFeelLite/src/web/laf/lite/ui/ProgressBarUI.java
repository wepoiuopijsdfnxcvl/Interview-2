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
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.SkyTimer;
import web.laf.lite.utils.SwingUtils;
import web.laf.lite.utils.ThreadUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * User: mgarin Date: 28.04.11 Time: 15:05
 */

public class ProgressBarUI extends BasicProgressBarUI implements ShapeProvider
{
    private static final int indeterminateStep = 20;
    private static final int determinateAnimationWidth = 120;

    private static AffineTransform moveX = new AffineTransform ();

    static
    {
        moveX.translate ( indeterminateStep * 2, 0 );
    }

    private Color bgTop = new Color ( 242, 242, 242 );
    private Color bgBottom = new Color ( 223, 223, 223 );

    private Color progressTopColor = Color.WHITE;
    private Color progressBottomColor = new Color ( 223, 223, 223 );

    private Color indeterminateBorder = new Color ( 210, 210, 210 );

    private Color highlightWhite = new Color ( 255, 255, 255, 180 );
    private Color highlightDarkWhite = new Color ( 255, 255, 255, 210 );

    private int round = 4;
    private int innerRound = 2;
    private int shadeWidth = 2;

    private boolean paintIndeterminateBorder = true;

    private int preferredProgressWidth = 240;

    private int determinateAnimationPause = 1500;
    private int animationLocation = 0;
    private SkyTimer animator = null;

    private PropertyChangeListener propertyChangeListener;

    @SuppressWarnings ("UnusedParameters")
    public static ComponentUI createUI ( JComponent c )
    {
        return new ProgressBarUI ();
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public int getInnerRound ()
    {
        return innerRound;
    }

    public void setInnerRound ( int innerRound )
    {
        this.innerRound = innerRound;
    }

    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ( progressBar );
    }

    public boolean isPaintIndeterminateBorder ()
    {
        return paintIndeterminateBorder;
    }

    public void setPaintIndeterminateBorder ( boolean paintIndeterminateBorder )
    {
        this.paintIndeterminateBorder = paintIndeterminateBorder;
    }

    public int getPreferredProgressWidth ()
    {
        return preferredProgressWidth;
    }

    public void setPreferredProgressWidth ( int preferredProgressWidth )
    {
        this.preferredProgressWidth = preferredProgressWidth;
    }

    public Color getBgTop ()
    {
        return bgTop;
    }

    public void setBgTop ( Color bgTop )
    {
        this.bgTop = bgTop;
    }

    public Color getBgBottom ()
    {
        return bgBottom;
    }

    public void setBgBottom ( Color bgBottom )
    {
        this.bgBottom = bgBottom;
    }

    public Color getProgressTopColor ()
    {
        return progressTopColor;
    }

    public void setProgressTopColor ( Color progressTopColor )
    {
        this.progressTopColor = progressTopColor;
    }

    public Color getProgressBottomColor ()
    {
        return progressBottomColor;
    }

    public void setProgressBottomColor ( Color progressBottomColor )
    {
        this.progressBottomColor = progressBottomColor;
    }

    public Color getIndeterminateBorder ()
    {
        return indeterminateBorder;
    }

    public void setIndeterminateBorder ( Color indeterminateBorder )
    {
        this.indeterminateBorder = indeterminateBorder;
    }

    public Color getHighlightWhite ()
    {
        return highlightWhite;
    }

    public void setHighlightWhite ( Color highlightWhite )
    {
        this.highlightWhite = highlightWhite;
    }

    public Color getHighlightDarkWhite ()
    {
        return highlightDarkWhite;
    }

    public void setHighlightDarkWhite ( Color highlightDarkWhite )
    {
        this.highlightDarkWhite = highlightDarkWhite;
    }

    @Override
    public void installUI ( JComponent c )
    {
        super.installUI ( c );

        // Default settings
        progressBar.setOpaque ( false );
        progressBar.setBorderPainted ( false );
        progressBar.setForeground ( Color.DARK_GRAY );

        // Updating border
        updateBorder ( progressBar );

        // Change listeners
        propertyChangeListener = new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                updateAnimator ( progressBar );
            }
        };
        progressBar.addPropertyChangeListener ( "indeterminate", propertyChangeListener );
        progressBar.addPropertyChangeListener ( "enabled", propertyChangeListener );

        updateAnimator ( progressBar );
        c.addAncestorListener ( new AncestorAdapter ()
        {
            @Override
            public void ancestorAdded ( AncestorEvent event )
            {
                updateAnimator ( progressBar );
            }

            @Override
            public void ancestorRemoved ( AncestorEvent event )
            {
                updateAnimator ( progressBar );
            }
        } );
    }

    private void updateBorder ( JComponent c )
    {
        if ( c != null )
        {
            c.setBorder ( LafUtils.createWebBorder ( shadeWidth + 1, shadeWidth + 1, shadeWidth + 1, shadeWidth + 1 ) );
        }
    }

    @Override
    public void uninstallUI ( JComponent c )
    {
        c.removePropertyChangeListener ( propertyChangeListener );

        if ( animator != null )
        {
            animator.stop ();
        }

        super.uninstallUI ( c );
    }

    @Override
    public Shape provideShape ()
    {
        return LafUtils.getWebBorderShape ( progressBar, getShadeWidth (), getRound () );
    }

    private void updateAnimator ( final JProgressBar progressBar )
    {
        if ( animator != null )
        {
            animator.stop ();
        }
        if ( SwingUtils.getWindowAncestor ( progressBar ) != null && progressBar.isShowing () )
        {
            if ( progressBar.isEnabled () )
            {
                if ( progressBar.isIndeterminate () )
                {
                    animationLocation = 0;
                    animator = new SkyTimer ( "WebProgressBarUI.animator", 40, new ActionListener ()
                    {
                        @Override
                        public void actionPerformed ( ActionEvent e )
                        {
                            if ( animationLocation < indeterminateStep * 2 - 1 )
                            {
                                animationLocation++;
                            }
                            else
                            {
                                animationLocation = 0;
                            }
                            SwingUtilities.invokeLater ( new Runnable ()
                            {
                                @Override
                                public void run ()
                                {
                                    progressBar.repaint ();
                                }
                            } );
                        }
                    } );
                    animator.setUseEventDispatchThread ( false );
                }
                else
                {
                    animationLocation = -determinateAnimationWidth;
                    animator = new SkyTimer ( "SkyProgressBarUI.animator", 40, new ActionListener ()
                    {
                        @Override
                        public void actionPerformed ( ActionEvent e )
                        {
                            if ( animationLocation < getProgressWidth () )
                            {
                                animationLocation += 15;
                                refresh ( progressBar );
                            }
                            else
                            {
                                animationLocation = -determinateAnimationWidth;
                                refresh ( progressBar );
                                ThreadUtils.sleepSafely ( determinateAnimationPause );
                            }
                        }

                        private void refresh ( final JProgressBar progressBar )
                        {
                            if ( !progressBar.isIndeterminate () && progressBar.getValue () > progressBar.getMinimum () )
                            {
                                SwingUtilities.invokeLater ( new Runnable ()
                                {
                                    @Override
                                    public void run ()
                                    {
                                        progressBar.repaint ();
                                    }
                                } );
                            }
                        }
                    } );
                }
                animator.setUseEventDispatchThread ( false );
                animator.start ();
            }
        }
    }

    @Override
    protected Dimension getPreferredInnerHorizontal ()
    {
        Dimension ph = super.getPreferredInnerHorizontal ();
        ph.width = preferredProgressWidth;
        return ph;
    }

    @Override
    protected Dimension getPreferredInnerVertical ()
    {
        Dimension pv = super.getPreferredInnerVertical ();
        pv.height = preferredProgressWidth;
        return pv;
    }

    @Override
    public void paint ( Graphics g, JComponent c )
    {
        Object aa = LafUtils.setupAntialias ( g );
        super.paint ( g, c );
        LafUtils.restoreAntialias ( g, aa );
    }

    @Override
    protected void paintIndeterminate ( Graphics g, JComponent c )
    {
        Graphics2D g2d = ( Graphics2D ) g;

        // Outer border
        paintProgressBarBorder ( c, g2d );

        // Indeterminate view

        Shape is = getInnerProgressShape ( c );

        Shape oldClip = g2d.getClip ();
        Area newClip = new Area ( is );
        newClip.intersect ( new Area ( oldClip ) );
        g2d.setClip ( newClip );

        GeneralPath bs = getIndeterminateProgressShape ( c );
        AffineTransform at = new AffineTransform ();
        at.translate ( animationLocation, 0 );
        bs.transform ( at );

        int x = 0;
        while ( x < c.getWidth () - shadeWidth * 2 - 4 - 1 + indeterminateStep * 2 )
        {
            g2d.setPaint ( new GradientPaint ( 0, shadeWidth, progressTopColor, 0, c.getHeight () - shadeWidth, progressBottomColor ) );
            g2d.fill ( bs );

            if ( paintIndeterminateBorder )
            {
                g2d.setPaint ( indeterminateBorder );
                g2d.draw ( bs );
            }

            x += indeterminateStep * 2;
            bs.transform ( moveX );
        }

        if ( progressBar.isStringPainted () && progressBar.getString () != null &&
                progressBar.getString ().trim ().length () > 0 )
        {
            int tw = g2d.getFontMetrics ().stringWidth ( progressBar.getString () );
            float percentage = ( float ) tw / ( progressBar.getWidth () * 2 );
            float start = 0.5f - percentage;
            float end = 0.5f + percentage;
            g2d.setPaint ( new LinearGradientPaint ( 0, 0, progressBar.getWidth (), 0,
                    new float[]{ start / 2, start, end, end + ( 1f - end ) / 2 },
                    new Color[]{ new Color ( 255, 255, 255, 0 ), highlightWhite, highlightWhite, new Color ( 255, 255, 255, 0 ) } ) );
            g2d.fill ( is );
        }

        g2d.setClip ( oldClip );

        // Inner border
        if ( c.isEnabled () )
        {
            LafUtils.drawShade ( g2d, is, new Color ( 210, 210, 210 ), shadeWidth );
        }
        g2d.setPaint ( c.isEnabled () ? Color.GRAY : Color.LIGHT_GRAY );
        g2d.draw ( is );

        // Text
        drawProgressBarText ( g2d );
    }

    @Override
    protected void paintDeterminate ( Graphics g, JComponent c )
    {
        final Graphics2D g2d = ( Graphics2D ) g;

        // Outer border
        paintProgressBarBorder ( c, g2d );

        // Progress bar
        if ( progressBar.getValue () > progressBar.getMinimum () )
        {
            Shape is = getInnerProgressShape ( c );

            if ( c.isEnabled () )
            {
                LafUtils.drawShade ( g2d, is, new Color ( 210, 210, 210 ), shadeWidth );
            }

            if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
            {
                g2d.setPaint ( new GradientPaint ( 0, shadeWidth, progressTopColor, 0, c.getHeight () - shadeWidth, progressBottomColor ) );
            }
            else
            {
                if ( progressBar.getComponentOrientation ().isLeftToRight () )
                {
                    g2d.setPaint (
                            new GradientPaint ( shadeWidth, 0, progressTopColor, c.getWidth () - shadeWidth, 0, progressBottomColor ) );
                }
                else
                {
                    g2d.setPaint (
                            new GradientPaint ( shadeWidth, 0, progressBottomColor, c.getWidth () - shadeWidth, 0, progressTopColor ) );
                }
            }
            g2d.fill ( is );

            // Running highlight
            if ( c.isEnabled () )
            {
                Shape oldClip = g2d.getClip ();
                Area newClip = new Area ( is );
                newClip.intersect ( new Area ( oldClip ) );
                g2d.setClip ( newClip );
                if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
                {
                    g2d.setPaint ( new RadialGradientPaint ( shadeWidth * 2 + animationLocation + determinateAnimationWidth / 2,
                            progressBar.getHeight () / 2, determinateAnimationWidth / 2, new float[]{ 0f, 1f },
                            new Color[]{ highlightDarkWhite, new Color ( 255, 255, 255, 0 ) } ) );
                }
                else
                {
                    if ( progressBar.getComponentOrientation ().isLeftToRight () )
                    {
                        g2d.setPaint ( new RadialGradientPaint ( c.getWidth () / 2, c.getHeight () - shadeWidth * 2 - animationLocation -
                                determinateAnimationWidth / 2, determinateAnimationWidth / 2, new float[]{ 0f, 1f },
                                new Color[]{ highlightDarkWhite, new Color ( 255, 255, 255, 0 ) } ) );
                    }
                    else
                    {
                        g2d.setPaint ( new RadialGradientPaint ( c.getWidth () / 2,
                                shadeWidth * 2 + animationLocation + determinateAnimationWidth / 2, determinateAnimationWidth / 2,
                                new float[]{ 0f, 1f }, new Color[]{ highlightDarkWhite, new Color ( 255, 255, 255, 0 ) } ) );
                    }
                }
                g2d.fill ( is );
                g2d.setClip ( oldClip );
            }

            // Border
            g2d.setPaint ( c.isEnabled () ? Color.GRAY : Color.LIGHT_GRAY );
            g2d.draw ( is );
        }

        // Text
        drawProgressBarText ( g2d );
    }

    private void drawProgressBarText ( Graphics2D g2d )
    {
        if ( progressBar.isStringPainted () )
        {
            if ( progressBar.getOrientation () == JProgressBar.VERTICAL )
            {
                g2d.translate ( progressBar.getWidth () / 2, progressBar.getHeight () / 2 );
                g2d.rotate ( ( progressBar.getComponentOrientation ().isLeftToRight () ? -1 : 1 ) * Math.PI / 2 );
                g2d.translate ( -progressBar.getWidth () / 2, -progressBar.getHeight () / 2 );
            }

            final String string = progressBar.getString ();
            final Point ts = LafUtils.getTextCenterShear ( g2d.getFontMetrics (), string );
            if ( !progressBar.isEnabled () )
            {
                g2d.setPaint ( Color.WHITE );
                g2d.drawString ( string, progressBar.getWidth () / 2 + ts.x + 1, progressBar.getHeight () / 2 + ts.y + 1 );
            }
            g2d.setPaint ( progressBar.isEnabled () ? progressBar.getForeground () : new Color ( 160, 160, 160 ) );
            g2d.drawString ( string, progressBar.getWidth () / 2 + ts.x, progressBar.getHeight () / 2 + ts.y );

            if ( progressBar.getOrientation () == JProgressBar.VERTICAL )
            {
                g2d.rotate ( ( progressBar.getComponentOrientation ().isLeftToRight () ? 1 : -1 ) * Math.PI / 2 );
            }
        }
    }

    private void paintProgressBarBorder ( JComponent c, Graphics2D g2d )
    {
        Shape bs = getProgressShape ( c );

        if ( c.isEnabled () )
        {
            LafUtils.drawShade ( g2d, bs, new Color ( 210, 210, 210 ), shadeWidth );
        }

        if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
        {
            g2d.setPaint ( new GradientPaint ( 0, shadeWidth, bgTop, 0, c.getHeight () - shadeWidth, bgBottom ) );
        }
        else
        {
            g2d.setPaint ( new GradientPaint ( shadeWidth, 0, bgTop, c.getWidth () - shadeWidth, 0, bgBottom ) );
        }
        g2d.fill ( bs );

        g2d.setPaint ( c.isEnabled () ? Color.GRAY : Color.LIGHT_GRAY );
        g2d.draw ( bs );
    }

    private Shape getProgressShape ( JComponent c )
    {
        if ( round > 0 )
        {
            return new RoundRectangle2D.Double ( shadeWidth, shadeWidth, c.getWidth () - shadeWidth * 2 - 1,
                    c.getHeight () - shadeWidth * 2 - 1, round * 2, round * 2 );
        }
        else
        {
            return new Rectangle2D.Double ( shadeWidth, shadeWidth, c.getWidth () - shadeWidth * 2 - 1,
                    c.getHeight () - shadeWidth * 2 - 1 );
        }
    }

    private Shape getInnerProgressShape ( JComponent c )
    {
        int progress = getProgressWidth ();
        if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
        {
            if ( innerRound > 0 )
            {
                return new RoundRectangle2D.Double ( shadeWidth * 2, shadeWidth * 2, progress - 1, c.getHeight () - shadeWidth * 4 - 1,
                        innerRound * 2, innerRound * 2 );
            }
            else
            {
                return new Rectangle2D.Double ( shadeWidth * 2, shadeWidth * 2, progress - 1, c.getHeight () - shadeWidth * 4 - 1 );
            }
        }
        else
        {
            if ( progressBar.getComponentOrientation ().isLeftToRight () )
            {
                if ( innerRound > 0 )
                {
                    return new RoundRectangle2D.Double ( shadeWidth * 2, c.getHeight () - progress - shadeWidth * 2,
                            c.getWidth () - shadeWidth * 4 - 1, progress - 1, innerRound * 2, innerRound * 2 );
                }
                else
                {
                    return new Rectangle2D.Double ( shadeWidth * 2, c.getHeight () - progress - shadeWidth * 2,
                            c.getWidth () - shadeWidth * 4 - 1, progress - 1 );
                }
            }
            else
            {
                if ( innerRound > 0 )
                {
                    return new RoundRectangle2D.Double ( shadeWidth * 2, shadeWidth * 2, c.getWidth () - shadeWidth * 4 - 1, progress - 1,
                            innerRound * 2, innerRound * 2 );
                }
                else
                {
                    return new Rectangle2D.Double ( shadeWidth * 2, shadeWidth * 2, c.getWidth () - shadeWidth * 4 - 1, progress - 1 );
                }
            }
        }
    }

    private int getProgressWidth ()
    {
        int progress;
        if ( progressBar.isIndeterminate () )
        {
            if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
            {
                progress = progressBar.getWidth () - shadeWidth * 4;
            }
            else
            {
                progress = progressBar.getHeight () - shadeWidth * 4;
            }
        }
        else
        {
            if ( progressBar.getOrientation () == JProgressBar.HORIZONTAL )
            {
                progress = ( int ) ( ( float ) ( progressBar.getWidth () - shadeWidth * 4 ) *
                        ( ( float ) ( progressBar.getValue () - progressBar.getMinimum () ) /
                                ( float ) ( progressBar.getMaximum () - progressBar.getMinimum () ) ) );
            }
            else
            {
                progress = ( int ) ( ( float ) ( progressBar.getHeight () - shadeWidth * 4 ) *
                        ( ( float ) ( progressBar.getValue () - progressBar.getMinimum () ) /
                                ( float ) ( progressBar.getMaximum () - progressBar.getMinimum () ) ) );
            }
        }
        return progress;
    }


    private GeneralPath getIndeterminateProgressShape ( JComponent c )
    {
        // Small inner
        //        GeneralPath gp = new GeneralPath ( GeneralPath.WIND_EVEN_ODD );
        //        gp.moveTo ( shadeWidth * 2 - indeterminateStep * 2, c.getHeight () - shadeWidth*2 - 1 );
        //        gp.lineTo ( shadeWidth * 2 - indeterminateStep, shadeWidth*2 );
        //        gp.lineTo ( shadeWidth * 2, shadeWidth*2 );
        //        gp.lineTo ( shadeWidth * 2 - indeterminateStep, c.getHeight () - shadeWidth*2 - 1 );
        //        gp.closePath ();
        //        return gp;

        // Large inner
        //        GeneralPath gp = new GeneralPath ( GeneralPath.WIND_EVEN_ODD );
        //        gp.moveTo ( shadeWidth * 2 - indeterminateStep * 2, c.getHeight () - shadeWidth * 2 );
        //        gp.lineTo ( shadeWidth * 2 - indeterminateStep, shadeWidth * 2 - 1 );
        //        gp.lineTo ( shadeWidth * 2, shadeWidth * 2 - 1 );
        //        gp.lineTo ( shadeWidth * 2 - indeterminateStep, c.getHeight () - shadeWidth * 2 );
        //        gp.closePath ();
        //        return gp;

        // Outer
        GeneralPath gp = new GeneralPath ( GeneralPath.WIND_EVEN_ODD );
        gp.moveTo ( shadeWidth * 2 - indeterminateStep * 2, c.getHeight () - shadeWidth - 1 );
        gp.lineTo ( shadeWidth * 2 - indeterminateStep, shadeWidth );
        gp.lineTo ( shadeWidth * 2, shadeWidth );
        gp.lineTo ( shadeWidth * 2 - indeterminateStep, c.getHeight () - shadeWidth - 1 );
        gp.closePath ();
        return gp;
    }
}