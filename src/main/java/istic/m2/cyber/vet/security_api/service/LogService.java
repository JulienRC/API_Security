package istic.m2.cyber.vet.security_api.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import istic.m2cyber.vet.security_api.models.Log;
import istic.m2cyber.vet.security_api.repository.LogRepository;

@Configurable
@Service
public class LogService {
	
	@Autowired
	private LogRepository logRepository;
	
	
	public List<Log> findByGoogleid(String googleid){
		return logRepository.findByGoogleid(googleid);
	}
	
}
