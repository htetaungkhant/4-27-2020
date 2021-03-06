package external_classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class JNumberTextField extends JTextField {

	public JNumberTextField(int limit) {
			super();

			DocumentFilter filter = new LimitTextFieldInputSize(limit);
			((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	}

	public JNumberTextField(String placeHolder,int limit) {
        super();

        TextPrompt tp = new TextPrompt(placeHolder,this);
        tp.setForeground(Color.decode("#5D6D7E"));
        tp.setFont(Fonts.pyisuNormal12);

        DocumentFilter filter = new LimitTextFieldInputSize(limit);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
 }

	 public JNumberTextField(String placeHolder,int size,int limit) {
	        super(size);

	        TextPrompt tp = new TextPrompt(placeHolder,this);
	        tp.setForeground(Color.decode("#5D6D7E"));
	        tp.setFont(Fonts.pyisuNormal12);

	        DocumentFilter filter = new LimitTextFieldInputSize(limit);
			((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
	 }

    @Override
    public void processKeyEvent(KeyEvent ev) {			//ev.getModifiers() == InputEvent.CTRL_MASK  || 
        if (Character.isDigit(ev.getKeyChar()) || ev.getKeyCode() == KeyEvent.VK_LEFT || ev.getKeyCode() == KeyEvent.VK_RIGHT || ev.getKeyCode() == KeyEvent.VK_DELETE || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE || ev.getKeyCode() == KeyEvent.VK_ENTER) {
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