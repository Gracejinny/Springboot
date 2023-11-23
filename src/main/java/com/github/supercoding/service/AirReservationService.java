package com.github.supercoding.service;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import com.github.supercoding.respository.airlineTicket.AirlineTicketRepository;
import com.github.supercoding.respository.users.UserEntity;
import com.github.supercoding.respository.users.UserRepository;
import com.github.supercoding.web.dto.airline.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirReservationService {

    private UserRepository userRepository;
    private AirlineTicketRepository airlineTicketRepository;

    public AirReservationService(UserRepository userRepository, AirlineTicketRepository airlineTicketRepository) {
        this.userRepository = userRepository;
        this.airlineTicketRepository = airlineTicketRepository;
    }

    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        // 필요한 Repository : UserRepository, airlineTicketRepository
        // 유저 userId로 가져와서 선호하는 여행지 도출
        UserEntity userEntity = userRepository.findUserById(userId);
        String likePlace = userEntity.getLikeTravelPlace();

        // 선호하는 여행지와 ticketType으로 AirlineTicket table 질의해서 AirlineTicket
        List<AirlineTicket> airlineTickets
                = airlineTicketRepository.findAllAirelineTicketsWithPlaceAndTicketType(likePlace, ticketType);
        // 둘의 정보를 조합해서 Ticket DTO를 만든다.
        List<Ticket> tickets = airlineTickets.stream().map(Ticket::new).collect(Collectors.toList());
        return tickets;
    }
}
