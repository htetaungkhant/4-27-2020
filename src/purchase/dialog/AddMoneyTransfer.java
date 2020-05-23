package purchase.dialog;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.alee.extended.date.WebDateField;

import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import main.Main;
import purchase.Purchase;

public class AddMoneyTransfer extends JDialog{
		public AddMoneyTransfer(Frame parent) {
			super(parent, true);
			ImageIcon frameIcon = new ImageIcon("picture/money_transfer_icon.png");
			setIconImage(frameIcon.getImage());
			setTitle("ငွေလွဲစာရင်းသွင်းခြင်း");
			setSize(350, 450);
			setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			setLayout(null);
			
			JLabel lbDate = new JLabel("ငွေလွဲရက်စွဲ");
			lbDate.setFont(Fonts.pyisuNormal15);
			lbDate.setHorizontalAlignment(JLabel.RIGHT);
			WebDateField datePicker=new WebDateField(new Date());
			datePicker.setFont(Fonts.pyisuNormal15);
			datePicker.setAllowUserInput(false);
			JButton btnChooseInvoice = new JButton("ငွေလွဲဘောင်ချာရွေးပါ");
			btnChooseInvoice.setFont(Fonts.pyisuNormal15);
			JLabel lbSupplier = new JLabel("ငွေလွဲလက်ခံသူ");
			lbSupplier.setFont(Fonts.pyisuNormal15);
			MyTextField tfSupplier = new MyTextField(40);
			tfSupplier.setEnabled(false);
			tfSupplier.setEditable(false);
			tfSupplier.setFont(Fonts.pyisuNormal15);
			tfSupplier.setHorizontalAlignment(JLabel.RIGHT);
			JLabel lbInvoiceNo = new JLabel("ဘောင်ချာနံပါတ်");
			lbInvoiceNo.setFont(Fonts.pyisuNormal15);
			JNumberTextField tfInvoiceNo = new JNumberTextField(10);
			tfInvoiceNo.setEnabled(false);
			tfInvoiceNo.setEditable(false);
			tfInvoiceNo.setFont(Fonts.pyisuNormal15);
			tfInvoiceNo.setHorizontalAlignment(JLabel.RIGHT);
			JLabel lbRemainingAmount = new JLabel("ကျန်ငွေ");
			lbRemainingAmount.setFont(Fonts.pyisuNormal15);
			JNumberTextField tfRemainingAmount = new JNumberTextField(10);
			tfRemainingAmount.setEnabled(false);
			tfRemainingAmount.setEditable(false);
			tfRemainingAmount.setText("0");
			tfRemainingAmount.setFont(Fonts.pyisuNormal15);
			tfRemainingAmount.setHorizontalAlignment(JLabel.RIGHT);
			JLabel lbPaidAmount = new JLabel("ပေးငွေ");
			lbPaidAmount.setFont(Fonts.pyisuNormal15);
			JNumberTextField tfPaidAmount = new JNumberTextField(10);
			tfPaidAmount.setFont(Fonts.pyisuNormal15);
			tfPaidAmount.setHorizontalAlignment(JLabel.RIGHT);
			JButton btnAdd = new JButton("ထည့်မည်။");
			btnAdd.setFont(Fonts.pyisuNormal16);
			JButton btnCancel = new JButton("ပယ်ဖျက်မည်။");
			btnCancel.setFont(Fonts.pyisuNormal16);
			
			add(lbDate);
			add(datePicker);
			add(btnChooseInvoice);
			add(lbSupplier);
			add(tfSupplier);
			add(lbInvoiceNo);
			add(tfInvoiceNo);
			add(lbRemainingAmount);
			add(tfRemainingAmount);
			add(lbPaidAmount);
			add(tfPaidAmount);
			add(btnAdd);
			add(btnCancel);
			
			lbDate.setBounds(30, 30, 100, 40); datePicker.setBounds(140, 30, 100, 40);
			btnChooseInvoice.setBounds(70, 80, 180, 40);
			lbSupplier.setBounds(30, 130, 100, 40); tfSupplier.setBounds(140, 130, 160, 40);
			lbInvoiceNo.setBounds(30, 180, 100, 40); tfInvoiceNo.setBounds(140, 180, 160, 40);
			lbRemainingAmount.setBounds(30, 230, 100, 40); tfRemainingAmount.setBounds(140, 230, 160, 40);
			lbPaidAmount.setBounds(30, 280, 100, 40); tfPaidAmount.setBounds(140, 280, 160, 40);
			btnAdd.setBounds(40, 350, 120, 40); btnCancel.setBounds(180, 350, 120, 40);
			
			btnChooseInvoice.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog d = new JDialog(Main.frame, "ငွေလွဲဘောင်ချာရွေးပါ",true);
					Purchase invoiceList = new Purchase(d, btnChooseInvoice, tfSupplier, tfInvoiceNo, tfRemainingAmount);
					d.add(invoiceList);
					ImageIcon frameIcon = new ImageIcon("picture/purchase_record_icon.png");
					d.setIconImage(frameIcon.getImage());
					d.setSize(1000, 600);
					d.setLocationRelativeTo(null);
					d.setVisible(true);
				}
			});
			
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
		}
}
