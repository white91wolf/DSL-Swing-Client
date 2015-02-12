package app;


import java.util.ArrayList;
import java.util.List;
import dataAccess.Jpa;
import dataAccess.RoleDAO;
import dataAccess.UserDAO;
import dataAccess.User_rolleDAO;
import java.util.Locale;
import javax.persistence.PersistenceException;
import tableModels.DBRoleTableModel;
import tableModels.DBTableModel;
import tableModels.DBUserTableModel;
import tableModels.DBUser_rolleTableModel;


public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);
        Jpa jpa = null;
        boolean jpaError = false;
        List<DBTableModel> setupTables = null;
        
        try{
            jpa = new Jpa("testingPU");
            jpa.open();
        }catch(PersistenceException e){
            jpaError = true;
        }
        try{
            setupTables = setupTables(jpa);
        }catch(AssertionError ae){
            
        }
        Gui gui = new Gui(setupTables);            
        gui.setVisible(true);
        if(jpaError){
            gui.setJpaError(true);
        }
        gui.init();
    }

    private static List<DBTableModel> setupTables(Jpa jpa) throws AssertionError{
        ArrayList<DBTableModel> list = null;    
        list = new ArrayList<DBTableModel>();
        
        list.add(new DBRoleTableModel(new RoleDAO(jpa)));
        list.add(new DBUser_rolleTableModel(new User_rolleDAO(jpa)));
        list.add(new DBUserTableModel(new UserDAO(jpa)));
        
        return list;
        
    }
}
