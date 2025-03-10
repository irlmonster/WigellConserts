package com.grp5.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "arena")
public class Arena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", nullable = false)
    private Addresses address;

    @Column(name = "indoor", nullable = false)
    private boolean indoor;

    public Arena() {
    }

    public Arena(String name, Addresses address, boolean indoor) {
        this.name = name;
        this.address = address;
        this.indoor = indoor;
    }

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

    public Addresses getAddress() {
        return address;
    }

    public void setAddress(Addresses address) {
        this.address = address;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    @Override
    public String toString() {
        return "Arena{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", indoor=" + indoor +
                '}';
    }
}
