package com.github.supercoding.web.controller;

import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
public class AirReservationController {

    private AirReservationService airReservationService;

    public AirReservationController(AirReservationService airReservationService) {
        this.airReservationService = airReservationService;
    }

    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(@RequestParam("user-Id") Integer userId,
                                             @RequestParam("airline-ticket-type") String ticketType){
        List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
        return new TicketResponse(tickets);
    }

    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest){
        return airReservationService.makeReservation(reservationRequest);
    }
}
