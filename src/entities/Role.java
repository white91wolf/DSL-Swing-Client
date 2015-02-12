package entities;
		
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@NamedQuery(name= "Role.getAll", query = "SELECT t FROM Role t")
})
@Table(name = "role")  
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Double id;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date schwup;
	
	@Column(unique = true)
	private String bezeichnung;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date datum;
	
	@OneToMany (mappedBy = "user_rolle_role")
	private List<User_rolle> role_user_rolle;
	
        public Role(){
            this.datum = new Date();
            this.schwup = new Date();
        }
        
	public void setId(Double para){
		id = para;
	}
	public Double getId(){
		return id;
	}
	
	public void setSchwup(Date para){
		schwup = para;
	}
	public Date getSchwup(){
		return schwup;
	}
	
	public void setBezeichnung(String para){
		bezeichnung = para;
	}
	public String getBezeichnung(){
		return bezeichnung;
	}
	
	public void setDatum(Date para){
		datum = para;
	}
	public Date getDatum(){
		return datum;
	}
	
	public void setRole_user_rolle(List<User_rolle> para){
		role_user_rolle = para;
	}
	public List<User_rolle> getRole_user_rolle(){
		return role_user_rolle;
	}
	
}
