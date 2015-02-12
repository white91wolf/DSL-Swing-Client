/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import tableFormating.TableDateCellEditor;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Stefan
 */
public class ImprovedJTable extends JTable {

    public ImprovedJTable(TableModel dm) {
        super(dm);
        setDateRenderer();
        setDateEditor();
        this.setRowHeight(35);
    }

    public void setDateRenderer() {
        this.setDefaultRenderer(Date.class, new DefaultTableCellRenderer() {
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                if (value instanceof Date) {
                    value = f.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
            }

        });
    }

    public void setDateEditor() {
        this.setDefaultEditor(Date.class, new TableDateCellEditor());
    }

}
