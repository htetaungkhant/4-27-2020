package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import customer.CustomerInfo;
import external_classes.Fonts;
import net.miginfocom.swing.MigLayout;
import purchase.MoneyTransfer;
import purchase.Purchase;
import sale.PosManager;
import sale.Report;
import sale.Sale;
import saving.SavingPeople;
import stock.Items;
import supplier.SupplierInfo;

public class Main{

	public static JFrame frame;

	private static void addComponentsToPane(JFrame frame){

		//creating Menu Bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

		JMenu sale = new JMenu("အရောင်း");
		sale.setIcon(new ImageIcon("picture/sale_icon.png"));
		sale.setFont(Fonts.pyisuNormal16);
		JMenu purchase = new JMenu("အဝယ်");
		purchase.setIcon(new ImageIcon("picture/purchase_icon.png"));
		purchase.setFont(Fonts.pyisuNormal16);
		JMenu stock = new JMenu("ကုန်ပစ္စည်း");
		stock.setIcon(new ImageIcon("picture/stock_icon.png"));
		stock.setFont(Fonts.pyisuNormal16);
		JMenu cash = new JMenu("Cash");
		cash.setIcon(new ImageIcon("picture/account_icon.png"));
		JMenu saving = new JMenu("စုကြေး");
		saving.setIcon(new ImageIcon("picture/saving_icon.png"));
		saving.setFont(Fonts.pyisuNormal16);
		JMenu logout = new JMenu("ထွက်ရန်");
		logout.setIcon(new ImageIcon("picture/logout_icon.png"));
		logout.setFont(Fonts.pyisuNormal16);

		menuBar.add(sale);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(purchase);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(stock);
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
//		menuBar.add(saving);
//		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
//		menuBar.add(cash);
//		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(new JSeparator(SwingConstants.VERTICAL));
		menuBar.add(logout);

		//Menu Items for Sale Menu
		JMenuItem pos = new JMenuItem("နေ့စဉ်အရောင်း", new ImageIcon("picture/pos_icon.png"));
		pos.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokePos = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		pos.setAccelerator(keyStrokePos);
		JMenuItem saleRecord = new JMenuItem("အရောင်းမှတ်တမ်း", new ImageIcon("picture/sale_record_icon.png"));
		saleRecord.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokeForSaleRecord = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		saleRecord.setAccelerator(keyStrokeForSaleRecord);
		JMenuItem customer = new JMenuItem("ဝယ်သူများ", new ImageIcon("picture/customer_icon.png"));
		customer.setFont(Fonts.pyisuNormal15);
		JMenuItem reports = new JMenuItem("စာရင်းချုပ်", new ImageIcon("picture/reports_icon.png"));
		reports.setFont(Fonts.pyisuNormal15);
		JMenuItem exit = new JMenuItem("ပိတ်ရန်", new ImageIcon("picture/exit_icon.png"));
		exit.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokeForExit = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
		exit.setAccelerator(keyStrokeForExit);
		sale.add(pos);
		sale.add(saleRecord);
		sale.add(customer);
		sale.add(reports);
		sale.add(exit);

		//Menu Items for Purchase Menu
		JMenuItem purchaseRecord = new JMenuItem("အဝယ်မှတ်တမ်း", new ImageIcon("picture/purchase_record_icon.png"));
		purchaseRecord.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokeToPurchaseRecord = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);
		purchaseRecord.setAccelerator(keyStrokeToPurchaseRecord);
		JMenuItem transferMoney = new JMenuItem("ငွေလွဲစာရင်း", new ImageIcon("picture/money_transfer_icon.png"));
		transferMoney.setFont(Fonts.pyisuNormal15);
		JMenuItem supplier = new JMenuItem("ကုန်ပေးသူ / ဆိုင်များ", new ImageIcon("picture/supplier_icon.png"));
		supplier.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokeToSupplier = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
		supplier.setAccelerator(keyStrokeToSupplier);
		purchase.add(purchaseRecord);
		purchase.add(transferMoney);
		purchase.add(supplier);

