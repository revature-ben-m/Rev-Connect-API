package com.rev_connect_api.services;
import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//
   @Autowired
   private EmailService emailService;

    //private static final String SECRET_KEY = "fujbgbgibuaeigbgbeigbiebgiebgrgr";


    /**
     * Logs in a user by checking their username and password.
     * @param user The user trying to log in with username and password.
     * @return The user if login is successful.
     * @throws Exception If username or password is incorrect.
     */
    public User  login( User user) throws Exception{
//        User dbuser= userRepository.findByUsername(user.getUsername());
        User dbuser= userRepository.findByUsernameOrEmail(user.getUsername(),user.getUsername());
        if(dbuser == null || !user.getPassword().equals(dbuser.getPassword())){
         throw new IllegalArgumentException("password invalid");
        }
        ///ddd
        return dbuser ;
      }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    public void save(User user) {
        userRepository.save(user);
    }


    /**
     * Creates a new password reset token for a user.
     * @param user The user who needs a new reset token.
     * @return The newly created reset token.
     */
    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);
        return token;
    }

    /**
     * Resets a user's password using their reset token.
     * @param user The user whose password will be reset.
     * @param newPassword The new password.
     */
    public void resetPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setResetToken(null);
        userRepository.save(user);
    }

    /**
     * Sends an email to the user with a password reset link.
     * @param userEmail The email address of the user.
     * @param resetToken The token for resetting the password.
     */
    public void sendPasswordResetEmail(String userEmail, String resetToken) {
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        String subject = "Password Reset Request";
        String body = "Dear User,\n\n" +
                "Link to reset your password:\n" +
                resetLink;
        emailService.sendEmail(userEmail, subject, body);
    }

    /**
     * Handles the process when a user forgets their password.
     * @param email The email address of the user.
     * @return A message indicating if the email was sent successfully or not.
     */
    public String forgotPass(String email) {
        User user = findByEmail(email);
        if (user == null) {
            return "Invalid email.";
        }

        String resetToken = createPasswordResetToken(user);
        sendPasswordResetEmail(email, resetToken);

        return "Reset link sent to your email.";
    }

    /**
     * Resets the user's password using a reset token.
     * @param token The reset token.
     * @param newPassword The new password.
     * @return A message indicating if the password was updated successfully or not.
     */
    public String resetPass(String token, String newPassword) {
        try {
            User user = findByResetToken(token);
            if (user == null) {
                return "Invalid or expired token.";
            }
            resetPassword(user, newPassword);
            return "Your password successfully updated.";
        } catch (Exception e) {
            return "Password update unsuccessful.";
        }
    }

}
