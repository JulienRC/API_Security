package istic.m2.cyber.vet.security_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import istic.m2cyber.vet.security_api.repository.UserRepository;


import istic.m2cyber.vet.security_api.models.User;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;


    public Optional<User> findByGoogleid(String google_id) {
		return userRepository.findByGoogleid(google_id);
	}
	

}
