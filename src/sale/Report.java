package sale;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.alee.extended.date.WebDateField;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class Report extends JPanel{

	public Report() {
		setLayout(new BorderLayout());

				//creating Top Panel
				JPanel topPanel = new JPanel(new MigLayout());
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
				topPanel.add(datePicker1, "wrap");
				topPanel.add(lbEndDate);
				topPanel.add(datePicker2, "wrap");
				topPanel.add(btnGenerate, "span 2, align center");

				add(topPanel, BorderLayout.WEST);
				//End of Top Panel

				JScrollPane reportPanel= new JScrollPane(createReport(null));
				add(reportPanel, BorderLayout.CENTER);


				btnGenerate.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						HashMap parameter = new HashMap();
						parameter.put("first_date", new Timestamp(datePicker1.getDate().getTime()));
						parameter.put("second_date", new Timestamp(datePicker2.getDate().getTime()));
						reportPanel.setViewportView(createReport(parameter));
					}
				});
	}

	public JRViewer createReport(HashMap parameter){
		try {
			Connection connection = DBConnection.createConnection();
			JasperDesign reportDesign = JRXmlLoader.load(new File("").getAbsolutePath()+"/report/GPReportBySale.jrxml");
			JasperReport report = JasperCompileManager.compileReport(reportDesign);
			JasperPrint printReport = JasperFillManager.fillReport(report, parameter, connection);
			connection.close();
			return new JRViewer(printReport);
		} catch (JRException | SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
