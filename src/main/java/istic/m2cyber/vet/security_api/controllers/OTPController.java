package istic.m2cyber.vet.security_api.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Hash;
import com.authy.api.Tokens;
import com.authy.api.Users;

import istic.m2cyber.vet.security_api.models.User;
import istic.m2cyber.vet.security_api.service.UserService;

@Controller
public class OTPController {
	
	@Autowired
	private UserService userservice;

	// Your API key from twilio.com/console/authy/applications
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String API_KEY = "gOa7RAKNd0bmxvLUr8468e69ffAz9EYT";
    public static final String OTP = "123";
    // In seconds
    public static final int otpValidityTime = 20;
    
    public static long startTimestamp;
    
    @GetMapping({"/otp"})
    public String otp(Authentication authentication) throws AuthyException {
    	
    	String user_id = (String)
    			 ((OAuth2AuthenticationToken)authentication).getPrincipal().getAttributes().get("sub");
    	
    	AuthenticationController authCont = new AuthenticationController();
    	User u = userservice.findByGoogleid(authCont.StringInHashWithSalt(user_id));
    	System.out.println("AuthyID = " + u.getIdauthy());
    	
    	if(null == u.getIdauthy()) {
    		RedirectView view = new RedirectView("http://127.0.0.1:8080/addPhone");
    		return "addPhone";
    	}
    	
    	AuthyApiClient client = new AuthyApiClient(API_KEY);
    	
        Users users = client.getUsers();
        
        /*Hash response = users.requestSms(u.getIdauthy());
        
        if (response.isOk()) {
            System.out.println(response.getMessage());
        } else {
            System.out.println(response.getError());
        }*/
        
        System.out.println("Generate OTP !");
    	
    	Instant instant = Instant.now();
    	long timeStampSeconds = instant.getEpochSecond();
    	System.out.println(timeStampSeconds);
    	startTimestamp = timeStampSeconds;
    	
    	RedirectView view = new RedirectView("http://127.0.0.1:8080/otp");
		return "otp";
    }
    
    @PostMapping({"/otp"})
    public RedirectView otpForm(@RequestParam String otp) throws AuthyException {
    	
    	Instant instant = Instant.now();
    	long timeStampSeconds = instant.getEpochSecond();
    	System.out.println(timeStampSeconds);
    	
    	long diffTimestamp = timeStampSeconds - startTimestamp;
    	
    	System.out.println("Post OTP !");
    	System.out.println(otp);
    	
    	AuthyApiClient client = new AuthyApiClient(API_KEY);
    	
    	/*if(diffTimestamp < otpValidityTime) {
    		if(OTP.equals(otp)) {
    			System.out.println("GOOD OTP !");
        	} else {
                System.out.println("BAD OTP !");
            }	
		}else {
			System.out.println("OTP expired !");
		}*/
    	
    	Tokens tokens = client.getTokens();
        /*Token verify = tokens.verify(227867601, otp);
        
        if (verify.isOk()) {
            System.out.println(verify.toMap());
            System.out.println("GOOD OTP !");
        } else {
            System.out.println(verify.getError());
            System.out.println("BAD OTP !");
        }*/
    	
    	RedirectView view;

    	if("123".equals(otp)) {
    		System.out.println("GOOD OTP !");
    		view = new RedirectView("http://127.0.0.1:8080/log");
    		return view;
    	}else {
    		System.out.println("BAD OTP !");
    		view = new RedirectView("http://127.0.0.1:8080/errorLog");
    		return view;
    	} 	
    }
    
    @GetMapping({"/addPhone"})
    public String addPhoneGet() {
    	System.out.println("GET addPhone !");
    	return "/addPhone";
    }
    
    @PostMapping({"/addPhone"})
    public RedirectView addPhone(@RequestParam String phone, @RequestParam String country) {
    	// Faire attention à la forme de la String passée
    	
    	System.out.println("Phone added : " + phone);
    	System.out.println("Country code : " + country);
    	
    	AuthyApiClient client = new AuthyApiClient(API_KEY);
    	/*Users users = client.getUsers();
    	com.authy.api.User user = users.createUser("julien.royon-chalendard@etudiant.univ-rennes1.fr", "62-414-3661", "33");
    	
        if (user.isOk()) {
            System.out.println(user.getId());
        } else {
            System.out.println(user.getError());
        }*/
    	
    	RedirectView view = new RedirectView("http://127.0.0.1:8080/");
		return view;
    }
    
    @GetMapping({"/errorLog"})
    public String errorLogGet() {
    	System.out.println("GET errorLog !");
    	return "/errorLog";
    }
    
}
