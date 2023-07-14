package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Booking;
import ch.totoluto.coworkingspace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByEmail(String email);
}