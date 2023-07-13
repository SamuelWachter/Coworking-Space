package ch.totoluto.coworkingspace.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk_owner", nullable = false)
    private User userFkOwner;

    @OneToMany(mappedBy = "companyFk")
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "companyFk")
    private Set<Booking> bookings = new LinkedHashSet<>();

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getUserFkOwner() {
        return userFkOwner;
    }

    public void setUserFkOwner(User userFkOwner) {
        this.userFkOwner = userFkOwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}