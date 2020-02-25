package istic.m2cyber.vet.security_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import istic.m2cyber.vet.security_api.models.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>{
	
	
	List<Connection> findByGoogleid(String googleid);
	
	
	Connection save(Connection connection);
	
	
	
	
	
	
}