package org.example.ParkingLot.services;

import org.example.ParkingLot.exceptions.GateNotFoundException;
import org.example.ParkingLot.factories.SpotAssignmentStrategyFactory;
import org.example.ParkingLot.models.*;
import org.example.ParkingLot.repositories.GateRepository;
import org.example.ParkingLot.repositories.ParkingLotRepository;
import org.example.ParkingLot.repositories.TicketRepository;
import org.example.ParkingLot.repositories.VehicleRepository;
import org.example.ParkingLot.strategies.SpotAssignmentStrategy;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class TicketService {
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;

    public TicketService(GateRepository gateRepository, VehicleRepository vehicleRepository,
                         ParkingLotRepository parkingLotRepository, TicketRepository ticketRepository){
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketRepository = ticketRepository;
    }
    public Ticket issueTicket(Long gateId, String ownerName, VehicleType vehicleType, String vehicleNumber) throws GateNotFoundException {
        //generate Ticket logic
        //1. Fetch the Gate object from database
        //2. Fetch the Vehicle details from the database
        //3. Validate the user details
        //4. Finally book the ticket
        Operator operator = null;
        Gate gate = null;
        Optional<Gate> optionalGate = gateRepository.findGateById(gateId);
        if(optionalGate.isPresent()) {
            gate = optionalGate.get();
            operator = optionalGate.get().getOperator();
        }else{
            throw new GateNotFoundException("Invalid Gate Id");
        }

        Vehicle vehicle = null;
        Optional<Vehicle> optionalVehicle = vehicleRepository.findVehicleByNumber(vehicleNumber);
        if(optionalVehicle.isPresent()){
            vehicle = optionalVehicle.get();
        }else{
            vehicle = new Vehicle();
            vehicle.setVehicleNumber(vehicleNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle.setOwnerName(ownerName);
            vehicle = vehicleRepository.save(vehicle);
        }
//        Optional<ParkingLot> parkingLot = parkingLotRepository.findByGateId(gate.getId());
        SpotAssignmentStrategy spotAssignmentStrategy = SpotAssignmentStrategyFactory
                .getSpotAssignmentStrategy(SpotAssignmentStrategyType.RANDOM, parkingLotRepository);

        ParkingSpot parkingSpot = spotAssignmentStrategy.assignSpot(gate, vehicleType);

        Ticket ticket = new Ticket();
        ticket.setTicketNumber(String.valueOf(new Random().nextInt()));
        ticket.setEntryTime(new Date());
        ticket.setGeneratedAt(gate);
        ticket.setVehicle(vehicle);
        ticket.setParkingSpot(parkingSpot);
        ticket.setGeneratedBy(operator);

        ticketRepository.save(ticket);

        return ticket;
    }
}
