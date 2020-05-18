package sale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.extended.date.WebDateField;

import database.DBConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.swing.JRViewer;

public class Report extends JPanel{
	public Report() {
		setLayout(new BorderLayout());

		//creating Top Panel
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel lbStartDate = new JLabel("Start Date");
				WebDateField datePicker1=new WebDateField();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -1);
				datePicker1.setDate(calendar.getTime());
				datePicker1.setAllowUserInput(false);
				datePicker1.setPreferredSize(100, 40);
				JLabel lbEndDate = new JLabel("End Date");
				WebDateField datePicker2=new WebDateField(new Date());
				datePicker2.setAllowUserInput(false);
				datePicker2.setPreferredSize(100, 40);
				JButton btnGenerate = new JButton("Generate");
				btnGenerate.setPreferredSize(new Dimension(120, 40));
				topPanel.add(lbStartDate);
				topPanel.add(datePicker1);
				topPanel.add(lbEndDate);
				topPanel.add(datePicker2);
				topPanel.add(btnGenerate);

				add(topPanel, BorderLayout.NORTH);
				//End of Top Panel

				//creating Report Panel
				try {
					Connection connection = DBConnection.createConnection();
					String jasperDesign = "";
					JasperReport report = JasperCompileManager.compileReport(jasperDesign);
					JasperPrint printReport = JasperFillManager.fillReport(report, null, connection);
					connection.close();
					JRViewer reportPanel = new JRViewer(printReport);
					add(reportPanel, BorderLayout.CENTER);
				} catch (JRException | SQLException e) {
					e.printStackTrace();
				}
	}
}
