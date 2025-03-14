package com.grp5.entitys;


import jakarta.persistence.*;

@Entity
public class Concerts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "artist_name", nullable = false)
    private String artist_name;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "ticket_price", nullable = false)
    private double ticket_price;

    @ManyToOne
    @JoinColumn(name = "arena_id", nullable = false)  // 🔹 Korrekt relation till Arena
    private Arena arena;

    @Column(name = "age_limit", nullable = false)
    private int age_limit;

    public Concerts() {
    }

    public Concerts(String artist_name, String date, double ticket_price, int age_limit, Arena arena) {
        this.artist_name = artist_name;
        this.date = date;
        this.ticket_price = ticket_price;
        this.age_limit = age_limit;
        this.arena = arena;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(double ticket_price) {
        this.ticket_price = ticket_price;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public int getAge_limit() {
        return age_limit;
    }

    public void setAge_limit(int age_limit) {
        this.age_limit = age_limit;
    }

    @Override
    public String toString() {
        return artist_name + " " + ticket_price;
    }
}
