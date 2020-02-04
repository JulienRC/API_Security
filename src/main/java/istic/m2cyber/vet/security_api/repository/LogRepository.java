package istic.m2cyber.vet.security_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import istic.m2cyber.vet.security_api.models.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long>{
	
	
	List<Log> findByGoogleid(String googleid);
	
	
	
	
	
	
	
	
	
}