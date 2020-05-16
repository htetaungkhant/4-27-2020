package tester;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class MyTable extends JTable {

	private DefaultTableModel defaultTableModel;

	private Object[][] dataArray;
	private Object[] columnNameArray;

	private final int WIDTH_C0 = 40;
	private final int WIDTH_C1 = 120;
	private final int WIDTH_C2 = 40;
	private final int WIDTH_C3 = 20;
	private final int WIDTH_SPINNER_COLUMN = 100;
	private final int WIDTH_C5 = 80;
	private final int WIDTH_C6 = 20;

	public final int SPINNER_COLUMN = 4;

	// column headers
	public static final String SPINNER_COLUMN_HEADER = "Spinner";

	// spinner values
	final int MAX_SPIN_VALUE = 99;
	final String[] spinValues;

	/**
	 * constructor
	 *
	 * @param DefaultTableModel
	 */
	public MyTable(DefaultTableModel defaultTableModel) {

		this.setModel(defaultTableModel)	;

		spinValues = new String[MAX_SPIN_VALUE];
		for (int i = 0; i < MAX_SPIN_VALUE; i++) {
			spinValues[i] = new String(Integer.toString(i));
		}
	}

	/**
	 * constructor
	 *
	 * @param variableNameArray
	 * @param columnNameArray
	 */
	public MyTable(Object[][] variableNameArray, Object[] columnNameArray) {

		this.dataArray = variableNameArray;
		this.columnNameArray = columnNameArray;

		defaultTableModel = new DefaultTableModel(variableNameArray, columnNameArray);
		this.setModel(defaultTableModel)	;

		// fixed column widths
		this.setAutoResizeMode( JTable.AUTO_RESIZE_OFF ) ;

        this.getColumnModel().getColumn(0).setPreferredWidth(WIDTH_C0) ;
        this.getColumnModel().getColumn(1).setPreferredWidth(WIDTH_C1) ;
        this.getColumnModel().getColumn(2).setPreferredWidth(WIDTH_C2) ;
        this.getColumnModel().getColumn(3).setPreferredWidth(WIDTH_C3) ;
        this.getColumnModel().getColumn(SPINNER_COLUMN).setPreferredWidth(WIDTH_SPINNER_COLUMN) ;
        this.getColumnModel().getColumn(5).setPreferredWidth(WIDTH_C5) ;
        this.getColumnModel().getColumn(6).setPreferredWidth(WIDTH_C6) ;

        // spinner values
		spinValues = new String[MAX_SPIN_VALUE];
		for (int i = 0; i < MAX_SPIN_VALUE; i++) {
			spinValues[i] = new String(Integer.toString(i));
		}
	}

	/**
	 * mark certain columns as custom cell editors
	 */
	public TableCellEditor getCellEditor()
    {
        int column = this.getEditingColumn();
        //int row=this.getEditingRow();
        if (column == SPINNER_COLUMN) {
            return new SpinnerEditor(spinValues);
        }

        return super.getCellEditor();

    }

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e){
			e.printStackTrace();
		}

	    String[] columnNameArray = {"0",
	    		"1",
	    		"2",
	    		"3" ,
	    		SPINNER_COLUMN_HEADER,
	    		"5",
	    		"6"};

//		Object[][]  dataArray = { 	};
//		Object[][]  dataArray = {
//				{ "", "", "", "", "", "", ""},
//				{ "", "", "", "", "", "", ""},
//				{ "", "", "", "", "", "", ""},
//				{ "", "", "", "", "", "", ""},
//		};
		Object[][]  dataArray = {
		{},
		{},
		{},
		{},
};
		final MyTable panel = new MyTable(dataArray, columnNameArray);

		final JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(panel));
		frame.setTitle("My Table");
		//frame.setPreferredSize(new Dimension(500, 500));
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(300, 200);
		frame.pack();
		frame.setVisible(true);

	}

}