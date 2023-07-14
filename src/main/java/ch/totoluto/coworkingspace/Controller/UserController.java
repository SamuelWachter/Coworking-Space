package ch.totoluto.coworkingspace.Controller;

import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.UserRepository;
import ch.totoluto.coworkingspace.Service.TokenService;
import ch.totoluto.coworkingspace.Service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String token) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                List<User> users = userService.getAllUsers();
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(403).build();
            }
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                User userById = userService.getUserById(id);
                return ResponseEntity.ok(userById);
            } else if(user.getId() == id) {
                //Allow User to access own data
                User userById = userService.getUserById(id);
                userById.setPassword(null);
                return ResponseEntity.ok(userById);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        User userByToken = userService.getUserById(tokenService.getUserIdByToken(token));
        if(userService.isUserAdmin(userByToken)) {
            //Allow Admin to create users
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        User userByToken = userService.getUserById(tokenService.getUserIdByToken(token));
        if (tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(userByToken)) {
                //Allow Admin to update all users
                User updatedUser = userService.updateUser(user);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(403).build();
            }
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable int id) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if (tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to delete users
                userService.deleteUser(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        }else {
            return ResponseEntity.status(401).build();
        }
    }
}
