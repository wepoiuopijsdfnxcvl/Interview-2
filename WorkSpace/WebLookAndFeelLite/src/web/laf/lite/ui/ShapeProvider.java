package web.laf.lite.ui;

import java.awt.Shape;

/**
 * This interface provides a single method for requesting shape.
 * This can be used by components to provide their shape for various usage cases.
 *
 * @author Mikle Garin
 */

public interface ShapeProvider
{
    /**
     * Returns component shape.
     *
     * @return component shape
     */
    public Shape provideShape ();
}