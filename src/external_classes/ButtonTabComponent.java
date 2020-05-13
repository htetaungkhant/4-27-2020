package external_classes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import com.alee.managers.style.StyleId;

import sale.POS;

import java.awt.*;
import java.awt.event.*;
public class ButtonTabComponent extends JPanel {
    private final JTabbedPane pane;

    public ButtonTabComponent(final JTabbedPane pane, POS newInvoicePanel) {
        //unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);

        //make JLabel read titles from JTabbedPane
        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label);
        //add more space between the label and the button
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        //tab button
        JButton button = new JButton(new ImageIcon("picture/cross_sign.png"));
        button.putClientProperty ( StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        button.setPreferredSize(new Dimension(20, 20));
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            int index = pane.indexOfTabComponent(ButtonTabComponent.this);
	            if (index != -1) {
	            	for(int i = 0; i < POS.getCustomerListSize(); i++){
	            		if(newInvoicePanel.getTextFrombtnCustomer().equals(POS.getCustomer(i))){
	            			POS.removeCustomer(i);
	            			break;
	            		}
	            	}
	                pane.remove(index);
	            }
			}
		});
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
}
