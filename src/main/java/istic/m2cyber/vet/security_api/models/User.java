package istic.m2cyber.vet.security_api.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer iduser;
	
	private String email;
	
	private String googleid;

	private String pathpicture;
	
	private Integer idauthy;

	public User() {
	}
	
	public User(String email, String googleid, String pathpicture) {
		super();
		this.email = email;
		this.googleid = googleid;
		this.pathpicture = pathpicture;
	}
	
	public Integer getIduser() {
		return iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGoogleid() {
		return googleid;
	}

	public void setGoogleid(String googleid) {
		this.googleid = googleid;
	}

	public String getPathpicture() {
		return pathpicture;
	}

	public void setPathpicture(String pathpicture) {
		this.pathpicture = pathpicture;
	}

	public Integer getIdauthy() {
		return idauthy;
	}

	public void setIdauthy(Integer idauthy) {
		this.idauthy = idauthy;
	}
	

}