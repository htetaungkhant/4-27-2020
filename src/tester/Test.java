package tester;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.DefaultMenuLayout;
import javax.swing.text.MaskFormatter;

import com.alee.laf.WebLookAndFeel;

public class Test
{
    public static void main ( final String[] args )
    {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                WebLookAndFeel.install ();

                final JFrame frame = new JFrame ();

                final JMenuBar menuBar = new JMenuBar ();
                menuBar.add ( new JMenu ( "File" ) );
                menuBar.add ( new JMenu ( "Edit" ) );
                frame.setJMenuBar ( menuBar );

                frame.setLayout(new FlowLayout());
                frame.setSize ( 500, 500 );
                JButton gg = new JButton("GG");
                gg.setPreferredSize(new Dimension(150, 40));
                MaskFormatter mask = null;
                try {
                    mask = new MaskFormatter("#,###,###,###");
                    mask.setPlaceholderCharacter(' ');
                } catch (ParseException e) {
                    e.printStackTrace();
                }       
                JFormattedTextField textfield = new JFormattedTextField(mask);
                textfield.setPreferredSize(new Dimension(150, 40));
                frame.add(gg);
                frame.add(textfield);
                frame.setLocationRelativeTo ( null );
                frame.setVisible ( true );
                
                gg.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println(textfield.getText());
					}
				});
            }
        } );
    }
}
