package clientSide;

import javax.swing.table.DefaultTableModel;
/**
 * @author jesika
 * ChatTableModel extends DefaultTableModel, and is used as the table model for the Online Users table. 
 * This class is similar to DefaultTableModel except that cells are not editable.
 * long serialVersionUID: Used for serializing the class ChatTableModel
 * 
 */
public class ChatTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;

	/**
	 * This method constructs the ChatTableModel similar to DefaultTableModel in Java, initializes the table by data and col to the setDataVector method. 
	 * @param data data to be initialized to table 
	 * @param col column names 
	 */
	public ChatTableModel(String[][] data, String[] col) {
		super(data,col);
	}

	/**
	 * This method overrides the DefaultTableModel cells to be non-editable by the user of the GUI. 
	 * @param row row number of the cell
	 * @param column column number of the cell 
	 */
	@Override 
	public boolean isCellEditable(int row, int column) {
		return false; 
	}
}
