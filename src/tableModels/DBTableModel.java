/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tableModels;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stefan
 */
public abstract class DBTableModel extends AbstractTableModel{
    public String _tableName;
    private boolean lastEditable = false;
    public abstract void remove(int row);
    
    public abstract void createRow();
    
    public abstract void saveAll();
    
    public abstract void loadEntities();
    
    public abstract void discardAll();
    
    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected void makeCellsEditableForLastEntry() {
        lastEditable = true;
    }
    
}
