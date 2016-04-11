package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.LayoutQueue;

public class MainFrame extends JFrame {
	
	public MainFrame(){
		super();
		
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
		
		JTable tbl_result = new JTable(data,columnNames);		
	
		this.setLayout(new GridLayout(2,1));
		
		JPanel panel = new JPanel(new GridLayout(1,2));
		panel.add(txtf_serch);
		panel.add(btn_serch);
		this.add(panel);
		this.add(tbl_result);		
		
		
	}
	
	public static void main(String[] args) {
		
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
		
	}

}
