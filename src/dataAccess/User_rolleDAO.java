package dataAccess;

import entities.User_rolle;
import java.util.List;
import java.util.Date;
import javax.swing.table.AbstractTableModel;

import entities.User_rolle;

public class User_rolleDAO extends AbstractDataAccess<User_rolle>{
	

	public User_rolleDAO (final Jpa jpa){
		super(jpa);
	}
	
	
	@Override
	public List<User_rolle> get(Range range) {
		return this.getAll();
	}
			
	public List<User_rolle> getAll(){
		return getBy("User_rolle.getAll", User_rolle.class);
	}
	
	@Override
	public void remove(User_rolle entity){
		
		remove(entity);
	}
		
}