package tester;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.alee.laf.WebLookAndFeel;

public class Test2 {

    public static void main(String[] argv) throws Exception {
		  JTextField textField = new JTextField();

		  textField.addKeyListener(new MKeyListener());

		  JFrame jframe = new JFrame();

		  jframe.add(textField);

		  jframe.setSize(400, 350);
		  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  jframe.setVisible(true);

	    }
}

class MKeyListener extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent event) {
		  char ch = event.getKeyChar();
		  if (ch == 'a' ||ch == 'b'||ch == 'c' ) {
			  	System.out.println(event.getKeyChar());
			}

		  if (event.getKeyCode() == KeyEvent.VK_END) {
				System.out.println("Key codes: " + event.getKeyCode());
			}
    }
}