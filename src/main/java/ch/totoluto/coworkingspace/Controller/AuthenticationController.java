package ch.totoluto.coworkingspace.Controller;

import ch.totoluto.coworkingspace.Entity.Token;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.RequestsResponses.LoginRequest;
import ch.totoluto.coworkingspace.Service.TokenService;
import ch.totoluto.coworkingspace.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final TokenService tokenService;


    @Autowired
    public AuthenticationController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (!userService.checkIfUserExists(user.getEmail())) {
            // Create User
            userService.createUser(user);

            // Generate JWT Token
            String token = tokenService.generateToken(user).getToken();

            // Return token in the response
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user != null) {
            if (userService.checkIfPasswordMatches(user.getPassword(), loginRequest.getPassword())) {
                // Generate JWT Token
                String token = tokenService.generateToken(user).getToken();

                // Return token in the response
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token){
        Token tokenDB = tokenService.getTokenByToken(token);
        if (tokenDB != null) {
            tokenService.deleteToken(tokenDB);
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not found");
        }
    }
}
