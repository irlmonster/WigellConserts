package com.grp5.entitys;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class WC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "concerts")
    private Concerts concerts;

    @ManyToOne
    @JoinColumn(name = "customers")
    private Customer customer;

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

   // public List<Concerts> getConserts() {
    //   return concerts;
    //}

    public void setConcerts(Concerts concerts) {
        this.concerts = concerts;
    }

    //public List<Customer> getCustomers() {
      //  return customer;
    //}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "WC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", conserts=" + concerts +
                ", customers=" + customer +
                '}';
    }
}
