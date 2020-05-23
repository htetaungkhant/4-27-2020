package external_classes;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class MyTextField extends JTextField{

    public MyTextField() {
        super();
    }

    public MyTextField(int limit){
    	super();
        DocumentFilter filter = new LimitTextFieldInputSize(limit);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
    }

    public MyTextField(String string){
    	super(string);
    }

    public MyTextField(String string, int size){
    	super(string, size);
    }

    public MyTextField(int limit, String placeHolder) {
        super();
        TextPrompt tp = new TextPrompt(placeHolder,this);
        tp.setForeground(Color.decode("#5D6D7E"));
        tp.setFont(Fonts.pyisuNormal12);

        DocumentFilter filter = new LimitTextFieldInputSize(limit);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
    }

    public MyTextField(int size, int limit, String placeHolder) {
        super(size);
        TextPrompt tp = new TextPrompt(placeHolder,this);
        tp.setForeground(Color.decode("#5D6D7E"));
        tp.setFont(Fonts.pyisuNormal12);

        DocumentFilter filter = new LimitTextFieldInputSize(limit);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
    }

    public MyTextField(String iconName,String placeHolder,int size,int limit) {
        super(size);
        TextPrompt tp7 = new TextPrompt(placeHolder,this);
    	tp7.changeAlpha(0.6f);
    	tp7.changeStyle(Font.BOLD );
    	tp7.setIcon(new ImageIcon(new ImageIcon("images/"+iconName).getImage().getScaledInstance(18,18,Image.SCALE_SMOOTH)));

        DocumentFilter filter = new LimitTextFieldInputSize(limit);
		((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
    }

}