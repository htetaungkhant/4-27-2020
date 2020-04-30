package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.basic.DefaultMenuLayout;

import com.sun.glass.events.KeyEvent;

import stock.Items;

public class Main{

	public static JFrame frame;

	private static void addComponentsToPane(JFrame frame){

		//creating Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new DefaultMenuLayout(menuBar, BoxLayout.X_AXIS));;

		JMenu sale = new JMenu("Sale");
		JMenu purchase = new JMenu("Purchase");
		JMenu stock = new JMenu("Stock");
		JMenu cash = new JMenu("Cash");
		JMenu saving = new JMenu("Saving");
		JMenu logout = new JMenu("Logout");

		sale.setIcon(new ImageIcon("picture/sale_icon.png"));
		menuBar.add(sale);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		purchase.setIcon(new ImageIcon("picture/purchase_icon.png"));
		menuBar.add(purchase);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		stock.setIcon(new ImageIcon("picture/stock_icon.png"));
		menuBar.add(stock);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		saving.setIcon(new ImageIcon("picture/saving_icon.png"));
		menuBar.add(saving);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		cash.setIcon(new ImageIcon("picture/account_icon.png"));
		menuBar.add(cash);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		logout.setIcon(new ImageIcon("picture/logout_icon.png"));
		menuBar.add(logout);

		//Menu Items for Sale Menu
		JMenuItem pos = new JMenuItem("Point of Sale");
		JMenuItem saleRecord = new JMenuItem("Sale Records");
		JMenuItem exit = new JMenuItem("Exit");
		sale.add(pos);
		sale.add(saleRecord);
		sale.add(exit);
		sale.setMnemonic(KeyEvent.VK_S);

		//Menu Items for Purchase Menu
		JMenuItem purchaseRecord = new JMenuItem("Purchase Records");
		JMenuItem supplier = new JMenuItem("Suppliers");
		purchase.add(purchaseRecord);
		purchase.add(supplier);
		purchase.setMnemonic(KeyEvent.VK_P);

		//Menu Items for Stock Menu
		JMenuItem item = new JMenuItem("Items");
		JMenuItem itemsToPurchase = new JMenuItem("Items to Purchase");
		stock.add(item);
		stock.add(itemsToPurchase);
		stock.setMnemonic(KeyEvent.VK_T);

		//Menu Items for Accounts Menu
		JMenuItem savingAccount = new JMenuItem("Saving Account");
		JMenuItem sellingAccount = new JMenuItem("Selling Account");
		JMenuItem totalAccount = new JMenuItem("Total");
		cash.add(totalAccount);
		cash.add(savingAccount);
		cash.add(sellingAccount);
		cash.setMnemonic(KeyEvent.VK_C);

		//Menu Items for Saving Menu
		JMenuItem addSaving = new JMenuItem("Add Record");
		JMenuItem showRecord = new JMenuItem("Show Records");
		saving.add(addSaving);
		saving.add(showRecord);
		saving.setMnemonic(KeyEvent.VK_V);

		frame.setJMenuBar(menuBar);
		//End of creating Menu Bar

		JPanel centerPanel = new JPanel();
		centerPanel.add(new JButton("Center"));
		//centerPanel.setBorder(BorderFactory.createEmptyBorder(-5,-5,-5,-5));

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(centerPanel,BorderLayout.CENTER);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		logout.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
				Login.createAndShowGUI();
				frame.dispose();
			}
		});

		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				JPanel items = new Items();
				pane.add(items);
			}
		});
	}

	public static void createAndShowGUI() {
        frame = new JFrame("Main");
        addComponentsToPane(frame);
		Toolkit tk=Toolkit.getDefaultToolkit();
		int x=(int)tk.getScreenSize().getWidth();
		int y=(int)tk.getScreenSize().getHeight();
		frame.setSize(x-100,y-100);
        frame.setLocationRelativeTo(null);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
