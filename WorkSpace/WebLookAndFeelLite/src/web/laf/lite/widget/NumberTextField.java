package web.laf.lite.widget;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public NumberTextField(){
		setDocument(new NumberDocument());
	}

}

class NumberDocument extends PlainDocument{
	private static final long serialVersionUID = 1L;

    @Override
    public void insertString(int offs, String str, AttributeSet a)
    {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return;
        }
        try {
			super.insertString(offs, str, a);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
    }               
}
