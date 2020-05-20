package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.DefaultMenuLayout;

import customer.CustomerInfo;
import purchase.Purchase;
import sale.POS;
import sale.PosManager;
import sale.Report;
import sale.Sale;
import stock.Items;
import supplier.SupplierInfo;
import tester.ProgressBar;

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
//		menuBar.add(cash);
//		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		logout.setIcon(new ImageIcon("picture/logout_icon.png"));
		menuBar.add(logout);

		//Menu Items for Sale Menu
		JMenuItem pos = new JMenuItem("Point of Sale", new ImageIcon("picture/pos_icon.png"));
		KeyStroke keyStrokePos = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		pos.setAccelerator(keyStrokePos);
		JMenuItem saleRecord = new JMenuItem("Sale Records", new ImageIcon("picture/sale_record_icon.png"));
		KeyStroke keyStrokeForSaleRecord = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		saleRecord.setAccelerator(keyStrokeForSaleRecord);
		JMenuItem customer = new JMenuItem("Customers", new ImageIcon("picture/customer_icon.png"));
		JMenuItem reports = new JMenuItem("Reports", new ImageIcon("picture/reports_icon.png"));
		JMenuItem exit = new JMenuItem("Exit", new ImageIcon("picture/exit_icon.png"));
		KeyStroke keyStrokeForExit = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
		exit.setAccelerator(keyStrokeForExit);
		sale.add(pos);
		sale.add(saleRecord);
		sale.add(customer);
		sale.add(reports);
		sale.add(exit);
		sale.setMnemonic(KeyEvent.VK_S);

		//Menu Items for Purchase Menu
		JMenuItem purchaseRecord = new JMenuItem("Purchase Records", new ImageIcon("picture/purchase_record_icon.png"));
		KeyStroke keyStrokeToPurchaseRecord = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
		purchaseRecord.setAccelerator(keyStrokeToPurchaseRecord);
		JMenuItem supplier = new JMenuItem("Suppliers", new ImageIcon("picture/supplier_icon.png"));
		KeyStroke keyStrokeToSupplier = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
		supplier.setAccelerator(keyStrokeToSupplier);
		purchase.add(purchaseRecord);
		purchase.add(supplier);
		purchase.setMnemonic(KeyEvent.VK_P);

		//Menu Items for Stock Menu
		JMenuItem item = new JMenuItem("Items", new ImageIcon("picture/items_icon.png"));
		KeyStroke keyStrokeToItem = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
		item.setAccelerator(keyStrokeToItem);
		stock.add(item);
		stock.setMnemonic(KeyEvent.VK_T);

		//Menu Items for Saving Menu
		JMenuItem dailyRecord = new JMenuItem("Daily Records", new ImageIcon("picture/add_daily_records_icon.png"));
		JMenuItem savingPeople = new JMenuItem("Saving People", new ImageIcon("picture/saving_people_icon.png"));
		saving.add(dailyRecord);
		saving.add(savingPeople);
		saving.setMnemonic(KeyEvent.VK_V);

		//Menu Items for Accounts Menu
		JMenuItem savingAccount = new JMenuItem("Saving Account");
		JMenuItem sellingAccount = new JMenuItem("Selling Account");
		JMenuItem totalAccount = new JMenuItem("Total");
		cash.add(totalAccount);
		cash.add(savingAccount);
		cash.add(sellingAccount);
		cash.setMnemonic(KeyEvent.VK_C);

		frame.setJMenuBar(menuBar);
		//End of creating Menu Bar

		JPanel centerPanel = new JPanel();
		centerPanel.add(new JButton("Center"));
		//centerPanel.setBorder(BorderFactory.createEmptyBorder(-5,-5,-5,-5));

		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(centerPanel,BorderLayout.CENTER);

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

		pos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				PosManager posManager = new PosManager();
				pane.add(posManager);
			}
		});

		saleRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				Sale saleInfo = new Sale();
				pane.add(saleInfo);
			}
		});

		customer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				CustomerInfo customerInfo = new CustomerInfo();
				pane.add(customerInfo);
			}
		});

		reports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProgressBar pb = new ProgressBar();
				pb.setVisible(true);
				SwingUtilities.invokeLater ( new Runnable ()
		        {
		            public void run ()
		            {
						pane.removeAll();
						pane.revalidate();
						pane.repaint();
						Report report = new Report();
						pane.add(report);
						pb.dispose();
		            }
		        } );
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, " Are you sure to exit?", "Exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		});

		purchaseRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				Purchase purchase = new Purchase();
				pane.add(purchase);
			}
		});

		supplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				SupplierInfo supplierInfo = new SupplierInfo();
				pane.add(supplierInfo);
			}
		});

		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				Items items = new Items();
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
        ImageIcon frameIcon = new ImageIcon("picture/frame_icon.png");
        frame.setIconImage(frameIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
