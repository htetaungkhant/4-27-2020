package external_classes;

import java.text.DecimalFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LimitTextFieldInputSize extends DocumentFilter {
	private final int limit;

	public LimitTextFieldInputSize(int limit) {
		this.limit=limit;
	}

    public void insertString(DocumentFilter.FilterBypass fb, int offset,
            String text, AttributeSet attr) throws BadLocationException {

    	if(fb.getDocument().getLength()+text.length()>limit)
        {
            return;
        }
        fb.insertString(offset, text, attr);
    }

    public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
            String text, AttributeSet attrs) throws BadLocationException {

    	if(fb.getDocument().getLength()+text.length()>limit)
        {
           return;
       }
        fb.replace(offset, length, text, attrs);
    }
}