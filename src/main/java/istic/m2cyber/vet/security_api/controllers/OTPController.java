package istic.m2cyber.vet.security_api.controllers;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Hash;
import com.authy.api.Token;
import com.authy.api.Tokens;
import com.authy.api.Users;
import com.google.api.services.people.v1.model.PhoneNumber;

import istic.m2cyber.vet.security_api.models.User;
import istic.m2cyber.vet.security_api.service.UserService;
import istic.m2cyber.vet.security_api.utils.Utils;

@Controller
public class OTPController {

	@Autowired
	private UserService userservice;
	
	private Utils utils;
	
	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String client_id;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String client_secret;

	// Your API key from twilio.com/console/authy/applications
	// DANGER! This is insecure. See http://twil.io/secure
	public static final String API_KEY = "gOa7RAKNd0bmxvLUr8468e69ffAz9EYT";
	// In seconds
	public static final int otpValidityTime = 20;

	public static long startTimestamp;

	private final OAuth2AuthorizedClientService authorizedClientService;

	public OTPController(OAuth2AuthorizedClientService authorizedClientService) {
		this.authorizedClientService = authorizedClientService;
		this.utils = new Utils();
	}

	@GetMapping({ "/otp" })
	public String otp(Authentication authentication, Model model) throws AuthyException {

		utils = new Utils();

		String user_id = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
				.get("sub");

		User u = userservice.findByGoogleid(utils.StringInHashWithSalt(user_id));
		System.out.println("AuthyID = " + u.getIdauthy());

		if (null == u.getIdauthy()) {
			return "redirect:/addPhone";
		}

		AuthyApiClient client = new AuthyApiClient(API_KEY);

		Users users = client.getUsers();

		Hash response = users.requestSms(u.getIdauthy());
		System.out.println(u.getIdauthy());

		if (response.isOk()) {
			System.out.println(response.getMessage());
		} else {
			System.out.println(response.getError());
		}

		System.out.println("Generate OTP !");

		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		System.out.println(timeStampSeconds);
		startTimestamp = timeStampSeconds;

		if (authentication == null)
			model.addAttribute("isConnected", false);
		else {
			model.addAttribute("isConnected", true);
		}

		return "otp";
	}

	@PostMapping({ "/otp" })
	public String otpForm(Authentication authentication, @RequestParam String otp) throws AuthyException {

		Instant instant = Instant.now();
		long timeStampSeconds = instant.getEpochSecond();
		System.out.println(timeStampSeconds);

		long diffTimestamp = timeStampSeconds - startTimestamp;

		System.out.println("Post OTP !");
		System.out.println(otp);

		String user_id = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
				.get("sub");

		User u = userservice.findByGoogleid(this.utils.StringInHashWithSalt(user_id));

		AuthyApiClient client = new AuthyApiClient(API_KEY);

		Tokens tokens = client.getTokens();
		Token verify = tokens.verify(u.getIdauthy(), otp);

		if (verify.isOk()) {
			System.out.println(verify.toMap());
			System.out.println("GOOD OTP !");
			return "redirect:/log";
		} else {
			System.out.println(verify.getError());
			System.out.println("BAD OTP !");
			return "redirect:/errorLog";
		}

	}

	@GetMapping({ "/addPhone" })
	public String addPhoneGet(Authentication authentication, Model model) throws IOException {
		System.out.println("GET addPhone !");

		//String user_id = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes().get("sub");

		//User u = userservice.findByGoogleid(this.utils.StringInHashWithSalt(user_id));

		List<PhoneNumber> phoneNumbers = this.utils.getPhoneNumbers(authentication, this.authorizedClientService, this.client_id, this.client_secret);

		if (phoneNumbers == null)
			model.addAttribute("nbPhone", null);
		else
			model.addAttribute("nbPhone", phoneNumbers.get(0).getValue());

		model.addAttribute("isConnected", true);
		// model.addAttribute("nbPhone", u.getTelephone());
		System.out.println(model.getAttribute("nbPhone"));
		return "/addPhone";
	}

	@PostMapping({ "/addPhone" })
	public String addPhone(Authentication authentication, @RequestParam String phone, @RequestParam String countryCode)
			throws AuthyException {
		// Faire attention à la forme de la String passée

		String user_id = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
				.get("sub");

		String email = (String) ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttributes()
				.get("email");

		User u = userservice.findByGoogleid(this.utils.StringInHashWithSalt(user_id));

		System.out.println("Phone added : " + phone);
		System.out.println("Country code : " + countryCode);

		AuthyApiClient client = new AuthyApiClient(API_KEY);
		Users users = client.getUsers();
		com.authy.api.User user = users.createUser(email, phone, countryCode);

		if (user.isOk()) {
			System.out.println(user.getId());
		} else {
			System.out.println(user.getError());
		}

		u.setIdauthy(user.getId());
	
		userservice.save(u);
		return "redirect:/";
	}

	@GetMapping({ "/errorLog" })
	public String errorLogGet() {
		System.out.println("GET errorLog !");

		return "/errorLog";
	}

}
