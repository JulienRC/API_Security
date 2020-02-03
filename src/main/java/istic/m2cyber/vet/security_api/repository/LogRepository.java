package istic.m2cyber.vet.security_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import istic.m2cyber.vet.security_api.models.User;

@Repository
public interface LogRepository extends JpaRepository<User, Long>{

}
