package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;

    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
      ParkingLot parkingLot=new ParkingLot(name,address);
      parkingLot.setName(name);
      parkingLot.setAddress(address);
      parkingLotRepository1.save(parkingLot);
      return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
      Spot spot=new Spot();
      ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
      if(parkingLot!=null) {
          spot.setParkingLot(parkingLot);
          if (numberOfWheels <= 2) {
              spot.setSpotType(SpotType.TWO_WHEELER);
          } else if (numberOfWheels > 2 && numberOfWheels <= 4) {
              spot.setSpotType(SpotType.FOUR_WHEELER);
          } else {
              spot.setSpotType(SpotType.OTHERS);
          }

          spot.setParkingLot(parkingLot);
          parkingLot.getSpotList().add(spot);
          spot.setPricePerHour(pricePerHour);
          spotRepository1.save(spot);
          parkingLotRepository1.save(parkingLot);
          return spot;
      }
      else {
          throw new RuntimeException("not parking found");
      }
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot=spotRepository1.findById(spotId).get();
        spotRepository1.delete(spot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList=parkingLot.getSpotList();
        Spot spot=new Spot();
        for (Spot s:spotList){
            if(s.getId()==spotId){
                spot=s;
                spot.setPricePerHour(pricePerHour);
            }
        }
        parkingLot.setSpotList(spotList);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
       ParkingLot parkingLot= parkingLotRepository1.findById(parkingLotId).get();
       parkingLotRepository1.delete(parkingLot);
    }
}
