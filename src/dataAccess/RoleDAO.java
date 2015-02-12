package dataAccess;

import entities.Role;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.Role;

public class RoleDAO extends AbstractDataAccess<Role>{
	

	public RoleDAO (final Jpa jpa) throws AssertionError{
		super(jpa);
	}
	
	
	@Override
	public List<Role> get(Range range) {
		return this.getAll();
	}
			
	public List<Role> getAll(){
		return getBy("Role.getAll", Role.class);
	}
	
	@Override
	public void remove(Role entity){
		
		remove(entity);
	}
		
}