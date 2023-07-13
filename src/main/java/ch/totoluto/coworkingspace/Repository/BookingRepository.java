package ch.totoluto.coworkingspace.Repository;

import ch.totoluto.coworkingspace.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}