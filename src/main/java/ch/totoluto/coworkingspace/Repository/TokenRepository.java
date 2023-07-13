package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}