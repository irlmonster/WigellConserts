package com.grp5.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "wigells_concert")
public class WC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false) // Gör så att namnet aldrig kan vara null i databasen
    private String name;

    @ManyToOne
    @JoinColumn(name = "concerts", referencedColumnName = "id", nullable = false)
    private Concerts concert;

    @ManyToOne
    @JoinColumn(name = "customers", referencedColumnName = "id", nullable = false)
    private Customer customer;

    // Getters och Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Concerts getConcert() {
        return concert;
    }

    public void setConcert(Concerts concert) {
        this.concert = concert;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "WC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", concert=" + concert +
                ", customer=" + customer +
                '}';
    }
}
