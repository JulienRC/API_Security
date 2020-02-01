package istic.m2cyber.vet.security_api.models;

import java.sql.Date;
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
	private Integer Id;
	
	private String google_id;

	private Date date;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	

	public String getGoogle_id() {
		return google_id;
	}

	public void setGoogle_id(String google_id) {
		this.google_id = google_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
