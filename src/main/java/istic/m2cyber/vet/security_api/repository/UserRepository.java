package istic.m2cyber.vet.security_api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import istic.m2cyber.vet.security_api.models.User;

public interface UserRepository extends JpaRepository<User, Long>  {

		User findByGoogleid(String google_id);	
}
