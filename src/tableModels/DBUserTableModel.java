package tableModels;

import entities.User;
import java.util.List;
import java.util.Date;

import dataAccess.UserDAO;
import java.util.ArrayList;

public class DBUserTableModel extends DBTableModel {

    private List<User> entities;
    private String[] titel = {"id:ID", "Name:VARCHAR", "visible:BOOLEAN", "test:DATE"};
    private UserDAO dao;
    private ArrayList<User> deleteList;
    private boolean lastEditable = false;

    public DBUserTableModel(UserDAO dao) {
        _tableName = "User";
        deleteList = new ArrayList<User>();
        entities = new ArrayList<User>();
        this.dao = dao;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User entity = entities.get(rowIndex);
        if (entity != null) {
            switch (columnIndex) {
                case 0:
                    return entity.getId();
                case 1:
                    return entity.getName();
                case 2:
                    return entity.getVisible();
                case 3:
                    return entity.getTest();
                default:
                    throw new AssertionError("DataType not found.");
            }
        }
        return null;

    }

    //set i = 0
    public void editEntity(int row, int col, Object val) {
        switch (col) {
            case 0:
                this.editId(row, col, val);
                break;
            case 1:
                this.editName(row, col, val);
                break;
            case 2:
                this.editVisible(row, col, val);
                break;
            case 3:
                this.editTest(row, col, val);
                break;
            default:
                throw new AssertionError("DataType not found.");
        }
    }

    private void editId(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setId((Long) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editName(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setName((String) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editVisible(int row, int col, Object val) {
        if (val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setVisible((Boolean) val);
            inform();
        } else {
            //TODO FEHLERAUSGABE
        }
    }

    private void editTest(int row, int col, Object val) {
        if (val != null && val.getClass() == this.getColumnClass(col)) {
            entities.get(row).setTest((Date) val);
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
        if(lastEditable && rowIndex == entities.size()-1){
            return true;
        }
        
        switch (colIndex) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            default:
                throw new AssertionError("DataType not found.");
        }
    }
    
    public boolean isColumnEditable(int colIndex){
        return isCellEditable(0, colIndex);
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
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
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

    public void reloadEntities() {
        loadEntities();
    }

    private void deleteEntity(int row) {
        if(row > 0 && row < entities.size()){
            User ent = entities.get(row);

            if (ent != null) {
                deleteList.add(ent);
                entities.remove(ent);
            }
            inform();
        }
    }

    @Override
    public void remove(int row) {
        deleteEntity(row);
    }

    @Override
    public void createRow() {
        createEntity();
    }

    private void createEntity() {
        this.entities.add(new User());
        makeCellsEditableForLastEntry();
        inform();
    }

    @Override
    public void saveAll() {
        for (User u : entities) {
            this.dao.update(u);
        }
        for (User u : deleteList) {
            this.dao.remove(u);
        }
        lastEditable = false;
    }

    @Override
    public void discardAll() {
        this.dao.clear();
        this.deleteList.clear();
        this.entities.clear();
        this.loadEntities();
        inform();
    }

}
