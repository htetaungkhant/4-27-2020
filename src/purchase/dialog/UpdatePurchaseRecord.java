package purchase.dialog;

import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class UpdatePurchaseRecord extends JDialog{
	public UpdatePurchaseRecord(Frame frame) {
		super(frame, true);
		ImageIcon frameIcon = new ImageIcon("picture/purchase_record_icon.png");
		setIconImage(frameIcon.getImage());
		setTitle("Update Purchase Record");
		setSize(1400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
