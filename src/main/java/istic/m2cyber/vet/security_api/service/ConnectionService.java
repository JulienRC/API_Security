package istic.m2cyber.vet.security_api.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import istic.m2cyber.vet.security_api.models.Connection;
import istic.m2cyber.vet.security_api.repository.ConnectionRepository;

@Configurable
@Service
public class ConnectionService {
	
	@Autowired
	private ConnectionRepository logRepository;
	
	
	public List<Connection> findByGoogleid(String googleid){
		return logRepository.findByGoogleid(googleid);
	}
	
	@Transactional
    public Connection save(Connection log) {
      return logRepository.save(log);
    }
	
	
	
	
}