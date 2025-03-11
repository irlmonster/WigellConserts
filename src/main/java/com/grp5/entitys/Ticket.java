package com.grp5.entitys;

public class Ticket {
    private String concertName;
    private double ticketPrice;

    public Ticket() {
    }

    public Ticket(String concertName, double ticketPrice) {
        this.concertName = concertName;
        this.ticketPrice = ticketPrice;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "concertName='" + concertName + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
