package tester;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
                frame.add(gg);
                frame.setLocationRelativeTo ( null );
                frame.setVisible ( true );
            }
        } );
    }
}
