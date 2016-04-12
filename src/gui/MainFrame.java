package gui;

import facade.Facade;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.LayoutQueue;

import org.apache.lucene.queryparser.classic.ParseException;

public class MainFrame extends JFrame {
	
	Facade f;
	JTable tbl_result;
	JPanel panel;
	
	public MainFrame(){
		super();
		
		f = new Facade();
		
		//garantir q o processo seja finalizado ao fechar janela
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setBounds(0, 0, 500, 300);
		
		JButton btn_serch = new JButton("Search!");
		
		JTextField txtf_serch = new JTextField();
		
		String[] columnNames = {"Índice", "Nome do arquivo"};
		
		String[][] data = {
				{"1","TALTAL"},
				{"2","TALTAL"},
				{"3","TALTAL"},
				{"4","TALTAL"}				
		};
		
		tbl_result = new JTable(data,columnNames);		
	
		this.setLayout(new GridLayout(2,1));
		
		panel = new JPanel(new GridLayout(1,2));
		panel.add(txtf_serch);
		panel.add(btn_serch);
		this.add(panel);
		this.add(tbl_result);		
	}
	
	public static void main(String[] args) {
		
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		
	}
	
	public void doQuery(String str){
		
		try {
			String[][] data = f.query(str);

			String[] columnNames = {"Índice", "Nome do arquivo"};
			
			tbl_result = new JTable(data,columnNames);
			tbl_result.fireTableDataChanged()
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();			
		}
	}

}
