package com.github.supercoding.web.controller;

import com.github.supercoding.config.security.CustomAuthenticationEntryPoint;
import com.github.supercoding.respository.reservations.Reservation;
import com.github.supercoding.respository.userDetails.CustomUserDetails;
import com.github.supercoding.service.AirReservationService;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import com.github.supercoding.web.dto.airline.TicketResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/air-reservation")
@RequiredArgsConstructor
@Slf4j
public class AirReservationController {

    private final AirReservationService airReservationService;

    @ApiOperation("선호하는 Ticket 찾기")
    @GetMapping("/tickets")
    public TicketResponse findAirlineTickets(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @ApiParam(name = "airline-ticket-type", value = "항공권 타입", example = "왕복|편도") @RequestParam("airline-ticket-type") String ticketType){

            Integer userId = customUserDetails.getUserId();
            List<Ticket> tickets = airReservationService.findUserFavoritePlaceTickets(userId, ticketType);
            return new TicketResponse(tickets);
    }

    @ApiOperation("User와 Ticekt ID로 예약")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservations")
    public ReservationResult makeReservation(@RequestBody ReservationRequest reservationRequest){
             return airReservationService.makeReservation(reservationRequest);
    }

    @ApiOperation("UserId의 예약한 항공편과 수수료 총합")
    @GetMapping("/users-sum-price")
    public Double findUserFlightSumPrice(@ApiParam(name = "user-Id", value = "유저 ID", example = "1")
                                         @RequestParam("user-id") Integer userId){
        Double sum = airReservationService.findUserFlightSumPrice(userId);
        return sum;
    }
}
