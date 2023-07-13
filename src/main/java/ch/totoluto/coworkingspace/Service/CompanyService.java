package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.Company;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.CompanyRepository;

public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company createCompany(Company company, User user){
        company.setUserFkOwner(user);
        return companyRepository.save(company);
    }

    public void deleteCompany(Company company, User user){
        if(user.getRoleFk().getId() == 1){
            companyRepository.delete(company);
        }
    }

    public Company getCompanyById(int id){
        return companyRepository.findById(id).get();
    }
}
