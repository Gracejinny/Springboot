package com.github.supercoding.service;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import com.github.supercoding.respository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.supercoding.respository.airlineTicket.AirlineTicketRepository;
import com.github.supercoding.respository.passenger.Passenger;
import com.github.supercoding.respository.passenger.PassengerRepository;
import com.github.supercoding.respository.reservations.Reservation;
import com.github.supercoding.respository.reservations.ReservationRepository;
import com.github.supercoding.respository.users.UserEntity;
import com.github.supercoding.respository.users.UserRepository;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirReservationService {

    private final UserRepository userRepository;
    private final AirlineTicketRepository airlineTicketRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;

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

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        // Repository : Reservation, Join (flight / airline_ticket), Passenger

        //0. userId, airlineTicketId
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        //1. Passenger
        Passenger passenger = passengerRepository.findPassengerByUserId(userId);
        Integer passengerId = passenger.getPassengerId();

        // 2. price 등의 정보 불러오기
        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfos
                = airlineTicketRepository.findAllAirlineTicketAndFlightInfo(airlineTicketId);

        //3. Reservation 생성
        Reservation reservation = new Reservation(passengerId, airlineTicketId);
        Boolean isSuccess = reservationRepository.saveReservation(reservation);

        // ReservationResult DTO 만들기
        List<Integer> prices = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getPrice).collect(Collectors.toList());
        List<Integer> charges = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getCharge).collect(Collectors.toList());
        Integer tax = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Integer totalPrice = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }
}
