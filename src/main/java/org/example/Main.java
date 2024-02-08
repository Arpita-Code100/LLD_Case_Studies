package org.example;

import org.example.ParkingLot.controllers.TicketController;
import org.example.ParkingLot.dtos.IssueTicketRequestDTO;
import org.example.ParkingLot.dtos.IssueTicketResponseDTO;
import org.example.ParkingLot.dtos.ResponseStatus;
import org.example.ParkingLot.models.*;
import org.example.ParkingLot.repositories.GateRepository;
import org.example.ParkingLot.repositories.ParkingLotRepository;
import org.example.ParkingLot.repositories.TicketRepository;
import org.example.ParkingLot.repositories.VehicleRepository;
import org.example.ParkingLot.services.TicketService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        GateRepository gateRepository = new GateRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        TicketRepository ticketRepository = new TicketRepository();

        TicketService ticketService = new TicketService(gateRepository,vehicleRepository,parkingLotRepository,ticketRepository);
        TicketController ticketController = new TicketController(ticketService);

        Gate gate = new Gate();
        Operator operator = new Operator();
        operator.setEmpId(1234);
        operator.setName("Arpita Pattanayak");
        gate.setGateNumber(1);
        gate.setId(123L);
        gate.setOperator(operator);
        gate.setGateStatus(GateStatus.OPEN);
        Map<Long, Gate> gates = new HashMap<>();
        gates.put(gate.getId(), gate);
        gateRepository.setGates(gates);

        ParkingLot parkingLot = new ParkingLot();
        List<Gate> gateList = new ArrayList<>();
        gateList.add(gate);
        List<VehicleType> vehicleTypeList = new ArrayList<>();
        vehicleTypeList.add(VehicleType.SUV);

        List<ParkingFloor> floorList = new ArrayList<>();
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setParkingSpotStatus(ParkingSpotStatus.EMPTY);
        parkingSpot.setVehicleTypes(vehicleTypeList);
        List<ParkingSpot> parkingSpotList = new ArrayList<>();
        parkingSpotList.add(parkingSpot);

        ParkingFloor parkingFloor = new ParkingFloor();
        parkingFloor.setFloorNumber(1);
        parkingFloor.setParkingSpots(parkingSpotList);
        floorList.add(parkingFloor);

        parkingLot.setGates(gateList);
        parkingLot.setVehicleTypes(vehicleTypeList);
        parkingLot.setParkingFloors(floorList);


        parkingLot.setGates(gateList);
        parkingLot.setVehicleTypes(vehicleTypeList);
        Map<Long, ParkingLot> parkingLotMap = new HashMap<>();
        parkingLotMap.put(gate.getId(), parkingLot);
        parkingLotRepository.setParkingLotMap(parkingLotMap);

        IssueTicketRequestDTO issueTicketRequestDTO = new IssueTicketRequestDTO();
        issueTicketRequestDTO.setGateId(123L);
        issueTicketRequestDTO.setVehicleNumber("HR16X1234");
        issueTicketRequestDTO.setVehicleOwnerName("Arpita Pattanayak");
        issueTicketRequestDTO.setVehicleType(VehicleType.SUV);

        IssueTicketResponseDTO issueTicketResponseDTO = ticketController.issueTicket(issueTicketRequestDTO);

        Ticket ticket = null;
        if(issueTicketResponseDTO.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            ticket = issueTicketResponseDTO.getTicket();
            System.out.println("------Ticket Details------");
            System.out.println("Ticket Number: "+ticket.getTicketNumber());
            System.out.println("Parking Spot: "+ticket.getParkingSpot().getSpotNumber());
            System.out.println("Vehicle Number: "+ticket.getVehicle().getVehicleNumber());
            System.out.println("Vehicle Type: "+ticket.getVehicle().getVehicleType());
            System.out.println("Ticket generated by: "+ticket.getGeneratedBy().getName());
            System.out.println("Entry Time: "+ticket.getEntryTime());
            System.out.println("Ticket generated at Gate number: "+ticket.getGeneratedAt().getGateNumber());
        }else{
            System.out.println("Issue Ticket method failed for some reason.");
        }
    }
}