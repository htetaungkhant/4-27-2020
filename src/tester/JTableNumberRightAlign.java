package tester;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class JTableNumberRightAlign extends JPanel {
    public JTableNumberRightAlign() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 200));

        JTable table = new JTable(new NumberTableModel());
        table.setFillsViewportHeight(true);

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new JTableNumberRightAlign();
        panel.setOpaque(true);

        JFrame frame = new JFrame("JTable Number Align");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JTableNumberRightAlign.showFrame();
            }
        });
    }

    class NumberTableModel extends AbstractTableModel {
        String[] columns = {"STUDENT ID", "NAME", "YEARS", "SCORE"};
        Object[][] data = {
            {"S001", "ALICE", 10, 8.75},
            {"S002", "BOB", 10, 7.50},
            {"S003", "CAROL", 10, 9.00},
            {"S004", "IGNASIA", 10, 9.00},
            {"S005", "MALLORY", 10, 8.50}
        };

        public int getRowCount() {
            return data.length;
        }

        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        // This method is used by the JTable to define the default
        // renderer or editor for each cell. For example if you have
        // a boolean data it will be rendered as a check box. A
        // number value is right aligned.
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return data[0][columnIndex].getClass();
        }
    }
}