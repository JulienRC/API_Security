package istic.m2cyber.vet.security_api;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import istic.m2cyber.vet.security_api.controllers.OTPController;
import istic.m2cyber.vet.security_api.models.Log;
import istic.m2cyber.vet.security_api.repository.LogRepository;
import istic.m2cyber.vet.security_api.service.LogService;

@SpringBootApplication
@Controller
public class SecurityApiApplication {
	
	@Autowired
	private LogService logService;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApiApplication.class, args);
	}
	
	private String StringInHashWithSalt(String string){
        String output = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            output = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return output;
    }

	@RequestMapping({ "/" })
	public String home(Authentication authentication, Model model) {

		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {

			OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

			model.addAttribute("first_name", user.getAttribute("given_name"));
			model.addAttribute("last_name", user.getAttribute("family_name"));
			model.addAttribute("email", user.getAttribute("email"));
			model.addAttribute("picture", user.getAttribute("picture"));
			model.addAttribute("isConnected", true);
		}
		return "home";
	}

	@RequestMapping({ "/log" })
	public String log(Authentication authentication, Model model) {
		
		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {
			
			OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();

			model.addAttribute("first_name", user.getAttribute("given_name"));
			model.addAttribute("last_name", user.getAttribute("family_name"));
			model.addAttribute("email", user.getAttribute("email"));
			model.addAttribute("picture", user.getAttribute("picture"));
			model.addAttribute("isConnected", true);
			
			// LOG 
			List<Log> listLogDB = logService.findByGoogleid(StringInHashWithSalt(user.getAttribute("sub")));
			List<String> list = new ArrayList<String>();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			for(Log log : listLogDB) {
		        list.add(log.getDate().format(formatter));
			}
			model.addAttribute("logs", list);
		}
		return "log";
	}


}
