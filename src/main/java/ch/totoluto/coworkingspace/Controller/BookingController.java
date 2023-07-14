package ch.totoluto.coworkingspace.Controller;

import ch.totoluto.coworkingspace.Entity.Booking;
import ch.totoluto.coworkingspace.Entity.Company;
import ch.totoluto.coworkingspace.Entity.User;
import ch.totoluto.coworkingspace.Service.BookingService;
import ch.totoluto.coworkingspace.Service.CompanyService;
import ch.totoluto.coworkingspace.Service.TokenService;
import ch.totoluto.coworkingspace.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final TokenService tokenService;

    public BookingController(BookingService bookingService, UserService userService, TokenService tokenService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Booking>> getAllBookings(@RequestHeader("Authorization") String token){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                List<Booking> bookings = bookingService.getAllBookings();
                return ResponseEntity.ok(bookings);
            } else {
                //Allow User to access only his own data
                List<Booking> bookings = bookingService.getAllBookingsByUser(user);
                return ResponseEntity.ok(bookings);
            }
        }else {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@RequestHeader("Authorization") String token, @PathVariable int id){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                Booking bookingById = bookingService.getBookingById(id);
                return ResponseEntity.ok(bookingById);
            } else {
                //Allow User to access only his own data
                Booking bookingById = bookingService.getBookingById(id);
                if(bookingById.getUserFk().getId() == user.getId()){
                    return ResponseEntity.ok(bookingById);
                } else {
                    return ResponseEntity.status(403).build();
                }
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Booking> createBooking(@RequestHeader("Authorization") String token, @RequestBody Booking booking){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                User bookingUser = userService.getUserById(booking.getUserFk().getId());
                Booking bookingById = bookingService.createBooking(booking, bookingUser);
                return ResponseEntity.ok(bookingById);
            } else {
                //Allow User to create only his own data
                Booking bookingById = bookingService.createBooking(booking, user);
                return ResponseEntity.ok(bookingById);
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Booking> updateBooking(@RequestHeader("Authorization") String token, @RequestBody Booking booking){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                System.out.println(booking.getUserFk());
                Booking updatedBooking = bookingService.updateBooking(booking, userService.getUserById(booking.getUserFk().getId()));
                return ResponseEntity.ok(updatedBooking);
            } else {
                //Allow User to update only his own data
                Booking updatedBooking = bookingService.updateBooking(booking, user);
                return ResponseEntity.ok(updatedBooking);
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<Booking> acceptBooking(@RequestHeader("Authorization") String token, @PathVariable int id){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                Booking bookingById = bookingService.acceptBooking(bookingService.getBookingById(id), user);
                return ResponseEntity.ok(bookingById);
            } else {
                return ResponseEntity.status(403).build();
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@RequestHeader("Authorization") String token, @PathVariable int id){
        User user = userService.getUserById(tokenService.getUserIdByToken(token));
        if(tokenService.isTokenNotExpired(token)){
            if(userService.isUserAdmin(user)) {
                //Allow Admin to access all data
                bookingService.deleteBooking(id);
                return ResponseEntity.ok("Booking deleted");
            } else {
                //Allow User to delete only his own data
                Booking bookingById = bookingService.getBookingById(id);
                if(bookingById.getUserFk().getId() == user.getId()){
                    bookingService.deleteBooking(id);
                    return ResponseEntity.ok("Booking deleted");
                } else {
                    return ResponseEntity.status(403).build();
                }
            }
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
