package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "spot")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int pricePerHour;

    private  boolean occupied;

    @Enumerated(value = EnumType.STRING)
    private SpotType spotType;

    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    List<Reservation> reservationList;

    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHours) {
        this.pricePerHour = pricePerHours;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }
}
