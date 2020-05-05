package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

import database.LoginChecker;

public class Login {

	private static void addComponentsToPane(JFrame frame) {
		Container pane = frame.getContentPane();
		pane.setLayout(null);

        JButton btnLogin = new JButton("Login");
		JButton btnCancel = new JButton("Cancel");
		JLabel lbUsername = new JLabel("Username");
		JLabel lbPassword = new JLabel("Password");
		JTextField tfUsername = new JTextField(20);
		JPasswordField tfPassword = new JPasswordField(20);
		pane.add(lbUsername);
		pane.add(tfUsername);
		pane.add(lbPassword);
		pane.add(tfPassword);
		pane.add(btnLogin);
		pane.add(btnCancel);

		lbUsername.setBounds(60, 20, 80, 30);
		tfUsername.setBounds(150, 20, 150, 30);
		lbPassword.setBounds(60, 70, 80, 30);
		tfPassword.setBounds(150, 70, 150, 30);
		btnLogin.setBounds(80, 130, 100, 50);
		btnCancel.setBounds(200, 130, 100, 50);

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
				System.exit(0);
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(database.LoginChecker.Check(tfUsername.getText(),tfPassword.getText())){
						Main.createAndShowGUI();
						frame.dispose();
					}
			}
		});
    }

	public static void createAndShowGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame);
        frame.setSize(350, 240);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        ImageIcon frameIcon = new ImageIcon("picture/frame_icon.png");
        frame.setIconImage(frameIcon.getImage());
        frame.setVisible(true);
    }

	public static void main(String[] args) {
//		String slafcn = UIManager.getSystemLookAndFeelClassName ();
//		try {
//			UIManager.setLookAndFeel (slafcn);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		WebLookAndFeel.install ();
		createAndShowGUI();
	}

}
