package istic.m2cyber.vet.security_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import istic.m2cyber.vet.security_api.repository.UserRepository;

import istic.m2cyber.vet.security_api.models.User;

@Configurable
@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;


    public User findByGoogleid(String googleid) {
		return userRepository.findByGoogleid(googleid);
	}
	
	

}