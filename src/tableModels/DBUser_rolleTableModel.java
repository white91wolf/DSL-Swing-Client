package tableModels;

import entities.User_rolle;
import java.util.List;
import java.util.Date;

import dataAccess.User_rolleDAO;
import java.util.ArrayList;

public class DBUser_rolleTableModel extends DBTableModel {

    private List<User_rolle> entities;
    private List<User_rolle> deleteList;
    private String[] titel = {"datum:DATE"};
    private User_rolleDAO dao;
    

    public DBUser_rolleTableModel(User_rolleDAO dao) {
        _tableName = "User_rolle";
        deleteList = new ArrayList<>();
        entities = new ArrayList<>();
        this.dao = dao;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User_rolle entity = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return entity.getDatum();
            default:
                throw new AssertionError("DataType not found.");
        }
    }

    //set i = 0
    public void editEntity(int row, int col, Object val) {
        switch (col) {
            case 0:
                this.editDatum(row, col, val); break;
            default:
                throw new AssertionError("DataType not found.");
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
        return 1;
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public String getColumnName(int colIndex) {
        return titel[colIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        switch (colIndex) {
            case 0:
                return true;
            default:
                throw new AssertionError("DataType not found.");
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
        this.dao.remove(entities.get(row));
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
        this.entities.add(null);
        inform();
    }

    @Override
    public void saveAll() {
        for (User_rolle e : entities) {
            this.dao.update(e);
        }
        for (User_rolle e : deleteList) {
            this.dao.remove(e);
        }
    }
    
    @Override
    public void discardAll() {
        this.deleteList.clear();
        this.entities.clear();
        this.loadEntities();
    }
}
