package web.laf.lite.widget;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import web.laf.lite.utils.CollectionUtils;


public class UnselectableButtonGroup extends ButtonGroup
{
    private ArrayList<ButtonGroupListener> listeners = new ArrayList<ButtonGroupListener> ( 1 );

    private boolean unselectable = true;

    public UnselectableButtonGroup ()
    {
        super ();
    }

    public UnselectableButtonGroup ( AbstractButton... buttons )
    {
        super ();
        add ( buttons );
    }

    public UnselectableButtonGroup ( List<AbstractButton> buttons )
    {
        super ();
        add ( buttons );
    }

    public boolean isUnselectable ()
    {
        return unselectable;
    }

    public void setUnselectable ( boolean unselectable )
    {
        this.unselectable = unselectable;
    }

    @Override
    public void setSelected ( ButtonModel model, boolean selected )
    {
        if ( selected || !unselectable )
        {
            super.setSelected ( model, selected );
        }
        else
        {
            clearSelection ();
        }
        fireSelectionChanged ();
    }

    public List<AbstractButton> getButtons ()
    {
        return CollectionUtils.copy ( buttons );
    }

    public void addButtonGroupListener ( ButtonGroupListener listener )
    {
        listeners.add ( listener );
    }

    public void removeButtonGroupListener ( ButtonGroupListener listener )
    {
        listeners.remove ( listener );
    }

    public void fireSelectionChanged ()
    {
        for ( ButtonGroupListener listener : CollectionUtils.copy ( listeners ) )
        {
            listener.selectionChanged ();
        }
    }

    public void add ( AbstractButton... b )
    {
        for ( AbstractButton button : b )
        {
            add ( button );
        }
    }

    public void add ( List<AbstractButton> b )
    {
        for ( AbstractButton button : b )
        {
            add ( button );
        }
    }

    public static UnselectableButtonGroup group ( AbstractButton... buttons )
    {
        return new UnselectableButtonGroup ( buttons );
    }

    public static UnselectableButtonGroup group ( List<AbstractButton> buttons )
    {
        return new UnselectableButtonGroup ( buttons );
    }
}