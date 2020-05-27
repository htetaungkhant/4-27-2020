package main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

import customer.CustomerInfo;
import external_classes.Fonts;
import purchase.MoneyTransfer;
import purchase.Purchase;
import sale.PosManager;
import sale.Report;
import sale.Sale;
import stock.Items;
import supplier.SupplierInfo;

public class SplashScreen extends JFrame
{

	private static JLabel percent;
	private static JProgressBar progressBar;
	
	public SplashScreen() {
        ImageIcon frameIcon = new ImageIcon("picture/frame_icon.png");
        setIconImage(frameIcon.getImage());
		setSize(400, 350);
//		getContentPane().setBackground(Color.black);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		JLabel background = new JLabel(new ImageIcon("picture/splash_background.png"));
		JLabel icon = new JLabel(new ImageIcon("picture/805.gif"));
		JLabel lbWelcome = new JLabel("ကြိုဆိုပါ၏");
		lbWelcome.setForeground(new Color(248, 249, 249));
		lbWelcome.setHorizontalAlignment(JLabel.CENTER);
		lbWelcome.setFont(Fonts.pyisuBold65);
		percent = new JLabel();
		percent.setFont(Fonts.pyisuBold20);
		percent.setHorizontalAlignment(JLabel.CENTER);
		progressBar = new JProgressBar();

		add(icon);
		add(lbWelcome);
		add(percent);
		add(progressBar);
		add(background);

		icon.setBounds(0, 0, 400, 250);
		lbWelcome.setBounds(0, 200, 400, 100);
		percent.setBounds(0, 320, 400, 30);
		progressBar.setBounds(0, 320, 400, 30);
		background.setBounds(0, 0, 400, 350);
	}
	public static void main(String[] args){
			WebLookAndFeel.install ();
	     	SplashScreen ss = new SplashScreen();
			ss.setVisible(true);
			new PosManager();
			new Sale();
			new CustomerInfo();
    		new Report();
    		new Purchase(false);
    		new MoneyTransfer();
    		new SupplierInfo();
    		new Items();
			try {
				for(int i = 0; i <= 100; i++){
					Thread.sleep(50);
					String text = Integer.toString(i) + " %";
					ss.percent.setText(text);
					ss.progressBar.setValue(i);
					if(i == 100){
						ss.dispose();
						Login.createAndShowGUI();
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}		
	}
}