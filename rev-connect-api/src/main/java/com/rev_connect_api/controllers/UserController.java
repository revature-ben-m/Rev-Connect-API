package com.rev_connect_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rev_connect_api.models.User;
import com.rev_connect_api.services.UserService;

import java.util.*;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Controller class for handling user-related operations.
 * Provides endpoints for user registration, fetching all users, and checking if a username is taken.
 */
@RestController
public class UserController {

	/**
	 * public key and pivate key to encrypt and decrypt password
	 */
	private static String privateKeyRSA = "MIICWwIBAAKBgGnMXDhpZSbbZwl+ZwlvnYcNMDKOloQ7Gs7OuKhiApsETN6vwvA/yFuoFtfldGfj5Q7MuhMYhu+tQSepS5873AR0axOCZ0o5QkbgyK5l5szSlgJ0osvVYKSyk+rZnuny87QwefD0m4Z5jWC6/SrWtVhK1E8OIHPA4CcGIGViK641AgMBAAECgYAabTNz28+qXw9jrbErCDg+7apmVnt+WA0gLz6swJ6J/g79aMW2oRf43Qmnr+bdtG0Yv0aTUoMSByLJN15uTdNs7oGMZpgFQRETe5Cn6KBAn9hznVgDaTPngt/13FC/BDtLTrzMeKmC+GVsmg6MhCLaaxGztOjuXHYPrwhUXJsggQJBALu4+uzXjUn8csw9P74KGB9wX8Wfir+HPpUS5twwC4HHknnUGTsGclrsM0AsbZrpjVQsRdwA+CqLw0dG/7dttuUCQQCQR1WaVxknGLgD1zlXEmNw+tt8QaO2j0sMd2bx+e+JABns0URjglF8bB3hepgY7rBhJtnVUKFBAAi2j4fuftURAkBVTFlgUxWVP8ud+FrFbGrHplUuRC9UlgyeykbyWDZyUw/AodqREn3VyKwFBesL0AHy9GcN77bUBkvGUIGThJvtAkBzW5XmQZ2q1OjMqVj/dYRs4SZ8wz7SyN+IqC93ag9LyvjvkKLNrXTZtthtYEgcJzx/cUDafg1wm0ZcMYbN+jsBAkEAtIBM3dhdXUKqGKzTgHgyog5+s/dvO/4vnVyNNpfVhkk5qVzG5ABf16UlYuU3CFL8HdrWUqyk7rgUxpXud2sNYw==";
	private static String publicKeyRSA = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgGnMXDhpZSbbZwl+ZwlvnYcNMDKOloQ7Gs7OuKhiApsETN6vwvA/yFuoFtfldGfj5Q7MuhMYhu+tQSepS5873AR0axOCZ0o5QkbgyK5l5szSlgJ0osvVYKSyk+rZnuny87QwefD0m4Z5jWC6/SrWtVhK1E8OIHPA4CcGIGViK641AgMBAAE=";

	@Autowired
	UserService userService;

	/**
	 * Endpoint for user registration.
	 *
	 * @param firstName - the first name of the user.
	 * @param lastName - the last name of the user.
	 * @param userName - the username of the user.
	 * @param email - the email address of the user.
	 * @param password - the password for the user.
	 * @param isBusiness - a boolean indicating whether the user is a business account.
	 * @return - a {@link ResponseEntity} containing a {@link Map} with the registration result.
	 * 				Contains "success" key indicating registration success or failure, and "user" key with the
	 * 				registered user object or "message" key with an error message.
	 */
	@PostMapping(value = "/register")
	@CrossOrigin(origins = "*")
	public ResponseEntity<Map<String, Object>> register(
		@RequestParam String firstName,
		@RequestParam String lastName,
		@RequestParam String userName,
		@RequestParam String email,
		@RequestParam String password,
		@RequestParam Boolean isBusiness ){
		
		//Throws Exception while encrypting password
		try {
			//Encrypt pwd before saving for security
			String pwd = encrypt(password);
			
			System.out.println(firstName+" "+lastName+" "+userName+" "+email);
			User newUser = new User(userName,firstName,lastName,email,pwd,isBusiness);
			userService.register(newUser);

			User registeredUser = userService.checkUserId(userName);
			Map<String, Object> response = new HashMap<>();
				if (registeredUser != null) {
					response.put("success", true);
					response.put("user", registeredUser);
					return ResponseEntity.ok(response);
				} else {
					response.put("success", false);
					response.put("message", "User not found!");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
    }


	/**
	 * Endpoint to check if a username is already taken.
	 *
	 * @param userName - the username to check.
	 * @return - boolean value indicating whether the username is taken (true) or available(false).
	 */
	 @PostMapping(value = "/checkUserId")
	 @CrossOrigin(origins = "*")
	public Boolean checkUserId(@RequestParam String userName){
		User registeredUser = userService.checkUserId(userName);
		if(registeredUser != null) return true;
		else	return false;
	}

	/**
	 * To load public key string variable to publicKey paramter to use it during encription
	 * @param key - public key as a string 
	 * @return - public key as a publicKey parameter
	 * @throws Exception
	 */
	private PublicKey loadPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

	/**
	 * To load private key string variable to privateKey paramter to use it during descryption
	 * @param key - private key as a string 
	 * @return - private key as a privateKey parameter
	 * @throws Exception
	 */
	private PrivateKey loadPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

	/**
	 * This function encript the password using public key
	 * @param plaintext - password that needs to be encripted
	 * @return - rreturns encrypted password
	 * @throws Exception
	 */
	public String encrypt(String plaintext) throws Exception {

		Key publicKey = loadPublicKey(publicKeyRSA);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

	/**
	 * This function decrypt the password using private key
	 * @param plaintext - encrypted password that needs to be descrypts
	 * @return - returns descryted password
	 * @throws Exception
	 */
	public String decrypt(String cyphertext) throws Exception {

		Key privateKey = loadPrivateKey(privateKeyRSA);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  // Use the loaded private key here
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cyphertext));
        return new String(decryptedBytes);
    }
}
