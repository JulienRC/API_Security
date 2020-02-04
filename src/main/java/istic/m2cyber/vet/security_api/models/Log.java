package istic.m2cyber.vet.security_api.models;

import java.sql.Timestamp;
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

	private Timestamp date;


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

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	
	
}
