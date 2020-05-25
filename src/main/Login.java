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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;

import database.LoginChecker;
import external_classes.Fonts;

public class Login {

	private static void addComponentsToPane(JFrame frame) {
		Container pane = frame.getContentPane();
		pane.setLayout(null);

		JLabel lbUsername = new JLabel("အသုံးပြုသူအမည်");
		lbUsername.setFont(Fonts.pyisuNormal15);
		JLabel lbPassword = new JLabel("စကားဝှက်");
		lbPassword.setFont(Fonts.pyisuNormal15);
		JTextField tfUsername = new JTextField(20);
		tfUsername.setFont(Fonts.pyisuNormal15);
		JPasswordField tfPassword = new JPasswordField(20);
		tfPassword.setFont(Fonts.pyisuNormal15);
        JButton btnLogin = new JButton("ဝင်မည်");
        btnLogin.setFont(Fonts.pyisuNormal18);
		JButton btnCancel = new JButton("မလုပ်ဆောင်ပါ");
		btnCancel.setFont(Fonts.pyisuNormal18);
		pane.add(lbUsername);
		pane.add(tfUsername);
		pane.add(lbPassword);
		pane.add(tfPassword);
		pane.add(btnLogin);
		pane.add(btnCancel);

		lbUsername.setBounds(40, 20, 100, 40); tfUsername.setBounds(150, 20, 150, 40);
		lbPassword.setBounds(40, 70, 100, 40); tfPassword.setBounds(150, 70, 150, 40);
		btnCancel.setBounds(60, 130, 120, 50); btnLogin.setBounds(200, 130, 100, 50);

		tfUsername.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(database.LoginChecker.Check(tfUsername.getText(), new String(tfPassword.getPassword()))){
					Main.createAndShowGUI();
					frame.dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		tfPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(database.LoginChecker.Check(tfUsername.getText(), new String(tfPassword.getPassword()))){
					Main.createAndShowGUI();
					frame.dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
				System.exit(0);
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(database.LoginChecker.Check(tfUsername.getText(), new String(tfPassword.getPassword()))){
						Main.createAndShowGUI();
						frame.dispose();
					}
					else{
						JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Incorrect", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
    }

	public static void createAndShowGUI() {
        JFrame frame = new JFrame("\"လင်းလင်း\" စတိုးဆိုင်");
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
		 SwingUtilities.invokeLater ( new Runnable ()
	        {
	            public void run ()
	            {
	                WebLookAndFeel.install ();
	        		createAndShowGUI();
	            }
	        } );
	}

}
