package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.Company;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.CompanyRepository;
import ch.totoluto.coworkingspace.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;


    @Autowired
    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository){
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company createCompany(Company company){
        User user = userRepository.getById(company.getUserFkOwner().getId());
        company.setUserFkOwner(user);
        return companyRepository.save(company);
    }

    public void deleteCompany(int id){
        companyRepository.deleteById(id);
    }

    public Company getCompanyById(int id){
        return companyRepository.findById(id).get();
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company updateCompany(Company company){
        User user = userRepository.getById(company.getUserFkOwner().getId());
        Company companyToUpdate = companyRepository.findById(company.getId()).get();
        companyToUpdate.setName(company.getName());
        companyToUpdate.setUserFkOwner(user);
        return companyRepository.save(companyToUpdate);
    }
}
