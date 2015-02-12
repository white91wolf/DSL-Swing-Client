package entities;
		
import java.util.List;
import javax.persistence.Table;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import javax.persistence.Column;
import java.util.Date;
			import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import javax.persistence.Id;
		
@Entity
@NamedQueries({
	@NamedQuery(name= "User.getAll", query = "SELECT t FROM User t")
})
@Table(name = "usertbl")  
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	
	private String name;
	
	private Boolean visible;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date test;
	
	@OneToMany (mappedBy = "user_rolle_user")
	private List<User_rolle> user_user_rolle;
	
        
        public User(){
            this.test = new Date();
        }
        
	public void setId(Long para){
		id = para;
	}
	public Long getId(){
		return id;
	}
	
	public void setName(String para){
		name = para;
	}
	public String getName(){
		return name;
	}
	
	public void setVisible(Boolean para){
		visible = para;
	}
	public Boolean getVisible(){
		return visible;
	}
	
	public void setTest(Date para){
		test = para;
	}
	public Date getTest(){
		return test;
	}
	
	public void setUser_user_rolle(List<User_rolle> para){
		user_user_rolle = para;
	}
	public List<User_rolle> getUser_user_rolle(){
		return user_user_rolle;
	}
	
}
