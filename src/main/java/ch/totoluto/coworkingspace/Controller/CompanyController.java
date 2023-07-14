package ch.totoluto.coworkingspace.Controller;

import ch.totoluto.coworkingspace.Entity.Company;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Service.CompanyService;
import ch.totoluto.coworkingspace.Service.TokenService;
import ch.totoluto.coworkingspace.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    private final TokenService tokenService;

    public CompanyController(CompanyService companyService, UserService userService, TokenService tokenService) {
        this.companyService = companyService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Company>> getAllCompanies(@RequestHeader("Authorization") String token) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                List<Company> companies = companyService.getAllCompanies();
                return ResponseEntity.ok(companies);
            } else {
                return ResponseEntity.status(403).build();
            }
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@RequestHeader("Authorization") String token, @PathVariable int id) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                Company companyById = companyService.getCompanyById(id);
                return ResponseEntity.ok(companyById);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Company> createCompany(@RequestHeader("Authorization") String token, @RequestBody Company company) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                Company createdCompany = companyService.createCompany(company);
                return ResponseEntity.ok(createdCompany);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Company> updateCompany(@RequestHeader("Authorization") String token, @RequestBody Company company) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                Company updatedCompany = companyService.updateCompany(company);
                return ResponseEntity.ok(updatedCompany);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Company> deleteCompany(@RequestHeader("Authorization") String token, @PathVariable int id) {
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                companyService.deleteCompany(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
