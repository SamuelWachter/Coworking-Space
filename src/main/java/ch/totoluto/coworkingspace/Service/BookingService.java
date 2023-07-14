package ch.totoluto.coworkingspace.Service;

import ch.totoluto.coworkingspace.Entity.Booking;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Repository.BookingRepository;
import ch.totoluto.coworkingspace.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private RoleRepository roleRepo;

    @Autowired
    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking, User user){
        booking.setUserFk(user);
        booking.setAccepted(false);
        if(user.getCompany() != booking.getCompanyFk()){
            user.setCompanyFk(null);
        }
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking booking, User user){
        if(user.getRoleFk().getId() == 1){
            return bookingRepository.save(booking);
        }else{
            if(getBookingById(booking.getId()).getUserFk() == user){
                if(user.getCompany() != booking.getCompanyFk()){
                    user.setCompanyFk(null);
                }
                booking.setAccepted(false);
                return bookingRepository.save(booking);
            }
            return null;
        }
    }

    public Booking acceptBooking(Booking booking, User user){
        if(user.getRoleFk().getId() == 1){
            booking.setAccepted(true);
            bookingRepository.save(booking);
            return booking;
        }else {
            return null;
        }
    }

    public Booking getBookingById(int id){
        return bookingRepository.findById(id).get();
    }

    public void deleteBooking(int id){
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }


    public List<Booking> getAllBookingsByUser(User user){
        return bookingRepository.findAllByUserFk(user);
    }
}
