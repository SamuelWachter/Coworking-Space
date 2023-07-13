package ch.totoluto.coworkingspace.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk")
    private User userFk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_fk")
    private Company companyFk;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "halfday_fk")
    private Halfday halfdayFk;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted = false;

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Halfday getHalfdayFk() {
        return halfdayFk;
    }

    public void setHalfdayFk(Halfday halfdayFk) {
        this.halfdayFk = halfdayFk;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Company getCompanyFk() {
        return companyFk;
    }

    public void setCompanyFk(Company companyFk) {
        this.companyFk = companyFk;
    }

    public User getUserFk() {
        return userFk;
    }

    public void setUserFk(User userFk) {
        this.userFk = userFk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}