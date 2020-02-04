package istic.m2cyber.vet.security_api.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user")
public class User {
	
	@Id
	private Integer iduser;
	
	private String email;
	
	private String googleid;

	private String pathpicture;

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

}