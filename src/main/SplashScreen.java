package main;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.managers.style.StyleId;

import customer.CustomerInfo;
import external_classes.Fonts;
import external_classes.ProgressBarSkin;
import purchase.MoneyTransfer;
import purchase.Purchase;
import sale.PosManager;
import sale.Report;
import sale.Sale;
import stock.Items;
import supplier.SupplierInfo;

public class SplashScreen extends JFrame
{

	private static JProgressBar progressBar;
	
	public SplashScreen() {
        ImageIcon frameIcon = new ImageIcon("picture/frame_icon.png");
        setIconImage(frameIcon.getImage());
		setSize(400, 300);
		getContentPane().setBackground(new Color(52, 73, 94));
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		JLabel loadingIcon = new JLabel(new ImageIcon("picture/loading.gif"));
		JLabel logo = new JLabel(new ImageIcon("picture/splash_logo.png"));
		JLabel lbWelcome = new JLabel("ကြိုဆိုပါ၏");
		lbWelcome.setForeground(new Color(247, 220, 111));
		lbWelcome.setHorizontalAlignment(JLabel.CENTER);
		lbWelcome.setFont(Fonts.pyisuBold65);
		progressBar = new JProgressBar();

		add(logo);
		add(loadingIcon);
		add(lbWelcome);
		add(progressBar);

		logo.setBounds(0, 0, 400, 150);
		loadingIcon.setBounds(0, 0, 400, 150);
		lbWelcome.setBounds(0, 160, 400, 100);
		progressBar.setBounds(0, 290, 400, 12);
	}
	public static void main(String[] args){
			WebLookAndFeel.install(ProgressBarSkin.class);
	     	SplashScreen ss = new SplashScreen();
			ss.setVisible(true);
			new PosManager();
			new Sale();
			new CustomerInfo();
    		new Report();
			try {
				for(int i = 0; i <= 100; i++){
					Thread.sleep(40);
					String text = Integer.toString(i) + " %";
					ss.progressBar.setValue(i);
					if(i == 100){
			    		new Purchase(false);
			    		new MoneyTransfer();
			    		new SupplierInfo();
			    		new Items();
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