package com.github.supercoding.respository.flight;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = true)
    private AirlineTicket airlineTicket;

    @Column(name = "departure_at")
    private LocalDateTime departAt;

    @Column(name = "arrival_at")
    private LocalDateTime arrivalAt;

    @Column(name = "departure_loc", length = 50)
    private String departureLocation;

    @Column(name = "arrival_loc", length = 50)
    private String arrivalLocation;

    @Column(name = "flight_price")
    private Double flightPrice;

    @Column(name = "charge")
    private Double charge;
}