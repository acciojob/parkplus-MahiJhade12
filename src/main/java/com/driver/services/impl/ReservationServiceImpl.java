package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        Reservation reservation = new Reservation();
        List<Reservation> reservationList=reservationRepository3.findAll();
        User user = userRepository3.findById(userId).get();
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        if (parkingLot == null || user == null) {
            throw new Exception("reservation can not made");
        }
        int reservation_price = 0;
        List<Spot> spotList = parkingLot.getSpotList();
        Collections.sort(spotList, (s1, s2) -> s1.getPricePerHour() - s2.getPricePerHour());
        Spot spot1 = new Spot();
        for (Spot spot : spotList) {
            if (spot.getOccupied() == false) {
                if (spot.getSpotType() == SpotType.TWO_WHEELER && numberOfWheels == 2) {
                    spot1 = spot;
                    spot1.setSpotType(SpotType.TWO_WHEELER);
                    spot1.setOccupied(true);
                    break;
                } else if (spot.getSpotType() == SpotType.FOUR_WHEELER && numberOfWheels == 4) {
                    spot1 = spot;
                    spot1.setSpotType(SpotType.FOUR_WHEELER);
                    spot1.setOccupied(true);
                    break;
                } else {
                    spot1 = spot;
                    spot1.setSpotType(SpotType.OTHERS);
                    spot1.setOccupied(true);
                    break;
                }
            } else {
                throw new Exception("reservation can not made");
            }
        }
        int reservationPayment=spot1.getPricePerHour()*timeInHours;
           reservation.setNumberOfHours(timeInHours);
           reservation.setSpot(spot1);
           reservation.setUser(user);
           reservationRepository3.save(reservation);
           spot1.getReservationList().add(reservation);
           spotRepository3.save(spot1);
           return reservation;




    }
}
