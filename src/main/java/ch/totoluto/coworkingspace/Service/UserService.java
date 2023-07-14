package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.RoleRepository;
import ch.totoluto.coworkingspace.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepository;
    }

    public User createUser(User user){

        //decrypt password
        String passwordEncoded = user.getPassword();
        passwordEncoded = passwordEncoder.encode(passwordEncoded);
        user.setPassword(passwordEncoded);

        //check if any user exist in repo and give admin role to first user
        if(userRepo.count() == 0) {
            user.setRoleFk(roleRepo.findById(1).get());
        } else {
            user.setRoleFk(roleRepo.findById(2).get());
        }

        return userRepo.save(user);
    }

    public User updateUser(User user){
        User userToUpdate = userRepo.findById(user.getId()).get();
        userToUpdate.setPrename(user.getPrename());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setCompanyFk(user.getCompany());

        //decrypt password
        String passwordEncoded = user.getPassword();
        passwordEncoded = passwordEncoder.encode(passwordEncoded);
        userToUpdate.setPassword(passwordEncoded);

        return userRepo.save(userToUpdate);
    }

    public void deleteUser(int id){
        userRepo.deleteById(id);
    }

    public User getUserById(int id){
        return userRepo.findById(id).get();
    }

    public User getUserByEmail(String email){
        return userRepo.findFirstByEmail(email);
    }

    public Boolean checkIfUserExists(String email){
        if(userRepo.findFirstByEmail(email) != null){
            return true;
        }else{
            return false;
        }
    }

    public boolean isUserAdmin(User user){
        if(user.getRoleFk().getId() == 1){
            return true;
        }else{
            return false;
        }
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public Boolean checkIfPasswordMatches(String userPwd, String password){
        return passwordEncoder.matches(password, userPwd);
    }
}
