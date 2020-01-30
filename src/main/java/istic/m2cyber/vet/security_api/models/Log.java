package istic.m2cyber.vet.security_api.models;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Log")
public class Log {
	
	@Id 
	private Integer Id;
	
	private Date date;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
