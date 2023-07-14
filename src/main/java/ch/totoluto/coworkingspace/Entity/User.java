package ch.totoluto.coworkingspace.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "prename", nullable = false, length = 100)
    private String prename;

    @Column(name = "surname", nullable = false, length = 100)
    private String surname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_fk")
    @JsonIgnoreProperties({"users", "bookings"})
    private Company companyFk;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_fk")
    @JsonIgnoreProperties("users")
    private Role roleFk;

    @OneToMany(mappedBy = "userFk")
    @JsonIgnore
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "userFkOwner")
    @JsonIgnore
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Role getRoleFk() {
        return roleFk;
    }

    public void setRoleFk(Role roleFk) {
        this.roleFk = roleFk;
    }

    public Company getCompanyFk() {
        return companyFk;
    }

    public void setCompanyFk(Company companyFk) {
        this.companyFk = companyFk;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}