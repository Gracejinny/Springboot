package com.github.supercoding.respository.airlineTicket;

import java.util.List;

public interface AirlineTicketRepository {
    List<AirlineTicket> findAllAirelineTicketsWithPlaceAndTicketType(String likePlace, String ticketType);

    List<AirlineTicketAndFlightInfo> findAllAirlineTicketAndFlightInfo(Integer airlineTicketId);
}
