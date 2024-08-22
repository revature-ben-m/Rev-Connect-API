package com.rev_connect_api.services;
import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import com.rev_connect_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< Updated upstream
import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//

    public User  login( User user) throws Exception{
//        User dbuser= userRepository.findByUsername(user.getUsername());
        User dbuser= userRepository.findByUsernameOrEmail(user.getUsername(),user.getUsername());
        if(dbuser == null || !user.getPassword().equals(dbuser.getPassword())){
         throw new IllegalArgumentException("password invalid");
        }
        ///ddd
        return dbuser ;
      }
=======
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final String SECRET_KEY = "fujbgbgibuaeigbgbeigbiebgiebgrgr";

    public User login(User user) throws Exception {
        User dbUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getUsername());
        if (dbUser == null || !user.getPassword().equals(dbUser.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return dbUser;
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

    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);
        return token;
    }

    public void resetPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setResetToken(null);
        userRepository.save(user);
    }

    public void sendPasswordResetEmail(String userEmail, String resetToken) {
        String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
        String subject = "Password Reset Request";
        String body = "Dear User,\n\n" +
                "Link to reset your password:\n" +
                resetLink + "\n\n" +
                "If you did not request this, please ignore this email.";
        emailService.sendEmail(userEmail, subject, body);
    }

    public String forgotPass(String email) {
        User user = findByEmail(email);
        if (user == null) {
            return "Invalid email id.";
        }

        String resetToken = createPasswordResetToken(user);
        sendPasswordResetEmail(email, resetToken);

        return "Reset link sent to your email.";
    }

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
>>>>>>> Stashed changes
}
