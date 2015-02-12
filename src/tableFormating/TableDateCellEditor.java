/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableFormating;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Stefan
 */
public class TableDateCellEditor extends AbstractCellEditor implements TableCellEditor{
private Object cellEditorValue;

    @Override
    public Object getCellEditorValue() {    
        return this.cellEditorValue;
    }

    public void setCellEditorValue(Object value)
    {
        this.cellEditorValue = value;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,  Object value, boolean isSelected, int row, int col) 
    {   

        JPanel c = new JPanel();

        if (value instanceof Date)
        {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            final JTextField textField = new JTextField();

            textField.setText(dateFormat.format(value));

            textField.addCaretListener(new CaretListenerForDate(this, textField));

            c.add(textField);       
        }

        return c;
    }

}
