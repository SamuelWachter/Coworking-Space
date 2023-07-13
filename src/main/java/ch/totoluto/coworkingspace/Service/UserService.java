package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.RoleRepository;
import ch.totoluto.coworkingspace.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepo;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
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
        return userRepo.findByEmail(email);
    }
}
