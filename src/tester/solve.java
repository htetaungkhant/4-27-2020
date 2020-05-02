package tester;// java Program to create a dialog within a dialog
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class solve extends JFrame implements ActionListener {

	// frame
	static JFrame f;

	// dialog
	static JDialog d, d1;

	// main class
	public static void main(String[] args)
	{
		// create a new frame
		f = new JFrame("frame");

		// create a object
		solve s = new solve();

		// create a panel
		JPanel p = new JPanel();

		JButton b = new JButton("click");

		// add actionlistener to button
		b.addActionListener(s);

		// add button to panel
		p.add(b);

		f.add(p);

		// set the size of frame
		f.setSize(400, 400);

		f.show();
	}
	public void actionPerformed(ActionEvent e)
	{
		String s = e.getActionCommand();
		if (s.equals("click")) {
			// create a dialog Box
			d = new JDialog(f, "dialog Box", true);
			// create a label
			JLabel l = new JLabel("this is first dialog box");

			// create a button
			JButton b = new JButton("click me");

			// add Action Listener
			b.addActionListener(this);

			// create a panel
			JPanel p = new JPanel();

			p.add(b);
			p.add(l);

			// add panel to dialog
			d.add(p);

			// setsize of dialog
			d.setSize(200, 200);

			// set visibility of dialog
			d.setVisible(true);
		}
		else { // create a dialog Box
			d1 = new JDialog(d, "dialog Box", true);

			// create a label
			JLabel l = new JLabel("this is second dialog box");

			d1.add(l);

			// setsize of dialog
			d1.setSize(200, 200);

			// set loaction of dialog
//			d1.setLocation(200, 200);
			d1.setLocationRelativeTo(null);

			// set visibility of dialog
			d1.setVisible(true);
		}
	}
}
