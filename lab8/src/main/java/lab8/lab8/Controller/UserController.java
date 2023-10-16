package lab8.lab8.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab8.lab8.model.Role;
import lab8.lab8.model.User;
import lab8.lab8.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String email, @RequestParam String password,
            @RequestParam Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("email already exists");
        }
        User user = new User(email, passwordEncoder.encode(password), role);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
