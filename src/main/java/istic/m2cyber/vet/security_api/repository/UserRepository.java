package istic.m2cyber.vet.security_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import istic.m2cyber.vet.security_api.models.User;

public interface UserRepository extends JpaRepository<User, Long>  {

}
