package tableModels;

import entities.Role;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import dataAccess.RoleDAO;
import java.util.ArrayList;

public class DBRoleTableModel extends DBTableModel {

    private List<Role> entities;
    private List<Role> deleteList;
    private String[] titel = {"id:Double", "schwup:DATE", "bezeichnung:VARCHAR", "datum:DATE"};
    private RoleDAO dao;
    private int lastEditable = 0;

    public DBRoleTableModel(RoleDAO dao) {
        _tableName = "Role";
        deleteList  = new ArrayList<>();
        entities = new ArrayList<Role>();
        this.dao = dao;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Role entity = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return entity.getId();
            case 1:
                return entity.getSchwup();
            case 2:
                return entity.getBezeichnung();
            case 3:
                return entity.getDatum();
            default:
                throw new AssertionError("DataType not found.");
        }
    }

    //set i = 0
    public void editEntity(int row, int col, Object val) {
        switch (col) {
            case 0:
                this.editId(row, col, val); break;
            case 1:
                this.editSchwup(row, col, val); break;
            case 2:
                this.editBezeichnung(row, col, val); break;
            case 3:
                this.editDatum(row, col, val); break;
            default:
                throw new AssertionError("DataType not found.");
        }
    }

    private void editId(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setId((Double) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editSchwup(int row, int col, Object val) {
        if (val != null && val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setSchwup((Date) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editBezeichnung(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setBezeichnung((String) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editDatum(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setDatum((Date) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        int i = 0;
        if(entities != null) i = entities.size();
        return i;
    }

    @Override
    public String getColumnName(int colIndex) {
        return titel[colIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        if(rowIsNew(rowIndex)){
            return true;
        }else{
        
            switch (colIndex) {
                case 0:
                    return true;
                case 1:
                    return true;
                case 2:
                    return false;
                case 3:
                    return true;
                default:
                    throw new AssertionError("DataType not found.");
            }
        }
    }

    @Override
    public void setValueAt(Object val, int row, int column) {
        editEntity(row, column, val);
    }

    @Override
    public Class<?> getColumnClass(int colIndex) {
        switch (colIndex) {
            //set i = 0
            case 0:
                return Double.class;
            case 1:
                return Date.class;
            case 2:
                return String.class;
            case 3:
                return Date.class;
            default:
                throw new AssertionError("DataType not found.");
        }
    }

    private void inform() {
        this.fireTableDataChanged();
        //save();
    }

    @Override
    public void loadEntities() {
        entities = this.dao.get(null);
    }

    private void deleteEntity(int row) {
        if(row > 0 && row < entities.size()){
            Role ent = entities.get(row);

            if (ent != null) {
                if(rowIsNew(row)){
                    lastEditable--;
                }else{
                    deleteList.add(ent);
                }                
                entities.remove(ent);
            }
            inform();
        }
    }

    @Override
    public void remove(int row) {
        this.deleteEntity(row);
    }

    @Override
    public void createRow() {
        this.createEntity();
    }
    
    private void createEntity() {
        this.entities.add(new Role());
        makeCellsEditableForLastEntry();
        lastEditable++;
        inform();
    }

    @Override
    public void saveAll() {
        for (Role e : entities) {
            this.dao.update(e);
        }
        for (Role e : deleteList) {
            this.dao.remove(e);
        }
        lastEditable = 0;
    }
    
    @Override
    public void discardAll() {
        this.deleteList.clear();
        this.entities.clear();
        this.loadEntities();
    }

    private boolean rowIsNew(int rowIndex) {
        return ((entities.size()-1) - lastEditable) < rowIndex;
    }
}
