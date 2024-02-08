package org.example.ParkingLot.factories;

import org.example.ParkingLot.models.SpotAssignmentStrategyType;
import org.example.ParkingLot.repositories.ParkingLotRepository;
import org.example.ParkingLot.strategies.CheapestSpotAssignmentStrategy;
import org.example.ParkingLot.strategies.RandomSpotAssignmentStrategy;
import org.example.ParkingLot.strategies.SpotAssignmentStrategy;

public class SpotAssignmentStrategyFactory {
    public static SpotAssignmentStrategy getSpotAssignmentStrategy(SpotAssignmentStrategyType spotAssignmentStrategyType,
                                                                   ParkingLotRepository parkingLotRepository){
        if(spotAssignmentStrategyType.equals(SpotAssignmentStrategyType.RANDOM)){
            return new RandomSpotAssignmentStrategy(parkingLotRepository);
        }else if(spotAssignmentStrategyType.equals((SpotAssignmentStrategyType.CHEAPEST))){
            return new CheapestSpotAssignmentStrategy();
        }
        return null;
    }
}
