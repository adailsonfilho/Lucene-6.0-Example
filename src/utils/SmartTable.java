package utils;

import javax.swing.event.*;
import javax.swing.table.TableModel;

public class SmartTable implements TableModelListener {
    
    public SmartTable() {
//        table.getModel().addTableModelListener(this);
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);

       // Do something with the data...
    }
    
}