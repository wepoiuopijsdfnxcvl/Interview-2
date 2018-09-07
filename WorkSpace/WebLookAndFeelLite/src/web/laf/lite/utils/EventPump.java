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

import java.awt.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Event pump for modal frame.
 *
 * @author Mikle Garin
 */

public class EventPump implements InvocationHandler
{
    /**
     * Modal frame.
     */
    private Frame frame;

    /**
     * Constructs an event pump for modal frame.
     *
     * @param frame modal frame
     */
    public EventPump ( Frame frame )
    {
        this.frame = frame;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke ( Object proxy, Method method, Object[] args ) throws Throwable
    {
        return frame.isShowing () ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * A small hack to pump an event.
     * Reflection calls in this method has to be replaced once Sun provides a public API to pump events.
     *
     * @throws Exception
     */
    public void start () throws Exception
    {
        Class clazz = Class.forName ( "java.awt.Conditional" );
        Object conditional = Proxy.newProxyInstance ( clazz.getClassLoader (), new Class[]{ clazz }, this );
        Method pumpMethod = Class.forName ( "java.awt.EventDispatchThread" ).getDeclaredMethod ( "pumpEvents", new Class[]{ clazz } );
        pumpMethod.setAccessible ( true );
        pumpMethod.invoke ( Thread.currentThread (), conditional );
    }
}