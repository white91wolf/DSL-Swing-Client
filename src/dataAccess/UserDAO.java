package dataAccess;

import entities.User;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.User;

public class UserDAO extends AbstractDataAccess<User>{
	

	public UserDAO (final Jpa jpa){
		super(jpa);
	}
	
	
	@Override
	public List<User> get(Range range) {
		return this.getAll();
	}
			
	public List<User> getAll(){
		return getBy("User.getAll", User.class);
	}
	
	@Override
	public void remove(User entity){
		
		super.remove(entity);
	}
		
}