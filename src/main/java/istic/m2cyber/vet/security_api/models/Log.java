package istic.m2cyber.vet.security_api.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Log")
public class Log {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idlog;
	
	private String googleid;

	private LocalDateTime date;
	
	public Log(){	
	}


	public Log(String googleid, LocalDateTime date) {
		super();
		this.googleid = googleid;
		this.date = date;
	}

	public Integer getIdlog() {
		return idlog;
	}

	public void setIdlog(Integer idlog) {
		this.idlog = idlog;
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