package istic.m2cyber.vet.security_api.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleService.People.Get;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

public class Utils {

	public Utils() {
	}

	public String StringInHashWithSalt(String string) {
		String output = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = md.digest(string.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			output = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * This method allows to get phone numbers of one user.
	 * 
	 * @param authentication                Contains informations about user.
	 * @param oauth2AuthorizedClientService Allows to get token.
	 * @return Null if the user don't have phone number, a list otherwise.
	 * @throws IOException
	 */
	public List<PhoneNumber> getPhoneNumbers(Authentication authentication,
			OAuth2AuthorizedClientService oauth2AuthorizedClientService, String client_id, String client_secret) {

		OAuth2AuthenticationToken oauth2AuthToken = ((OAuth2AuthenticationToken) authentication);

		// We get access token in order to used it with PeopleService class.
		OAuth2AccessToken access_token = oauth2AuthorizedClientService
				.loadAuthorizedClient(oauth2AuthToken.getAuthorizedClientRegistrationId(), oauth2AuthToken.getName())
				.getAccessToken();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(new NetHttpTransport())
				.setJsonFactory(new JacksonFactory()).setClientSecrets(client_id, client_secret).build()
				.setAccessToken(access_token.getTokenValue());

		PeopleService peopleService = new PeopleService.Builder(new NetHttpTransport(), new JacksonFactory(),
				credential).build();

		// We get the profile of the user.
		Get get;
		
		try {
			get = peopleService.people().get("people/me");
			
			// We get informations about the user.
			Person person = get.setRequestMaskIncludeField("person.phone_numbers").execute();

			// Return a list of phone number or null.
			return person.getPhoneNumbers();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
