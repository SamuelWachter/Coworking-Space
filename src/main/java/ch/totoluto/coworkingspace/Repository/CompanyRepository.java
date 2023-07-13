package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}