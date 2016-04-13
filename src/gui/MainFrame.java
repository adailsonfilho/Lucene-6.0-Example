package gui;

import facade.Facade;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.LayoutQueue;

import org.apache.lucene.queryparser.classic.ParseException;

public class MainFrame extends JFrame {

	Facade f;
	JTable tbl_result;
	DefaultTableModel dtm;

	public MainFrame(){
		super();

		f = new Facade();

		//garantir q o processo seja finalizado ao fechar janela
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setBounds(0, 0, 700, 500);

		JTextField txtf_serch = new JTextField();

		JButton btn_serch = new JButton("Search!");
		
		btn_serch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Querying for: "+txtf_serch.getText());
				doQuery(txtf_serch.getText());				
			}
		});


		//		String[] columnNames = {"Índice", "Nome do arquivo"};
		//		String[][] data = {
		//				{"1","TALTAL"},
		//				{"2","TALTAL"},
		//				{"3","TALTAL"},
		//				{"4","TALTAL"}				
		//		};

		tbl_result = new JTable();
		dtm = new DefaultTableModel();

		tbl_result.setModel(dtm);

		GridLayout fullGrid = new GridLayout(2,1);
		this.setLayout(new FlowLayout(2));
		
		txtf_serch.setPreferredSize(new Dimension(300, 20));
		btn_serch.setPreferredSize(new Dimension(100, 20));
		
//		JPanel panel = new JPanel(topGrid);
		JPanel panel = new JPanel();
		panel.add(txtf_serch);
		panel.add(btn_serch);
		
		this.add(panel);
		
		JScrollPane scrollPanel = new JScrollPane();
		
		scrollPanel.setViewportView(tbl_result);
		this.add(scrollPanel);
	}

	public static void main(String[] args) {

		MainFrame mf = new MainFrame();
		mf.setVisible(true);

	}

	public void doQuery(String str){
		
		try {
			String[][] data = f.query(str);

			String[] columnNames = {"Índice", "Nome do arquivo"};
			
			DefaultTableModel dtm2 = new DefaultTableModel();
			dtm2.setColumnIdentifiers(columnNames);
			DocsTableModel dtm = new DocsTableModel(data, columnNames);
			
			tbl_result.setModel(dtm);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();			
		}
	}

	// public void doQuery(String str){

	// 	try {
	// 		String[][] data = f.query(str);

	// 		String[] columnNames = {"Índice", "Nome do arquivo"};

	// 		dtm.setColumnIdentifiers(columnNames);

	// 		for (int i = 0; i < data.length; i++) {
	// 			dtm.addRow(data[i]);
	// 		}

	// 		//			DocsTableModel dtm = new DocsTableModel(data, columnNames);

	// 		dtm.fireTableDataChanged();


	// 	} catch (ParseException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	} catch(IOException e){
	// 		e.printStackTrace();			
	// 	}
	// }

	//	TableModel model = new YourTableModel(...);
	//	table.setModel( model );

	@SuppressWarnings("serial")
	class DocsTableModel extends AbstractTableModel {
		private String[] columnNames;
		private String[][] data;

		public DocsTableModel(String[][] data, String[] columnNames){
			super();
			this.data = data;
			this.columnNames = columnNames;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's
		 * editable.
		 */
		public boolean isCellEditable(int row, int col) {
			//Note that the data/cell address is constant,
			//no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's
		 * data can change.
		 */
		public void setValueAt(String value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}

}

