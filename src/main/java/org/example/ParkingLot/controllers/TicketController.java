package org.example.ParkingLot.controllers;

import org.example.ParkingLot.dtos.IssueTicketRequestDTO;
import org.example.ParkingLot.dtos.IssueTicketResponseDTO;
import org.example.ParkingLot.dtos.ResponseStatus;
import org.example.ParkingLot.models.Gate;
import org.example.ParkingLot.models.Ticket;
import org.example.ParkingLot.models.VehicleType;
import org.example.ParkingLot.services.TicketService;

public class TicketController {
   // private TicketService ticketService = new TicketService()(); -> Tight Coupling
    private TicketService ticketService;
    //Dependency Injection principle - instead of creating object manually, let client pass the ticketService object
    public  TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

// public Ticket issueTicket(Gate gate, VehicleType vehicleType, String vehicleNumber, String ownerName){}
//Instead of doing above use DTOs to not expose models directly to the clients
//Do like below
    public IssueTicketResponseDTO issueTicket(IssueTicketRequestDTO requestDTO){
        IssueTicketResponseDTO responseDTO = new IssueTicketResponseDTO();
        //calls the ticket service
        try {
            Ticket ticket = ticketService.issueTicket(requestDTO.getGateId(), requestDTO.getVehicleOwnerName(),
                    requestDTO.getVehicleType(), requestDTO.getVehicleNumber());

            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            responseDTO.setTicket(ticket);
        }catch (Exception e){
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }
}
