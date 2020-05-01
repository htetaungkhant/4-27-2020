package external_classes;

import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class JNumberTextField extends JTextField {

	public JNumberTextField(int size) {
			super(size);

			DocumentFilter filter = new LimitTextFieldInputSize(size);
			((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	}

	 public JNumberTextField(String placeHolder,int size,int limit) {
	        super(size);

	        TextPrompt tp7 = new TextPrompt(placeHolder,this);
//	        tp7.setFont(Fonts.zawgyione11);

	        DocumentFilter filter = new LimitTextFieldInputSize(limit);
			((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	 }

    @Override
    public void processKeyEvent(KeyEvent ev) {
        if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE  || ev.getModifiers() == InputEvent.CTRL_MASK ) {
            super.processKeyEvent(ev);
        }
        ev.consume();
        return;
    }

    /**
     * As the user is not even able to enter a dot ("."), only integers (whole numbers) may be entered.
     */
    public Long getNumber() {
        Long result = null;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Long.valueOf(text);
        }
        return result;
    }
}