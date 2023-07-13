package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Halfday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HalfDayRepository extends JpaRepository<Halfday, Integer> {
}