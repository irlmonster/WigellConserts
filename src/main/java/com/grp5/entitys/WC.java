package com.grp5.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;

import java.util.List;

@Entity
public class WC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private List<Concerts> conserts;
    private List<Customer> customers;

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

    public List<Concerts> getConserts() {
        return conserts;
    }

    public void setConserts(List<Concerts> conserts) {
        this.conserts = conserts;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "WC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", conserts=" + conserts +
                ", customers=" + customers +
                '}';
    }
}
