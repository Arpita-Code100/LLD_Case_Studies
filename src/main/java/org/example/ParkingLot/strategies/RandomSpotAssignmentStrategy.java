package org.example.ParkingLot.strategies;

import org.example.ParkingLot.exceptions.ParkingLotNotFoundException;
import org.example.ParkingLot.models.*;
import org.example.ParkingLot.repositories.ParkingLotRepository;

import java.util.List;
import java.util.Optional;

public class RandomSpotAssignmentStrategy implements SpotAssignmentStrategy{
    private ParkingLotRepository parkingLotRepository;
    public RandomSpotAssignmentStrategy(ParkingLotRepository parkingLotRepository){
        this.parkingLotRepository = parkingLotRepository;
    }
    @Override
    public ParkingSpot assignSpot(Gate gate, VehicleType vehicleType) {
        //findParkingLotByGateId
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository.findByGateId(gate.getId());
        ParkingLot parkingLot;
        if(optionalParkingLot.isPresent()){
            parkingLot = optionalParkingLot.get();
        }else{
            try {
                throw new ParkingLotNotFoundException("No Parking lot available due to invalid gate id.");
            } catch (ParkingLotNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        for(ParkingFloor parkingFloor : parkingLot.getParkingFloors()){
            List<ParkingSpot> parkingSpots = parkingFloor.getParkingSpots();
            for(ParkingSpot parkingSpot : parkingSpots){
                if(parkingSpot.getParkingSpotStatus().equals(ParkingSpotStatus.EMPTY) &&
                parkingSpot.getVehicleTypes().contains(vehicleType)){
                    return parkingSpot;
                }
            }
        }


        return null;
    }
}
