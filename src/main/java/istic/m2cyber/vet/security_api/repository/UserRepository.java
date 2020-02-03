package istic.m2cyber.vet.security_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import istic.m2cyber.vet.security_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
	
		User findByGoogleid(String google_id);	
}
