package com.grp5;

import com.grp5.entitys.Concerts;
import com.grp5.entitys.Customer;

import java.util.ArrayList;
import java.util.List;


public class Booking {
    private int id;
    private int numberOfTickets;
    private Customer customer;
    private Concerts concert;
    // static för att vi ska kunna nå den överallt
    private static List<Booking> bookings = new ArrayList<>();

    public Booking() {
    }

    public Booking(int numberOfTickets, Customer customer, Concerts concert) {
        this.numberOfTickets = numberOfTickets;
        this.customer = customer;
        this.concert = concert;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
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

// static för att vi ska kunna nå den överallt
    public static List<Booking> getBookingsForCustomer(Customer customer) {
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCustomer().equals(customer)) {
                customerBookings.add(booking);
            }
        }
        return customerBookings;
    }



    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", numberOfTickets=" + numberOfTickets +
                ", customer=" + customer +
                ", concert=" + concert +
                '}';
    }


}
