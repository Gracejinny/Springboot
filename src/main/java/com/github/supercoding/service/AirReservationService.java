package com.github.supercoding.service;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import com.github.supercoding.respository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.supercoding.respository.airlineTicket.AirlineTicketJpaRepository;
import com.github.supercoding.respository.airlineTicket.AirlineTicketRepository;
import com.github.supercoding.respository.flight.Flight;
import com.github.supercoding.respository.passenger.Passenger;
import com.github.supercoding.respository.passenger.PassengerJpaRepository;
import com.github.supercoding.respository.passenger.PassengerRepository;
import com.github.supercoding.respository.reservations.FlightPriceAndCharge;
import com.github.supercoding.respository.reservations.Reservation;
import com.github.supercoding.respository.reservations.ReservationJpaRepository;
import com.github.supercoding.respository.reservations.ReservationRepository;
import com.github.supercoding.respository.users.UserEntity;
import com.github.supercoding.respository.users.UserJpaRepository;
import com.github.supercoding.respository.users.UserRepository;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirReservationService {

    private final UserJpaRepository userJpaRepository;
    private final AirlineTicketJpaRepository airlineTicketJpaRepository;
    private final PassengerJpaRepository passengerJpaRepository;
    private final ReservationJpaRepository reservationJpaRepository;

    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        // 필요한 Repository : UserRepository, airlineTicketRepository
        // 유저 userId로 가져와서 선호하는 여행지 도출

        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도", "왕복"));

        if ( !ticketTypeSet.contains(ticketType) )
            throw new InvalidValueException("해당 TicketType " + ticketType + "은 지원하지 않습니다.");

        UserEntity userEntity = userJpaRepository.findById(userId).orElseThrow(() -> new NotFoundException("해당 ID: " + userId +" 유저를 찾을 수 없습니다."));
        String likePlace = userEntity.getLikeTravelPlace();

        // 선호하는 여행지와 ticketType으로 AirlineTicket table 질의해서 AirlineTicket
        List<AirlineTicket> airlineTickets
                = airlineTicketJpaRepository.findAirlineTicketsByArrivalLocAndTicketType(likePlace, ticketType);

        if(airlineTickets.isEmpty())
            throw new NotFoundException("해당 likeplace : " + likePlace + "와 TicketType : " + ticketType + "에 해당하는 항공편 찾을 수 없습니다.");


        // 둘의 정보를 조합해서 Ticket DTO를 만든다.
        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;
    }

    @Transactional(transactionManager = "tmJpa2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        // Repository : Reservation, Join (flight / airline_ticket), Passenger

        //0. userId, airlineTicketId
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        AirlineTicket airlineTicket  = airlineTicketJpaRepository.findById(airlineTicketId)
                .orElseThrow(()-> new NotFoundException(("airlineTicket 찾을 수 없습니다.")));

        //1. Passenger
        Passenger passenger = passengerJpaRepository.findPassengerByUserUserId(userId)
                                                    .orElseThrow( () -> new NotFoundException("요청하신 UserId " + userId + "에 해당하는 passenger를 찾을 수 없습니다."));

        // 2. price 등의 정보 불러오기
        List<Flight> flightList = airlineTicket.getFlightList();

        if(flightList.isEmpty())
            throw new NotFoundException("AirlineTicket Id " + airlineTicketId + "에 해당하는 항공편과 항공권 찾을 수 없습니다.");

        Boolean isSuccess = false;

        //3. Reservation 생성
        Reservation reservation = new Reservation(passenger, airlineTicket);
        try{
            reservationJpaRepository.save(reservation);
            isSuccess = true;
        } catch (RuntimeException e){
            throw new NotAcceptException("Reservation이 등록되는 과정이 거부되었습니다.");
        }

        // ReservationResult DTO 만들기
        List<Integer> prices = flightList.stream().map(Flight::getFlightPrice).map(Double::intValue).collect(Collectors.toList());
        List<Integer> charges = flightList.stream().map(Flight::getCharge).map(Double::intValue).collect(Collectors.toList());
        Integer tax = airlineTicket.getTax().intValue();
        Integer totalPrice = airlineTicket.getTotalPrice().intValue();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }

    public Double findUserFlightSumPrice(Integer userId) {
        // 1. flight_price, Charge 구하기
        List<FlightPriceAndCharge> flightPriceAndCharges = reservationJpaRepository.findFlightPriceAndCharge(userId);

        // 2. 모든 flight_price, Charge의 각각 합을 구하기
        Double flightSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getFlightPrice).sum();
        Double chargeSum = flightPriceAndCharges.stream().mapToDouble(FlightPriceAndCharge::getCharge).sum();

        //3. 두개의 합을 다시 더하고 Return
        return flightSum + chargeSum;
    }
}
