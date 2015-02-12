package entities;
		
import javax.persistence.Table;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import javax.persistence.Column;
import java.util.Date;
			import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
		
@Entity
@NamedQueries({
	@NamedQuery(name= "User_rolle.getAll", query = "SELECT t FROM User_rolle t")
})
@Table(name = "user_rolle")  
public class User_rolle implements Serializable{
	private static final long serialVersionUID = 1L;
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date datum;
	
        @Id
        private Long id;
        
	@ManyToOne
	private Role user_rolle_role;
	
	@ManyToOne
	private User user_rolle_user;
	
	public void setDatum(Date para){
		datum = para;
	}
	public Date getDatum(){
		return datum;
	}
	
	public void setUser_rolle_role(Role para){
		user_rolle_role = para;
	}
	public Role getUser_rolle_role(){
		return user_rolle_role;
	}
	
	public void setUser_rolle_user(User para){
		user_rolle_user = para;
	}
	public User getUser_rolle_user(){
		return user_rolle_user;
	}
	
}