		//Menu Items for Stock Menu
		JMenuItem item = new JMenuItem("ကုန်ပစ္စည်းစာရင်း", new ImageIcon("picture/items_icon.png"));
		item.setFont(Fonts.pyisuNormal15);
		KeyStroke keyStrokeToItem = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);
		item.setAccelerator(keyStrokeToItem);
		stock.add(item);

		//Menu Items for Saving Menu
		JMenuItem jmIDailySavingRecord = new JMenuItem("နေ့စဉ်စုကြေးမှတ်တမ်း", new ImageIcon("picture/add_daily_records_icon.png"));
		jmIDailySavingRecord.setFont(Fonts.pyisuNormal15);
		JMenuItem jmiSavingPeople = new JMenuItem("စုကြေးပါဝင်သူများ", new ImageIcon("picture/saving_people_icon.png"));
		jmiSavingPeople.setFont(Fonts.pyisuNormal15);
		saving.add(jmIDailySavingRecord);
		saving.add(jmiSavingPeople);

		//Menu Items for Accounts Menu
		JMenuItem savingAccount = new JMenuItem("Saving Account");
		JMenuItem sellingAccount = new JMenuItem("Selling Account");
		JMenuItem totalAccount = new JMenuItem("Total");
		cash.add(totalAccount);
		cash.add(savingAccount);
		cash.add(sellingAccount);

		frame.setJMenuBar(menuBar);
		//End of creating Menu Bar

		//creating Center Panel
		JPanel centerPanel = new JPanel(new MigLayout("fill"));
		centerPanel.setBackground(new Color(191, 201, 202));
		JLabel lbFistWord = new JLabel("ဗုဒ္ဓံ");
		lbFistWord.setFont(Fonts.pyisuBold100);
		JLabel lbSecondWord = new JLabel("ဓမ္မံ");
		lbSecondWord.setFont(Fonts.pyisuBold100);
		JLabel lbThirdWord = new JLabel("သံဃံ");
		lbThirdWord.setFont(Fonts.pyisuBold100);
		JLabel lbTitle = new JLabel("\"လင်းလင်း\"");
		lbTitle.setFont(Fonts.pyisuBold100);
		JLabel lbWelcome = new JLabel("စတိုးဆိုင်မှ ကြိုဆိုပါ၏");
		lbWelcome.setFont(Fonts.pyisuBold65);
		JButton btnStart = new JButton("လုပ်ငန်းစလုပ်မယ်");
		btnStart.setFont(Fonts.pyisuBold20);
		btnStart.setOpaque(true);
		btnStart.setBackground(Color.orange);
		btnStart.setPreferredSize(new Dimension(200, 50));
		centerPanel.add(lbFistWord);
		centerPanel.add(lbSecondWord, "align center");
		centerPanel.add(lbThirdWord, "align right, wrap");
		centerPanel.add(lbTitle, "split 2, span 2, align right");
		centerPanel.add(lbWelcome, "wrap");
		centerPanel.add(btnStart, "align center, span 3");
		//centerPanel.setBorder(BorderFactory.createEmptyBorder(-5,-5,-5,-5));
		//End of Center Panel

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
		
		btnStart.addActionListener(new ActionListener() {
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
//				SwingUtilities.invokeLater ( new Runnable ()
//		        {
//		            public void run ()
//		            {
						pane.removeAll();
						pane.revalidate();
						pane.repaint();
						Report report = new Report();
						pane.add(report);
//		            }
//		        } );
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JLabel label = new JLabel("ပိတ်မှာသေချာပီလား");
				label.setFont(Fonts.pyisuNormal15);
				int result = JOptionPane.showConfirmDialog(null, label, "ပိတ်မှာလား?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
				Purchase purchase = new Purchase(false);
				pane.add(purchase);
			}
		});
		
		transferMoney.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				MoneyTransfer moneyTransfer = new MoneyTransfer();
				pane.add(moneyTransfer);
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
		
		jmiSavingPeople.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.removeAll();
				pane.revalidate();
				pane.repaint();
				SavingPeople savingPeople = new SavingPeople();
				pane.add(savingPeople);
			}
		});
	}

	public static void createAndShowGUI() {
        frame = new JFrame("\"လင်းလင်း\" စတိုးဆိုင်");
        ImageIcon frameIcon = new ImageIcon("picture/frame_icon.png");
        frame.setIconImage(frameIcon.getImage());
		Toolkit tk=Toolkit.getDefaultToolkit();
		int x=(int)tk.getScreenSize().getWidth();
		int y=(int)tk.getScreenSize().getHeight();
		frame.setSize(x-100,y-100);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        addComponentsToPane(frame);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
