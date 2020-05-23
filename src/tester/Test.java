package tester;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
                menuBar.setLayout(new DefaultMenuLayout(menuBar, BoxLayout.X_AXIS));
                menuBar.add ( new JMenu ( "File" ) );
                menuBar.add ( new JMenu ( "Edit" ) );
                frame.setJMenuBar ( menuBar );

                frame.setLayout(new FlowLayout());
                frame.setSize ( 500, 500 );
                JButton gg = new JButton("GG");
                gg.setPreferredSize(new Dimension(150, 40));
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
                DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
                decimalFormat.setGroupingUsed(false);
                JTextField deg = new JFormattedTextField(decimalFormat);
                deg.setColumns(15); //whatever size you wish to set
                frame.add(gg);
                frame.add(deg);
                frame.setLocationRelativeTo ( null );
                frame.setVisible ( true );
            }
        } );
    }
}
