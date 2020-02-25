package istic.m2cyber.vet.security_api.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "connection")
public class Connection {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idconnection;
	
	private String googleid;

	private LocalDateTime date;
	
	public Connection(){	
	}


	public Connection(String googleid, LocalDateTime date) {
		super();
		this.googleid = googleid;
		this.date = date;
	}
	

	public Integer getIdlog() {
		return idconnection;
	}

	public void setIdlog(Integer idlog) {
		this.idconnection = idlog;
	}

	public String getGoogleid() {
		return googleid;
	}

	public void setGoogleid(String googleid) {
		this.googleid = googleid;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	
	
}