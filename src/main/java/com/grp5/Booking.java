package com.grp5;

import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;
import com.grp5.entitys.Ticket;

public class Booking {
    private int id;
    private int numberOfTickets;
    private Customer customer;
    private Concerts concert;
    private Ticket ticket;

    public Booking() {
    }

    public Booking(int id, int numberOfTickets, Customer customer, Concerts concert, Ticket ticket) {
        this.id = id;
        this.numberOfTickets = numberOfTickets;
        this.customer = customer;
        this.concert = concert;
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Concerts getConcert() {
        return concert;
    }

    public void setConcert(Concerts concert) {
        this.concert = concert;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", numberOfTickets=" + numberOfTickets +
                ", customer=" + customer +
                ", concert=" + concert +
                ", ticket=" + ticket +
                '}';
    }


}
