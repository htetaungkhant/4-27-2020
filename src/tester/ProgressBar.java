package tester;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.WebLookAndFeel;

import external_classes.Fonts;
import main.Login;
import net.miginfocom.swing.MigLayout;

public class ProgressBar extends JFrame
{

	public ProgressBar() {
		setLayout(new BorderLayout());
		setSize(200, 200);
		setUndecorated(true);
		setOpacity(0.55f);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel icon = new JLabel(new ImageIcon("picture/805.gif"));
		panel.add(icon, BorderLayout.CENTER);

		add(panel, BorderLayout.CENTER);
	}
	public static void main(String[] args){
		ProgressBar pb = new ProgressBar();
		pb.setVisible(true);

		try {
			for(int i = 0; i <= 100; i++){
				Thread.sleep(40);
				if(i == 100){
//					pb.dispose();
//					Login.createAndShowGUI();
				}
			}
		} catch (Exception e) {
		}
	}
}