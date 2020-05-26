package saving.dialog;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import database.SavingTypeTable;
import external_classes.Fonts;
import external_classes.JNumberTextField;
import external_classes.MyTextField;
import net.miginfocom.swing.MigLayout;

public class AddOrUpdatePerson extends JDialog{
		public AddOrUpdatePerson(Frame parent) {
			super(parent, true);
			ImageIcon frameIcon = new ImageIcon("picture/saving_people_icon.png");
			setIconImage(frameIcon.getImage());
			setTitle("လူအသစ်ထည့်သွင်းခြင်း");
			setSize(400, 400);
			setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			setLayout(new MigLayout("fill"));
			
			JLabel lbName = new JLabel("အမည်");
			lbName.setFont(Fonts.pyisuNormal15);
			MyTextField tfName = new MyTextField(40);
			tfName.setFont(Fonts.pyisuNormal15);
			tfName.setPreferredSize(new Dimension(180, 40));
			JLabel lbSavingType = new JLabel("စုမည့်ပုံစံ");
			lbSavingType.setFont(Fonts.pyisuNormal15);
			String[] savingTypes = SavingTypeTable.getSavingTypes();
			JComboBox< String> jcbSavingType = new JComboBox<String>(savingTypes);
			jcbSavingType.setFont(Fonts.pyisuNormal15);
			jcbSavingType.setPreferredSize(new Dimension(100, 40));
			JLabel lbSavingTime = new JLabel("ကောက်ရမည့်အချိန်");
			lbSavingTime.setFont(Fonts.pyisuNormal15);
			String[] savingTimes = {"မနက်ခင်း", "ညနေခင်း"};
			JComboBox< String> jcbSavingTime = new JComboBox<String>(savingTimes);
			jcbSavingTime.setFont(Fonts.pyisuNormal15);
			jcbSavingTime.setPreferredSize(new Dimension(100, 40));
			JLabel lbAmount = new JLabel("စုမည့်ငွေပမာဏ");
			lbAmount.setFont(Fonts.pyisuNormal15);
			JNumberTextField tfAmount = new JNumberTextField(10);
			tfAmount.setFont(Fonts.pyisuNormal15);
			tfAmount.setPreferredSize(new Dimension(180, 40));
			JLabel lbPhone = new JLabel("ဖုန်းနံပါတ်");
			lbPhone.setFont(Fonts.pyisuNormal15);
			JNumberTextField tfPhone = new JNumberTextField(15);
			tfPhone.setFont(Fonts.pyisuNormal15);
			tfPhone.setPreferredSize(new Dimension(180, 40));
			JLabel lbAddress = new JLabel("လိပ်စာ");
			lbAddress.setFont(Fonts.pyisuNormal15);
			MyTextField tfAddress = new MyTextField(50);
			tfAddress.setFont(Fonts.pyisuNormal15);
			tfAddress.setPreferredSize(new Dimension(180, 40));
			JButton btnCancel = new JButton("မလုပ်ဆောင်ပါ");
			btnCancel.setFont(Fonts.pyisuNormal15);
			btnCancel.setPreferredSize(new Dimension(120, 40));
			JButton btnAdd = new JButton("ထည့်မည်");
			btnAdd.setFont(Fonts.pyisuNormal15);
			btnAdd.setPreferredSize(new Dimension(120, 40));
			
			add(lbName, "gapleft 30");
			add(tfName, "wrap");
			add(lbSavingType, "gapleft 30");
			add(jcbSavingType, "wrap");
			add(lbSavingTime, "gapleft 30");
			add(jcbSavingTime, "wrap");
			add(lbAmount, "gapleft 30");
			add(tfAmount, "wrap");
			add(lbPhone, "gapleft 30");
			add(tfPhone, "wrap");
			add(lbAddress, "gapleft 30");
			add(tfAddress, "wrap");
			add(btnCancel, "align right");
			add(btnAdd, "align center, wrap");
			
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();
				}
			});
			
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = tfName.getText();
					String savingType = (String) jcbSavingType.getSelectedItem();
					String savingTime = (String) jcbSavingTime.getSelectedItem();
					String amount = tfAmount.getText();
					String phone = tfPhone.getText();
					String address = tfAddress.getText();
					if(name.equals("")) {
						JLabel label = new JLabel("ကျေးဇူးပြု၍ အမည်ထည့်ပါ");
						label.setFont(Fonts.pyisuNormal15);
						JOptionPane.showMessageDialog(null, label, "အမည်ထည့်ပါ", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(amount.equals("")) {
						JLabel label = new JLabel("ကျေးဇူးပြု၍ စုမည့်ငွေပမာဏထည့်ပါ");
						label.setFont(Fonts.pyisuNormal15);
						JOptionPane.showMessageDialog(null, label, "စုမည့်ငွေပမာဏထည့်ပါ", JOptionPane.INFORMATION_MESSAGE);						
					}
				}
			});
		}
}
