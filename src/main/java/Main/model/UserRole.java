package Main.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String role;

	
	public int getId() {
		return id;
	}
	public UserRole() {}
	public UserRole(int id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	public void setId(int  id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "UserRole [id=" + id 
		        + ", role=" + role 
		        + ", description=" + "]";
	}
}