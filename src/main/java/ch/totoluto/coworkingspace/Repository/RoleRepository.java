package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}